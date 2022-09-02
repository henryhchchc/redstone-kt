package net.henryhc.redstone.clikt

import com.github.ajalt.clikt.completion.CompletionCandidates
import com.github.ajalt.clikt.parameters.options.NullableOption
import com.github.ajalt.clikt.parameters.options.RawOption
import com.github.ajalt.clikt.parameters.options.convert
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.okio.decodeFromBufferedSource
import kotlinx.serialization.serializer
import net.henryhc.redstone.okio.FileSystemContext
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer

/**
 * Convert the option to a [Path].
 *
 * @param mustExist If true, fail if the given path does not exist
 * @param canBeFile If false, fail if the given path is a file
 * @param canBeDir If false, fail if the given path is a directory
 * @param canBeSymlink If false, fail if the given path is a symlink
 */
context (FileSystemContext)
fun RawOption.okioPath(
    mustExist: Boolean = false,
    canBeFile: Boolean = true,
    canBeDir: Boolean = true,
    canBeSymlink: Boolean = true
) = convert({ localization.pathMetavar() }, CompletionCandidates.Path) { str ->
    val pathTypeName = when {
        canBeFile && !canBeDir -> context.localization.pathTypeFile()
        !canBeFile && canBeDir -> context.localization.pathTypeDirectory()
        else -> context.localization.pathTypeOther()
    }
    with(context.localization) {
        val path = str.toPath()
        fileSystem.metadataOrNull(path).also {
            if (mustExist && !fileSystem.exists(path)) fail(pathDoesNotExist(pathTypeName, it.toString()))
            if (!canBeFile && it?.isRegularFile == true) fail(pathIsFile(pathTypeName, it.toString()))
            if (!canBeDir && it?.isDirectory == true) fail(pathIsDirectory(pathTypeName, it.toString()))
            if (!canBeSymlink && it?.symlinkTarget != null) fail(pathIsSymlink(pathTypeName, it.toString()))
        }
        path
    }
}

/**
 * Converts the option of a [Path] to a [T] by deserializing its Json content.
 * @param serializer The serializer used for deserialization.
 */
context(FileSystemContext) @OptIn(ExperimentalSerializationApi::class)
inline fun <reified T : Any> NullableOption<Path, Path>.deserializeJson(
    serializer: KSerializer<T> = Json.Default.serializersModule.serializer()
) = convert { fileSystem.source(it).buffer().use { src -> Json.decodeFromBufferedSource(serializer, src) } }
