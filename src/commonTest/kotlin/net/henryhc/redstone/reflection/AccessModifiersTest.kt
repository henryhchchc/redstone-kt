package net.henryhc.redstone.reflection

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


internal class AccessModifiersTest {

    @Test
    fun testCombination() {
        assertEquals(
            net.henryhc.redstone.reflection.AccessModifiers(7),
            net.henryhc.redstone.reflection.AccessModifiers(1) and net.henryhc.redstone.reflection.AccessModifiers(2) and net.henryhc.redstone.reflection.AccessModifiers(
                4
            )
        )
    }

    @Test
    fun testContains() {
        assertTrue { net.henryhc.redstone.reflection.AccessModifiers(1) in net.henryhc.redstone.reflection.AccessModifiers(
            7
        )
        }
        assertTrue { net.henryhc.redstone.reflection.AccessModifiers(1) !in net.henryhc.redstone.reflection.AccessModifiers(
            8
        )
        }
    }
}
