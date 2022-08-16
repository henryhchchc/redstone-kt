package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.elements.references.TypeReference

/**
 * Denotes an array type.
 * @property elementType The type of its elements
 */
class ArrayType(val elementType: TypeReference<Type>) : ReferenceType() {

    override val identifier: String get() = "${elementType.type.identifier}[]"

    override val descriptor: String get() = "[${elementType.type.descriptor}"

    override val signature: String get() = "[${elementType.signature}"

    /**
     * Gets the dimension of the array type.
     */
    val dimension: Int
        get() = DeepRecursiveFunction<Type, Int> {
            if (it is ArrayType) 1 + callRecursive(it.elementType.type) else 0
        }(this)

}
