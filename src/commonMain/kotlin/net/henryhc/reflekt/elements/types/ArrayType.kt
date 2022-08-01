package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.elements.references.TypeReference

/**
 * Denotes an array type.
 * @property elementType The type of its elements
 */
class ArrayType(val elementType: TypeReference) : ReferenceType {
    override val name: String get() = "${elementType.type.name}[]"
}
