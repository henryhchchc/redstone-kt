package net.henryhc.reflekt.elements

import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.types.Type

/**
 * Denotes a JVM element that is callable.
 * @property parameterTypes The types of its parameters.
 */
interface Invokable : Element {
    val parameterTypes: List<TypeReference<out Type>>
}

