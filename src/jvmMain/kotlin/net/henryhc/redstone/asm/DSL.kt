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

    private val insnList = InsnList()

    /**
     * Builds the [InsnList].
     */
    @PublishedApi
    internal fun build() = insnList

    // Constants
    val nop get() = insnList.add(InsnNode(Opcodes.NOP))
    val aconst_null get() = insnList.add(InsnNode(Opcodes.ACONST_NULL))
    fun bipush(operand: Int) = insnList.add(IntInsnNode(Opcodes.BIPUSH, operand))
    fun sipush(operand: Int) = insnList.add(IntInsnNode(Opcodes.SIPUSH, operand))
    val iconst_m1 get() = insnList.add(InsnNode(Opcodes.ICONST_M1))
    val iconst_0 get() = insnList.add(InsnNode(Opcodes.ICONST_0))
    val iconst_1 get() = insnList.add(InsnNode(Opcodes.ICONST_1))
    val iconst_2 get() = insnList.add(InsnNode(Opcodes.ICONST_2))
    val iconst_3 get() = insnList.add(InsnNode(Opcodes.ICONST_3))
    val iconst_4 get() = insnList.add(InsnNode(Opcodes.ICONST_4))
    val iconst_5 get() = insnList.add(InsnNode(Opcodes.ICONST_5))
    val lconst_0 get() = insnList.add(InsnNode(Opcodes.LCONST_0))
    val lconst_1 get() = insnList.add(InsnNode(Opcodes.LCONST_1))
    val fconst_0 get() = insnList.add(InsnNode(Opcodes.FCONST_0))
    val fconst_1 get() = insnList.add(InsnNode(Opcodes.FCONST_1))
    val fconst_2 get() = insnList.add(InsnNode(Opcodes.FCONST_2))
    val dconst_0 get() = insnList.add(InsnNode(Opcodes.DCONST_0))
    val dconst_1 get() = insnList.add(InsnNode(Opcodes.DCONST_1))

    fun ldc(operand: Any) = insnList.add(LdcInsnNode(operand))

    // Loads

    val iaload get() = insnList.add(InsnNode(Opcodes.IALOAD))
    val laload get() = insnList.add(InsnNode(Opcodes.LALOAD))
    val faload get() = insnList.add(InsnNode(Opcodes.FALOAD))
    val daload get() = insnList.add(InsnNode(Opcodes.DALOAD))
    val aaload get() = insnList.add(InsnNode(Opcodes.AALOAD))
    val baload get() = insnList.add(InsnNode(Opcodes.BALOAD))
    val caload get() = insnList.add(InsnNode(Opcodes.CALOAD))
    val saload get() = insnList.add(InsnNode(Opcodes.SALOAD))

    // Stores
    val iastore get() = insnList.add(InsnNode(Opcodes.IASTORE))
    val lastore get() = insnList.add(InsnNode(Opcodes.LASTORE))
    val fastore get() = insnList.add(InsnNode(Opcodes.FASTORE))
    val dastore get() = insnList.add(InsnNode(Opcodes.DASTORE))
    val aastore get() = insnList.add(InsnNode(Opcodes.AASTORE))
    val bastore get() = insnList.add(InsnNode(Opcodes.BASTORE))
    val castore get() = insnList.add(InsnNode(Opcodes.CASTORE))
    val sastore get() = insnList.add(InsnNode(Opcodes.SASTORE))

    // Stack
    val pop get() = insnList.add(InsnNode(Opcodes.POP))
    val pop2 get() = insnList.add(InsnNode(Opcodes.POP2))
    val dup get() = insnList.add(InsnNode(Opcodes.DUP))
    val dup_x1 get() = insnList.add(InsnNode(Opcodes.DUP_X1))
    val dup_x2 get() = insnList.add(InsnNode(Opcodes.DUP_X2))
    val dup2 get() = insnList.add(InsnNode(Opcodes.DUP2))
    val dup2_x1 get() = insnList.add(InsnNode(Opcodes.DUP2_X1))
    val dup2_x2 get() = insnList.add(InsnNode(Opcodes.DUP2_X2))
    val swap get() = insnList.add(InsnNode(Opcodes.SWAP))

    // Math
    val iadd get() = insnList.add(InsnNode(Opcodes.IADD))
    val ladd get() = insnList.add(InsnNode(Opcodes.LADD))
    val fadd get() = insnList.add(InsnNode(Opcodes.FADD))
    val dadd get() = insnList.add(InsnNode(Opcodes.DADD))
    val isub get() = insnList.add(InsnNode(Opcodes.ISUB))
    val lsub get() = insnList.add(InsnNode(Opcodes.LSUB))
    val fsub get() = insnList.add(InsnNode(Opcodes.FSUB))
    val dsub get() = insnList.add(InsnNode(Opcodes.DSUB))
    val imul get() = insnList.add(InsnNode(Opcodes.IMUL))
    val lmul get() = insnList.add(InsnNode(Opcodes.LMUL))
    val fmul get() = insnList.add(InsnNode(Opcodes.FMUL))
    val dmul get() = insnList.add(InsnNode(Opcodes.DMUL))
    val idiv get() = insnList.add(InsnNode(Opcodes.IDIV))
    val ldiv get() = insnList.add(InsnNode(Opcodes.LDIV))
    val fdiv get() = insnList.add(InsnNode(Opcodes.FDIV))
    val ddiv get() = insnList.add(InsnNode(Opcodes.DDIV))
    val irem get() = insnList.add(InsnNode(Opcodes.IREM))
    val lrem get() = insnList.add(InsnNode(Opcodes.LREM))
    val frem get() = insnList.add(InsnNode(Opcodes.FREM))
    val drem get() = insnList.add(InsnNode(Opcodes.DREM))
    val ineg get() = insnList.add(InsnNode(Opcodes.INEG))
    val lneg get() = insnList.add(InsnNode(Opcodes.LNEG))
    val fneg get() = insnList.add(InsnNode(Opcodes.FNEG))
    val dneg get() = insnList.add(InsnNode(Opcodes.DNEG))
    val ishl get() = insnList.add(InsnNode(Opcodes.ISHL))
    val lshl get() = insnList.add(InsnNode(Opcodes.LSHL))
    val ishr get() = insnList.add(InsnNode(Opcodes.ISHR))
    val lshr get() = insnList.add(InsnNode(Opcodes.LSHR))
    val iushr get() = insnList.add(InsnNode(Opcodes.IUSHR))
    val lushr get() = insnList.add(InsnNode(Opcodes.LUSHR))
    val iand get() = insnList.add(InsnNode(Opcodes.IAND))
    val land get() = insnList.add(InsnNode(Opcodes.LAND))
    val ior get() = insnList.add(InsnNode(Opcodes.IOR))
    val lor get() = insnList.add(InsnNode(Opcodes.LOR))
    val ixor get() = insnList.add(InsnNode(Opcodes.IXOR))
    val lxor get() = insnList.add(InsnNode(Opcodes.LXOR))
    fun iinc(varIdx: Int, incr: Int) = insnList.add(IincInsnNode(varIdx, incr))

    // Conversions
    val i2l get() = insnList.add(InsnNode(Opcodes.I2L))
    val i2f get() = insnList.add(InsnNode(Opcodes.I2F))
    val i2d get() = insnList.add(InsnNode(Opcodes.I2D))
    val l2i get() = insnList.add(InsnNode(Opcodes.L2I))
    val l2f get() = insnList.add(InsnNode(Opcodes.L2F))
    val l2d get() = insnList.add(InsnNode(Opcodes.L2D))
    val f2i get() = insnList.add(InsnNode(Opcodes.F2I))
    val f2l get() = insnList.add(InsnNode(Opcodes.F2L))
    val f2d get() = insnList.add(InsnNode(Opcodes.F2D))
    val d2i get() = insnList.add(InsnNode(Opcodes.D2I))
    val d2l get() = insnList.add(InsnNode(Opcodes.D2L))
    val d2f get() = insnList.add(InsnNode(Opcodes.D2F))
    val i2b get() = insnList.add(InsnNode(Opcodes.I2B))
    val i2c get() = insnList.add(InsnNode(Opcodes.I2C))
    val i2s get() = insnList.add(InsnNode(Opcodes.I2S))

    // Comparisons
    val lcmp get() = insnList.add(InsnNode(Opcodes.LCMP))
    val fcmpl get() = insnList.add(InsnNode(Opcodes.FCMPL))
    val fcmpg get() = insnList.add(InsnNode(Opcodes.FCMPG))
    val dcmpl get() = insnList.add(InsnNode(Opcodes.DCMPL))
    val dcmpg get() = insnList.add(InsnNode(Opcodes.DCMPG))
    fun ifeq(target: LabelNode) = insnList.add(JumpInsnNode(Opcodes.IFEQ, target))
    fun ifne(target: LabelNode) = insnList.add(JumpInsnNode(Opcodes.IFNE, target))
    fun iflt(target: LabelNode) = insnList.add(JumpInsnNode(Opcodes.IFLT, target))
    fun ifge(target: LabelNode) = insnList.add(JumpInsnNode(Opcodes.IFGE, target))
    fun ifgt(target: LabelNode) = insnList.add(JumpInsnNode(Opcodes.IFGT, target))
    fun ifle(target: LabelNode) = insnList.add(JumpInsnNode(Opcodes.IFLE, target))
    fun if_icmpeq(target: LabelNode) = insnList.add(JumpInsnNode(Opcodes.IF_ICMPEQ, target))
    fun if_icmpne(target: LabelNode) = insnList.add(JumpInsnNode(Opcodes.IF_ICMPNE, target))
    fun if_icmplt(target: LabelNode) = insnList.add(JumpInsnNode(Opcodes.IF_ICMPLT, target))
    fun if_icmpge(target: LabelNode) = insnList.add(JumpInsnNode(Opcodes.IF_ICMPGE, target))
    fun if_icmpgt(target: LabelNode) = insnList.add(JumpInsnNode(Opcodes.IF_ICMPGT, target))
    fun if_icmple(target: LabelNode) = insnList.add(JumpInsnNode(Opcodes.IF_ICMPLE, target))
    fun if_acmpeq(target: LabelNode) = insnList.add(JumpInsnNode(Opcodes.IF_ACMPEQ, target))
    fun if_acmpne(target: LabelNode) = insnList.add(JumpInsnNode(Opcodes.IF_ACMPNE, target))

    // Control
    fun goto(target: LabelNode) = insnList.add(JumpInsnNode(Opcodes.GOTO, target))
    fun jsr(target: LabelNode) = insnList.add(JumpInsnNode(Opcodes.JSR, target))
    fun tableswitch(min: Int, max: Int, default: LabelNode, vararg targets: LabelNode) =
        insnList.add(TableSwitchInsnNode(min, max, default, *targets))

    fun lookupswitch(default: LabelNode, handlers: Map<Int, LabelNode>) = handlers.entries.unzip { it.toPair() }
        .let { (keys, labels) -> insnList.add(LookupSwitchInsnNode(default, keys.toIntArray(), labels.toTypedArray())) }

    val ireturn get() = insnList.add(InsnNode(Opcodes.IRETURN))
    val lreturn get() = insnList.add(InsnNode(Opcodes.LRETURN))
    val freturn get() = insnList.add(InsnNode(Opcodes.FRETURN))
    val dreturn get() = insnList.add(InsnNode(Opcodes.DRETURN))
    val areturn get() = insnList.add(InsnNode(Opcodes.ARETURN))
    val `return` get() = insnList.add(InsnNode(Opcodes.RETURN))

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

    fun new(desc: String) = insnList.add(TypeInsnNode(Opcodes.NEW, desc))
    fun newarray(desc: String) = insnList.add(TypeInsnNode(Opcodes.NEWARRAY, desc))
    val arraylength get() = insnList.add(InsnNode(Opcodes.ARRAYLENGTH))
    val athrow get() = insnList.add(InsnNode(Opcodes.ATHROW))
    fun checkcast(desc: String) = insnList.add(TypeInsnNode(Opcodes.CHECKCAST, desc))
    fun instanceof(desc: String) = insnList.add(TypeInsnNode(Opcodes.INSTANCEOF, desc))

    val monitorenter get() = insnList.add(InsnNode(Opcodes.MONITORENTER))
    val monitorexit get() = insnList.add(InsnNode(Opcodes.MONITOREXIT))

    // Extended

    fun ifnull(target: LabelNode) = insnList.add(JumpInsnNode(Opcodes.IFNULL, target))
    fun ifnonnull(target: LabelNode) = insnList.add(JumpInsnNode(Opcodes.IFNONNULL, target))
    fun muiltianewarray(desc: String, dim: Int) = insnList.add(MultiANewArrayInsnNode(desc, dim))


    // Misc
    val newLabel get() = LabelNode()
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

    val f_same get() = insnList.add(FrameNode(Opcodes.F_SAME, 0, null, 0, null))

    fun f_same1(vararg stack: Any) = insnList.add(FrameNode(Opcodes.F_SAME1, 0, null, stack.size, stack))
}
