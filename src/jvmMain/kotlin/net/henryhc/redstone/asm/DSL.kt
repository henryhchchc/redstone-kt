package net.henryhc.redstone.asm

import arrow.core.unzip
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*


/**
 * Marks a class as a DSL marker for ObjectWeb ASM.
 */
@DslMarker
internal annotation class AsmDsl

/**
 * Builds an instruction list.
 */
inline fun instructions(init: InstructionsBuilder.() -> Unit): InsnList = InstructionsBuilder().apply(init).build()


/**
 * A builder class for constructing an [InsnList].
 */
@Suppress("KDocMissingDocumentation", "PropertyName", "SpellCheckingInspection", "FunctionName", "unused")
@AsmDsl
class InstructionsBuilder @PublishedApi internal constructor() {

    @PublishedApi
    internal val insnList = InsnList()

    /**
     * Builds the [InsnList].
     */
    @PublishedApi
    internal fun build() = insnList

    // Constants
    inline val nop get() = insnList.add(InsnNode(Opcodes.NOP))
    inline val aconst_null get() = insnList.add(InsnNode(Opcodes.ACONST_NULL))
    inline fun bipush(operand: () -> Int) = insnList.add(IntInsnNode(Opcodes.BIPUSH, operand()))
    inline fun sipush(operand: () -> Int) = insnList.add(IntInsnNode(Opcodes.SIPUSH, operand()))
    inline val iconst_m1 get() = insnList.add(InsnNode(Opcodes.ICONST_M1))
    inline val iconst_0 get() = insnList.add(InsnNode(Opcodes.ICONST_0))
    inline val iconst_1 get() = insnList.add(InsnNode(Opcodes.ICONST_1))
    inline val iconst_2 get() = insnList.add(InsnNode(Opcodes.ICONST_2))
    inline val iconst_3 get() = insnList.add(InsnNode(Opcodes.ICONST_3))
    inline val iconst_4 get() = insnList.add(InsnNode(Opcodes.ICONST_4))
    inline val iconst_5 get() = insnList.add(InsnNode(Opcodes.ICONST_5))
    inline val lconst_0 get() = insnList.add(InsnNode(Opcodes.LCONST_0))
    inline val lconst_1 get() = insnList.add(InsnNode(Opcodes.LCONST_1))
    inline val fconst_0 get() = insnList.add(InsnNode(Opcodes.FCONST_0))
    inline val fconst_1 get() = insnList.add(InsnNode(Opcodes.FCONST_1))
    inline val fconst_2 get() = insnList.add(InsnNode(Opcodes.FCONST_2))
    inline val dconst_0 get() = insnList.add(InsnNode(Opcodes.DCONST_0))
    inline val dconst_1 get() = insnList.add(InsnNode(Opcodes.DCONST_1))

    inline fun ldc(operand: () -> Any) = insnList.add(LdcInsnNode(operand()))

    // Loads
    inline val iaload get() = insnList.add(InsnNode(Opcodes.IALOAD))
    inline val laload get() = insnList.add(InsnNode(Opcodes.LALOAD))
    inline val faload get() = insnList.add(InsnNode(Opcodes.FALOAD))
    inline val daload get() = insnList.add(InsnNode(Opcodes.DALOAD))
    inline val aaload get() = insnList.add(InsnNode(Opcodes.AALOAD))
    inline val baload get() = insnList.add(InsnNode(Opcodes.BALOAD))
    inline val caload get() = insnList.add(InsnNode(Opcodes.CALOAD))
    inline val saload get() = insnList.add(InsnNode(Opcodes.SALOAD))

    // Stores
    inline val iastore get() = insnList.add(InsnNode(Opcodes.IASTORE))
    inline val lastore get() = insnList.add(InsnNode(Opcodes.LASTORE))
    inline val fastore get() = insnList.add(InsnNode(Opcodes.FASTORE))
    inline val dastore get() = insnList.add(InsnNode(Opcodes.DASTORE))
    inline val aastore get() = insnList.add(InsnNode(Opcodes.AASTORE))
    inline val bastore get() = insnList.add(InsnNode(Opcodes.BASTORE))
    inline val castore get() = insnList.add(InsnNode(Opcodes.CASTORE))
    inline val sastore get() = insnList.add(InsnNode(Opcodes.SASTORE))

