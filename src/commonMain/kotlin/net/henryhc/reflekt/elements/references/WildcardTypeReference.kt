package net.henryhc.reflekt.elements.references

import net.henryhc.reflekt.elements.references.materialization.Materialization
import net.henryhc.reflekt.elements.types.WildcardType

/**
 * Denotes a reference to a [WildcardType].
 */
class WildcardTypeReference(override val type: WildcardType) : TypeReference<WildcardType>() {

    override val materialization: Materialization = Materialization.EMPTY

    override val signature: String get() = type.signature

    override fun toString(): String = type.toString()
}
