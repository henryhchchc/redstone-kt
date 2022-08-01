package net.henryhc.reflekt.elements.members

import net.henryhc.reflekt.AccessModifiers
import net.henryhc.reflekt.elements.types.GenericType

/**
 * Denotes a member.
 * @property declaration The [GenericType] where it is declared.
 * @property modifiers The access modifiers.
 * @see Field
 * @see Method
 */
interface Member {
    val declaration: GenericType
    val modifiers: AccessModifiers
}
