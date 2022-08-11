package net.henryhc.reflekt.types

import net.henryhc.reflekt.elements.references.DanglingTypeReference
import net.henryhc.reflekt.elements.references.materialization.DanglingMaterialization
import net.henryhc.reflekt.elements.types.ObjectType
import net.henryhc.reflekt.elements.types.ClassOrInterfaceType
import net.henryhc.reflekt.elements.types.ReferenceType
import net.henryhc.reflekt.elements.types.TypeVariable
import kotlin.test.Test
import kotlin.test.assertNotEquals

internal class ReferenceTypeTest {

    @Test
    fun equalityTest() {

        // org.example.Foo<B extends Foo<B>>
        val tvBound = DanglingTypeReference<ReferenceType>()
        val materialization = DanglingMaterialization(mapOf("org.example.Foo->B" to tvBound))
        val bUpperBound = DanglingTypeReference<TypeVariable<ClassOrInterfaceType>>(materialization)
        val tv = TypeVariable<ClassOrInterfaceType>("B", listOf(bUpperBound))
        tvBound.bind(tv)
        materialization.bind { tv }
        val type = ClassOrInterfaceType(
            identifier = "org.example.Foo",
            superType = ObjectType.makeReference(),
            typeParameters = listOf(tv)
        )
        bUpperBound.bind(type)
        tv.bindDeclaration(type)


        // org.example.Foo<B extends Object>
        val tv2 = TypeVariable<ClassOrInterfaceType>("B", listOf(ObjectType.makeReference()))
        val type2 = ClassOrInterfaceType(
            identifier = "org.example.Foo",
            superType = ObjectType.makeReference(),
            typeParameters = listOf(tv2)
        )
        tv2.bindDeclaration(type2)

        assertNotEquals(type, type2)

    }
}
