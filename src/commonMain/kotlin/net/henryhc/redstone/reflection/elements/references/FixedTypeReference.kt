package net.henryhc.redstone.reflection.elements.references

import net.henryhc.redstone.reflection.elements.types.ReferenceType
import net.henryhc.redstone.reflection.elements.types.Type

/**
 * Denotes an unmodifiable type reference
 */
class FixedTypeReference<T : Type>(
    override val type: T,
    override val materialization: List<TypeReference<ReferenceType>> = emptyList()
) : TypeReference<T>()
