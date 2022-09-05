package net.henryhc.redstone.reflection.elements.members

import net.henryhc.redstone.reflection.AccessModifiers
import net.henryhc.redstone.reflection.elements.GenericDeclaration
import net.henryhc.redstone.reflection.elements.Invokable
import net.henryhc.redstone.reflection.elements.references.TypeReference
import net.henryhc.redstone.reflection.elements.types.ClassType
import net.henryhc.redstone.reflection.elements.types.Type
import net.henryhc.redstone.reflection.elements.types.TypeVariable

/**
 * Denotes a JVM method.
 * @property name The name of the method.
 * @property signature The JVM signature of the method.
 * @property returnType The return type of the method.
 */
class Method(
    val name: String,
    val returnType: TypeReference<Type>,
    override val modifiers: AccessModifiers,
    override val declaration: ClassType,
    override val parameterTypes: List<TypeReference<Type>>,
    override val typeParameters: List<TypeVariable<Method>>
) : Invokable, GenericDeclaration<Method>, Member {

    override val signature: String
        get() = buildString {
            if (typeParameters.isNotEmpty()) {
                append(typeParameters.joinToString(separator = "", prefix = "<", postfix = ">") { it.signature })
            }
            append(parameterTypes.joinToString(separator = "", prefix = "(", postfix = ")") { it.signature })
            append(returnType.signature)
        }

    override val descriptor: String
        get() = buildString {
            append(parameterTypes.joinToString(separator = "", prefix = "(", postfix = ")") { it.descriptor })
            append(returnType.descriptor)
        }

    override fun toString(): String = buildString {
        append(declaration.toString())
        append("::")
        append(name)
        if (typeParameters.isNotEmpty()) {
            append('<')
            append(typeParameters.joinToString(", "))
            append('>')
        }
        append('(')
        append(parameterTypes.joinToString(", "))
        append("):")
        append(returnType)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Method

        if (name != other.name) return false
        if (returnType != other.returnType) return false
        if (modifiers != other.modifiers) return false
        if (declaration != other.declaration) return false
        if (parameterTypes != other.parameterTypes) return false
        if (typeParameters != other.typeParameters) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + returnType.hashCode()
        result = 31 * result + modifiers.hashCode()
        result = 31 * result + declaration.hashCode()
        result = 31 * result + parameterTypes.hashCode()
        result = 31 * result + typeParameters.hashCode()
        return result
    }


}
