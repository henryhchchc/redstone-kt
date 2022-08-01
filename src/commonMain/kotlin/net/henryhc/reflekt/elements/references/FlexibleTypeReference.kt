package net.henryhc.reflekt.elements.references

import net.henryhc.reflekt.elements.types.GenericType
import net.henryhc.reflekt.elements.types.Type
import net.henryhc.reflekt.elements.types.TypeVariable

/**
 * An implementation of [TypeReference] that can be bind later, which is useful for handling circular dependencies.
 */
class FlexibleTypeReference(
    override val materialization: Map<TypeVariable, TypeReference>
) : TypeReference {

    override lateinit var type: Type
        private set

    /**
     * Bind the [type] field to an actual type.
     * This can be only called once.
     */
    fun bind(value: Type) {
        require(!this::type.isInitialized) { "The type reference is already bind." }
        type = value
    }

    override fun toString(): String = buildString {
        append(type.name)
        if (materialization.isNotEmpty()) {
            append('<')
            append((type as GenericType).typeParameters.joinToString(",") { materialization[it].toString() })
            append('>')
        }
    }
}
