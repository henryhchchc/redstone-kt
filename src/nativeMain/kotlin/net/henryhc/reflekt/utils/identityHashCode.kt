package net.henryhc.reflekt.utils

import kotlin.native.identityHashCode

/**
 * Gets the object identifier.
 */
actual fun Any.identityHashCode(): Int  = identityHashCode()
