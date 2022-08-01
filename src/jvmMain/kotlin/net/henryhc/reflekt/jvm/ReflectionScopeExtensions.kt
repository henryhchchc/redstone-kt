package net.henryhc.reflekt.jvm

import net.henryhc.reflekt.AccessModifiers
import net.henryhc.reflekt.ReflectionScope
import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.types.GenericType
import net.henryhc.reflekt.elements.types.TypeVariable
import javax.swing.ComponentInputMap

/**
 * Adds a JVM class to the reflection scope.
 * @param jvmClass The JVM class.
 */
fun ReflectionScope.addClass(jvmClass: JvmClass) = resolveNewTypes {
    val newJvmTypes = mutableSetOf<JvmType>().also {
        DeepRecursiveFunction<JvmType, _> { t ->
            if (t is JvmWildcardType) {
                (t.upperBounds + t.lowerBounds).forEach { callRecursive(it) }
            } else if (t !in it && t.qualifiedTypeName !in typesInScope) {
                it.add(t)
                t.dependencies.forEach { callRecursive(it) }
            }
        }.invoke(jvmClass)
    }

    newJvmTypes.filterIsInstance<JvmTypeVariable>().associate {
        it.qualifiedTypeName to TypeVariable(it.name, it.bounds.map { b -> newTypeReference(b.qualifiedTypeName) })
    }.also(newlyResolvedTypes::putAll)

    fun JvmType.resolveReference(): TypeReference {
        val materialization = if (this is JvmParameterizedType)
            (this.rawType as JvmClass).typeParameters.zip(this.actualTypeArguments).associate { (t, a) ->
                findResolvedTypeVariable(t.qualifiedTypeName) to a.resolveReference()
            } else emptyMap()
        return newTypeReference(this.qualifiedTypeName, materialization)
    }

    newJvmTypes.filterIsInstance<JvmClass>().associate { c ->
        val typeParams = c.typeParameters.map { findResolvedTypeVariable(it.qualifiedTypeName) }
        val gt = GenericType(
            c.typeName,
            modifiers = AccessModifiers(c.modifiers),
            typeParameters = typeParams,
            superType = (c.genericSuperclass ?: c.superclass)?.resolveReference(),
            implementedInterfaces = c.genericInterfaces.map { it.resolveReference() }
        )
        typeParams.forEach { it.declaration = gt }
        gt.name to gt
    }.also(newlyResolvedTypes::putAll)

    val x = 10

}

private val JvmType.dependencies: List<JvmType>
    get() = when (this) {
        is JvmClass -> interfaces.toList() + genericInterfaces.toList() + typeParameters.toList() + listOfNotNull(
            superclass,
            genericSuperclass
        ) + methods.flatMap { it.dependencies }

        is JvmParameterizedType -> listOf(rawType) + actualTypeArguments
        is JvmTypeVariable -> bounds.toList()
        is JvmGenericArrayType -> listOf(genericComponentType)
        else -> throw NotImplementedError()
    }

private val JvmMethod.dependencies: List<JvmType>
    get() = listOf(genericReturnType) + genericParameterTypes.toList() + typeParameters.toList()

private val JvmType.qualifiedTypeName: String
    get() = when {
        this is JvmClass -> typeName
        this is JvmParameterizedType -> rawType.qualifiedTypeName
        this is JvmTypeVariable && this.genericDeclaration is JvmType -> "${(genericDeclaration as JvmType).typeName}->$name"
        this is JvmTypeVariable && this.genericDeclaration is JvmMethod -> {
            val decl = (genericDeclaration as JvmMethod)
            val desc = "${decl.name}(${decl.parameterTypes.joinToString(",") { it.qualifiedTypeName }})"
            "${decl.declaringClass.qualifiedTypeName}::${desc}->$name"
        }

        this is JvmGenericArrayType -> "${genericComponentType.qualifiedTypeName}[]"
        else -> throw NotImplementedError()
    }

