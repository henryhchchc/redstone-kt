package net.henryhc.redstone.reflection.elements

/**
 * @property descriptor The JVM descriptor.
 * @property signature the JVM type signature.
 */
interface Element {

    val signature: String
    val descriptor: String
}
