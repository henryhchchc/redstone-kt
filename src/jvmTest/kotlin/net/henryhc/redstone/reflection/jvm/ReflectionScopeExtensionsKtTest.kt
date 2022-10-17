package net.henryhc.redstone.reflection.jvm

import io.kotest.assertions.arrow.core.shouldBeSome
import io.kotest.matchers.collections.shouldHaveSize
import net.henryhc.redstone.reflection.ReflectionScope
import net.henryhc.redstone.reflection.elements.types.ObjectType
import net.henryhc.redstone.reflection.elements.types.PrimitiveType
import net.henryhc.redstone.reflection.elements.types.ClassType
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
        val type = scope[jvmType.name] as ClassType
        assertNotNull(type.superType)
        assertEquals(ObjectType, type.superType?.type)
    }

    @Test
    fun addRecursiveInterface() {
        val scope = ReflectionScope()
        val jvmType = I3::class.java
        scope.addClass(jvmType)

        assertTrue { jvmType.name in scope }
        val type = scope[jvmType.name] as ClassType
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

        val complicatedClassType = scope[complicatedType.name] as ClassType
        val simpleClassType = scope[simpleType.name] as ClassType

        assertNotNull(complicatedClassType.superType)
        assertEquals(simpleClassType, complicatedClassType.superType?.type)

        assertEquals(2, complicatedClassType.implementedInterfaces.size)
    }


    @Test
    fun addClassWithMethod() {
        val scope = ReflectionScope()
        val jvmType = TypeWithMembers::class.java
        scope.addClass(jvmType)

        assertTrue { jvmType.name in scope }
        val type = scope[jvmType.name] as ClassType
        scope.getMethods((type)).shouldBeSome().shouldHaveSize(2)
    }

    @Test
    fun addClassWithFields() {
        val scope = ReflectionScope()
        val jvmType = TypeWithMembers::class.java
        scope.addClass(jvmType)

        assertTrue { jvmType.name in scope }
        val type = scope[jvmType.name] as ClassType
        scope.getFields(type).shouldBeSome().shouldHaveSize(1)
    }

    @Test
    fun addClassWithConstructors() {
        val scope = ReflectionScope()
        val jvmType = TypeWithMembers::class.java
        scope.addClass(jvmType)

        assertTrue { jvmType.name in scope }
        val type = scope[jvmType.name] as ClassType
        scope.getConstructors(type).shouldBeSome().shouldHaveSize(1)
    }

}
