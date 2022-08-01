package net.henryhc.reflekt.elements.types

/**
 * Denotes a type in JVM.
 * @property name The raw type name.
 * @see GenericType
 * @see TypeVariable
 */
sealed interface Type {
    val name: String
}

