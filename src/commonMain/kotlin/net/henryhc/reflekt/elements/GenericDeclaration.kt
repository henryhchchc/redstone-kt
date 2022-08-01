package net.henryhc.reflekt.elements

import net.henryhc.reflekt.elements.types.TypeVariable

/**
 * Denotes a declaration with type parameters.
 * @property typeParameters The type parameters at the declaration.
 */
interface GenericDeclaration {
    val typeParameters: List<TypeVariable>
}
