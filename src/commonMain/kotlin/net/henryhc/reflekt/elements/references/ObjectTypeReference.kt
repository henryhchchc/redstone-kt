package net.henryhc.reflekt.elements.references

import net.henryhc.reflekt.elements.types.ObjectType
import net.henryhc.reflekt.elements.types.Type
import net.henryhc.reflekt.elements.types.TypeVariable


/**
 * The reference to [ObjectType].
 */
object ObjectTypeReference : TypeReference {
    override val type: Type = ObjectType
    override val materialization: Map<TypeVariable, TypeReference> = emptyMap()
}
