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
import okio.FileSystem
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
fun RawOption.okioPath(
    fileSystem: FileSystem,
    mustExist: Boolean = false,
    canBeFile: Boolean = true,
    canBeDir: Boolean = true,
    canBeSymlink: Boolean = true
) = convert({ localization.pathMetavar() }, CompletionCandidates.Path) { str ->
    val fail = { it: String -> fail(it) }
    val name = when {
        canBeFile && !canBeDir -> context.localization.pathTypeFile()
        !canBeFile && canBeDir -> context.localization.pathTypeDirectory()
        else -> context.localization.pathTypeOther()
    }
    with(context.localization) {
        val path = str.toPath()
        fileSystem.metadataOrNull(path).also {
            if (mustExist && !fileSystem.exists(path)) fail(pathDoesNotExist(name, it.toString()))
            if (!canBeFile && it?.isRegularFile == true) fail(pathIsFile(name, it.toString()))
            if (!canBeDir && it?.isDirectory == true) fail(pathIsDirectory(name, it.toString()))
            if (!canBeSymlink && it?.symlinkTarget != null) fail(pathIsSymlink(name, it.toString()))
        }
        path
    }
}


/**
 * Converts the option of a [Path] to a [T] by deserializing its Json content.
 * @param serializer The serializer used for deserialization.
 */
@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T : Any> NullableOption<Path, Path>.deserializeJson(
    fileSystem: FileSystem,
    serializer: KSerializer<T> = Json.Default.serializersModule.serializer()
) = convert { fileSystem.source(it).buffer().use { src -> Json.decodeFromBufferedSource<T>(serializer, src) } }
