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

/**
 * Gets the file name extension.
 */
val Path.extension get() = this.name.substringAfterLast('.', "")

/**
 * Recursive copies the contents from [source] to [destination].
 * @param source The source directory.
 * @param destination The destination directory. It will be created if not exist.
 */
fun FileSystem.copyRecursively(source: Path, destination: Path) {
    require(metadataOrNull(source)?.isDirectory == true) { "$source must be a directory." }
    require(metadataOrNull(destination)?.isDirectory != false) { "$destination must be a directory." }

    createDirectories(destination)
    listRecursively(source).sortedBy { it.segments.size }.forEach {
        val meta = metadata(it)
        when {
            meta.isRegularFile -> copy(it, destination / it.relativeTo(source))
            meta.isDirectory -> createDirectory(destination / it.relativeTo(source))
            meta.symlinkTarget != null -> createSymlink(it.relativeTo(source), meta.symlinkTarget!!)
        }
    }
}
