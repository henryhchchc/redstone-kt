package net.henryhc.reflekt.jvm

import net.henryhc.reflekt.AccessModifiers
import net.henryhc.reflekt.ReflectionScope
import net.henryhc.reflekt.elements.members.Constructor
import net.henryhc.reflekt.elements.members.Field
import net.henryhc.reflekt.elements.members.Member
import net.henryhc.reflekt.elements.members.Method
import net.henryhc.reflekt.elements.references.FlexibleTypeReference
import net.henryhc.reflekt.elements.references.Materialization
import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.references.WildcardTypeReference
import net.henryhc.reflekt.elements.types.ArrayType
import net.henryhc.reflekt.elements.types.ReferenceType
import net.henryhc.reflekt.elements.types.TypeVariable
import net.henryhc.reflekt.elements.types.WildcardType

/**
 * Adds a JVM class to the reflection scope.
 * @param jvmClass The JVM class.
 */
fun ReflectionScope.addClass(jvmClass: JvmClass) = resolveNewTypes {
    val newJvmTypes = buildSet<JvmType> {
        DeepRecursiveFunction<JvmType, _> {
            if (it !in this@buildSet) {
                if ((it is JvmClass && !it.isArray) || it is JvmTypeVariable) add(it)
                it.dependencies.forEach { callRecursive(it) }
            }
        }.invoke(jvmClass)
    }

    newJvmTypes.filterIsInstance<JvmTypeVariable>().associate {
        it.qualifiedTypeName to TypeVariable(it.name, it.bounds.map { b -> newTypeReference(b.qualifiedTypeName) })
    }.also(newlyResolvedTypes::putAll)

    newJvmTypes.filterIsInstance<JvmClass>().map { resolve(it) }.associateBy { it.name }
        .also(newlyResolvedTypes::putAll)

    val methods = newJvmTypes.filterIsInstance<JvmClass>().flatMap { it.declaredMethods.toList() }.distinct()
    val constructors = newJvmTypes.filterIsInstance<JvmClass>().flatMap { it.declaredConstructors.toList() }.distinct()
    val fields = newJvmTypes.filterIsInstance<JvmClass>().flatMap { it.declaredFields.toList() }.distinct()

    methods.map { resolve(it) }.groupByDeclaration().also(newlyResolvedMethods::putAll)
    constructors.map { resolve(it) }.groupByDeclaration().also(newlyResolvedConstructors::putAll)
    fields.map { resolve(it) }.groupByDeclaration().also(newlyResolvedFields::putAll)

}

private fun ReflectionScope.ResolutionContext.makeReference(jvmType: JvmType): TypeReference = when {
    jvmType is JvmParameterizedType -> newTypeReference(jvmType.qualifiedTypeName, generateMaterialization(jvmType))
    jvmType is JvmClass && !jvmType.isArray -> newTypeReference(jvmType.qualifiedTypeName)
    jvmType is JvmClass && jvmType.isArray -> FlexibleTypeReference().also { it.bind(ArrayType(makeReference(jvmType.componentType))) }
    jvmType is JvmGenericArrayType -> FlexibleTypeReference().also { it.bind(ArrayType(makeReference(jvmType.genericComponentType))) }
    jvmType is JvmTypeVariable -> newTypeReference(jvmType.qualifiedTypeName)
    jvmType is JvmWildcardType -> WildcardTypeReference(resolve(jvmType))
    else -> throw NotImplementedError()
}

private inline fun <reified T : Member> List<T>.groupByDeclaration() =
    this.groupBy { it.declaration }.mapValues { (_, v) -> v.toSet() }

private fun ReflectionScope.ResolutionContext.generateMaterialization(jvmType: JvmParameterizedType) =
    Materialization(jvmType.run { (rawType as JvmClass).typeParameters.zip(actualTypeArguments) }.associate { (t, a) ->
        findResolvedTypeVariable(t.qualifiedTypeName) to makeReference(a)
    })

private fun ReflectionScope.ResolutionContext.resolve(jvmType: JvmWildcardType) =
    WildcardType(upperBounds = jvmType.upperBounds.map { makeReference(it) },
        lowerBounds = jvmType.lowerBounds.map { makeReference(it) })

