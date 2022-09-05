package net.henryhc.redstone.reflection.elements.members

import net.henryhc.redstone.reflection.AccessModifiers
import net.henryhc.redstone.reflection.elements.Element
import net.henryhc.redstone.reflection.elements.types.ClassType

/**
 * Denotes a member.
 * @property declaration The [ClassType] where it is declared.
 * @property modifiers The access modifiers.
 * @see Field
 * @see Method
 */
interface Member : Element {
    val declaration: ClassType
    val modifiers: AccessModifiers
}
