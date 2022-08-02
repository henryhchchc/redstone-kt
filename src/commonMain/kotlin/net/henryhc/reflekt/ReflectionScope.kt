package net.henryhc.reflekt

import arrow.core.getOrElse
import arrow.core.toOption
import net.henryhc.reflekt.elements.members.Constructor
import net.henryhc.reflekt.elements.members.Field
import net.henryhc.reflekt.elements.members.Method
import net.henryhc.reflekt.elements.references.FlexibleTypeReference
import net.henryhc.reflekt.elements.references.Materialization
import net.henryhc.reflekt.elements.references.ObjectTypeReference
import net.henryhc.reflekt.elements.types.*


class ReflectionScope {

    private val typeMap: MutableMap<String, Type> = buildMap {
        knownPrimitiveTypes.forEach {
            this[it.name] = it
            this[it.boxedType.name] = it.boxedType
        }
        ObjectType.also { this[it.name] = it }
    }.toMutableMap()

    private val methods: MutableMap<ReferenceType, Set<Method>> = mutableMapOf()
    private val constructors = mutableMapOf<ReferenceType, Set<Constructor>>()
    private val fields = mutableMapOf<ReferenceType, Set<Field>>()

    operator fun contains(qualifiedName: String) = typeMap.contains(qualifiedName)

    operator fun get(qualifiedName: String) = getTypeByName(qualifiedName).getOrElse { null }

    fun getTypeByName(qualifiedName: String) = (typeMap[qualifiedName] as? ReferenceType).toOption()

    fun getTypeVariable(typeName: String, variableName: String) =
        (typeMap["$typeName->$variableName"] as? TypeVariable).toOption()

    internal fun resolveNewTypes(block: ResolutionContext.() -> Unit) = ResolutionContext(this, block).resolve()

    internal class ResolutionContext(
        private val scope: ReflectionScope,
        private val block: ResolutionContext.() -> Unit
    ) {

        private val danglingTypeReferences = mutableMapOf<FlexibleTypeReference, String>()
        private val typesInScope get() = scope.typeMap

        val newlyResolvedTypes = mutableMapOf<String, Type>()

        val newlyResolvedMethods = mutableMapOf<ReferenceType, Set<Method>>()

        val newlyResolvedConstructors = mutableMapOf<ReferenceType, Set<Constructor>>()

        val newlyResolvedFields = mutableMapOf<ReferenceType, Set<Field>>()

        fun findResolvedTypeVariable(qualifiedName: String) =
            scope.getTypeByName(qualifiedName).getOrElse { newlyResolvedTypes.getValue(qualifiedName) } as TypeVariable

        fun findResolvedType(qualifiedName: String) =
            scope.getTypeByName(qualifiedName).getOrElse { newlyResolvedTypes.getValue(qualifiedName) }

        fun newTypeReference(qualifiedName: String, materialization: Materialization = Materialization.EMPTY) =
            if (qualifiedName == ObjectType.name) ObjectTypeReference
            else FlexibleTypeReference(materialization).also { danglingTypeReferences[it] = qualifiedName }

        fun resolve() {
            this.block()
            danglingTypeReferences.forEach { (r, t) ->
                r.bind(scope.getTypeByName(t).getOrElse { newlyResolvedTypes.getValue(t) })
            }
            // Ensure that the lazy property is initialized by accessing the getter
            newlyResolvedTypes.values.filterIsInstance<TypeVariable>().forEach { it.declaration }

            scope.typeMap.putAll(newlyResolvedTypes.filterKeys { it !in typesInScope })
            scope.methods.putAll(newlyResolvedMethods)
            scope.constructors.putAll(newlyResolvedConstructors)
            scope.fields.putAll(newlyResolvedFields)
        }

    }

}
