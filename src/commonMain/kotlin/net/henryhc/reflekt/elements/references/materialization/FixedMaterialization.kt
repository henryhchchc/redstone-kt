package net.henryhc.reflekt.elements.references.materialization

import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.types.ReferenceType
import net.henryhc.reflekt.elements.types.TypeVariable

/**
 * Denotes an immutable materialization
 */
class FixedMaterialization(private val rawMapping: Map<TypeVariable<*>, TypeReference<out ReferenceType>>) : Materialization(),
    Map<TypeVariable<*>, TypeReference<out ReferenceType>> by rawMapping {

    constructor(vararg mappings: Pair<TypeVariable<*>, TypeReference<out ReferenceType>>) : this(mapOf(*mappings))

}
