package net.henryhc.redstone.reflection.utils

/**
 * Gets the object identifier.
 */
actual fun Any.identityHashCode(): Int = System.identityHashCode(this)
