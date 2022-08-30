package net.henryhc.redstone.jdk

import okio.Path

/**
 * An Java Development Kit environment for running Java process and Java compiler.
 */
interface JDKEnvironment {

    /**
     * The path to JAVA_HOME
     */
    val javaHome: Path

    /**
     * The path to the java executable.
     */
    val javaExecutable: Path

    /**
     * The path to the javac executable.
     */
    val javaCompilerExecutable: Path

    /**
     * Denotes the result of running a process.
     * @property exitValue The exit value of the process.
     * @property stdOut The content printed to stdout by the process.
     * @property stdErr The content printed to stderr by the process.
     */
    data class ExecutionResult(val exitValue: Int = 0, val stdOut: String = "", val stdErr: String = "") {
        override fun toString() = buildString {
            append("Exit Value: ")
            appendLine(exitValue)
            appendLine()
            append("Stdout: ")
            appendLine(stdOut)
            appendLine()
            append("Stderr: ")
            appendLine(stdErr)
        }
    }

    /**
     * Launches a Java compiler.
     * @param sourceDirectory The root directory of the source files.
     * @param sourceFiles The list of source files.
     * @param outputDirectory The directory to place the binaries.
     * @param classPath The class path for compiling the source files.
     * @param sourceVersion The Java language version of the source files.
     * @param enablePreviewFeatures Whether to enable support for preview feature of the Java language level.
     * @param targetVersion The Java version of the generated binaries.
     * @param suppressWarnings Suppress the output of warning of the compiler.
     * @param generateDebuggingInfo Whether to include debug info (e.g., line numbers) in the generated binaries.
     */
    suspend fun runJavaCompiler(
        sourceDirectory: Path,
        sourceFiles: Iterable<Path>,
        outputDirectory: Path,
        classPath: Iterable<Path> = emptyList(),
        sourceVersion: Int = 11,
        enablePreviewFeatures: Boolean = false,
        targetVersion: Int = 11,
        suppressWarnings: Boolean = false,
        generateDebuggingInfo: Boolean = true,
    ): ExecutionResult

    /**
     * Launch a Java process with specifies arguments.
     * @param arguments The arguments
     */
    suspend fun runJavaProcess(arguments: Iterable<String>, workingDir: Path): ExecutionResult
}
