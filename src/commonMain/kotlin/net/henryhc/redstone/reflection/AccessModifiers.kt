package net.henryhc.redstone.reflection

import kotlin.jvm.JvmInline


/**
 * The access modifiers for JVM elements.
 */
@JvmInline
value class AccessModifiers(private val rawValue: Int) {

    /**
     * Combines the [AccessModifiers] with another.
     */
    infix fun and(other: net.henryhc.redstone.reflection.AccessModifiers): net.henryhc.redstone.reflection.AccessModifiers =
        net.henryhc.redstone.reflection.AccessModifiers(this.rawValue or other.rawValue)

    /**
     * Tells if the current [AccessModifiers] includes another.
     */
    operator fun contains(other: net.henryhc.redstone.reflection.AccessModifiers): Boolean = (this.rawValue and other.rawValue) == other.rawValue


    override fun toString(): String = mapOf(
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_PUBLIC to "public",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_PRIVATE to "private",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_PROTECTED to "protected",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_STATIC to "static",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_FINAL to "final",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_SUPER to "super",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_SYNCHRONIZED to "synchronized",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_OPEN to "open",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_TRANSITIVE to "transitive",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_VOLATILE to "volatile",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_BRIDGE to "bridge",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_STATIC_PHASE to "static_phase",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_VARARGS to "varargs",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_TRANSIENT to "transient",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_NATIVE to "native",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_INTERFACE to "interface",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_ABSTRACT to "abstract",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_STRICT to "strict",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_SYNTHETIC to "synthetic",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_ANNOTATION to "annotation",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_ENUM to "enum",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_MANDATED to "mandated",
        net.henryhc.redstone.reflection.AccessModifiers.Known.ACC_MODULE to "module",
    ).filter { (k, _) -> k in this }.values.joinToString(" ")

    /**
     * Contains a set of known modifiers.
     */
    @Suppress("KDocMissingDocumentation")
    companion object Known {
        val ACC_PUBLIC: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x0001)
        val ACC_PRIVATE: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x0002)
        val ACC_PROTECTED: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x0004)
        val ACC_STATIC: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x0008)
        val ACC_FINAL: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x0010)
        val ACC_SUPER: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x0020)
        val ACC_SYNCHRONIZED: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x0020)
        val ACC_OPEN: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x0020)
        val ACC_TRANSITIVE: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x0020)
        val ACC_VOLATILE: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x0040)
        val ACC_BRIDGE: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x0040)
        val ACC_STATIC_PHASE: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x0040)
        val ACC_VARARGS: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x0080)
        val ACC_TRANSIENT: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x0080)
        val ACC_NATIVE: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x0100)
        val ACC_INTERFACE: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x0200)
        val ACC_ABSTRACT: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x0400)
        val ACC_STRICT: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x0800)
        val ACC_SYNTHETIC: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x1000)
        val ACC_ANNOTATION: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x2000)
        val ACC_ENUM: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x4000)
        val ACC_MANDATED: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x8000)
        val ACC_MODULE: net.henryhc.redstone.reflection.AccessModifiers =
            net.henryhc.redstone.reflection.AccessModifiers(0x8000)
    }
}

