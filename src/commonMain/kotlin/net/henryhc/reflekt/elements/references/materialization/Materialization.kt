package net.henryhc.reflekt.elements.references.materialization

import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.types.TypeVariable

/**
 * Denotes a mapping from type variables to their actual types.
 */
sealed class Materialization : Map<TypeVariable<*>, TypeReference> {

    companion object {
        /**
         * Denotes an empty [Materialization].
         */
        val EMPTY: Materialization = FixedMaterialization(emptyMap())


        /**
         * Creates a materialization.
         */
        fun materialize(vararg mappings: Pair<TypeVariable<*>, TypeReference>): Materialization =
            FixedMaterialization(*mappings)

        /**
         * Creates a materialization.
         */
        fun materialize(mapping: Map<TypeVariable<*>, TypeReference>): Materialization =
            FixedMaterialization(mapping)
    }
}

