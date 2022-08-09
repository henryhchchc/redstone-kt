package net.henryhc.reflekt.elements.references.materialization

import net.henryhc.reflekt.elements.references.TypeReference
import net.henryhc.reflekt.elements.types.TypeVariable
import net.henryhc.reflekt.utils.identityHashCode

/**
 * Denotes a materialization allowing dangling state.
 */
class DanglingMaterialization(private var danglingMapping: Map<String, TypeReference>) : Materialization() {

    private lateinit var rawMapping: Map<TypeVariable<*>, TypeReference>

    /**
     * Bind the type variable to actual values.
     * Can be called only once.
     */
    fun bind(resolveBlock: (String) -> TypeVariable<*>) {
        if (this::rawMapping.isInitialized)
            return
        rawMapping = buildMap {
            danglingMapping.forEach { (k, v) -> this[resolveBlock(k)] = v }
        }
        danglingMapping = emptyMap()
    }

    override val entries: Set<Map.Entry<TypeVariable<*>, TypeReference>> get() = rawMapping.entries
    override val keys: Set<TypeVariable<*>> get() = rawMapping.keys
    override val size: Int get() = rawMapping.size
    override val values: Collection<TypeReference> get() = rawMapping.values
    override fun containsKey(key: TypeVariable<*>): Boolean = rawMapping.containsKey(key)
    override fun containsValue(value: TypeReference): Boolean = rawMapping.containsValue(value)
    override fun get(key: TypeVariable<*>): TypeReference? = rawMapping[key]
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
