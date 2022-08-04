package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.elements.references.FixedTypeReference
import net.henryhc.reflekt.elements.references.Materialization
import net.henryhc.reflekt.elements.references.TypeReference

/**
 * Denotes a type in JVM.
 * @property name The raw type name.
 * @see ReferenceType
 * @see TypeVariable
 */
sealed class Type {

    abstract val name: String

    /**
     * Creates a type denoting the type of the array of the current type.
     * @param materialization The materialization of the type parameters.
     */
    fun makeArrayType(materialization: Materialization = Materialization.EMPTY): ArrayType =
        ArrayType(makeReference(materialization))

    /**
     * Creates a reference to the current type.
     * @param materialization The materialization of the type parameters.
     */
    open fun makeReference(materialization: Materialization = Materialization.EMPTY): TypeReference =
        FixedTypeReference(this, materialization)
}

