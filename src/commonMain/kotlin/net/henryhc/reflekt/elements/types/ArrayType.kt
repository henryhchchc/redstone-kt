package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.elements.references.TypeReference

/**
 * Denotes an array type.
 * @property elementType The type of its elements
 */
class ArrayType(val elementType: TypeReference<out Type>) : ReferenceType() {

    override val identifier: String get() = "${elementType.type.identifier}[]"

    override val descriptor: String get() = "[${elementType.type.descriptor}"

    /**
     * Gets the dimension of the array type.
     */
    val dimension: Int
        get() = DeepRecursiveFunction<Type, Int> {
            if (it is ArrayType) 1 + callRecursive(it.elementType.type) else 0
        }(this)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ArrayType) return false

        if (elementType != other.elementType) return false

        return true
    }

    override fun hashCode(): Int {
        return elementType.hashCode()
    }
}
