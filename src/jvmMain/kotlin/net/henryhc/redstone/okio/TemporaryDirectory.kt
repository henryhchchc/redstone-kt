package net.henryhc.redstone.okio

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import kotlin.jvm.JvmInline
import kotlin.random.Random
import kotlin.random.nextULong

/**
 * Wraps a path that represents a temporary directory.
 * @property path The path to that directory.
 */
@JvmInline
actual value class TemporaryDirectory actual constructor(actual val path: Path) {

    /**
     * Creates a new [TemporaryDirectory] at [FileSystem.SYSTEM_TEMPORARY_DIRECTORY].
     */
    actual constructor() : this(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / Random.nextULong().toString().toPath())

    /**
     * Create the directory, performs some operations, and deletes it.
     * @param block The operations.
     */
    context(FileSystemContext) inline fun <reified R> use(block: (Path) -> R): R =
        this.use(fileSystem, block)
}

