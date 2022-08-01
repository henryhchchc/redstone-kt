@file:Suppress("KDocMissingDocumentation")

package net.henryhc.reflekt.elements.types

val knownPrimitiveTypes =
    setOf(VoidType, BooleanType, ByteType, CharType, DoubleType, FloatType, IntType, LongType, ShortType)

object ObjectType : GenericType("java.lang.Object", superType = null)

object VoidType : PrimitiveType("void", BoxedVoidType)

object BooleanType : PrimitiveType("boolean", BoxedBooleanType)
object ByteType : PrimitiveType("byte", BoxedByteType)
object CharType : PrimitiveType("char", BoxedCharType)
object DoubleType : PrimitiveType("double", BoxedDoubleType)
object FloatType : PrimitiveType("float", BoxedFloatType)
object IntType : PrimitiveType("int", BoxedIntType)
object LongType : PrimitiveType("long", BoxedLongType)
object ShortType : PrimitiveType("short", BoxedShortType)

object BoxedVoidType : BoxedPrimitiveType("java.lang.Void")

object BoxedBooleanType : BoxedPrimitiveType("java.lang.Boolean")
object BoxedByteType : BoxedPrimitiveType("java.lang.Byte")
object BoxedCharType : BoxedPrimitiveType("java.lang.Character")
object BoxedShortType : BoxedPrimitiveType("java.lang.Short")
object BoxedIntType : BoxedPrimitiveType("java.lang.Integer")
object BoxedLongType : BoxedPrimitiveType("java.lang.Long")
object BoxedFloatType : BoxedPrimitiveType("java.lang.Float")
object BoxedDoubleType : BoxedPrimitiveType("java.lang.Double")

