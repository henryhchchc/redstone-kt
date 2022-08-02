package net.henryhc.reflekt.elements.references

import net.henryhc.reflekt.elements.types.TypeVariable
import kotlin.jvm.JvmInline

/**
 * Denotes a mapping from type variables to their actual types.
 */
@JvmInline
value class Materialization(private val rawMapping: Map<TypeVariable<*>, TypeReference>) :
    Map<TypeVariable<*>, TypeReference> by rawMapping {

    companion object {
        /**
         * Denotes an empty [Materialization].
         */
        val EMPTY: Materialization = Materialization(emptyMap())
    }
}

