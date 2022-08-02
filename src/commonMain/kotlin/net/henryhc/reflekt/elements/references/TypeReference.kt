package net.henryhc.reflekt.elements.references

import net.henryhc.reflekt.elements.types.Type

/**
 * Denotes a reference to a [Type].
 * @property type The type referring to.
 * @property materialization Known mapping from the type variables to the actual types.
 */
interface TypeReference {
    val type: Type
    val materialization: Materialization
}
