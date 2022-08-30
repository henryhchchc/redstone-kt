package net.henryhc.redstone.jdk

/**
 * A context providing access to a [JDKEnvironment]
 * @see UnixJDKEnvironment
 */
interface JDKContext {

    /**
     * The [JDKEnvironment] in the current context.
     */
    val jdkEnvironment: JDKEnvironment

}
