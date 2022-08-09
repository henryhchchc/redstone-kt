package net.henryhc.reflekt.elements.references

import net.henryhc.reflekt.elements.references.materialization.Materialization
import net.henryhc.reflekt.elements.references.materialization.Materialization.Companion.materialize
import net.henryhc.reflekt.elements.types.ReferenceType
import net.henryhc.reflekt.elements.types.Type
import net.henryhc.reflekt.utils.identityHashCode

/**
 * An implementation of [TypeReference] that can be bind later, which is useful for handling circular dependencies.
 */
class DanglingTypeReference(
    materialization: Materialization = Materialization.EMPTY
) : TypeReference() {

    override var materialization: Materialization = materialization
        private set

    override lateinit var type: Type
        private set

    /**
     * Bind the [type] field to an actual type.
     * This can be only called once.
     */
    fun bind(value: Type) {
        require(!this::type.isInitialized) { "The type reference is already bind." }
        type = value
        val relevantTypeVariables = if (type is ReferenceType) (type as ReferenceType).typeParameters else emptyList()
        this.materialization = materialize(materialization.filterKeys { it in relevantTypeVariables })
    }

    override fun toString(): String = if (!this::type.isInitialized) "<Dangling>" else super.toString()

    override fun hashCode(): Int {
        if (!this::type.isInitialized)
            return this.identityHashCode()
        return super.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (!this::type.isInitialized)
            return this.identityHashCode() == other?.identityHashCode()
        return super.equals(other)
    }
}
