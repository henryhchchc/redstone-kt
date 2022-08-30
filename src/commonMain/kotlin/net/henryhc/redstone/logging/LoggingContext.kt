package net.henryhc.redstone.logging

import mu.KLogger

/**
 * A context providing access to a [KLogger]
 */
interface LoggingContext {

    /**
     * The [KLogger] in the current context.
     */
    val logger: KLogger

}
