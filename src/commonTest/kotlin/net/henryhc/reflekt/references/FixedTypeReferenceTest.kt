package net.henryhc.reflekt.references

import net.henryhc.reflekt.elements.references.FixedTypeReference
import net.henryhc.reflekt.elements.references.materialization.Materialization.Companion.materialize
import net.henryhc.reflekt.elements.types.ObjectType
import net.henryhc.reflekt.elements.types.ReferenceType
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
        val tv1 = TypeVariable<ReferenceType>("A", listOf(ObjectType.makeReference()))
        val tv2 = TypeVariable<ReferenceType>("B", listOf(ObjectType.makeReference()))
        val type = ReferenceType(
            name = "org.example.Foo",
            superType = ObjectType.makeReference(),
            typeParameters = listOf(tv1, tv2)
        )
        tv1.declaration = type
        tv2.declaration = type

        val tr1 = FixedTypeReference(type, materialize(tv1 to ObjectType.makeReference()))
        val tr2 = FixedTypeReference(type, materialize(tv1 to ObjectType.makeReference()))

        assertEquals(tr1, tr2)
    }

    @Test
    fun notEqualWithDifferentMaterialization() {
        val tv1 = TypeVariable<ReferenceType>("A", listOf(ObjectType.makeReference()))
        val tv2 = TypeVariable<ReferenceType>("B", listOf(ObjectType.makeReference()))
        val type = ReferenceType(
            name = "org.example.Foo",
            superType = ObjectType.makeReference(),
            typeParameters = listOf(tv1, tv2)
        )
        tv1.declaration = type
        tv2.declaration = type

        val tr1 = FixedTypeReference(type, materialize(tv1 to ObjectType.makeReference()))
        val tr2 = FixedTypeReference(type, materialize(tv2 to ObjectType.makeReference()))

        assertNotEquals(tr1, tr2)
    }

    @Test
    fun equalWithIrrelevantMaterialization() {
        val tv1 = TypeVariable<ReferenceType>("A", listOf(ObjectType.makeReference()))
        val tv2 = TypeVariable<ReferenceType>("B", listOf(ObjectType.makeReference()))
        val type = ReferenceType(
            name = "org.example.Foo",
            superType = ObjectType.makeReference(),
            typeParameters = listOf(tv1)
        )
        tv1.declaration = type
        tv2.declaration = type

        val tr1 = FixedTypeReference(type, materialize(tv2 to type.makeReference()))
        val tr2 = FixedTypeReference(type, materialize(tv2 to ObjectType.makeReference()))

        assertEquals(tr1, tr2)
    }
}
