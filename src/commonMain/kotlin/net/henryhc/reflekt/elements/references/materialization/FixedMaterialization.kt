package net.henryhc.reflekt.elements.references.materialization

import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.types.TypeVariable

/**
 * Denotes an immutable materialization
 */
class FixedMaterialization(private val rawMapping: Map<TypeVariable<*>, TypeReference>) : Materialization(),
    Map<TypeVariable<*>, TypeReference> by rawMapping {

    constructor(vararg mappings: Pair<TypeVariable<*>, TypeReference>) : this(mapOf(*mappings))

}
