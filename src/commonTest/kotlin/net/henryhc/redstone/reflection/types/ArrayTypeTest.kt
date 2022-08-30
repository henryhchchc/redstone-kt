package net.henryhc.redstone.reflection.types

import net.henryhc.redstone.reflection.elements.types.ObjectType
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

    @Test
    fun testDimension() {
        assertEquals(1, ObjectType.makeArrayType().dimension)
        assertEquals(2, ObjectType.makeArrayType().makeArrayType().dimension)
        assertEquals(
            5,
            ObjectType
                .makeArrayType()
                .makeArrayType()
                .makeArrayType()
                .makeArrayType()
                .makeArrayType()
                .dimension
        )
    }
}
