package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.elements.references.TypeReference

/**
 * Denotes a wildcard type.
 * @property upperBounds Upper bounds of the wildcard type for ? super xxx.
 * @property lowerBounds Lower bounds of the wildcard type for ? extends xxx.
 */
sealed class WildcardType(
    val upperBounds: List<TypeReference> = emptyList(),
    val lowerBounds: List<TypeReference> = emptyList()
) : Type {

    override val name: String get() = "?"

}
