package net.henryhc.redstone.reflection.references

import net.henryhc.redstone.reflection.elements.references.FixedTypeReference
import net.henryhc.redstone.reflection.elements.types.ObjectType
import net.henryhc.redstone.reflection.elements.types.ClassType
import net.henryhc.redstone.reflection.elements.types.TypeVariable
import net.henryhc.redstone.reflection.elements.types.UnknownType
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

        val tr1 = FixedTypeReference(type, listOf(ObjectType.makeReference(), UnknownType.makeReference()))
        val tr2 = FixedTypeReference(type, listOf(ObjectType.makeReference(), UnknownType.makeReference()))

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

        val tr1 = FixedTypeReference(type, listOf(ObjectType.makeReference(), UnknownType.makeReference()))
        val tr2 = FixedTypeReference(type, listOf(UnknownType.makeReference(), ObjectType.makeReference()))

        assertNotEquals(tr1, tr2)
    }

    @Test
    fun equalWithIrrelevantMaterialization() {
        val tv1 = TypeVariable<ClassType>("A", listOf(ObjectType.makeReference()))
        val type = ClassType(
            identifier = "org.example.Foo",
            superType = ObjectType.makeReference(),
            typeParameters = listOf(tv1)
        )
        tv1.bindDeclaration(type)

        val tr1 = FixedTypeReference(type, listOf(type.makeReference(listOf(UnknownType.makeReference()))))
        val tr2 = FixedTypeReference(type, listOf(ObjectType.makeReference()))

        assertNotEquals(tr1, tr2)
    }
}
