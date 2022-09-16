package net.henryhc.redstone.jdk

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import okio.Path
import okio.Path.Companion.toPath

/**
 * A JDK environment containing java executable and compiler in POSIX compatible operating systems.
 */
class PosixJDK(override val javaHome: Path) : JDK {

    override val javaExecutable = javaHome / "bin" / "java"

    override val javaCompilerExecutable = javaHome / "bin" / "javac"


    override suspend fun runJavaCompiler(
        sourceDirectory: Path,
        sourceFiles: Iterable<Path>,
        outputDirectory: Path,
        classPath: Iterable<Path>,
        sourceVersion: Int,
        enablePreviewFeatures: Boolean,
        targetVersion: Int,
        suppressWarnings: Boolean,
        generateDebuggingInfo: Boolean,
    ): Either<JDK.ExecutionResult, JDK.ExecutionResult> {
        val args = buildList {
            add(javaCompilerExecutable.toString())
            add("-classpath"); add(classPath.joinToString(CLASS_PATH_SEPARATOR))
            add("-d"); add(outputDirectory.toString())
            add("-sourcepath"); add(sourceDirectory.toString())
            add("-source"); add(sourceVersion.toString())
            add("-target"); add(targetVersion.toString())
            if (enablePreviewFeatures) add("--enable-preview")
            if (generateDebuggingInfo) add("-g")
            if (suppressWarnings) add("-nowarn")
            addAll(sourceFiles.map { it.toString() })
        }
        val compilerProcess = withContext(Dispatchers.IO) { ProcessBuilder().apply { command(args) }.start() }
        return pumpProcessOutput(compilerProcess)
    }

    override suspend fun runJavaProcess(arguments: Iterable<String>, workingDir: Path): Either<JDK.ExecutionResult, JDK.ExecutionResult> {
        val args = buildList {
            add(javaExecutable.toString())
            addAll(arguments)
        }
        val javaProcess = withContext(Dispatchers.IO) {
            ProcessBuilder().apply {
                command(args)
                directory(workingDir.toFile())
            }.start()
        }
        return pumpProcessOutput(javaProcess)
    }

    private suspend fun pumpProcessOutput(process: Process) = coroutineScope {
        // Pull the output from the output streams so that the process will not get stuck.
        val stdOutPump = async(Dispatchers.IO) { process.inputStream.readAllBytes() }
        val stdErrPump = async(Dispatchers.IO) { process.errorStream.readAllBytes() }

        val exitValue = withContext(Dispatchers.IO) { process.waitFor() }
        val result =
            JDK.ExecutionResult(exitValue, stdOutPump.await().decodeToString(), stdErrPump.await().decodeToString())
        if (exitValue == 0) result.right() else result.left()
    }

    companion object {

        /**
         * The JAVA_HOME directory of the running JVM.
         */
        val CURRENT_JAVA_HOME: Path = System.getProperty("java.home").toPath()

        private const val CLASS_PATH_SEPARATOR: String = ":"
    }

}
