package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.elements.references.ObjectTypeReference
import net.henryhc.reflekt.elements.references.TypeReference

/**
 * Denotes a wildcard type.
 * @property upperBounds Upper bounds of the wildcard type for ? super xxx.
 * @property lowerBounds Lower bounds of the wildcard type for ? extends xxx.
 */
class WildcardType(
    val upperBounds: List<TypeReference> = emptyList(),
    val lowerBounds: List<TypeReference> = emptyList()
) : Type() {

    override val name: String get() = toString()


    private fun isImplicitUpperbound() = upperBounds.size == 1 && upperBounds.single() == ObjectTypeReference
    override fun toString() = buildString {
        append('?')
        if (upperBounds.isNotEmpty() && !isImplicitUpperbound()){
            append(" extends ")
            append(upperBounds.joinToString(", "))
        }
        if (lowerBounds.isNotEmpty()) {
            append(" super ")
            append(lowerBounds.joinToString(", "))
        }
    }
}
