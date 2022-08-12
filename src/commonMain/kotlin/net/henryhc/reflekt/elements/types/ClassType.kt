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
open class ClassType(
    override val identifier: String,
    val modifiers: AccessModifiers = ACC_PUBLIC,
    override val typeParameters: List<TypeVariable<out ClassType>> = emptyList(),
    val superType: TypeReference<out ClassType>?,
    val implementedInterfaces: List<TypeReference<out ClassType>> = emptyList(),
) : ReferenceType(), GenericDeclaration<ClassType> {

    override val descriptor: String get() = "L${identifier.replace(".", "/")};"

    override val signature: String
        get() = buildString {
            if (typeParameters.isNotEmpty()) {
                append(typeParameters.joinToString(separator = "", prefix = "<", postfix = ">") { it.signature })
            }
            append(superType?.signature ?: "")
            append(implementedInterfaces.joinToString(separator = "") { it.signature })
        }

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

        other as ClassType

        return this.toString() == other.toString()
    }

    override fun hashCode(): Int {
        return this.toString().hashCode()
    }

    override fun makeReference(materialization: Materialization): TypeReference<out ClassType> {
        @Suppress("UNCHECKED_CAST")
        return super.makeReference(materialization) as TypeReference<ClassType>
    }

}
