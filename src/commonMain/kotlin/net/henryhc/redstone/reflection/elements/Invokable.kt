package net.henryhc.redstone.reflection.elements

import net.henryhc.redstone.reflection.elements.references.TypeReference
import net.henryhc.redstone.reflection.elements.types.Type

/**
 * Denotes a JVM element that is callable.
 * @property parameterTypes The types of its parameters.
 */
interface Invokable : Element {
    val parameterTypes: List<TypeReference<Type>>
}

