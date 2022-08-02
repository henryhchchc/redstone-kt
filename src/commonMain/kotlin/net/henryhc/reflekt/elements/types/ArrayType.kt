package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.elements.references.TypeReference

/**
 * Denotes an array type.
 * @property elementType The type of its elements
 */
class ArrayType(val elementType: TypeReference) : ReferenceType("DUMMY", superType = ObjectType.makeReference()) {

    override val name: String get() = "${elementType.type.name}[]"

    val dimension
        get() = DeepRecursiveFunction<Type, Int> {
            if (it is ArrayType) 1 + callRecursive(it.elementType.type) else 0
        }(this)
}
