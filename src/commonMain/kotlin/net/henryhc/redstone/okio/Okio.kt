package net.henryhc.redstone.okio

import okio.FileSystem
import okio.Path
import okio.buffer
import okio.use

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

/**
 * Copies to another [FileSystem].
 * @param otherFs The target file system.
 * @param source The path in the current file system.
 * @param target The destination path in [otherFs].
 */
fun FileSystem.copyTo(otherFs: FileSystem, source: Path, target: Path) =
    this.source(source).use { bytesIn ->
        otherFs.sink(target).buffer().use { bytesOut ->
            bytesOut.writeAll(bytesIn)
        }
    }


/**
 * Recursive copies the contents from [source] to [destination].
 * @param otherFs The target file system.
 * @param source The source directory.
 * @param destination The destination directory in [otherFs]. It will be created if not exist.
 */
fun FileSystem.copyRecursivelyTo(otherFs: FileSystem, source: Path, destination: Path) {
    require(metadataOrNull(source)?.isDirectory == true) { "$source must be a directory." }
    require(otherFs.metadataOrNull(destination)?.isDirectory != false) { "$destination must be a directory." }

    otherFs.createDirectories(destination)
    this.listRecursively(source).sortedBy { it.segments.size }.forEach {
        val meta = metadata(it)
        when {
            meta.isRegularFile -> this.copyTo(otherFs, it, destination / it.relativeTo(source))
            meta.isDirectory -> otherFs.createDirectory(destination / it.relativeTo(source))
            meta.symlinkTarget != null -> otherFs.createSymlink(it.relativeTo(source), meta.symlinkTarget!!)
        }
    }
}
