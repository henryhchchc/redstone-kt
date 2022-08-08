package net.henryhc.reflekt.elements.references

import net.henryhc.reflekt.elements.references.materialization.Materialization
import net.henryhc.reflekt.elements.references.materialization.Materialization.Companion.materialize
import net.henryhc.reflekt.elements.types.ReferenceType
import net.henryhc.reflekt.elements.types.Type

/**
 * Denotes an unmodifiable type reference
 */
class FixedTypeReference(
    override val type: Type,
    materialization: Materialization = Materialization.EMPTY
) : TypeReference() {

    override val materialization: Materialization

    init {
        val relevantTypeVariables = if (type is ReferenceType) type.typeParameters else emptyList()
        this.materialization = materialize(materialization.filterKeys { it in relevantTypeVariables })
    }

}
