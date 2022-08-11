package net.henryhc.reflekt.elements.references

import net.henryhc.reflekt.elements.references.materialization.Materialization
import net.henryhc.reflekt.elements.references.materialization.Materialization.Companion.materialize
import net.henryhc.reflekt.elements.types.ClassOrInterfaceType
import net.henryhc.reflekt.elements.types.Type

/**
 * Denotes an unmodifiable type reference
 */
class FixedTypeReference<T : Type>(
    override val type: T,
    materialization: Materialization = Materialization.EMPTY
) : TypeReference<T>() {

    override val materialization: Materialization

    init {
        val relevantTypeVariables = if (type is ClassOrInterfaceType) type.typeParameters else emptyList()
        this.materialization = materialize(materialization.filterKeys { it in relevantTypeVariables })
    }

}
