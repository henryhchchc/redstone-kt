
package net.henryhc.reflekt.elements.types

/**
 * Denotes a JVM primitive type.
 * @property boxedType The corresponding boxed type.
 */
sealed class PrimitiveType(override val name: String, val boxedType: BoxedPrimitiveType) : Type() {

    override fun toString(): String = name

    companion object {

        /**
         * The set of known primitive types.
         */
    }
}


