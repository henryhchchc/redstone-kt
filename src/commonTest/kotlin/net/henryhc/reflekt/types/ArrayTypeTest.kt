package net.henryhc.reflekt.types

import net.henryhc.reflekt.elements.types.ObjectType
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ArrayTypeTest {

    @Test
    fun testEquality() {
        assertEquals(
            ObjectType.makeArrayType(),
            ObjectType.makeArrayType()
        )
    }
}
