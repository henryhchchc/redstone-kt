package net.henryhc.reflekt.elements.members

import net.henryhc.reflekt.AccessModifiers
import net.henryhc.reflekt.elements.types.ReferenceType

/**
 * Denotes a member.
 * @property declaration The [ReferenceType] where it is declared.
 * @property modifiers The access modifiers.
 * @see Field
 * @see Method
 */
interface Member {
    val declaration: ReferenceType
    val modifiers: AccessModifiers
}