    // Stack
    inline val pop get() = insnList.add(InsnNode(Opcodes.POP))
    inline val pop2 get() = insnList.add(InsnNode(Opcodes.POP2))
    inline val dup get() = insnList.add(InsnNode(Opcodes.DUP))
    inline val dup_x1 get() = insnList.add(InsnNode(Opcodes.DUP_X1))
    inline val dup_x2 get() = insnList.add(InsnNode(Opcodes.DUP_X2))
    inline val dup2 get() = insnList.add(InsnNode(Opcodes.DUP2))
    inline val dup2_x1 get() = insnList.add(InsnNode(Opcodes.DUP2_X1))
    inline val dup2_x2 get() = insnList.add(InsnNode(Opcodes.DUP2_X2))
    inline val swap get() = insnList.add(InsnNode(Opcodes.SWAP))

    // Math
    inline val iadd get() = insnList.add(InsnNode(Opcodes.IADD))
    inline val ladd get() = insnList.add(InsnNode(Opcodes.LADD))
    inline val fadd get() = insnList.add(InsnNode(Opcodes.FADD))
    inline val dadd get() = insnList.add(InsnNode(Opcodes.DADD))
    inline val isub get() = insnList.add(InsnNode(Opcodes.ISUB))
    inline val lsub get() = insnList.add(InsnNode(Opcodes.LSUB))
    inline val fsub get() = insnList.add(InsnNode(Opcodes.FSUB))
    inline val dsub get() = insnList.add(InsnNode(Opcodes.DSUB))
    inline val imul get() = insnList.add(InsnNode(Opcodes.IMUL))
    inline val lmul get() = insnList.add(InsnNode(Opcodes.LMUL))
    inline val fmul get() = insnList.add(InsnNode(Opcodes.FMUL))
    inline val dmul get() = insnList.add(InsnNode(Opcodes.DMUL))
    inline val idiv get() = insnList.add(InsnNode(Opcodes.IDIV))
    inline val ldiv get() = insnList.add(InsnNode(Opcodes.LDIV))
    inline val fdiv get() = insnList.add(InsnNode(Opcodes.FDIV))
    inline val ddiv get() = insnList.add(InsnNode(Opcodes.DDIV))
    inline val irem get() = insnList.add(InsnNode(Opcodes.IREM))
    inline val lrem get() = insnList.add(InsnNode(Opcodes.LREM))
    inline val frem get() = insnList.add(InsnNode(Opcodes.FREM))
    inline val drem get() = insnList.add(InsnNode(Opcodes.DREM))
    inline val ineg get() = insnList.add(InsnNode(Opcodes.INEG))
    inline val lneg get() = insnList.add(InsnNode(Opcodes.LNEG))
    inline val fneg get() = insnList.add(InsnNode(Opcodes.FNEG))
    inline val dneg get() = insnList.add(InsnNode(Opcodes.DNEG))
    inline val ishl get() = insnList.add(InsnNode(Opcodes.ISHL))
    inline val lshl get() = insnList.add(InsnNode(Opcodes.LSHL))
    inline val ishr get() = insnList.add(InsnNode(Opcodes.ISHR))
    inline val lshr get() = insnList.add(InsnNode(Opcodes.LSHR))
    inline val iushr get() = insnList.add(InsnNode(Opcodes.IUSHR))
    inline val lushr get() = insnList.add(InsnNode(Opcodes.LUSHR))
    inline val iand get() = insnList.add(InsnNode(Opcodes.IAND))
    inline val land get() = insnList.add(InsnNode(Opcodes.LAND))
    inline val ior get() = insnList.add(InsnNode(Opcodes.IOR))
    inline val lor get() = insnList.add(InsnNode(Opcodes.LOR))
    inline val ixor get() = insnList.add(InsnNode(Opcodes.IXOR))
    inline val lxor get() = insnList.add(InsnNode(Opcodes.LXOR))
    fun iinc(varIdx: Int, incr: Int) = insnList.add(IincInsnNode(varIdx, incr))

