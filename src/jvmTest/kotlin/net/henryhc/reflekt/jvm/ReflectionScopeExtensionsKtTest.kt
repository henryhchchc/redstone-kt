package net.henryhc.reflekt.jvm

import net.henryhc.reflekt.ReflectionScope
import net.henryhc.reflekt.elements.types.ReferenceType
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
        val type = scope[jvmType.name] as ReferenceType
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

        val complicatedReferenceType = scope[complicatedType.name] as ReferenceType
        val simpleReferenceType = scope[simpleType.name] as ReferenceType

        assertNotNull(complicatedReferenceType.superType)
        assertEquals(simpleReferenceType, complicatedReferenceType.superType?.type)

        assertEquals(2, complicatedReferenceType.implementedInterfaces.size)
    }



    @Test
    fun addClassWithMethod() {
        val scope = ReflectionScope()
        val jvmType = TypeWithMethod::class.java
        scope.addClass(jvmType)

        assert(jvmType.name in scope)
        val type = scope[jvmType.name] as ReferenceType
        assertNotNull(type.superType)
        assertEquals(ObjectType, type.superType?.type)
    }
}
