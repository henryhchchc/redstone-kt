package net.henryhc.reflekt.utils

/**
 * Gets the object identifier.
 */
actual fun Any.identityHashCode(): Int = System.identityHashCode(this)
