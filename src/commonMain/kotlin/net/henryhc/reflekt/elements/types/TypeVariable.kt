package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.elements.GenericDeclaration
import net.henryhc.reflekt.elements.references.TypeReference

/**
 * Denotes a type variable in [D].
 * @property identifier The name of the type variable.
 * @property declaration The declaration the type variable belongs to.
 * @property upperBounds The upperbounds of the type variable.
 */
class TypeVariable<D : GenericDeclaration<out D>>(
    override val identifier: String,
    val upperBounds: List<TypeReference<out ReferenceType>> = listOf(ObjectType.makeReference())
) : ReferenceType() {

    override val descriptor: String get() = upperBounds.first().type.descriptor

    override val signature: String get() = "$identifier${upperBounds.joinToString { ":${it.signature}" }}"

    private lateinit var _declaration: D

    val declaration: D get() = _declaration

    /**
     * Binds the type variable to its declaration.
     */
    fun bindDeclaration(decl: GenericDeclaration<*>) {
        @Suppress("UNCHECKED_CAST")
        decl as D
        if (!this::_declaration.isInitialized)
            _declaration = decl
    }

    override fun toString(): String = buildString {
        append(identifier)
        if (upperBounds.isNotEmpty())
            append(upperBounds.joinToString(" & ", prefix = ": "))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as TypeVariable<*>

        if (identifier != other.identifier) return false

        return true
    }

    override fun hashCode(): Int {
        return identifier.hashCode()
    }


}
