package net.henryhc.reflekt.types

import net.henryhc.reflekt.elements.references.DanglingTypeReference
import net.henryhc.reflekt.elements.references.materialization.DanglingMaterialization
import net.henryhc.reflekt.elements.types.ObjectType
import net.henryhc.reflekt.elements.types.ReferenceType
import net.henryhc.reflekt.elements.types.TypeVariable
import kotlin.test.Test
import kotlin.test.assertNotEquals

internal class ReferenceTypeTest {

    @Test
    fun equalityTest() {

        // org.example.Foo<B extends Foo<B>>
        val tvBound = DanglingTypeReference()
        val materialization = DanglingMaterialization(mapOf("org.example.Foo->B" to tvBound))
        val bUpperBound = DanglingTypeReference(materialization)
        val tv = TypeVariable<ReferenceType>("B", listOf(bUpperBound))
        tvBound.bind(tv)
        materialization.bind { tv }
        val type = ReferenceType(
            name = "org.example.Foo",
            superType = ObjectType.makeReference(),
            typeParameters = listOf(tv)
        )
        bUpperBound.bind(type)
        tv.declaration = type


        // org.example.Foo<B extends Object>
        val tv2 = TypeVariable<ReferenceType>("B", listOf(ObjectType.makeReference()))
        val type2 = ReferenceType(
            name = "org.example.Foo",
            superType = ObjectType.makeReference(),
            typeParameters = listOf(tv2)
        )
        tv2.declaration = type2

        assertNotEquals(type, type2)

    }
}
