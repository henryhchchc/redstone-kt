package net.henryhc.reflekt.elements.references.materialization

import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.types.ReferenceType
import net.henryhc.reflekt.elements.types.TypeVariable

/**
 * Denotes a mapping from type variables to their actual types.
 */
sealed class Materialization : Map<TypeVariable<*>, TypeReference<out ReferenceType>> {


    companion object {
        /**
         * Denotes an empty [Materialization].
         */
        val EMPTY: Materialization = FixedMaterialization(emptyMap())


        /**
         * Creates a materialization.
         */
        fun materialize(vararg mappings: Pair<TypeVariable<*>, TypeReference<out ReferenceType>>): Materialization =
            FixedMaterialization(*mappings)

        /**
         * Creates a materialization.
         */
        fun materialize(mapping: Map<TypeVariable<*>, TypeReference<out ReferenceType>>): Materialization =
            FixedMaterialization(mapping)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is Materialization) return false

        return entries == other.entries
    }

    override fun hashCode(): Int {
        return entries.hashCode()
    }

}

