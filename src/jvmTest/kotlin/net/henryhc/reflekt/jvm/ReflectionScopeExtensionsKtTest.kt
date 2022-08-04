package net.henryhc.reflekt.jvm

import arrow.core.None
import arrow.core.Some
import net.henryhc.reflekt.ReflectionScope
import net.henryhc.reflekt.elements.types.ReferenceType
import net.henryhc.reflekt.elements.types.ObjectType
import net.henryhc.reflekt.elements.types.PrimitiveType
import org.example.ComplicatedType
import org.example.SimpleType
import org.example.TypeWithMembers
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.fail

internal class ReflectionScopeExtensionsTest {


    @Test
    fun testAddEssentialTypes() {
        val scope = ReflectionScope()
        scope.addEssentialTypes()
        assert(PrimitiveType.ALL.all { it.boxedTypeName in scope })
    }

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
        val jvmType = TypeWithMembers::class.java
        scope.addClass(jvmType)

        assert(jvmType.name in scope)
        val type = scope[jvmType.name] as ReferenceType
        when (val m = scope.getMethods(type)) {
            None -> fail("Fail to get the methods")
            is Some -> assertEquals(2, m.value.size)
        }
    }

    @Test
    fun addClassWithFields() {
        val scope = ReflectionScope()
        val jvmType = TypeWithMembers::class.java
        scope.addClass(jvmType)

        assert(jvmType.name in scope)
        val type = scope[jvmType.name] as ReferenceType
        when (val f = scope.getFields(type)) {
            None -> fail("Fail to get the fields")
            is Some -> assertEquals(1, f.value.size)
        }
    }


    @Test
    fun addClassWithConstructors() {
        val scope = ReflectionScope()
        val jvmType = TypeWithMembers::class.java
        scope.addClass(jvmType)

        assert(jvmType.name in scope)
        val type = scope[jvmType.name] as ReferenceType
        when (val f = scope.getConstructors(type)) {
            None -> fail("Fail to get the constructors")
            is Some -> assertEquals(1, f.value.size)
        }
    }

}