    // Conversions
    inline val i2l get() = insnList.add(InsnNode(Opcodes.I2L))
    inline val i2f get() = insnList.add(InsnNode(Opcodes.I2F))
    inline val i2d get() = insnList.add(InsnNode(Opcodes.I2D))
    inline val l2i get() = insnList.add(InsnNode(Opcodes.L2I))
    inline val l2f get() = insnList.add(InsnNode(Opcodes.L2F))
    inline val l2d get() = insnList.add(InsnNode(Opcodes.L2D))
    inline val f2i get() = insnList.add(InsnNode(Opcodes.F2I))
    inline val f2l get() = insnList.add(InsnNode(Opcodes.F2L))
    inline val f2d get() = insnList.add(InsnNode(Opcodes.F2D))
    inline val d2i get() = insnList.add(InsnNode(Opcodes.D2I))
    inline val d2l get() = insnList.add(InsnNode(Opcodes.D2L))
    inline val d2f get() = insnList.add(InsnNode(Opcodes.D2F))
    inline val i2b get() = insnList.add(InsnNode(Opcodes.I2B))
    inline val i2c get() = insnList.add(InsnNode(Opcodes.I2C))
    inline val i2s get() = insnList.add(InsnNode(Opcodes.I2S))

    // Comparisons
    inline val lcmp get() = insnList.add(InsnNode(Opcodes.LCMP))
    inline val fcmpl get() = insnList.add(InsnNode(Opcodes.FCMPL))
    inline val fcmpg get() = insnList.add(InsnNode(Opcodes.FCMPG))
    inline val dcmpl get() = insnList.add(InsnNode(Opcodes.DCMPL))
    inline val dcmpg get() = insnList.add(InsnNode(Opcodes.DCMPG))
    inline fun ifeq(target: () -> LabelNode) = insnList.add(JumpInsnNode(Opcodes.IFEQ, target()))
    inline fun ifne(target: () -> LabelNode) = insnList.add(JumpInsnNode(Opcodes.IFNE, target()))
    inline fun iflt(target: () -> LabelNode) = insnList.add(JumpInsnNode(Opcodes.IFLT, target()))
    inline fun ifge(target: () -> LabelNode) = insnList.add(JumpInsnNode(Opcodes.IFGE, target()))
    inline fun ifgt(target: () -> LabelNode) = insnList.add(JumpInsnNode(Opcodes.IFGT, target()))
    inline fun ifle(target: () -> LabelNode) = insnList.add(JumpInsnNode(Opcodes.IFLE, target()))
    inline fun if_icmpeq(target: () -> LabelNode) = insnList.add(JumpInsnNode(Opcodes.IF_ICMPEQ, target()))
    inline fun if_icmpne(target: () -> LabelNode) = insnList.add(JumpInsnNode(Opcodes.IF_ICMPNE, target()))
    inline fun if_icmplt(target: () -> LabelNode) = insnList.add(JumpInsnNode(Opcodes.IF_ICMPLT, target()))
    inline fun if_icmpge(target: () -> LabelNode) = insnList.add(JumpInsnNode(Opcodes.IF_ICMPGE, target()))
    inline fun if_icmpgt(target: () -> LabelNode) = insnList.add(JumpInsnNode(Opcodes.IF_ICMPGT, target()))
    inline fun if_icmple(target: () -> LabelNode) = insnList.add(JumpInsnNode(Opcodes.IF_ICMPLE, target()))
    inline fun if_acmpeq(target: () -> LabelNode) = insnList.add(JumpInsnNode(Opcodes.IF_ACMPEQ, target()))
    inline fun if_acmpne(target: () -> LabelNode) = insnList.add(JumpInsnNode(Opcodes.IF_ACMPNE, target()))

    // Control
    inline fun goto(target: () -> LabelNode) = insnList.add(JumpInsnNode(Opcodes.GOTO, target()))
    inline fun jsr(target: () -> LabelNode) = insnList.add(JumpInsnNode(Opcodes.JSR, target()))
    fun tableswitch(min: Int, max: Int, default: LabelNode, vararg targets: LabelNode) =
        insnList.add(TableSwitchInsnNode(min, max, default, *targets))

    fun lookupswitch(default: LabelNode, handlers: Map<Int, LabelNode>) = handlers.entries.unzip { it.toPair() }
        .let { (keys, labels) -> insnList.add(LookupSwitchInsnNode(default, keys.toIntArray(), labels.toTypedArray())) }

