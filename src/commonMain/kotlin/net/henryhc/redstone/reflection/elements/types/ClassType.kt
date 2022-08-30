package net.henryhc.redstone.reflection.elements.types

import net.henryhc.redstone.reflection.AccessModifiers
import net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_PUBLIC
import net.henryhc.redstone.reflection.elements.GenericDeclaration
import net.henryhc.redstone.reflection.elements.references.TypeReference

/**
 * Denotes a JVM type with type parameters.
 * @property superType A reference to its super type.
 * @property modifiers The access modifiers.
 * @property implementedInterfaces References to the implemented interfaces.
 */
open class ClassType(
    override val identifier: String,
    val modifiers: net.henryhc.redstone.reflection.AccessModifiers = ACC_PUBLIC,
    override val typeParameters: List<TypeVariable<out ClassType>> = emptyList(),
    val superType: TypeReference<ClassType>?,
    val implementedInterfaces: List<TypeReference<ClassType>> = emptyList(),
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

    override fun makeReference(materialization: List<TypeReference<ReferenceType>>): TypeReference<ClassType> {
        require(materialization.size == typeParameters.size)
        @Suppress("UNCHECKED_CAST")
        return super.makeReference(materialization) as TypeReference<ClassType>
    }

}
