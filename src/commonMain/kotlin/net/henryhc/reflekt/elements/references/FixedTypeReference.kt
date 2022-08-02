package net.henryhc.reflekt.elements.references

import net.henryhc.reflekt.elements.types.Type

/**
 * Denotes an unmodifiable type reference
 */
class FixedTypeReference(
    override val type: Type,
    override val materialization: Materialization = Materialization.EMPTY
) : TypeReference {
}
