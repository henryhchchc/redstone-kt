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
    internal val insnList: InsnList = InsnList()

    /**
     * Builds the [InsnList].
     */
    @PublishedApi
    internal fun build(): InsnList = insnList

    // Constants
    inline val nop: Unit get() = insnList.add(InsnNode(Opcodes.NOP))
    inline val aconst_null: Unit get() = insnList.add(InsnNode(Opcodes.ACONST_NULL))
    inline fun bipush(operand: () -> Int): Unit = insnList.add(IntInsnNode(Opcodes.BIPUSH, operand()))
    inline fun sipush(operand: () -> Int): Unit = insnList.add(IntInsnNode(Opcodes.SIPUSH, operand()))
    inline val iconst_m1: Unit get() = insnList.add(InsnNode(Opcodes.ICONST_M1))
    inline val iconst_0: Unit get() = insnList.add(InsnNode(Opcodes.ICONST_0))
    inline val iconst_1: Unit get() = insnList.add(InsnNode(Opcodes.ICONST_1))
    inline val iconst_2: Unit get() = insnList.add(InsnNode(Opcodes.ICONST_2))
    inline val iconst_3: Unit get() = insnList.add(InsnNode(Opcodes.ICONST_3))
    inline val iconst_4: Unit get() = insnList.add(InsnNode(Opcodes.ICONST_4))
    inline val iconst_5: Unit get() = insnList.add(InsnNode(Opcodes.ICONST_5))
    inline val lconst_0: Unit get() = insnList.add(InsnNode(Opcodes.LCONST_0))
    inline val lconst_1: Unit get() = insnList.add(InsnNode(Opcodes.LCONST_1))
    inline val fconst_0: Unit get() = insnList.add(InsnNode(Opcodes.FCONST_0))
    inline val fconst_1: Unit get() = insnList.add(InsnNode(Opcodes.FCONST_1))
    inline val fconst_2: Unit get() = insnList.add(InsnNode(Opcodes.FCONST_2))
    inline val dconst_0: Unit get() = insnList.add(InsnNode(Opcodes.DCONST_0))
    inline val dconst_1: Unit get() = insnList.add(InsnNode(Opcodes.DCONST_1))

    inline fun ldc(operand: () -> Any): Unit = insnList.add(LdcInsnNode(operand()))

    // Loads
    inline val iaload: Unit get() = insnList.add(InsnNode(Opcodes.IALOAD))
    inline val laload: Unit get() = insnList.add(InsnNode(Opcodes.LALOAD))
    inline val faload: Unit get() = insnList.add(InsnNode(Opcodes.FALOAD))
    inline val daload: Unit get() = insnList.add(InsnNode(Opcodes.DALOAD))
    inline val aaload: Unit get() = insnList.add(InsnNode(Opcodes.AALOAD))
    inline val baload: Unit get() = insnList.add(InsnNode(Opcodes.BALOAD))
    inline val caload: Unit get() = insnList.add(InsnNode(Opcodes.CALOAD))
    inline val saload: Unit get() = insnList.add(InsnNode(Opcodes.SALOAD))

    // Stores
    inline val iastore: Unit get() = insnList.add(InsnNode(Opcodes.IASTORE))
    inline val lastore: Unit get() = insnList.add(InsnNode(Opcodes.LASTORE))
    inline val fastore: Unit get() = insnList.add(InsnNode(Opcodes.FASTORE))
    inline val dastore: Unit get() = insnList.add(InsnNode(Opcodes.DASTORE))
    inline val aastore: Unit get() = insnList.add(InsnNode(Opcodes.AASTORE))
    inline val bastore: Unit get() = insnList.add(InsnNode(Opcodes.BASTORE))
    inline val castore: Unit get() = insnList.add(InsnNode(Opcodes.CASTORE))
    inline val sastore: Unit get() = insnList.add(InsnNode(Opcodes.SASTORE))

    // Stack
    inline val pop: Unit get() = insnList.add(InsnNode(Opcodes.POP))
    inline val pop2: Unit get() = insnList.add(InsnNode(Opcodes.POP2))
    inline val dup: Unit get() = insnList.add(InsnNode(Opcodes.DUP))
    inline val dup_x1: Unit get() = insnList.add(InsnNode(Opcodes.DUP_X1))
    inline val dup_x2: Unit get() = insnList.add(InsnNode(Opcodes.DUP_X2))
    inline val dup2: Unit get() = insnList.add(InsnNode(Opcodes.DUP2))
    inline val dup2_x1: Unit get() = insnList.add(InsnNode(Opcodes.DUP2_X1))
    inline val dup2_x2: Unit get() = insnList.add(InsnNode(Opcodes.DUP2_X2))
    inline val swap: Unit get() = insnList.add(InsnNode(Opcodes.SWAP))

    // Math
    inline val iadd: Unit get() = insnList.add(InsnNode(Opcodes.IADD))
    inline val ladd: Unit get() = insnList.add(InsnNode(Opcodes.LADD))
    inline val fadd: Unit get() = insnList.add(InsnNode(Opcodes.FADD))
    inline val dadd: Unit get() = insnList.add(InsnNode(Opcodes.DADD))
    inline val isub: Unit get() = insnList.add(InsnNode(Opcodes.ISUB))
    inline val lsub: Unit get() = insnList.add(InsnNode(Opcodes.LSUB))
    inline val fsub: Unit get() = insnList.add(InsnNode(Opcodes.FSUB))
    inline val dsub: Unit get() = insnList.add(InsnNode(Opcodes.DSUB))
    inline val imul: Unit get() = insnList.add(InsnNode(Opcodes.IMUL))
    inline val lmul: Unit get() = insnList.add(InsnNode(Opcodes.LMUL))
    inline val fmul: Unit get() = insnList.add(InsnNode(Opcodes.FMUL))
    inline val dmul: Unit get() = insnList.add(InsnNode(Opcodes.DMUL))
    inline val idiv: Unit get() = insnList.add(InsnNode(Opcodes.IDIV))
    inline val ldiv: Unit get() = insnList.add(InsnNode(Opcodes.LDIV))
    inline val fdiv: Unit get() = insnList.add(InsnNode(Opcodes.FDIV))
    inline val ddiv: Unit get() = insnList.add(InsnNode(Opcodes.DDIV))
    inline val irem: Unit get() = insnList.add(InsnNode(Opcodes.IREM))
    inline val lrem: Unit get() = insnList.add(InsnNode(Opcodes.LREM))
    inline val frem: Unit get() = insnList.add(InsnNode(Opcodes.FREM))
    inline val drem: Unit get() = insnList.add(InsnNode(Opcodes.DREM))
    inline val ineg: Unit get() = insnList.add(InsnNode(Opcodes.INEG))
    inline val lneg: Unit get() = insnList.add(InsnNode(Opcodes.LNEG))
    inline val fneg: Unit get() = insnList.add(InsnNode(Opcodes.FNEG))
    inline val dneg: Unit get() = insnList.add(InsnNode(Opcodes.DNEG))
    inline val ishl: Unit get() = insnList.add(InsnNode(Opcodes.ISHL))
    inline val lshl: Unit get() = insnList.add(InsnNode(Opcodes.LSHL))
    inline val ishr: Unit get() = insnList.add(InsnNode(Opcodes.ISHR))
    inline val lshr: Unit get() = insnList.add(InsnNode(Opcodes.LSHR))
    inline val iushr: Unit get() = insnList.add(InsnNode(Opcodes.IUSHR))
    inline val lushr: Unit get() = insnList.add(InsnNode(Opcodes.LUSHR))
    inline val iand: Unit get() = insnList.add(InsnNode(Opcodes.IAND))
    inline val land: Unit get() = insnList.add(InsnNode(Opcodes.LAND))
    inline val ior: Unit get() = insnList.add(InsnNode(Opcodes.IOR))
    inline val lor: Unit get() = insnList.add(InsnNode(Opcodes.LOR))
    inline val ixor: Unit get() = insnList.add(InsnNode(Opcodes.IXOR))
    inline val lxor: Unit get() = insnList.add(InsnNode(Opcodes.LXOR))
    fun iinc(varIdx: Int, incr: Int): Unit = insnList.add(IincInsnNode(varIdx, incr))

    // Conversions
    inline val i2l: Unit get() = insnList.add(InsnNode(Opcodes.I2L))
    inline val i2f: Unit get() = insnList.add(InsnNode(Opcodes.I2F))
    inline val i2d: Unit get() = insnList.add(InsnNode(Opcodes.I2D))
    inline val l2i: Unit get() = insnList.add(InsnNode(Opcodes.L2I))
    inline val l2f: Unit get() = insnList.add(InsnNode(Opcodes.L2F))
    inline val l2d: Unit get() = insnList.add(InsnNode(Opcodes.L2D))
    inline val f2i: Unit get() = insnList.add(InsnNode(Opcodes.F2I))
    inline val f2l: Unit get() = insnList.add(InsnNode(Opcodes.F2L))
    inline val f2d: Unit get() = insnList.add(InsnNode(Opcodes.F2D))
    inline val d2i: Unit get() = insnList.add(InsnNode(Opcodes.D2I))
    inline val d2l: Unit get() = insnList.add(InsnNode(Opcodes.D2L))
    inline val d2f: Unit get() = insnList.add(InsnNode(Opcodes.D2F))
    inline val i2b: Unit get() = insnList.add(InsnNode(Opcodes.I2B))
    inline val i2c: Unit get() = insnList.add(InsnNode(Opcodes.I2C))
    inline val i2s: Unit get() = insnList.add(InsnNode(Opcodes.I2S))

    // Comparisons
    inline val lcmp: Unit get() = insnList.add(InsnNode(Opcodes.LCMP))
    inline val fcmpl: Unit get() = insnList.add(InsnNode(Opcodes.FCMPL))
    inline val fcmpg: Unit get() = insnList.add(InsnNode(Opcodes.FCMPG))
    inline val dcmpl: Unit get() = insnList.add(InsnNode(Opcodes.DCMPL))
    inline val dcmpg: Unit get() = insnList.add(InsnNode(Opcodes.DCMPG))
    inline fun ifeq(target: () -> LabelNode): Unit = insnList.add(JumpInsnNode(Opcodes.IFEQ, target()))
    inline fun ifne(target: () -> LabelNode): Unit = insnList.add(JumpInsnNode(Opcodes.IFNE, target()))
    inline fun iflt(target: () -> LabelNode): Unit = insnList.add(JumpInsnNode(Opcodes.IFLT, target()))
    inline fun ifge(target: () -> LabelNode): Unit = insnList.add(JumpInsnNode(Opcodes.IFGE, target()))
    inline fun ifgt(target: () -> LabelNode): Unit = insnList.add(JumpInsnNode(Opcodes.IFGT, target()))
    inline fun ifle(target: () -> LabelNode): Unit = insnList.add(JumpInsnNode(Opcodes.IFLE, target()))
    inline fun if_icmpeq(target: () -> LabelNode): Unit = insnList.add(JumpInsnNode(Opcodes.IF_ICMPEQ, target()))
    inline fun if_icmpne(target: () -> LabelNode): Unit = insnList.add(JumpInsnNode(Opcodes.IF_ICMPNE, target()))
    inline fun if_icmplt(target: () -> LabelNode): Unit = insnList.add(JumpInsnNode(Opcodes.IF_ICMPLT, target()))
    inline fun if_icmpge(target: () -> LabelNode): Unit = insnList.add(JumpInsnNode(Opcodes.IF_ICMPGE, target()))
    inline fun if_icmpgt(target: () -> LabelNode): Unit = insnList.add(JumpInsnNode(Opcodes.IF_ICMPGT, target()))
    inline fun if_icmple(target: () -> LabelNode): Unit = insnList.add(JumpInsnNode(Opcodes.IF_ICMPLE, target()))
    inline fun if_acmpeq(target: () -> LabelNode): Unit = insnList.add(JumpInsnNode(Opcodes.IF_ACMPEQ, target()))
    inline fun if_acmpne(target: () -> LabelNode): Unit = insnList.add(JumpInsnNode(Opcodes.IF_ACMPNE, target()))

    // Control
    inline fun goto(target: () -> LabelNode): Unit = insnList.add(JumpInsnNode(Opcodes.GOTO, target()))
    inline fun jsr(target: () -> LabelNode): Unit = insnList.add(JumpInsnNode(Opcodes.JSR, target()))
    fun tableswitch(min: Int, max: Int, default: LabelNode, vararg targets: LabelNode): Unit =
        insnList.add(TableSwitchInsnNode(min, max, default, *targets))

    fun lookupswitch(default: LabelNode, handlers: Map<Int, LabelNode>): Unit = handlers.entries.unzip { it.toPair() }
        .let { (keys, labels) -> insnList.add(LookupSwitchInsnNode(default, keys.toIntArray(), labels.toTypedArray())) }

    inline val ireturn: Unit get() = insnList.add(InsnNode(Opcodes.IRETURN))
    inline val lreturn: Unit get() = insnList.add(InsnNode(Opcodes.LRETURN))
    inline val freturn: Unit get() = insnList.add(InsnNode(Opcodes.FRETURN))
    inline val dreturn: Unit get() = insnList.add(InsnNode(Opcodes.DRETURN))
    inline val areturn: Unit get() = insnList.add(InsnNode(Opcodes.ARETURN))
    inline val `return`: Unit get() = insnList.add(InsnNode(Opcodes.RETURN))

    // References
    fun getstatic(owner: String, name: String, desc: String): Unit =
        insnList.add(FieldInsnNode(Opcodes.GETSTATIC, owner, name, desc))

    fun putstatic(owner: String, name: String, desc: String): Unit =
        insnList.add(FieldInsnNode(Opcodes.PUTSTATIC, owner, name, desc))

    fun getfield(owner: String, name: String, desc: String): Unit =
        insnList.add(FieldInsnNode(Opcodes.GETFIELD, owner, name, desc))

    fun putfield(owner: String, name: String, desc: String): Unit =
        insnList.add(FieldInsnNode(Opcodes.PUTFIELD, owner, name, desc))

    fun invokevirtual(owner: String, name: String, desc: String): Unit =
        insnList.add(MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner, name, desc, false))

    fun invokespecial(owner: String, name: String, desc: String): Unit =
        insnList.add(MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner, name, desc, false))

    fun invokestatic(owner: String, name: String, desc: String, isInterface: Boolean): Unit =
        insnList.add(MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner, name, desc, isInterface))

    fun invokeinterface(owner: String, name: String, desc: String): Unit =
        insnList.add(MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner, name, desc, true))

    // TODO: Invoke dynamic here

    inline fun new(desc: () -> String): Unit = insnList.add(TypeInsnNode(Opcodes.NEW, desc()))
    inline fun newarray(desc: () -> String): Unit = insnList.add(TypeInsnNode(Opcodes.NEWARRAY, desc()))
    inline val arraylength: Unit get() = insnList.add(InsnNode(Opcodes.ARRAYLENGTH))
    inline val athrow: Unit get() = insnList.add(InsnNode(Opcodes.ATHROW))
    inline fun checkcast(desc: () -> String): Unit = insnList.add(TypeInsnNode(Opcodes.CHECKCAST, desc()))
    inline fun instanceof(desc: () -> String): Unit = insnList.add(TypeInsnNode(Opcodes.INSTANCEOF, desc()))

    inline val monitorenter: Unit get() = insnList.add(InsnNode(Opcodes.MONITORENTER))
    inline val monitorexit: Unit get() = insnList.add(InsnNode(Opcodes.MONITOREXIT))

    // Extended

    inline fun ifnull(target: () -> LabelNode): Unit = insnList.add(JumpInsnNode(Opcodes.IFNULL, target()))
    inline fun ifnonnull(target: () -> LabelNode): Unit = insnList.add(JumpInsnNode(Opcodes.IFNONNULL, target()))
    fun muiltianewarray(desc: String, dim: Int): Unit = insnList.add(MultiANewArrayInsnNode(desc, dim))


    // Misc
    inline val newLabel: LabelNode get() = LabelNode()
    fun label(label: LabelNode, lineNumber: Int? = null): Unit = insnList.add(label).apply {
        lineNumber?.also { insnList.add(LineNumberNode(it, label)) }
    }

    fun f_new(local: List<Any>, stack: List<Any>): Unit =
        insnList.add(FrameNode(Opcodes.F_NEW, local.size, local.toTypedArray(), stack.size, stack.toTypedArray()))


    fun f_full(local: List<Any>, stack: List<Any>): Unit =
        insnList.add(FrameNode(Opcodes.F_FULL, local.size, local.toTypedArray(), stack.size, stack.toTypedArray()))

    fun f_append(local: List<Any>): Unit =
        insnList.add(FrameNode(Opcodes.F_FULL, local.size, local.toTypedArray(), 0, null))

    fun f_chop(numLocal: Int): Unit = insnList.add(FrameNode(Opcodes.F_FULL, numLocal, null, 0, null))

    inline val f_same: Unit get() = insnList.add(FrameNode(Opcodes.F_SAME, 0, null, 0, null))

    fun f_same1(vararg stack: Any): Unit = insnList.add(FrameNode(Opcodes.F_SAME1, 0, null, stack.size, stack))
}
