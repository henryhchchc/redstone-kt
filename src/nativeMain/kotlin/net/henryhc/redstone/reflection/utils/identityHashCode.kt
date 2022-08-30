package net.henryhc.redstone.reflection.utils

import kotlin.native.identityHashCode

/**
 * Gets the object identifier.
 */
actual fun Any.identityHashCode(): Int  = identityHashCode()
