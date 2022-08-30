package net.henryhc.redstone.okio

import okio.FileSystem
import okio.Path

/**
 * Wraps a path that represents a temporary directory.
 * @property path The path to that directory.
 */
expect value class TemporaryDirectory(val path: Path) {
    /**
     * Creates a new [TemporaryDirectory] at [FileSystem.SYSTEM_TEMPORARY_DIRECTORY].
     */
    constructor()
}

