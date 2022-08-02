package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.elements.references.FlexibleTypeReference
import net.henryhc.reflekt.elements.references.Materialization

/**
 * Denotes a type in JVM.
 * @property name The raw type name.
 * @see ReferenceType
 * @see TypeVariable
 */
sealed class Type {
    abstract val name: String

    fun makeArrayType(materialization: Materialization) = ArrayType(makeReference(materialization))

    fun makeReference(materialization: Materialization) = FlexibleTypeReference(materialization).also { it.bind(this) }
}

