package net.henryhc.redstone.reflection.elements.members

import net.henryhc.redstone.reflection.AccessModifiers
import net.henryhc.redstone.reflection.elements.references.TypeReference
import net.henryhc.redstone.reflection.elements.types.ClassType
import net.henryhc.redstone.reflection.elements.types.Type

/**
 * Denotes a JVM field.
 * @property name The name of the field.
 * @property type The reference to the type of the field.
 */
class Field(
    val name: String,
    val type: TypeReference<Type>,
    override val modifiers: net.henryhc.redstone.reflection.AccessModifiers,
    override val declaration: ClassType
) : Member {

    override val signature: String get() = type.signature

    override val descriptor: String get() = type.descriptor

    override fun toString(): String = buildString {
        append(declaration.toString())
        append(".")
        append(name)
        append(":")
        append(type.toString())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Field

        if (name != other.name) return false
        if (type != other.type) return false
        if (modifiers != other.modifiers) return false
        if (declaration != other.declaration) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + modifiers.hashCode()
        result = 31 * result + declaration.hashCode()
        return result
    }
}
