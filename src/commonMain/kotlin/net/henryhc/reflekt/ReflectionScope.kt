package net.henryhc.reflekt

import arrow.core.*
import net.henryhc.reflekt.elements.members.Constructor
import net.henryhc.reflekt.elements.members.Field
import net.henryhc.reflekt.elements.members.Method
import net.henryhc.reflekt.elements.references.DanglingTypeReference
import net.henryhc.reflekt.elements.references.materialization.DanglingMaterialization
import net.henryhc.reflekt.elements.references.materialization.Materialization
import net.henryhc.reflekt.elements.types.*


/**
 * Denotes a reflection scope.
 */
class ReflectionScope {

    private val typeMap: MutableMap<String, Type> = buildMap {
        PrimitiveType.ALL.forEach { this[it.name] = it }
//        this[ObjectType.name] = ObjectType
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
     * Gets a [Type] from the scope by type name.
     * @param qualifiedName The fully qualified type name.
     * @return The [Type] if found, `null` otherwise.
     */
    operator fun get(qualifiedName: String): Type? = getTypeByName(qualifiedName).getOrElse { null }

    /**
     * Gets a [Type] from the scope by type name.
     * @param qualifiedName The fully qualified type name.
     * @return A [Some] if found, [None] otherwise.
     */
    fun getTypeByName(qualifiedName: String): Option<Type> = typeMap[qualifiedName].toOption()

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
        val typesInScope get() = scope.typeMap

        val newlyResolvedTypeVariablesForReferenceTypes = mutableMapOf<String, TypeVariable<ReferenceType>>()

        val newlyResolvedTypeVariablesForMethods = mutableMapOf<String, TypeVariable<Method>>()

        val newlyResolvedTypes = mutableMapOf<String, ReferenceType>()

        val newlyResolvedMethods = mutableMapOf<ReferenceType, Set<Method>>()

        val newlyResolvedConstructors = mutableMapOf<ReferenceType, Set<Constructor>>()

        val newlyResolvedFields = mutableMapOf<ReferenceType, Set<Field>>()

        val danglingMaterializations = mutableSetOf<DanglingMaterialization>()


        private val qualifiedNameRegex =
            "(?<typeName>[\\w.\$]+)(::(?<methodSig>\\w+\\(.*?\\)))?->(?<varName>\\w+)".toRegex()

        fun findTypeVariable(qualifiedName: String): TypeVariable<*> {
            val match = qualifiedNameRegex.matchEntire(qualifiedName)!!
            val typeName = match.groups[1]!!.value
            val methodSig = match.groups[3]?.value
            val varName = match.groups[4]!!.value
            return if (methodSig != null) {
                findResolvedTypeVariable(typeName, methodSig, varName)
            } else {
                findResolvedTypeVariable(typeName, varName)
            }
        }

        fun findResolvedTypeVariable(typeName: String, varName: String) =
            if (typeName in scope || typeName in newlyResolvedTypes)
                (findResolvedType(typeName) as ReferenceType).typeParameters.single { it.name == varName }
            else
                newlyResolvedTypeVariablesForReferenceTypes.getValue("$typeName->$varName")

        fun findResolvedTypeVariable(typeName: String, methodSig: String, varName: String): TypeVariable<Method> =
            newlyResolvedTypeVariablesForMethods.getValue("$typeName::$methodSig->$varName")

        fun findResolvedType(qualifiedName: String) =
            scope.getTypeByName(qualifiedName).getOrElse { newlyResolvedTypes.getValue(qualifiedName) }

        fun newTypeReference(qualifiedName: String, materialization: Materialization = Materialization.EMPTY) =
            DanglingTypeReference(materialization).also { danglingTypeReferences[it] = qualifiedName }

        fun newTypeVariableReference(typeName: String, varName: String) =
            DanglingTypeReference().also { danglingTypeReferences[it] = "$typeName->$varName" }

        fun newTypeVariableReference(typeName: String, methodSig: String, varName: String) =
            DanglingTypeReference().also { danglingTypeReferences[it] = "$typeName::$methodSig->$varName" }

        fun resolve() {
            this.block()
            danglingMaterializations.forEach { it.bind { tv -> findTypeVariable(tv) } }
            danglingTypeReferences.forEach { (r, t) ->
                if (t.contains("->")) {
                    r.bind(findTypeVariable(t))
                } else {
                    r.bind(scope.getTypeByName(t).getOrElse { newlyResolvedTypes.getValue(t) })
                }
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
