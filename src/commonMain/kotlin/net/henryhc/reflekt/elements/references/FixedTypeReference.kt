package net.henryhc.reflekt.elements.references

import net.henryhc.reflekt.elements.types.ReferenceType
import net.henryhc.reflekt.elements.types.Type

/**
 * Denotes an unmodifiable type reference
 */
class FixedTypeReference<T : Type>(
    override val type: T,
    override val materialization: List<TypeReference<ReferenceType>> = emptyList()
) : TypeReference<T>()
