package net.henryhc.reflekt.elements.references

import net.henryhc.reflekt.elements.types.ObjectType
import net.henryhc.reflekt.elements.types.Type


/**
 * The reference to [ObjectType].
 */
object ObjectTypeReference : TypeReference {
    override val type: Type = ObjectType
    override val materialization: Materialization = Materialization.EMPTY

    override fun toString(): String  = ObjectType.toString()
}
