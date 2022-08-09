package net.henryhc.reflekt.elements.references.materialization

import net.henryhc.reflekt.ReflectionScope
import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.types.TypeVariable
import net.henryhc.reflekt.utils.identityHashCode

/**
 * Denotes a materialization allowing dangling state.
 */
class DanglingMaterialization(private val danglingMapping: Map<String, TypeReference>) : Materialization() {

    private lateinit var rawMapping: Map<TypeVariable<*>, TypeReference>

    internal fun bind(ctx: ReflectionScope.ResolutionContext) {
        rawMapping = buildMap {
            danglingMapping.forEach { (k, v) -> this[ctx.findTypeVariable(k)] = v }
        }
    }

    override val entries: Set<Map.Entry<TypeVariable<*>, TypeReference>> get() = rawMapping.entries
    override val keys: Set<TypeVariable<*>> get() = rawMapping.keys
    override val size: Int get() = rawMapping.size
    override val values: Collection<TypeReference> get() = rawMapping.values
    override fun containsKey(key: TypeVariable<*>): Boolean = rawMapping.containsKey(key)
    override fun containsValue(value: TypeReference): Boolean = rawMapping.containsValue(value)
    override fun get(key: TypeVariable<*>): TypeReference? = rawMapping.get(key)
    override fun isEmpty(): Boolean = rawMapping.isEmpty()

    override fun hashCode(): Int {
        if (!this::rawMapping.isInitialized)
            return this.identityHashCode()
        return super.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (!this::rawMapping.isInitialized)
            return this.identityHashCode() == other?.identityHashCode()
        return super.equals(other)
    }
}
