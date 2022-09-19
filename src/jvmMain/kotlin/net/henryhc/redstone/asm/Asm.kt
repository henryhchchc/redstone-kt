package net.henryhc.redstone.asm

import okio.BufferedSource
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode

/**
 * Reads the stream and parses its content as a [ClassNode].
 * @param parsingOptions The options used for parsing the class.
 * @see ClassReader.accept
 */
fun BufferedSource.readAsClassCode(parsingOptions: Int = 0) =
    ClassNode().apply { ClassReader(inputStream()).accept(this, parsingOptions) }


private val jUnitTestAnnotationDescriptors = setOf("Lorg/junit/Test;", "Lorg/junit/jupiter/api/Test;")

/**
 * Tells whether a [MethodNode] is a JUnit test case.
 */
val MethodNode.isJUnitTestCase: Boolean get() = this.visibleAnnotations?.any { it.desc in jUnitTestAnnotationDescriptors } == true
