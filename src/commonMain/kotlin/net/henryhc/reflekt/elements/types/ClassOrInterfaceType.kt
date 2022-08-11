package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.AccessModifiers
import net.henryhc.reflekt.AccessModifiers.Known.ACC_PUBLIC
import net.henryhc.reflekt.elements.GenericDeclaration
import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.references.materialization.Materialization

/**
 * Denotes a JVM type with type parameters.
 * @property superType A reference to its super type.
 * @property modifiers The access modifiers.
 * @property implementedInterfaces References to the implemented interfaces.
 */
open class ClassOrInterfaceType(
    override val identifier: String,
    val modifiers: AccessModifiers = ACC_PUBLIC,
    override val typeParameters: List<TypeVariable<out ClassOrInterfaceType>> = emptyList(),
    val superType: TypeReference<out ClassOrInterfaceType>?,
    val implementedInterfaces: List<TypeReference<out ClassOrInterfaceType>> = emptyList(),
) : ReferenceType(), GenericDeclaration<ClassOrInterfaceType> {

    override fun toString(): String = buildString {
        append(identifier)
        if (typeParameters.isNotEmpty()) {
            append('<')
            append(typeParameters.joinToString(", "))
            append('>')
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ClassOrInterfaceType

        return this.toString() == other.toString()
    }

    override fun hashCode(): Int {
        return this.toString().hashCode()
    }

    override fun makeReference(materialization: Materialization): TypeReference<out ClassOrInterfaceType> {
        @Suppress("UNCHECKED_CAST")
        return super.makeReference(materialization) as TypeReference<ClassOrInterfaceType>
    }

}
