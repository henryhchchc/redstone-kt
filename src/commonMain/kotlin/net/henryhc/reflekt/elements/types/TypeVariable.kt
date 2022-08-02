package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.elements.GenericDeclaration
import net.henryhc.reflekt.elements.references.TypeReference

/**
 * Denotes a type variable in [GenericDeclaration].
 * @property name The name of the type variable.
 * @property declaration The declaration the type variable belongs to.
 * @property upperBounds The upperbounds of the type variable.
 */
class TypeVariable(
    override val name: String,
    val upperBounds: List<TypeReference>
) : Type() {

    private lateinit var _declaration: GenericDeclaration

    var declaration
        get() = _declaration
        set(value) {
            if (!this::_declaration.isInitialized)
                _declaration = value
        }

    override fun toString(): String = buildString {
        append(name)
        val displayedBounds = upperBounds.filterNot { it.type == ObjectType }
        if (displayedBounds.isNotEmpty()) {
            append(": ")
            append(displayedBounds.joinToString(", "))
        }
    }
}
