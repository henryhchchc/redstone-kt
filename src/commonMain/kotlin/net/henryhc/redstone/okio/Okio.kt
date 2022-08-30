package net.henryhc.redstone.okio

import okio.FileSystem
import okio.Path

/**
 * Create the directory, performs some operations, and deletes it.
 * @param block The operations.
 */
inline fun <reified R> TemporaryDirectory.use(fileSystem: FileSystem, block: (Path) -> R): R {
    var result: R? = null
    var thrown: Throwable? = null

    fileSystem.createDirectories(path)

    try {
        result = block(path)
    } catch (t: Throwable) {
        thrown = t
    }

    try {
        fileSystem.deleteRecursively(path)
    } catch (t: Throwable) {
        if (thrown == null) thrown = t
        else thrown.addSuppressed(t)
    }

    if (thrown != null) throw thrown
    return result!!

}

