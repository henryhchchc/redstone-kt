package net.henryhc.reflekt

import arrow.core.toOption
import net.henryhc.reflekt.elements.GenericDeclaration
import net.henryhc.reflekt.elements.references.FlexibleTypeReference
import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.types.*


class ReflectionScope {

    private val typeMap: MutableMap<String, Type> = buildMap {
        knownPrimitiveTypes.forEach {
            this[it.name] = it
            this[it.boxedType.name] = it.boxedType
        }
        ObjectType.also { this[it.name] = it }
    }.toMutableMap()

    val knownTypes: Map<String, Type> = typeMap

    operator fun contains(qualifiedName: String) = knownTypes.contains(qualifiedName)

    fun getTypeByName(qualifiedName: String) = (knownTypes[qualifiedName] as? GenericType).toOption()

    fun getTypeVariable(typeName: String, variableName: String) =
        (knownTypes["$typeName->$variableName"] as? TypeVariable).toOption()

    internal fun resolveNewTypes(block: ResolutionContext.() -> Unit) = ResolutionContext(this, block).resolve()

    class ResolutionContext(
        private val scope: ReflectionScope,
        private val block: ResolutionContext.() -> Unit
    ) {

        private val danglingTypeReferences = mutableMapOf<FlexibleTypeReference, String>()

        val typesInScope get() = scope.typeMap

        val newlyResolvedTypes = mutableMapOf<String, Type>()

        fun findResolvedTypeVariable(qualifiedName: String) =
            (typesInScope[qualifiedName] ?: newlyResolvedTypes.getValue(qualifiedName)) as TypeVariable

        fun newTypeReference(
            qualifiedName: String,
            materialization: Map<TypeVariable, TypeReference> = emptyMap()
        ): TypeReference = FlexibleTypeReference(materialization)
            .also { danglingTypeReferences[it] = qualifiedName }

        fun resolve() {
            this.block()
            danglingTypeReferences.forEach { (r, t) -> r.bind(typesInScope[t] ?: newlyResolvedTypes.getValue(t)) }
            // Ensure that the lazy property is initialized by accessing the getter
            newlyResolvedTypes.values.filterIsInstance<TypeVariable>().forEach { it.declaration }
            scope.typeMap.putAll(newlyResolvedTypes)
        }

    }


}
