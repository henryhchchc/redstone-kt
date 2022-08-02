package net.henryhc.reflekt.elements.members

import net.henryhc.reflekt.AccessModifiers
import net.henryhc.reflekt.elements.GenericDeclaration
import net.henryhc.reflekt.elements.Invokable
import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.types.ReferenceType
import net.henryhc.reflekt.elements.types.TypeVariable

/**
 * Denotes a JVM method.
 * @property name The name of the method.
 * @property returnType The return type of the method.
 */
class Method(
    val name: String,
    val returnType: TypeReference,
    override val modifiers: AccessModifiers,
    override val declaration: ReferenceType,
    override val parameterTypes: List<TypeReference>,
    override val typeParameters: List<TypeVariable<Method>>
) : Invokable, GenericDeclaration<Method>, Member {
    override fun toString() = buildString {
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
}
