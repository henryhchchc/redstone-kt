package net.henryhc.reflekt.jvm

import net.henryhc.reflekt.ReflectionScope
import net.henryhc.reflekt.elements.types.ObjectType
import net.henryhc.reflekt.elements.types.PrimitiveType
import net.henryhc.reflekt.elements.types.ReferenceType
import org.example.ComplicatedType
import org.example.I3
import org.example.SimpleType
import org.example.TypeWithMembers
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class ReflectionScopeExtensionsTest {

    @Test
    fun testAddEssentialTypes() {
        val scope = ReflectionScope()
        scope.addEssentialTypes()
        assertTrue { PrimitiveType.ALL.all { it.boxedTypeName in scope } }
    }

    @Test
    fun addSimpleClass() {
        val scope = ReflectionScope()
        val jvmType = SimpleType::class.java
        scope.addClass(jvmType)

        assertTrue { jvmType.name in scope }
        val type = scope[jvmType.name] as ReferenceType
        assertNotNull(type.superType)
        assertEquals(ObjectType, type.superType?.type)
    }

    @Test
    fun addRecursiveInterface() {
        val scope = ReflectionScope()
        val jvmType = I3::class.java
        scope.addClass(jvmType)

        assertTrue { jvmType.name in scope }
        val type = scope[jvmType.name] as ReferenceType
        assertEquals(1, type.typeParameters.size)
        val tv1 = type.typeParameters.single()
        assertEquals(1, tv1.upperBounds.size)
        val ub = tv1.upperBounds.single()
        assertEquals(1, ub.materialization.size)
    }

    @Test
    fun addComplicatedType() {
        val scope = ReflectionScope()
        val complicatedType = ComplicatedType::class.java
        val simpleType = SimpleType::class.java
        scope.addClass(complicatedType)

        assertTrue { complicatedType.name in scope }
        assertTrue { simpleType.name in scope }

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

        assertTrue { jvmType.name in scope }
        val type = scope[jvmType.name] as ReferenceType
        scope.getMethods((type))
            .also { assertTrue { it.isDefined() } }
            .tap { assertEquals(2, it.size) }
    }

    @Test
    fun addClassWithFields() {
        val scope = ReflectionScope()
        val jvmType = TypeWithMembers::class.java
        scope.addClass(jvmType)

        assertTrue { jvmType.name in scope }
        val type = scope[jvmType.name] as ReferenceType
        scope.getFields(type)
            .also { assertTrue { it.isDefined() } }
            .tap { assertEquals(1, it.size) }
    }

    @Test
    fun addClassWithConstructors() {
        val scope = ReflectionScope()
        val jvmType = TypeWithMembers::class.java
        scope.addClass(jvmType)

        assertTrue { jvmType.name in scope }
        val type = scope[jvmType.name] as ReferenceType
        scope.getConstructors(type)
            .also { assertTrue { it.isDefined() } }
            .tap { assertEquals(1, it.size) }
    }

}
