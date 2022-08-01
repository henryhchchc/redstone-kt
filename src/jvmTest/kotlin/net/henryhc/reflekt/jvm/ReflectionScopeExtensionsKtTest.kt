package net.henryhc.reflekt.jvm

import net.henryhc.reflekt.ReflectionScope
import net.henryhc.reflekt.elements.types.GenericType
import net.henryhc.reflekt.elements.types.ObjectType
import org.example.ComplicatedType
import org.example.SimpleType
import org.example.TypeWithMethod
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class ReflectionScopeExtensionsTest {

    @Test
    fun addSimpleClass() {
        val scope = ReflectionScope()
        val jvmType = SimpleType::class.java
        scope.addClass(jvmType)

        assert(jvmType.name in scope)
        val type = scope.knownTypes.getValue(jvmType.name) as GenericType
        assertNotNull(type.superType)
        assertEquals(ObjectType, type.superType?.type)
    }

    @Test
    fun addComplicatedType() {
        val scope = ReflectionScope()
        val complicatedType = ComplicatedType::class.java
        val simpleType = SimpleType::class.java
        scope.addClass(complicatedType)

        assert(complicatedType.name in scope)
        assert(simpleType.name in scope)

        val complicatedGenericType = scope.knownTypes.getValue(complicatedType.name) as GenericType
        val simpleGenericType = scope.knownTypes.getValue(simpleType.name) as GenericType

        assertNotNull(complicatedGenericType.superType)
        assertEquals(simpleGenericType, complicatedGenericType.superType?.type)

        assertEquals(2, complicatedGenericType.implementedInterfaces.size)
    }



    @Test
    fun addClassWithMethod() {
        val scope = ReflectionScope()
        val jvmType = TypeWithMethod::class.java
        scope.addClass(jvmType)

        assert(jvmType.name in scope)
        val type = scope.knownTypes.getValue(jvmType.name) as GenericType
        assertNotNull(type.superType)
        assertEquals(ObjectType, type.superType?.type)
    }
}
