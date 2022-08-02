package net.henryhc.reflekt

import arrow.core.*
import net.henryhc.reflekt.elements.members.Constructor
import net.henryhc.reflekt.elements.members.Field
import net.henryhc.reflekt.elements.members.Method
import net.henryhc.reflekt.elements.references.DanglingTypeReference
import net.henryhc.reflekt.elements.references.Materialization
import net.henryhc.reflekt.elements.types.PrimitiveType
import net.henryhc.reflekt.elements.types.ReferenceType
import net.henryhc.reflekt.elements.types.Type
import net.henryhc.reflekt.elements.types.TypeVariable


/**
 * Denotes a reflection scope.
 */
class ReflectionScope {

    private val typeMap: MutableMap<String, Type> = buildMap {
        PrimitiveType.ALL.forEach { this[it.name] = it }
    }.toMutableMap()

    private val methods: MutableMap<ReferenceType, Set<Method>> = mutableMapOf()
    private val constructors = mutableMapOf<ReferenceType, Set<Constructor>>()
    private val fields = mutableMapOf<ReferenceType, Set<Field>>()

    /**
     * Tells whether a given type name is in the scope.
     * @param qualifiedName The fully qualified type name.
     */
    operator fun contains(qualifiedName: String): Boolean = typeMap.contains(qualifiedName)

    /**
     * Gets a [ReferenceType] from the scope by type name.
     * @param qualifiedName The fully qualified type name.
     * @return The [ReferenceType] if found, `null` otherwise.
     */
    operator fun get(qualifiedName: String): ReferenceType? = getTypeByName(qualifiedName).getOrElse { null }

    /**
     * Gets a [ReferenceType] from the scope by type name.
     * @param qualifiedName The fully qualified type name.
     * @return A [Some] if found, [None] otherwise.
     */
    fun getTypeByName(qualifiedName: String): Option<ReferenceType> =
        (typeMap[qualifiedName] as? ReferenceType).toOption()

    /**
     * Gets the set of methods of the given type.
     * @param referenceType The type.
     * @return A [Some] if found, [None] otherwise.
     */
    fun getMethods(referenceType: ReferenceType): Option<Set<Method>> = methods[referenceType].toOption()

    /**
     * Gets the set of constructors of the given type.
     * @param referenceType The type.
     * @return A [Some] if found, [None] otherwise.
     */
    fun getConstructors(referenceType: ReferenceType): Option<Set<Constructor>> = constructors[referenceType].toOption()

    /**
     * Gets the set of fields of the given type.
     * @param referenceType The type.
     * @return A [Some] if found, [None] otherwise.
     */
    fun getFields(referenceType: ReferenceType): Option<Set<Field>> = fields[referenceType].toOption()

    internal fun resolveNewTypes(block: ResolutionContext.() -> Unit) = ResolutionContext(this, block).resolve()

    internal class ResolutionContext(
        private val scope: ReflectionScope,
        private val block: ResolutionContext.() -> Unit
    ) {

        private val danglingTypeReferences = mutableMapOf<DanglingTypeReference, String>()
        private val typesInScope get() = scope.typeMap

        val newlyResolvedTypeVariablesForReferenceTypes = mutableMapOf<String, TypeVariable<ReferenceType>>()

        val newlyResolvedTypeVariablesForMethods = mutableMapOf<String, TypeVariable<Method>>()

        val newlyResolvedTypes = mutableMapOf<String, ReferenceType>()

        val newlyResolvedMethods = mutableMapOf<ReferenceType, Set<Method>>()

        val newlyResolvedConstructors = mutableMapOf<ReferenceType, Set<Constructor>>()

        val newlyResolvedFields = mutableMapOf<ReferenceType, Set<Field>>()

        fun findResolvedTypeVariable(typeName: String, varName: String) =
            if (typeName in scope || typeName in newlyResolvedTypes)
                findResolvedType(typeName).typeParameters.single { it.name == varName }
            else
                newlyResolvedTypeVariablesForReferenceTypes.getValue("$typeName->$varName")

        fun findResolvedTypeVariable(typeName: String, methodSig: String, varName: String): TypeVariable<Method> =
            newlyResolvedTypeVariablesForMethods.getValue("$typeName::$methodSig->$varName")

        fun findResolvedType(qualifiedName: String) =
            scope.getTypeByName(qualifiedName).getOrElse { newlyResolvedTypes.getValue(qualifiedName) }

        fun newTypeReference(qualifiedName: String, materialization: Materialization = Materialization.EMPTY) =
            DanglingTypeReference(materialization).also { danglingTypeReferences[it] = qualifiedName }

        fun newTypeVariableReference(typeName: String, varName: String) =
            findResolvedTypeVariable(typeName, varName).makeReference()

        fun newTypeVariableReference(typeName: String, methodSig: String, varName: String) =
            findResolvedTypeVariable(typeName, methodSig, varName).makeReference()

        fun resolve() {
            this.block()
            danglingTypeReferences.forEach { (r, t) ->
                r.bind(scope.getTypeByName(t).getOrElse { newlyResolvedTypes.getValue(t) })
            }
            // Ensure that the lazy property is initialized by accessing the getter
            newlyResolvedTypeVariablesForMethods.values.forEach { it.declaration }
            newlyResolvedTypeVariablesForReferenceTypes.values.forEach { it.declaration }

            scope.typeMap.putAll(newlyResolvedTypes.filterKeys { it !in typesInScope })
            scope.methods.putAll(newlyResolvedMethods)
            scope.constructors.putAll(newlyResolvedConstructors)
            scope.fields.putAll(newlyResolvedFields)
        }

    }

}
