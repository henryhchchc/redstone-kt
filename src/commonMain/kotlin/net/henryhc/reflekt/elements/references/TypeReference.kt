package net.henryhc.reflekt.elements.references

import net.henryhc.reflekt.elements.types.Type
import net.henryhc.reflekt.elements.types.TypeVariable

/**
 * Denotes a reference to a [Type].
 * @property type The type referring to.
 * @property materialization Known mapping from the type variables to the actual types.
 */
sealed interface TypeReference {
    val type: Type
    val materialization: Map<TypeVariable, TypeReference>
}
