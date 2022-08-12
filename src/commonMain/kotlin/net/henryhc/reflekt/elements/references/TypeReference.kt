package net.henryhc.reflekt.elements.references

import net.henryhc.reflekt.elements.references.materialization.Materialization
import net.henryhc.reflekt.elements.types.*

/**
 * Denotes a reference to a [Type].
 * @property type The type referring to.
 * @property signature The JVM `ReferenceTypeSignature`.
 * @property materialization Known mapping from the type variables to the actual types.
 */
abstract class TypeReference<T : Type> {

    abstract val type: T

    open val signature: String
        get() = when (type) {
            is ClassType -> buildString {
                append("L")
                append(type.identifier.replace(".", "/"))
                val classType = type as ClassType
                if (classType.typeParameters.isNotEmpty()) {
                    append("<")
                    classType.typeParameters.mapNotNull { materialization[it] }.forEach { append(it.signature) }
                    append(">")
                }
                append(";")
            }

            is TypeVariable<*> -> "T${type.identifier};"
            is ArrayType -> "[${(type as ArrayType).elementType.signature}"
            is PrimitiveType -> type.descriptor

            else -> error("Should not reach here.")
        }

    abstract val materialization: Materialization

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TypeReference<*>) return false

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
        append(type.identifier)
        if (materialization.isNotEmpty()) {
            append(
                (type as ClassType).typeParameters.joinToString(
                    separator = ",",
                    prefix = "<",
                    postfix = ">"
                ) { materialization[it].toString() })
        }
    }

}
