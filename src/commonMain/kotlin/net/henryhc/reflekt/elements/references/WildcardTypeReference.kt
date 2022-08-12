package net.henryhc.reflekt.elements.references

import net.henryhc.reflekt.elements.references.materialization.Materialization
import net.henryhc.reflekt.elements.types.WildcardType

/**
 * Denotes a reference to a [WildcardType].
 */
class WildcardTypeReference(override val type: WildcardType) : TypeReference<WildcardType>() {

    override val materialization: Materialization = Materialization.EMPTY

    override val signature: String
        get() = buildString {
            append("<")
            if (type.upperBounds.isEmpty() && type.lowerBounds.isEmpty())
                append("*")
            else {
                type.upperBounds.forEach { append("+"); append(it.signature) }
                type.lowerBounds.forEach { append("-"); append(it.signature) }
            }
            append(">")
        }

    override fun toString(): String = type.toString()
}
