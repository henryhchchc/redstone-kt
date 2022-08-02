package net.henryhc.reflekt

import kotlin.test.Test
import kotlin.test.assertEquals


internal class AccessModifiersTest {

    @Test
    fun testCombination() {
        assertEquals(
            AccessModifiers(7),
            AccessModifiers(1) and AccessModifiers(2) and AccessModifiers(4)
        )
    }
}
