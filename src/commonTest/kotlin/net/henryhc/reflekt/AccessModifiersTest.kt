package net.henryhc.reflekt

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


internal class AccessModifiersTest {

    @Test
    fun testCombination() {
        assertEquals(
            AccessModifiers(7),
            AccessModifiers(1) and AccessModifiers(2) and AccessModifiers(4)
        )
    }

    @Test
    fun testContains() {
        assertTrue { AccessModifiers(1) in AccessModifiers(7) }
        assertTrue { AccessModifiers(1) !in AccessModifiers(8) }
    }
}
