package net.henryhc.redstone.git

import net.henryhc.redstone.jvm.useIf
import okio.*
import okio.Path.Companion.toPath
import okio.internal.commonToUtf8String
import org.eclipse.jgit.lib.FileMode
import org.eclipse.jgit.lib.ObjectLoader
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevTree
import org.eclipse.jgit.treewalk.TreeWalk

/**
 * A readonly file system implementation for a Git tree.
 */
@Suppress("KDocMissingDocumentation")
class GitTreeFileSystem(
    private val repository: Repository,
    private val tree: RevTree
) : FileSystem() {

    override fun appendingSink(file: Path, mustExist: Boolean): Sink = throw IOException(READ_ONLY_MSG)

    override fun atomicMove(source: Path, target: Path): Unit = throw IOException(READ_ONLY_MSG)

    override fun canonicalize(path: Path) = ROOT.resolve(path).run {
        if (isRoot) this else relativeTo(ROOT)
    }

    override fun createDirectory(dir: Path, mustCreate: Boolean): Unit = throw IOException(READ_ONLY_MSG)

    override fun createSymlink(source: Path, target: Path): Unit = throw IOException(READ_ONLY_MSG)

    override fun delete(path: Path, mustExist: Boolean): Unit = throw IOException(READ_ONLY_MSG)

    override fun list(dir: Path): List<Path> = listOrNull(dir) ?: throw IOException("$dir is not a directory.")

    override fun listOrNull(dir: Path): List<Path>? = treeWalkTo(canonicalize(dir))?.let { tw ->
        if (dir.isRoot) tw else tw.useIf({ it.getFileMode(0) == FileMode.TREE }) { it.descent() }
    }?.use { dw -> buildList { while (dw.next()) add(dir.resolve(dw.pathString)) } }

    override fun metadataOrNull(path: Path): FileMetadata? = treeWalkTo(canonicalize(path))?.use { tw ->
        val fileMode = tw.getFileMode(0)
        val loader = repository.open(tw.getObjectId(0))
        val symLinkTarget = loader.takeIf { fileMode == FileMode.SYMLINK }?.openStream()?.use {
            it.readAllBytes().commonToUtf8String().toPath()
        }
        val isFile = fileMode == FileMode.REGULAR_FILE || fileMode == FileMode.EXECUTABLE_FILE
        FileMetadata(
            isRegularFile = isFile,
            isDirectory = (fileMode.bits and FileMode.TYPE_TREE) != 0,
            size = loader.takeIf { isFile }?.size,
            symlinkTarget = symLinkTarget
        )
    }

    override fun openReadOnly(file: Path): FileHandle = TODO("Not yet implemented")

    override fun openReadWrite(file: Path, mustCreate: Boolean, mustExist: Boolean): FileHandle =
        throw UnsupportedOperationException(READ_ONLY_MSG)

    override fun sink(file: Path, mustCreate: Boolean): Sink = throw IOException(READ_ONLY_MSG)

    override fun source(file: Path): Source = openReader(canonicalize(file))?.openStream()?.source()
        ?: throw IOException("$file not found.")

    private fun treeWalkTo(path: Path): TreeWalk? =
        if (path.isRoot) TreeWalk(repository).apply { addTree(tree) }
        else TreeWalk.forPath(repository, path.toString(), tree)

    private fun openReader(file: Path): ObjectLoader? =
        TreeWalk.forPath(repository, file.toString(), tree)?.use { repository.open(it.getObjectId(0))!! }

    private fun TreeWalk.descent() = TreeWalk(repository).apply { addTree(this@descent.getObjectId(0)) }

    companion object {
        private const val READ_ONLY_MSG = "This file system is readonly."
        private val ROOT = "/".toPath()
    }
}
