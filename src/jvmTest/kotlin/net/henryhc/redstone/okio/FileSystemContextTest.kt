package net.henryhc.redstone.okio

import io.mockk.mockk
import okio.FileSystem
import kotlin.test.Test
import kotlin.test.assertSame

class FileSystemContextTest {

    @Test
    fun testWithFileSystem() {
        val fs = mockk<FileSystem>()
        withFileSystem(fs) {
            assertSame(fs, fileSystem)
        }
    }
}