    inline val ireturn get() = insnList.add(InsnNode(Opcodes.IRETURN))
    inline val lreturn get() = insnList.add(InsnNode(Opcodes.LRETURN))
    inline val freturn get() = insnList.add(InsnNode(Opcodes.FRETURN))
    inline val dreturn get() = insnList.add(InsnNode(Opcodes.DRETURN))
    inline val areturn get() = insnList.add(InsnNode(Opcodes.ARETURN))
    inline val `return` get() = insnList.add(InsnNode(Opcodes.RETURN))

    // References
    fun getstatic(owner: String, name: String, desc: String) =
        insnList.add(FieldInsnNode(Opcodes.GETSTATIC, owner, name, desc))

    fun putstatic(owner: String, name: String, desc: String) =
        insnList.add(FieldInsnNode(Opcodes.PUTSTATIC, owner, name, desc))

    fun getfield(owner: String, name: String, desc: String) =
        insnList.add(FieldInsnNode(Opcodes.GETFIELD, owner, name, desc))

    fun putfield(owner: String, name: String, desc: String) =
        insnList.add(FieldInsnNode(Opcodes.PUTFIELD, owner, name, desc))

    fun invokevirtual(owner: String, name: String, desc: String) =
        insnList.add(MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner, name, desc, false))

    fun invokespecial(owner: String, name: String, desc: String) =
        insnList.add(MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner, name, desc, false))

    fun invokestatic(owner: String, name: String, desc: String, isInterface: Boolean) =
        insnList.add(MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner, name, desc, isInterface))

    fun invokeinterface(owner: String, name: String, desc: String) =
        insnList.add(MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner, name, desc, true))

    // TODO: Invoke dynamic here

    inline fun new(desc: () -> String) = insnList.add(TypeInsnNode(Opcodes.NEW, desc()))
    inline fun newarray(desc: () -> String) = insnList.add(TypeInsnNode(Opcodes.NEWARRAY, desc()))
    inline val arraylength get() = insnList.add(InsnNode(Opcodes.ARRAYLENGTH))
    inline val athrow get() = insnList.add(InsnNode(Opcodes.ATHROW))
    inline fun checkcast(desc: () -> String) = insnList.add(TypeInsnNode(Opcodes.CHECKCAST, desc()))
    inline fun instanceof(desc: () -> String) = insnList.add(TypeInsnNode(Opcodes.INSTANCEOF, desc()))

    inline val monitorenter get() = insnList.add(InsnNode(Opcodes.MONITORENTER))
    inline val monitorexit get() = insnList.add(InsnNode(Opcodes.MONITOREXIT))

    // Extended

    inline fun ifnull(target: () -> LabelNode) = insnList.add(JumpInsnNode(Opcodes.IFNULL, target()))
    inline fun ifnonnull(target: () -> LabelNode) = insnList.add(JumpInsnNode(Opcodes.IFNONNULL, target()))
    fun muiltianewarray(desc: String, dim: Int) = insnList.add(MultiANewArrayInsnNode(desc, dim))


    // Misc
    inline val newLabel get() = LabelNode()
    fun label(label: LabelNode, lineNumber: Int? = null) = insnList.add(label).apply {
        lineNumber?.also { insnList.add(LineNumberNode(it, label)) }
    }

    fun f_new(local: List<Any>, stack: List<Any>) =
        insnList.add(FrameNode(Opcodes.F_NEW, local.size, local.toTypedArray(), stack.size, stack.toTypedArray()))


    fun f_full(local: List<Any>, stack: List<Any>) =
        insnList.add(FrameNode(Opcodes.F_FULL, local.size, local.toTypedArray(), stack.size, stack.toTypedArray()))

    fun f_append(local: List<Any>) =
        insnList.add(FrameNode(Opcodes.F_FULL, local.size, local.toTypedArray(), 0, null))

    fun f_chop(numLocal: Int) = insnList.add(FrameNode(Opcodes.F_FULL, numLocal, null, 0, null))

    inline val f_same get() = insnList.add(FrameNode(Opcodes.F_SAME, 0, null, 0, null))

    fun f_same1(vararg stack: Any) = insnList.add(FrameNode(Opcodes.F_SAME1, 0, null, stack.size, stack))
}
