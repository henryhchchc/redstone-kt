package net.henryhc.redstone.reflection.elements.references

import net.henryhc.redstone.reflection.elements.types.ReferenceType
import net.henryhc.redstone.reflection.elements.types.WildcardType

/**
 * Denotes a reference to a [WildcardType].
 */
class WildcardTypeReference(override val type: WildcardType) : TypeReference<WildcardType>() {

    override val materialization: List<TypeReference<ReferenceType>> = emptyList()

    override val signature: String get() = type.signature

    override fun toString(): String = type.toString()
}
