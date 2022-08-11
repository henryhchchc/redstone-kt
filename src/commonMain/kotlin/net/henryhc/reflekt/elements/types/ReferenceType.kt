package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.references.materialization.Materialization

/**
 * Denotes a reference type in JVM.
 */
sealed class ReferenceType : Type() {

    override fun makeReference(materialization: Materialization): TypeReference<out ReferenceType> {
        @Suppress("UNCHECKED_CAST")
        return super.makeReference(materialization) as TypeReference<out ReferenceType>
    }
}
