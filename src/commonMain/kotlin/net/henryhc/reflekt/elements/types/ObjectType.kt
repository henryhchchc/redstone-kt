package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.elements.references.FixedTypeReference
import net.henryhc.reflekt.elements.references.Materialization
import net.henryhc.reflekt.elements.references.TypeReference

/**
 * Denotes the type of `java.lang.Object`.
 */
object ObjectType : ReferenceType("java.lang.Object", superType = null) {

    override fun makeReference(materialization: Materialization): TypeReference = objectReference

    private val objectReference = FixedTypeReference(this)
}
