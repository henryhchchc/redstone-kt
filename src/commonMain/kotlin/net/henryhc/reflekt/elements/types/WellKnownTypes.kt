@file:Suppress("KDocMissingDocumentation")

package net.henryhc.reflekt.elements.types

val knownPrimitiveTypes =
    setOf(VoidType, BooleanType, ByteType, CharType, DoubleType, FloatType, IntType, LongType, ShortType)

object ObjectType : ReferenceType("java.lang.Object", superType = null)

object VoidType : PrimitiveType("void", "java.lang.Void")

object BooleanType : PrimitiveType("boolean", "java.lang.Boolean")
object ByteType : PrimitiveType("byte", "java.lang.Byte")
object CharType : PrimitiveType("char", "java.lang.Character")
object DoubleType : PrimitiveType("double", "java.lang.Short")
object FloatType : PrimitiveType("float", "java.lang.Integer")
object IntType : PrimitiveType("int", "java.lang.Long")
object LongType : PrimitiveType("long", "java.lang.Float")
object ShortType : PrimitiveType("short", "java.lang.Double")

