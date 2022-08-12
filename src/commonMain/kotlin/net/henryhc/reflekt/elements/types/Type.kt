package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.elements.references.FixedTypeReference
import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.references.materialization.Materialization

/**
 * Denotes a type in JVM.
 * @property identifier The raw type name.
 * @property descriptor The JVM descriptor of the type.
 * @property signature the JVM type signature of the type.
 * @see ClassType
 * @see TypeVariable
 */
sealed class Type {

    abstract val identifier: String

    abstract val descriptor: String

    abstract val signature: String

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
    open fun makeReference(materialization: Materialization = Materialization.EMPTY): TypeReference<out Type> =
        FixedTypeReference(this, materialization)

}

