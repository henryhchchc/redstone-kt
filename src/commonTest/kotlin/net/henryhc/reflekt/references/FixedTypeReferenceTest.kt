package net.henryhc.reflekt.references

import net.henryhc.reflekt.elements.references.FixedTypeReference
import net.henryhc.reflekt.elements.references.materialization.Materialization.Companion.materialize
import net.henryhc.reflekt.elements.types.ObjectType
import net.henryhc.reflekt.elements.types.ClassType
import net.henryhc.reflekt.elements.types.TypeVariable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class FixedTypeReferenceTest {

    @Test
    fun equalityWithoutMaterialization() {
        assertEquals(
            ObjectType.makeReference(),
            ObjectType.makeReference()
        )
    }

    @Test
    fun equalityWithMaterialization() {
        val tv1 = TypeVariable<ClassType>("A", listOf(ObjectType.makeReference()))
        val tv2 = TypeVariable<ClassType>("B", listOf(ObjectType.makeReference()))
        val type = ClassType(
            identifier = "org.example.Foo",
            superType = ObjectType.makeReference(),
            typeParameters = listOf(tv1, tv2)
        )
        tv1.bindDeclaration(type)
        tv2.bindDeclaration(type)

        val tr1 = FixedTypeReference(type, materialize(tv1 to ObjectType.makeReference()))
        val tr2 = FixedTypeReference(type, materialize(tv1 to ObjectType.makeReference()))

        assertEquals(tr1, tr2)
    }

    @Test
    fun notEqualWithDifferentMaterialization() {
        val tv1 = TypeVariable<ClassType>("A", listOf(ObjectType.makeReference()))
        val tv2 = TypeVariable<ClassType>("B", listOf(ObjectType.makeReference()))
        val type = ClassType(
            identifier = "org.example.Foo",
            superType = ObjectType.makeReference(),
            typeParameters = listOf(tv1, tv2)
        )
        tv1.bindDeclaration(type)
        tv2.bindDeclaration(type)

        val tr1 = FixedTypeReference(type, materialize(tv1 to ObjectType.makeReference()))
        val tr2 = FixedTypeReference(type, materialize(tv2 to ObjectType.makeReference()))

        assertNotEquals(tr1, tr2)
    }

    @Test
    fun equalWithIrrelevantMaterialization() {
        val tv1 = TypeVariable<ClassType>("A", listOf(ObjectType.makeReference()))
        val tv2 = TypeVariable<ClassType>("B", listOf(ObjectType.makeReference()))
        val type = ClassType(
            identifier = "org.example.Foo",
            superType = ObjectType.makeReference(),
            typeParameters = listOf(tv1)
        )
        tv1.bindDeclaration(type)
        tv2.bindDeclaration(type)

        val tr1 = FixedTypeReference(type, materialize(tv2 to type.makeReference()))
        val tr2 = FixedTypeReference(type, materialize(tv2 to ObjectType.makeReference()))

        assertEquals(tr1, tr2)
    }
}
