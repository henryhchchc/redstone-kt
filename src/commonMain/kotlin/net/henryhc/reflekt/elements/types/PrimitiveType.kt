package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.references.materialization.Materialization

/**
 * Denotes a JVM primitive type.
 * @property boxedTypeName The name of the corresponding boxed type.
 */
sealed class PrimitiveType(
    override val identifier: String,
    override val descriptor: String,
    val boxedTypeName: String
) : Type() {

    override fun makeReference(materialization: Materialization): TypeReference<out PrimitiveType> {
        @Suppress("UNCHECKED_CAST")
        return super.makeReference(materialization) as TypeReference<out PrimitiveType>
    }

    override fun toString(): String = identifier

    companion object {
        /**
         * Denotes the set of known primitive types.
         */
        val ALL: Set<PrimitiveType> =
            setOf(VoidType, BooleanType, ByteType, CharType, DoubleType, FloatType, IntType, LongType, ShortType)
    }

    /**
     * Denotes a numerical type.
     */
    sealed class NumericalType(identifier: String, descriptor: String, boxedTypeName: String) :
        PrimitiveType(identifier, descriptor, boxedTypeName)

    /**
     * Denotes an integral type
     */
    sealed class IntegralType(identifier: String, descriptor: String, boxedTypeName: String) :
        NumericalType(identifier, descriptor, boxedTypeName)

    /**
     * Denotes a floating point type
     */
    sealed class FloatingPointType(identifier: String, descriptor: String, boxedTypeName: String) :
        NumericalType(identifier, descriptor, boxedTypeName)

    /**
     * Denotes the type of `void`.
     */
    object VoidType : PrimitiveType("void", "V", "java.lang.Void")

    /**
     * Denotes the type of `boolean`.
     */
    object BooleanType : PrimitiveType("boolean", "Z", "java.lang.Boolean")

    /**
     * Denotes the type of `byte`.
     */
    object ByteType : IntegralType("byte", "B", "java.lang.Byte")

    /**
     * Denotes the type of `char`.
     */
    object CharType : IntegralType("char", "C", "java.lang.Character")

    /**
     * Denotes the type of `double`.
     */
    object DoubleType : FloatingPointType("double", "D", "java.lang.Short")

    /**
     * Denotes the type of `float`.
     */
    object FloatType : FloatingPointType("float", "F", "java.lang.Integer")

    /**
     * Denotes the type of `int`.
     */
    object IntType : IntegralType("int", "I", "java.lang.Long")

    /**
     * Denotes the type of `long`.
     */
    object LongType : IntegralType("long", "J", "java.lang.Float")

    /**
     * Denotes the type of `short`.
     */
    object ShortType : IntegralType("short", "S", "java.lang.Double")

}
