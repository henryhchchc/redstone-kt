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
    infix fun and(other: AccessModifiers): AccessModifiers = AccessModifiers(this.rawValue or other.rawValue)

    /**
     * Tells if the current [AccessModifiers] includes another.
     */
    operator fun contains(other: AccessModifiers): Boolean = (this.rawValue and other.rawValue) == other.rawValue


    override fun toString(): String = mapOf(
        ACC_PUBLIC to "public",
        ACC_PRIVATE to "private",
        ACC_PROTECTED to "protected",
        ACC_STATIC to "static",
        ACC_FINAL to "final",
        ACC_SUPER to "super",
        ACC_SYNCHRONIZED to "synchronized",
        ACC_OPEN to "open",
        ACC_TRANSITIVE to "transitive",
        ACC_VOLATILE to "volatile",
        ACC_BRIDGE to "bridge",
        ACC_STATIC_PHASE to "static_phase",
        ACC_VARARGS to "varargs",
        ACC_TRANSIENT to "transient",
        ACC_NATIVE to "native",
        ACC_INTERFACE to "interface",
        ACC_ABSTRACT to "abstract",
        ACC_STRICT to "strict",
        ACC_SYNTHETIC to "synthetic",
        ACC_ANNOTATION to "annotation",
        ACC_ENUM to "enum",
        ACC_MANDATED to "mandated",
        ACC_MODULE to "module",
    ).filter { (k, _) -> k in this }.values.joinToString(" ")

    /**
     * Contains a set of known modifiers.
     */
    @Suppress("KDocMissingDocumentation")
    companion object Known {
        val ACC_PUBLIC: AccessModifiers = AccessModifiers(0x0001)
        val ACC_PRIVATE: AccessModifiers = AccessModifiers(0x0002)
        val ACC_PROTECTED: AccessModifiers = AccessModifiers(0x0004)
        val ACC_STATIC: AccessModifiers = AccessModifiers(0x0008)
        val ACC_FINAL: AccessModifiers = AccessModifiers(0x0010)
        val ACC_SUPER: AccessModifiers = AccessModifiers(0x0020)
        val ACC_SYNCHRONIZED: AccessModifiers = AccessModifiers(0x0020)
        val ACC_OPEN: AccessModifiers = AccessModifiers(0x0020)
        val ACC_TRANSITIVE: AccessModifiers = AccessModifiers(0x0020)
        val ACC_VOLATILE: AccessModifiers = AccessModifiers(0x0040)
        val ACC_BRIDGE: AccessModifiers = AccessModifiers(0x0040)
        val ACC_STATIC_PHASE: AccessModifiers = AccessModifiers(0x0040)
        val ACC_VARARGS: AccessModifiers = AccessModifiers(0x0080)
        val ACC_TRANSIENT: AccessModifiers = AccessModifiers(0x0080)
        val ACC_NATIVE: AccessModifiers = AccessModifiers(0x0100)
        val ACC_INTERFACE: AccessModifiers = AccessModifiers(0x0200)
        val ACC_ABSTRACT: AccessModifiers = AccessModifiers(0x0400)
        val ACC_STRICT: AccessModifiers = AccessModifiers(0x0800)
        val ACC_SYNTHETIC: AccessModifiers = AccessModifiers(0x1000)
        val ACC_ANNOTATION: AccessModifiers = AccessModifiers(0x2000)
        val ACC_ENUM: AccessModifiers = AccessModifiers(0x4000)
        val ACC_MANDATED: AccessModifiers = AccessModifiers(0x8000)
        val ACC_MODULE: AccessModifiers = AccessModifiers(0x8000)
    }
}

