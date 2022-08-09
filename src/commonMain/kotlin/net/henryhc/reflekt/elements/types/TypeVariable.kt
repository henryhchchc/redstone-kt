package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.elements.GenericDeclaration
import net.henryhc.reflekt.elements.references.TypeReference

/**
 * Denotes a type variable in [D].
 * @property name The name of the type variable.
 * @property declaration The declaration the type variable belongs to.
 * @property upperBounds The upperbounds of the type variable.
 */
class TypeVariable<D : GenericDeclaration<D>>(
    override val name: String,
    val upperBounds: List<TypeReference>
) : Type() {

    private lateinit var _declaration: D

    var declaration: D
        get() = _declaration
        set(value) {
            if (!this::_declaration.isInitialized)
                _declaration = value
        }

    override fun toString(): String = buildString {
        append(name)
        if (upperBounds.isNotEmpty())
            append(upperBounds.joinToString(", ", prefix = ": "))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as TypeVariable<*>

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }


}
