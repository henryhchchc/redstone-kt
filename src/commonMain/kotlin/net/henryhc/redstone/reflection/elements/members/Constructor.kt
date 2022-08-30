package net.henryhc.redstone.reflection.elements.members

import net.henryhc.redstone.reflection.AccessModifiers
import net.henryhc.redstone.reflection.elements.Invokable
import net.henryhc.redstone.reflection.elements.references.TypeReference
import net.henryhc.redstone.reflection.elements.types.ClassType
import net.henryhc.redstone.reflection.elements.types.PrimitiveType
import net.henryhc.redstone.reflection.elements.types.Type

/**
 * Denotes a JVM constructor.
 */
class Constructor(
    override val modifiers: net.henryhc.redstone.reflection.AccessModifiers,
    override val declaration: ClassType,
    override val parameterTypes: List<TypeReference<Type>>,
) : Invokable, Member {

    override val signature: String
        get() = parameterTypes.joinToString(separator = "", prefix = "(", postfix = ")") { it.signature }

    override val descriptor: String
        get() = buildString {
            append(parameterTypes.joinToString(separator = "", prefix = "(", postfix = ")") { it.descriptor })
            append(PrimitiveType.VoidType.descriptor)
        }

    override fun toString(): String = buildString {
        append(declaration.toString())
        append("::<init>")
        append('(')
        append(parameterTypes.joinToString(", "))
        append(")")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Constructor

        if (modifiers != other.modifiers) return false
        if (declaration != other.declaration) return false
        if (parameterTypes != other.parameterTypes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = modifiers.hashCode()
        result = 31 * result + declaration.hashCode()
        result = 31 * result + parameterTypes.hashCode()
        return result
    }
}
