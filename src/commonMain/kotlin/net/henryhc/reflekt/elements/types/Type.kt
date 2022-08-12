package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.elements.Element
import net.henryhc.reflekt.elements.references.FixedTypeReference
import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.references.materialization.Materialization

/**
 * Denotes a type in JVM.
 * @property identifier The raw type name.
 * @see ClassType
 * @see TypeVariable
 */
sealed class Type : Element {

    abstract val identifier: String

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Type

        if (descriptor != other.descriptor) return false
        if (signature != other.signature) return false

        return true
    }

    override fun hashCode(): Int {
        var result = descriptor.hashCode()
        result = 31 * result + signature.hashCode()
        return result
    }


}

