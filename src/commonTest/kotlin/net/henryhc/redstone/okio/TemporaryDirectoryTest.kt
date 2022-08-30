package net.henryhc.redstone.okio

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okio.FileSystem
import okio.Path.Companion.toPath
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

internal class TemporaryDirectoryTest : FileSystemContext {

    override val fileSystem: FileSystem = mockk(relaxed = true)

    @Test
    fun shouldDeleteAfterUse() {
        val path = "/aa".toPath()
        val tempDir = TemporaryDirectory(path)
        tempDir.use(fileSystem) { }
        verify { fileSystem.createDirectories(path) }
        verify { fileSystem.deleteRecursively(path) }
        confirmVerified(fileSystem)
    }

    @Test
    fun shouldDeleteWhenException() {
        val path = "/aa".toPath()
        val tempDir = TemporaryDirectory(path)
        assertFailsWith<Exception>("E1") {
            tempDir.use<Unit>(fileSystem) {
                throw Exception("E1")
            }
        }
        verify { fileSystem.createDirectories(path) }
        verify { fileSystem.deleteRecursively(path) }
        confirmVerified(fileSystem)
    }

    @Test
    fun shouldNotExecuteWhenFailToCreate() {
        val path = "/aa".toPath()
        val tempDir = TemporaryDirectory(path)
        every { fileSystem.createDirectories(any()) } throws Exception("E1")
        var executed = false
        assertFailsWith<Exception>("E1") {
            tempDir.use(fileSystem) { executed = true }
        }
        verify { fileSystem.createDirectories(path) }
        confirmVerified(fileSystem)
        assertFalse(executed)
    }

    @Test
    fun shouldSuppressWhenFailToDelete() {
        val path = "/aa".toPath()
        val tempDir = TemporaryDirectory(path)
        every { fileSystem.deleteRecursively(any()) } throws Exception("E1")
        val suppressed = assertFailsWith<Exception>("E2") {
            tempDir.use(fileSystem) { throw Exception("E2") }
        }.suppressedExceptions.single()
        assertEquals("E1", suppressed.message)
        verify { fileSystem.createDirectories(path) }
        verify { fileSystem.deleteRecursively(path) }
        confirmVerified(fileSystem)
    }
}
