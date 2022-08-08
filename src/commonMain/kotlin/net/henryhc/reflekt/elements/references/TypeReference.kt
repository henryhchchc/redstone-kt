package net.henryhc.reflekt.elements.references

import net.henryhc.reflekt.elements.references.materialization.Materialization
import net.henryhc.reflekt.elements.types.ReferenceType
import net.henryhc.reflekt.elements.types.Type

/**
 * Denotes a reference to a [Type].
 * @property type The type referring to.
 * @property materialization Known mapping from the type variables to the actual types.
 */
abstract class TypeReference {

    abstract val type: Type

    abstract val materialization: Materialization

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TypeReference) return false

        if (type != other.type) return false
        if (materialization != other.materialization) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + materialization.hashCode()
        return result
    }

    override fun toString(): String = buildString {
        append(type.name)
        if (materialization.isNotEmpty()) {
            append('<')
            append((type as ReferenceType).typeParameters.joinToString(",") { materialization[it].toString() })
            append('>')
        }
    }

}
