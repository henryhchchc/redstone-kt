package net.henryhc.reflekt.elements.members

import net.henryhc.reflekt.AccessModifiers
import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.types.ReferenceType

/**
 * Denotes a JVM field.
 * @property name The name of the field.
 * @property type The reference to the type of the field.
 */
class Field(
    val name: String,
    val type: TypeReference,
    override val modifiers: AccessModifiers,
    override val declaration: ReferenceType
) : Member