private fun ReflectionScope.ResolutionContext.resolve(f: JvmField) = Field(
    name = f.name,
    type = makeReference(f.genericType),
    modifiers = AccessModifiers(f.modifiers),
    declaration = findResolvedType(f.declaringClass.qualifiedTypeName) as ReferenceType
)

private fun ReflectionScope.ResolutionContext.resolve(method: JvmMethod): Method {
    val typeParams = method.typeParameters.map { findResolvedTypeVariable(it.qualifiedTypeName) }
    return Method(
        name = method.name,
        returnType = makeReference(method.genericReturnType),
        modifiers = AccessModifiers(method.modifiers),
        declaration = findResolvedType(method.declaringClass.qualifiedTypeName) as ReferenceType,
        parameterTypes = method.genericParameterTypes.map { makeReference(it) },
        typeParameters = typeParams
    ).also { gm -> typeParams.forEach { it.declaration = gm } }
}

private fun ReflectionScope.ResolutionContext.resolve(constructor: JvmConstructor) =
    Constructor(modifiers = AccessModifiers(constructor.modifiers),
        declaration = findResolvedType(constructor.declaringClass.qualifiedTypeName) as ReferenceType,
        parameterTypes = constructor.genericParameterTypes.map { makeReference(it) })

private fun ReflectionScope.ResolutionContext.resolve(jvmClass: JvmClass): ReferenceType {
    val typeParams = jvmClass.typeParameters.map { findResolvedTypeVariable(it.qualifiedTypeName) }
    return ReferenceType(name = jvmClass.typeName,
        modifiers = AccessModifiers(jvmClass.modifiers),
        typeParameters = typeParams,
        superType = (jvmClass.genericSuperclass ?: jvmClass.superclass)?.let { makeReference(it) },
        implementedInterfaces = jvmClass.genericInterfaces.map { makeReference(it) }).also { gt ->
        typeParams.forEach {
            it.declaration = gt
        }
    }
}

private val JvmType.dependencies: List<JvmType>
    get() = when {
        this is JvmClass && this.isArray -> listOf(this.componentType)
        this is JvmClass -> this.dependencies
        this is JvmParameterizedType -> listOf(rawType) + actualTypeArguments
        this is JvmTypeVariable -> bounds.toList()
        this is JvmGenericArrayType -> listOf(genericComponentType)
        this is JvmWildcardType -> upperBounds.toList() + lowerBounds.toList()
        else -> throw NotImplementedError()
    }

private val JvmClass.dependencies: List<JvmType>
    get() = listOf(interfaces.toList(),
        genericInterfaces.toList(),
        typeParameters.toList(),
        declaredClasses.toList(),
        listOfNotNull(superclass, genericSuperclass),
        declaredMethods.flatMap { it.dependencies },
        declaredFields.flatMap { it.dependencies },
        declaredConstructors.flatMap { it.dependencies }).flatten().distinct()

private val JvmMethod.dependencies: List<JvmType>
    get() = listOf(
        listOf(genericReturnType),
        genericParameterTypes.toList(),
        typeParameters.toList()
    ).flatten().distinct()

private val JvmConstructor.dependencies: List<JvmType> get() = genericParameterTypes.toList()

private val JvmField.dependencies: List<JvmType> get() = listOf(genericType)

private val JvmType.qualifiedTypeName: String
    get() = when {
        this is JvmClass -> typeName
        this is JvmParameterizedType -> rawType.qualifiedTypeName
        this is JvmTypeVariable && this.genericDeclaration is JvmType -> "${(genericDeclaration as JvmType).typeName}->$name"
        this is JvmTypeVariable && this.genericDeclaration is JvmMethod -> "${(genericDeclaration as JvmMethod).qualifiedName}->$name"
        this is JvmGenericArrayType -> "${genericComponentType.qualifiedTypeName}[]"
        this is JvmWildcardType -> "?"
        else -> throw NotImplementedError()
    }

private val JvmMethod.qualifiedName
    get() = buildString {
        append(declaringClass.qualifiedTypeName)
        append("::")
        append(name)
        append('(')
        append(parameterTypes.joinToString(",") { it.qualifiedTypeName })
        append(')')
    }
