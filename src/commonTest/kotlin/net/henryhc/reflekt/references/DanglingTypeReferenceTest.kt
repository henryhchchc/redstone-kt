package net.henryhc.reflekt.references

import net.henryhc.reflekt.elements.references.DanglingTypeReference
import net.henryhc.reflekt.elements.types.ObjectType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class DanglingTypeReferenceTest {

    @Test
    fun danglingOnesShouldNotEqual() {
        val tv1 = DanglingTypeReference()
        val tv2 = DanglingTypeReference()
        assertNotEquals(tv1, tv2)
    }

    @Test
    fun boundShouldNotEqual() {
        val tv1 = DanglingTypeReference()
        val tv2 = DanglingTypeReference()
        tv1.bind(ObjectType)
        tv2.bind(ObjectType)
        assertEquals(tv1, tv2)
    }
}
