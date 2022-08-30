package net.henryhc.redstone.reflection.elements.types

import net.henryhc.redstone.reflection.elements.GenericDeclaration
import net.henryhc.redstone.reflection.elements.references.TypeReference

/**
 * Denotes a type variable in [D].
 * @property identifier The name of the type variable.
 * @property declaration The declaration the type variable belongs to.
 * @property upperBounds The upperbounds of the type variable.
 */
class TypeVariable<D : GenericDeclaration<D>>(
    override val identifier: String,
    val upperBounds: List<TypeReference<ReferenceType>> = listOf(ObjectType.makeReference())
) : ReferenceType() {

    override val descriptor: String get() = upperBounds.first().descriptor

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
        if (other !is TypeVariable<*>) return false
//        if (!super.equals(other)) return false

        if (identifier != other.identifier) return false

        return true
    }

    override fun hashCode(): Int = identifier.hashCode()


}
