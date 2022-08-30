package net.henryhc.redstone.reflection.elements.references

import net.henryhc.redstone.reflection.elements.types.ReferenceType
import net.henryhc.redstone.reflection.elements.types.Type
import net.henryhc.redstone.reflection.utils.identityHashCode

/**
 * An implementation of [TypeReference] that can be bind later, which is useful for handling circular dependencies.
 */
class DanglingTypeReference<out T : Type>(
    override val materialization: List<TypeReference<ReferenceType>> = emptyList()
) : TypeReference<T>() {

    override lateinit var type: @UnsafeVariance T
        private set

    override val descriptor: String get() = if (!this::type.isInitialized) toString() else super.descriptor

    /**
     * Bind the [type] field to an actual type.
     * This can be only called once.
     */
    fun bind(value: Type) {
        require(!this::type.isInitialized) { "The type reference is already bind." }
        @Suppress("UNCHECKED_CAST")
        type = value as T
    }

    override fun toString(): String =
        if (!this::type.isInitialized) "<Dangling@${identityHashCode()}>" else super.toString()

    override val signature: String get() = if (!this::type.isInitialized) toString() else super.signature

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
