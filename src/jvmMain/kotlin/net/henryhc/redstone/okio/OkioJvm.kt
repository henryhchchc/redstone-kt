package net.henryhc.redstone.okio

import okio.FileSystem

/**
 * Create a scope with access to a [FileSystem].
 * @param fs The [FileSystem] accessible in the scope
 * @param block The code that accesses the provided file system.
 */
inline fun <reified T> withFileSystem(fs: FileSystem, block: context(FileSystemContext) () -> T) =
    block(object : FileSystemContext {
        override val fileSystem = fs
    })
