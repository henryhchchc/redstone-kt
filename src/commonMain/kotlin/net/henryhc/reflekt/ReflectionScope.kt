package net.henryhc.reflekt

import arrow.core.*
import net.henryhc.reflekt.elements.members.Constructor
import net.henryhc.reflekt.elements.members.Field
import net.henryhc.reflekt.elements.members.Method
import net.henryhc.reflekt.elements.references.DanglingTypeReference
import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.types.*


/**
 * Denotes a reflection scope.
 */
class ReflectionScope {

    private val typeMap: MutableMap<String, Type> = buildMap {
        PrimitiveType.ALL.forEach { this[it.identifier] = it }
//        this[ObjectType.name] = ObjectType
    }.toMutableMap()

    private val methods: MutableMap<ClassType, Set<Method>> = mutableMapOf()
    private val constructors = mutableMapOf<ClassType, Set<Constructor>>()
    private val fields = mutableMapOf<ClassType, Set<Field>>()

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
     * @param classType The type.
     * @return A [Some] if found, [None] otherwise.
     */
    fun getMethods(classType: ClassType): Option<Set<Method>> = methods[classType].toOption()

    /**
     * Gets the set of constructors of the given type.
     * @param classType The type.
     * @return A [Some] if found, [None] otherwise.
     */
    fun getConstructors(classType: ClassType): Option<Set<Constructor>> = constructors[classType].toOption()

    /**
     * Gets the set of fields of the given type.
     * @param classType The type.
     * @return A [Some] if found, [None] otherwise.
     */
    fun getFields(classType: ClassType): Option<Set<Field>> = fields[classType].toOption()

    internal fun resolveNewTypes(block: ResolutionContext.() -> Unit) = ResolutionContext(this, block).resolve()

    internal class ResolutionContext(
        private val scope: ReflectionScope,
        private val block: ResolutionContext.() -> Unit
    ) {

        private val danglingTypeReferences = mutableMapOf<DanglingTypeReference<Type>, String>()
        val typesInScope get() = scope.typeMap

        val newlyResolvedTypeVariablesForClassTypes = mutableMapOf<String, TypeVariable<ClassType>>()

        val newlyResolvedTypeVariablesForMethods = mutableMapOf<String, TypeVariable<Method>>()

        val newlyResolvedTypes = mutableMapOf<String, ClassType>()

        val newlyResolvedMethods = mutableMapOf<ClassType, Set<Method>>()

        val newlyResolvedConstructors = mutableMapOf<ClassType, Set<Constructor>>()

        val newlyResolvedFields = mutableMapOf<ClassType, Set<Field>>()

        private val qualifiedNameRegex =
            "(?<typeName>[\\w.\$]+)(::(?<methodSig>\\w+\\(.*?\\)))?->(?<varName>\\w+)".toRegex()

        private fun findTypeVariable(qualifiedName: String): TypeVariable<*> {
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
                (findResolvedType(typeName) as ClassType).typeParameters.single { it.identifier == varName }
            else
                newlyResolvedTypeVariablesForClassTypes.getValue("$typeName->$varName")

        fun findResolvedTypeVariable(typeName: String, methodSig: String, varName: String): TypeVariable<Method> =
            newlyResolvedTypeVariablesForMethods.getValue("$typeName::$methodSig->$varName")

        fun findResolvedType(qualifiedName: String) =
            scope.getTypeByName(qualifiedName).getOrElse { newlyResolvedTypes.getValue(qualifiedName) }

        fun <T : Type> newTypeReference(
            qualifiedName: String,
            materialization: List<TypeReference<ReferenceType>> = emptyList()
        ) =
            DanglingTypeReference<T>(materialization).also { danglingTypeReferences[it] = qualifiedName }

        fun newTypeVariableReference(typeName: String, varName: String) =
            DanglingTypeReference<TypeVariable<ClassType>>().also { danglingTypeReferences[it] = "$typeName->$varName" }

        fun newTypeVariableReference(typeName: String, methodSig: String, varName: String) =
            DanglingTypeReference<TypeVariable<Method>>().also {
                danglingTypeReferences[it] = "$typeName::$methodSig->$varName"
            }

        fun resolve() {
            this.block()
            danglingTypeReferences.forEach { (r, t) ->
                if (t.contains("->")) {
                    r.bind(findTypeVariable(t))
                } else {
                    r.bind(scope.getTypeByName(t).getOrElse { newlyResolvedTypes.getValue(t) })
                }
            }
            // Ensure that the lazy property is initialized by accessing the getter
            newlyResolvedTypeVariablesForMethods.values.forEach { it.declaration }
            newlyResolvedTypeVariablesForClassTypes.values.forEach { it.declaration }

            scope.typeMap.putAll(newlyResolvedTypes.filterKeys { it !in typesInScope })
            scope.methods.putAll(newlyResolvedMethods)
            scope.constructors.putAll(newlyResolvedConstructors)
            scope.fields.putAll(newlyResolvedFields)
        }

    }

}
