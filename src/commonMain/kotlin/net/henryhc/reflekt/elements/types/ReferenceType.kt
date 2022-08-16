package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.elements.references.TypeReference

/**
 * Denotes a reference type in JVM.
 */
sealed class ReferenceType : Type() {

    override fun makeReference(materialization: List<TypeReference<ReferenceType>>): TypeReference<ReferenceType> {
        @Suppress("UNCHECKED_CAST")
        return super.makeReference(materialization) as TypeReference<ReferenceType>
    }
}
