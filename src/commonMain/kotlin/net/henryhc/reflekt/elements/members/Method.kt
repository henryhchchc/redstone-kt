package net.henryhc.reflekt.elements.members

import net.henryhc.reflekt.AccessModifiers
import net.henryhc.reflekt.elements.GenericDeclaration
import net.henryhc.reflekt.elements.Invokable
import net.henryhc.reflekt.elements.references.ObjectTypeReference
import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.types.GenericType
import net.henryhc.reflekt.elements.types.TypeVariable

/**
 * Denotes a JVM method.
 * @property name The name of the method.
 * @property returnType The return type of the method.
 */
class Method(
    val name: String,
    val returnType: ObjectTypeReference,
    override val modifiers: AccessModifiers,
    override val declaration: GenericType,
    override val parameterTypes: List<TypeReference>,
    override val typeParameters: List<TypeVariable>
) : Invokable, GenericDeclaration, Member
