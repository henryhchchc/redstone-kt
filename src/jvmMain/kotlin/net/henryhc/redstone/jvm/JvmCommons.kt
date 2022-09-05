package net.henryhc.redstone.jvm

/**
 * A combination of [takeIf] and [use] that [use] the object if [predicate] matches.
 */
inline fun <reified T : AutoCloseable, R> T.useIf(predicate: (T) -> Boolean, block: (T) -> R): R? = use {
    it.takeIf(predicate)?.let(block)
}
