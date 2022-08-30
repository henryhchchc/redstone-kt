package net.henryhc.redstone.reflection.elements.references

import net.henryhc.redstone.reflection.elements.Element
import net.henryhc.redstone.reflection.elements.types.*

/**
 * Denotes a reference to a [Type].
 * @property type The type referring to.
 * @property signature The JVM `ReferenceTypeSignature`.
 * @property materialization Known mapping from the type variables to the actual types.
 */
abstract class TypeReference<out T : Type> : Element {

    abstract val type: T

    override val descriptor: String get() = type.descriptor

    override val signature: String
        get() = when (type) {
            is ClassType -> buildString {
                append("L")
                append(type.identifier.replace(".", "/"))
                val classType = type as ClassType
                if (classType.typeParameters.isNotEmpty()) {
                    materialization.joinToString(separator = "", prefix = "<", postfix = ">") { it.signature }
                        .also(::append)
                }
                append(";")
            }

            is TypeVariable<*> -> "T${type.identifier};"
            is ArrayType -> type.signature
            is PrimitiveType -> type.descriptor
            is UnknownType -> UnknownType.signature

            else -> error("Should not reach here.")
        }

    abstract val materialization: List<TypeReference<ReferenceType>>


    override fun toString(): String = buildString {
        append(type.identifier)
        if (materialization.isNotEmpty()) {
            materialization.joinToString(separator = ",", prefix = "<", postfix = ">").also(::append)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TypeReference<*>) return false

        if (signature != other.signature) return false

        return true
    }

    override fun hashCode(): Int {
        return signature.hashCode()
    }

}
