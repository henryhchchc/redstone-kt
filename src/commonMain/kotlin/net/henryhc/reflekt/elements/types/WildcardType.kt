package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.elements.references.TypeReference

/**
 * Denotes a wildcard type.
 * @property upperBounds Upper bounds of the wildcard type for ? super xxx.
 * @property lowerBounds Lower bounds of the wildcard type for ? extends xxx.
 */
class WildcardType(
    val upperBounds: List<TypeReference<ReferenceType>> = emptyList(),
    val lowerBounds: List<TypeReference<ReferenceType>> = emptyList()
) : Type() {

    override val identifier: String get() = toString()

    override val descriptor: String get() = ""

    override val signature: String get() = buildString {
        append("<")
        if (upperBounds.isEmpty() && lowerBounds.isEmpty())
            append("*")
        else {
            upperBounds.forEach { append("+"); append(it.signature) }
            lowerBounds.forEach { append("-"); append(it.signature) }
        }
        append(">")
    }

    private fun isImplicitUpperbound() = upperBounds.size == 1 && upperBounds.single().type == ObjectType

    override fun toString(): String = buildString {
        append('?')
        if (upperBounds.isNotEmpty() && !isImplicitUpperbound()) {
            append(" extends ")
            append(upperBounds.joinToString(", "))
        }
        if (lowerBounds.isNotEmpty()) {
            append(" super ")
            append(lowerBounds.joinToString(", "))
        }
    }

}
