@file:Suppress("KDocMissingDocumentation")

package net.henryhc.reflekt.elements.types

import net.henryhc.reflekt.AccessModifiers.Known.ACC_FINAL
import net.henryhc.reflekt.AccessModifiers.Known.ACC_PUBLIC

/**
 * Denotes a boxed type of [PrimitiveType].
 */
sealed class BoxedPrimitiveType(name: String) : GenericType(name, modifiers = ACC_PUBLIC and ACC_FINAL)

