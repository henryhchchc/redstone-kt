package net.henryhc.reflekt.elements.references

import net.henryhc.reflekt.elements.types.WildcardType

/**
 * Denotes a reference to a [WildcardType].
 */
class WildcardTypeReference(
    override val type: WildcardType,
) : TypeReference {

    override val materialization: Materialization = Materialization.EMPTY

    override fun toString(): String = type.toString()
}
