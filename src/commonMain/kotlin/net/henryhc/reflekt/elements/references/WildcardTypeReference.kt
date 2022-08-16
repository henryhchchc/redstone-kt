package net.henryhc.reflekt.elements.references

import net.henryhc.reflekt.elements.types.ReferenceType
import net.henryhc.reflekt.elements.types.WildcardType

/**
 * Denotes a reference to a [WildcardType].
 */
class WildcardTypeReference(override val type: WildcardType) : TypeReference<WildcardType>() {

    override val materialization: List<TypeReference<ReferenceType>> = emptyList()

    override val signature: String get() = type.signature

    override fun toString(): String = type.toString()
}
