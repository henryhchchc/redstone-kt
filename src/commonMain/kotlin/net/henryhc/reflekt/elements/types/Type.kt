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

    fun makeArrayType(materialization: Materialization = Materialization.EMPTY) =
        ArrayType(makeReference(materialization))

    fun makeReference(materialization: Materialization = Materialization.EMPTY) =
        FlexibleTypeReference(materialization).also { it.bind(this) }
}

