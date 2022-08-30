package net.henryhc.redstone.reflection.elements.types

/**
 * Denotes an unknown type.
 */
object UnknownType : ReferenceType() {

    override val identifier: String = "?"

    override val signature: String = "L\$\$Unknown;"

    override val descriptor: String = "L\$\$Unknown;"

    override fun toString(): String = "?"
}
