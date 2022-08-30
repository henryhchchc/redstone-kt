package net.henryhc.redstone.okio

import okio.FileSystem

/**
 * A context providing access to file system
 * @see FileSystem
 */
interface FileSystemContext {

    /**
     * The [FileSystem] in the current context
     */
    val fileSystem: FileSystem

}
