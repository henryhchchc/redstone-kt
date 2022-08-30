package net.henryhc.redstone.okio

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import kotlin.random.Random
import kotlin.random.nextULong

/**
 * Wraps a path that represents a temporary directory.
 * @property path The path to that directory.
 */
actual value class TemporaryDirectory actual constructor(actual val path: Path) {
    /**
     * Creates a new [TemporaryDirectory] at [FileSystem.SYSTEM_TEMPORARY_DIRECTORY].
     */
    actual constructor() : this(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / Random.nextULong().toString().toPath())

}
