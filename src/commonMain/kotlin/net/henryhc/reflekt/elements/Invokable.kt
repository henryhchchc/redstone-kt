package net.henryhc.reflekt.elements

import net.henryhc.reflekt.elements.references.TypeReference

/**
 * Denotes a JVM element that is callable.
 * @property parameterTypes The types of its parameters.
 */
interface Invokable {
    val parameterTypes: List<TypeReference>
}

