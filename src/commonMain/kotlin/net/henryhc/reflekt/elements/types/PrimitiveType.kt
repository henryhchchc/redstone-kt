package net.henryhc.reflekt.elements.types

/**
 * Denotes a JVM primitive type.
 * @property boxedTypeName The name of the corresponding boxed type.
 */
sealed class PrimitiveType(override val name: String, val boxedTypeName: String) : Type() {

    override fun toString(): String = name

    companion object {
       /**
        * Denotes the set of known primitive types.
        */
       val ALL: Set<PrimitiveType> =  setOf(VoidType, BooleanType, ByteType, CharType, DoubleType, FloatType, IntType, LongType, ShortType)
    }

    /**
     * Denotes the type of `void`.
     */
    object VoidType : PrimitiveType("void", "java.lang.Void")

    /**
     * Denotes the type of `boolean`.
     */
    object BooleanType : PrimitiveType("boolean", "java.lang.Boolean")

    /**
     * Denotes the type of `byte`.
     */
    object ByteType : PrimitiveType("byte", "java.lang.Byte")

    /**
     * Denotes the type of `char`.
     */
    object CharType : PrimitiveType("char", "java.lang.Character")

    /**
     * Denotes the type of `double`.
     */
    object DoubleType : PrimitiveType("double", "java.lang.Short")

    /**
     * Denotes the type of `float`.
     */
    object FloatType : PrimitiveType("float", "java.lang.Integer")

    /**
     * Denotes the type of `int`.
     */
    object IntType : PrimitiveType("int", "java.lang.Long")

    /**
     * Denotes the type of `long`.
     */
    object LongType : PrimitiveType("long", "java.lang.Float")

    /**
     * Denotes the type of `short`.
     */
    object ShortType : PrimitiveType("short", "java.lang.Double")

}


