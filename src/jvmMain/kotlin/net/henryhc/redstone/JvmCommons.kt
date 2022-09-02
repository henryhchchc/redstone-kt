package net.henryhc.redstone

/**
 * A combination of [takeIf] and [use] what [use] the object is [predicate] matches.
 */
inline fun <reified T : AutoCloseable, R> T.useIf(predicate: (T) -> Boolean, block: (T) -> R): R? = use {
    it.takeIf(predicate)?.let(block)
}
