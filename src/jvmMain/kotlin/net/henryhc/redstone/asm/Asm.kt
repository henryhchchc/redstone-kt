package net.henryhc.redstone.asm

import okio.BufferedSource
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode

/**
 * Reads the stream and parses its content as a [ClassNode].
 * @param parsingOptions The options used for parsing the class.
 * @see ClassReader.accept
 */
fun BufferedSource.readAsClassCode(parsingOptions: Int = 0) =
    ClassNode().apply { ClassReader(inputStream()).accept(this, parsingOptions) }
