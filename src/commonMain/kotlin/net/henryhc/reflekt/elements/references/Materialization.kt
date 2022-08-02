package net.henryhc.reflekt.elements.references

import net.henryhc.reflekt.elements.types.TypeVariable
import kotlin.jvm.JvmInline

@JvmInline
value class Materialization(private val rawMapping: Map<TypeVariable, TypeReference>) :
    Map<TypeVariable, TypeReference> by rawMapping {

    companion object {
        val EMPTY = Materialization(emptyMap())
    }
}

