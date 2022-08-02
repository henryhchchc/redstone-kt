package net.henryhc.reflekt.elements.types

/**
 * Denotes a JVM primitive type.
 * @property boxedType The corresponding boxed type.
 */
sealed class PrimitiveType(override val name: String, val boxedTypeName: String) : Type() {

    override fun toString(): String = name

}


