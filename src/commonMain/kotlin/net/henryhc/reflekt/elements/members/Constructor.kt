package net.henryhc.reflekt.elements.members

import net.henryhc.reflekt.AccessModifiers
import net.henryhc.reflekt.elements.Invokable
import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.types.ClassOrInterfaceType
import net.henryhc.reflekt.elements.types.Type

/**
 * Denotes a JVM constructor.
 */
class Constructor(
    override val modifiers: AccessModifiers,
    override val declaration: ClassOrInterfaceType,
    override val parameterTypes: List<TypeReference<out Type>>,
) : Invokable, Member {

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
