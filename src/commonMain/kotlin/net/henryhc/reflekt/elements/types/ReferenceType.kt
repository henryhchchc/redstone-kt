package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.AccessModifiers
import net.henryhc.reflekt.AccessModifiers.Known.ACC_PUBLIC
import net.henryhc.reflekt.elements.GenericDeclaration
import net.henryhc.reflekt.elements.references.TypeReference

/**
 * Denotes a JVM type with type parameters.
 * @property superType A reference to its super type.
 * @property modifiers The access modifiers.
 * @property implementedInterfaces References to the implemented interfaces.
 */
open class ReferenceType(
    override val name: String,
    val modifiers: AccessModifiers = ACC_PUBLIC,
    override val typeParameters: List<TypeVariable<ReferenceType>> = emptyList(),
    val superType: TypeReference?,
    val implementedInterfaces: List<TypeReference> = emptyList(),
) : Type(), GenericDeclaration<ReferenceType> {

    override fun toString(): String = buildString {
        append(name)
        if (typeParameters.isNotEmpty()) {
            append('<')
            append(typeParameters.joinToString(", "))
            append('>')
        }
    }
}
