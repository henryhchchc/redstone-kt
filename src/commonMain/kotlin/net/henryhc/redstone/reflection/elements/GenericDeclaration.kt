package net.henryhc.redstone.reflection.elements

import net.henryhc.redstone.reflection.elements.types.TypeVariable

/**
 * Denotes a declaration with type parameters.
 * @property typeParameters The type parameters at the declaration.
 */
interface GenericDeclaration<out D : GenericDeclaration<D>> {
    val typeParameters: List<TypeVariable<out D>>
}
