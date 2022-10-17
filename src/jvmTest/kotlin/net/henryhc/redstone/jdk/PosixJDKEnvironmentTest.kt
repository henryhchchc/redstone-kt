package net.henryhc.redstone.jdk

import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.should
import kotlinx.coroutines.runBlocking
import net.henryhc.redstone.okio.FileSystemContext
import net.henryhc.redstone.okio.TemporaryDirectory
import okio.FileSystem
import okio.Path
import kotlin.test.Test
import kotlin.test.assertEquals

internal class PosixJDKEnvironmentTest : FileSystemContext {

    override val fileSystem = FileSystem.SYSTEM

    @Test
    fun testGetJavaExecutable() {
        val jdkEnv = PosixJDK(PosixJDK.CURRENT_JAVA_HOME)
        assert(jdkEnv.javaExecutable.name == "java")
    }

    @Test
    fun testGetJavaCompileExecutable() {
        val jdkEnv = PosixJDK(PosixJDK.CURRENT_JAVA_HOME)
        assert(jdkEnv.javaCompilerExecutable.name == "javac")
    }

    @Test
    fun testCompile(): Unit = runBlocking {
        val jdkEnv = PosixJDK(PosixJDK.CURRENT_JAVA_HOME)
        TemporaryDirectory().use { tempDir ->
            createCompilationSubjects(tempDir)
            val result = jdkEnv.runJavaCompiler(
                tempDir / "src",
                listOf("Example.java", "C2.java").map { tempDir / "src" / "org" / "example" / it },
                tempDir / "output"
            )
            result.shouldBeRight().should {
                assertEquals(0, it.exitValue)
                assert(fileSystem.exists(tempDir / "output" / "org" / "example" / "Example.class"))
                assert(fileSystem.exists(tempDir / "output" / "org" / "example" / "C2.class"))
            }
        }
    }

    context(FileSystemContext) private fun createCompilationSubjects(dir: Path) {
        val srcDir = (dir / "src" / "org" / "example").also { fileSystem.createDirectories(it) }
        (dir / "output").also { fileSystem.createDirectories(it) }
        val src1 = """
            package org.example;
            public class Example { }
        """.trimIndent()
        val src2 = """
            package org.example;
            public class C2 { 
                private Example field1;
            }
        """.trimIndent()
        (srcDir / "Example.java").also { fileSystem.write(it) { writeUtf8(src1) } }
        (srcDir / "C2.java").also { fileSystem.write(it) { writeUtf8(src2) } }
    }
}
