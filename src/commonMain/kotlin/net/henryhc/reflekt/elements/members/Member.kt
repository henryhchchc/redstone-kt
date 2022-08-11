package net.henryhc.reflekt.elements.members

import net.henryhc.reflekt.AccessModifiers
import net.henryhc.reflekt.elements.types.ClassOrInterfaceType

/**
 * Denotes a member.
 * @property declaration The [ClassOrInterfaceType] where it is declared.
 * @property modifiers The access modifiers.
 * @see Field
 * @see Method
 */
interface Member {
    val declaration: ClassOrInterfaceType
    val modifiers: AccessModifiers
}
