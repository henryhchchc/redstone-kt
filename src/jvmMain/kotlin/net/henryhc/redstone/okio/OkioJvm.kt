package net.henryhc.redstone.okio

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.okio.decodeFromBufferedSource
import okio.FileSystem
import okio.Path
import okio.buffer

/**
 * Create a scope with access to a [FileSystem].
 * @param fs The [FileSystem] accessible in the scope
 * @param block The code that accesses the provided file system.
 */
inline fun <reified T> withFileSystem(fs: FileSystem, block: context(FileSystemContext) () -> T) =
    block(object : FileSystemContext {
        override val fileSystem = fs
    })

/**
 * Gets if the path exists.
 */
context(FileSystemContext) val Path.exists get() = fileSystem.exists(this)

/**
 * Reads Json from the file and deserializes it into a [T].
 */
context(FileSystemContext) @OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> Path.deserializeJson() = fileSystem.source(this).buffer().use {
    Json.decodeFromBufferedSource<T>(it)
}

/**
 * Serializes the object into Json and writes it to [jsonFile].
 * @param jsonFile The path to the destination Json file.
 */
context(FileSystemContext) inline fun <reified T : Any> T.serializeJson(jsonFile: Path) =
    fileSystem.sink(jsonFile).buffer().use { Json.encodeToJsonElement(this) }
