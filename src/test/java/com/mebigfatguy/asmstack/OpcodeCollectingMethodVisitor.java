package com.mebigfatguy.asmstack;

import org.objectweb.asm.*;

import java.util.BitSet;

public class OpcodeCollectingMethodVisitor extends MethodVisitor {

    private BitSet opcodes;

    public OpcodeCollectingMethodVisitor(MethodVisitor visitor, BitSet opcodes) {
        super(Opcodes.ASM6, visitor);
        this.opcodes = opcodes;
    }

    @Override
    public void visitInsn(int opcode) {
        super.visitInsn(opcode);
        opcodes.set(opcode);
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        super.visitIntInsn(opcode, operand);
        opcodes.set(opcode);
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        super.visitVarInsn(opcode, var);
        opcodes.set(opcode);
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        super.visitTypeInsn(opcode, type);
        opcodes.set(opcode);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        super.visitFieldInsn(opcode, owner, name, descriptor);
        opcodes.set(opcode);
    }

    @Override
    @Deprecated
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor) {
        super.visitMethodInsn(opcode, owner, name, descriptor);
        opcodes.set(opcode);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        opcodes.set(opcode);
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
        opcodes.set(Opcodes.INVOKEDYNAMIC);
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        super.visitJumpInsn(opcode, label);
        opcodes.set(opcode);
    }

    @Override
    public void visitLdcInsn(Object value) {
        super.visitLdcInsn(value);
        opcodes.set(opcode);
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        super.visitIincInsn(var, increment);
        opcodes.set(opcode);
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        super.visitTableSwitchInsn(min, max, dflt, labels);
        opcodes.set(Opcodes.TABLESWITCH);
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        super.visitLookupSwitchInsn(dflt, keys, labels);
        opcodes.set(Opcodes.LOOKUPSWITCH);
    }

    @Override
    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
        super.visitMultiANewArrayInsn(descriptor, numDimensions);
        opcodes.set(Opcodes.MULTIANEWARRAY);
    }
}
