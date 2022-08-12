package net.henryhc.reflekt.elements.members

import net.henryhc.reflekt.AccessModifiers
import net.henryhc.reflekt.elements.Element
import net.henryhc.reflekt.elements.types.ClassType

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
