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
) : Invokable, Member
