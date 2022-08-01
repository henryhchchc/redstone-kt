package net.henryhc.reflekt

import kotlin.jvm.JvmInline


/**
 * The access modifiers for JVM elements.
 * @property rawValue The raw [Int] value.
 */
@JvmInline
value class AccessModifiers(val rawValue: Int) {

    /**
     * Combines the [AccessModifiers] with another.
     */
    infix fun and(other: AccessModifiers) = AccessModifiers(this.rawValue or other.rawValue)

    /**
     * Tells if the current [AccessModifiers] includes another.
     */
    operator fun contains(other: AccessModifiers) = (this.rawValue and other.rawValue) == other.rawValue

    /**
     * Contains a set of known modifiers.
     */
    @Suppress("KDocMissingDocumentation")
    companion object Known {
        val ACC_PUBLIC = AccessModifiers(0x0001)
        val ACC_PRIVATE = AccessModifiers(0x0002)
        val ACC_PROTECTED = AccessModifiers(0x0004)
        val ACC_STATIC = AccessModifiers(0x0008)
        val ACC_FINAL = AccessModifiers(0x0010)
        val ACC_SUPER = AccessModifiers(0x0020)
        val ACC_SYNCHRONIZED = AccessModifiers(0x0020)
        val ACC_OPEN = AccessModifiers(0x0020)
        val ACC_TRANSITIVE = AccessModifiers(0x0020)
        val ACC_VOLATILE = AccessModifiers(0x0040)
        val ACC_BRIDGE = AccessModifiers(0x0040)
        val ACC_STATIC_PHASE = AccessModifiers(0x0040)
        val ACC_VARARGS = AccessModifiers(0x0080)
        val ACC_TRANSIENT = AccessModifiers(0x0080)
        val ACC_NATIVE = AccessModifiers(0x0100)
        val ACC_INTERFACE = AccessModifiers(0x0200)
        val ACC_ABSTRACT = AccessModifiers(0x0400)
        val ACC_STRICT = AccessModifiers(0x0800)
        val ACC_SYNTHETIC = AccessModifiers(0x1000)
        val ACC_ANNOTATION = AccessModifiers(0x2000)
        val ACC_ENUM = AccessModifiers(0x4000)
        val ACC_MANDATED = AccessModifiers(0x8000)
        val ACC_MODULE = AccessModifiers(0x8000)
    }
}

