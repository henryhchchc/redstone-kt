package net.henryhc.reflekt.types

import net.henryhc.reflekt.elements.references.DanglingTypeReference
import net.henryhc.reflekt.elements.types.ObjectType
import net.henryhc.reflekt.elements.types.ClassType
import net.henryhc.reflekt.elements.types.ReferenceType
import net.henryhc.reflekt.elements.types.TypeVariable
import kotlin.test.Test
import kotlin.test.assertNotEquals

internal class ReferenceTypeTest {

    @Test
    fun equalityTest() {

        // org.example.Foo<B extends Foo<B>>
        val tvBound = DanglingTypeReference<ReferenceType>()
        val bUpperBound = DanglingTypeReference<TypeVariable<ClassType>>(listOf(tvBound))
        val tv = TypeVariable<ClassType>("B", listOf(bUpperBound))
        tvBound.bind(tv)
        val type = ClassType(
            identifier = "org.example.Foo",
            superType = ObjectType.makeReference(),
            typeParameters = listOf(tv)
        )
        bUpperBound.bind(type)
        tv.bindDeclaration(type)


        // org.example.Foo<B extends Object>
        val tv2 = TypeVariable<ClassType>("B", listOf(ObjectType.makeReference()))
        val type2 = ClassType(
            identifier = "org.example.Foo",
            superType = ObjectType.makeReference(),
            typeParameters = listOf(tv2)
        )
        tv2.bindDeclaration(type2)

        assertNotEquals(type, type2)

    }
}
