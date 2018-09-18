/*
 * asm-stack - an asm visitor that tracks the parameter stack
 * Copyright 2018 MeBigFatGuy.com
 * Copyright 2018 Dave Brosius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */
package com.mebigfatguy.asmstack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;

public class ParameterStackMethodVisitor extends MethodVisitor {

    private ParameterStack stack;
    private Map<Integer, Variable> variables;
    private int methodAccess;
    private String methodName;
    private String methodDescriptor;
    private String methodSignature;
    private String[] methodExceptions;
    private int nextParmSlot;

    public ParameterStackMethodVisitor(final int api) {
        this(api, null);
    }

    public ParameterStackMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);

        variables = new HashMap<>();
    }

    public void visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        methodAccess = access;
        methodName = name;
        methodDescriptor = descriptor;
        methodSignature = signature;
        methodExceptions = exceptions;

        nextParmSlot = (access & Opcodes.ACC_STATIC) != 0 ? 0 : 1;
    }

    @Override
    public void visitParameter(String name, int access) {
        Variable v = new Variable(nextParmSlot, name, access);
        variables.put(nextParmSlot, v);

        String sig = v.getSignature();
        if (sig.startsWith("L") || sig.startsWith("[")) {
            nextParmSlot += "J".equals(sig) || "D".equals(sig) ? 2 : 1;
        }
        super.visitParameter(name, access);
    }
    @Override
    public void visitCode() {
        stack = new ParameterStack();
        super.visitCode();
    }

    @Override
    public void visitInsn(int opcode) {

        super.visitInsn(opcode);

        switch (opcode) {
            case Opcodes.NOP:
            break;

            case Opcodes.ACONST_NULL:
                stack.push(new Parameter(null, (Object) null));
            break;

            case Opcodes.ICONST_M1:
                stack.push(new Parameter("I", Integer.valueOf(-1)));
            break;

            case Opcodes.ICONST_0:
                stack.push(new Parameter("I", Integer.valueOf(0)));
            break;

            case Opcodes.ICONST_1:
                stack.push(new Parameter("I", Integer.valueOf(1)));
            break;

            case Opcodes.ICONST_2:
                stack.push(new Parameter("I", Integer.valueOf(2)));
            break;

            case Opcodes.ICONST_3:
                stack.push(new Parameter("I", Integer.valueOf(3)));
            break;

            case Opcodes.ICONST_4:
                stack.push(new Parameter("I", Integer.valueOf(4)));
            break;

            case Opcodes.ICONST_5:
                stack.push(new Parameter("I", Integer.valueOf(5)));
            break;

            case Opcodes.LCONST_0:
                stack.push(new Parameter("J", Long.valueOf(0)));
            break;

            case Opcodes.LCONST_1:
                stack.push(new Parameter("J", Long.valueOf(1)));
            break;

            case Opcodes.FCONST_0:
                stack.push(new Parameter("F", Float.valueOf(0)));
            break;

            case Opcodes.FCONST_1:
                stack.push(new Parameter("F", Float.valueOf(1)));
            break;

            case Opcodes.FCONST_2:
                stack.push(new Parameter("F", Float.valueOf(2)));

            break;

            case Opcodes.DCONST_0:
                stack.push(new Parameter("D", Double.valueOf(0)));
            break;

            case Opcodes.DCONST_1:
                stack.push(new Parameter("D", Double.valueOf(1)));
            break;

            case Opcodes.IALOAD:
                stack.pop(2);
                stack.push(new Parameter("[I", null));
            break;

            case Opcodes.LALOAD:
                stack.pop(2);
                stack.push(new Parameter("[J", null));
            break;

            case Opcodes.FALOAD:
                stack.pop(2);
                stack.push(new Parameter("[F", null));
            break;

            case Opcodes.DALOAD:
                stack.pop(2);
                stack.push(new Parameter("[D", null));
            break;

            case Opcodes.AALOAD:
                stack.pop(2);
                stack.push(new Parameter("[Ljava/lang/Object;", null));
            break;

            case Opcodes.BALOAD:
                stack.pop(2);
                stack.push(new Parameter("[B", null));
                break;

            case Opcodes.CALOAD:
                stack.pop(2);
                stack.push(new Parameter("[C", null));
                break;

            case Opcodes.SALOAD:
                stack.pop(2);
                stack.push(new Parameter("[S", null));
                break;

            case Opcodes.IASTORE:
                stack.pop(3);
            break;

            case Opcodes.LASTORE:
                stack.pop(3);
            break;

            case Opcodes.FASTORE:
                stack.pop(3);
            break;

            case Opcodes.DASTORE:
                stack.pop(3);
            break;

            case Opcodes.AASTORE:
                stack.pop(3);
            break;

            case Opcodes.BASTORE:
                stack.pop(3);
            break;

            case Opcodes.CASTORE:
                stack.pop(3);
            break;

            case Opcodes.SASTORE:
                stack.pop(3);
            break;

            case Opcodes.POP:
                stack.pop();
            break;

            case Opcodes.POP2:
                stack.pop(2);
            break;

            case Opcodes.DUP:
                stack.push(stack.peek(0));

            break;

            case Opcodes.DUP_X1:
            break;

            case Opcodes.DUP_X2:
            break;

            case Opcodes.DUP2:
            break;

            case Opcodes.DUP2_X1:
            break;

            case Opcodes.DUP2_X2:
            break;

            case Opcodes.SWAP:
                Parameter p1 = stack.pop();
                Parameter p2 = stack.pop();
                stack.push(p1);
                stack.push(p2);
            break;

            case Opcodes.IADD:
            break;

            case Opcodes.LADD:
            break;

            case Opcodes.FADD:
            break;

            case Opcodes.DADD:
            break;

            case Opcodes.ISUB:
            break;

            case Opcodes.LSUB:
            break;

            case Opcodes.FSUB:
            break;

            case Opcodes.DSUB:
            break;

            case Opcodes.IMUL:
            break;

            case Opcodes.LMUL:
            break;

            case Opcodes.FMUL:
            break;

            case Opcodes.DMUL:
            break;

            case Opcodes.IDIV:
            break;

            case Opcodes.LDIV:
            break;

            case Opcodes.FDIV:
            break;

            case Opcodes.DDIV:
            break;

            case Opcodes.IREM:
            break;

            case Opcodes.LREM:
            break;

            case Opcodes.FREM:
            break;

            case Opcodes.DREM:
            break;

            case Opcodes.INEG:
            break;

            case Opcodes.LNEG:
            break;

            case Opcodes.FNEG:
            break;

            case Opcodes.DNEG:
            break;

            case Opcodes.ISHL:
            break;

            case Opcodes.LSHL:
            break;

            case Opcodes.ISHR:
            break;

            case Opcodes.LSHR:
            break;

            case Opcodes.IUSHR:
            break;

            case Opcodes.LUSHR:
            break;

            case Opcodes.IAND:
            break;

            case Opcodes.LAND:
            break;

            case Opcodes.IOR:
            break;

            case Opcodes.LOR:
            break;

            case Opcodes.IXOR:
            break;

            case Opcodes.LXOR:
            break;

            case Opcodes.I2L:
            break;

            case Opcodes.I2F:
            break;

            case Opcodes.I2D:
            break;

            case Opcodes.L2I:
            break;

            case Opcodes.L2F:
            break;

            case Opcodes.L2D:
            break;

            case Opcodes.F2I:
            break;

            case Opcodes.F2L:
            break;

            case Opcodes.F2D:
            break;

            case Opcodes.D2I:
            break;

            case Opcodes.D2L:
            break;

            case Opcodes.D2F:
            break;

            case Opcodes.I2B:
            break;

            case Opcodes.I2C:
            break;

            case Opcodes.I2S:
            break;

            case Opcodes.LCMP:
            break;

            case Opcodes.FCMPL:
            break;

            case Opcodes.FCMPG:
            break;

            case Opcodes.DCMPL:
            break;

            case Opcodes.DCMPG:
            break;

            case Opcodes.IRETURN:
                stack.pop();
            break;

            case Opcodes.LRETURN:
                stack.pop();
            break;

            case Opcodes.FRETURN:
                stack.pop();
            break;

            case Opcodes.DRETURN:
                stack.pop();
            break;

            case Opcodes.ARETURN:
                stack.pop();
            break;

            case Opcodes.RETURN:
            break;

            case Opcodes.ARRAYLENGTH:
            break;

            case Opcodes.ATHROW:
                stack.pop();
            break;

            case Opcodes.MONITORENTER:
                stack.pop();
            break;

            case Opcodes.MONITOREXIT:
                stack.pop();
            break;
        }
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {

        super.visitIntInsn(opcode, operand);

        switch (opcode) {
            case Opcodes.BIPUSH:
                stack.push(new Parameter("B", Integer.valueOf(operand)));
            break;

            case Opcodes.SIPUSH:
                stack.push(new Parameter("S", Integer.valueOf(operand)));
            break;

            case Opcodes.NEWARRAY:
            break;
        }
    }

    @Override
    public void visitVarInsn(int opcode, int var) {

        super.visitVarInsn(opcode, var);

        switch (opcode) {
            case Opcodes.ILOAD:
            break;

            case Opcodes.LLOAD:
            break;

            case Opcodes.FLOAD:
            break;

            case Opcodes.DLOAD:
            break;

            case Opcodes.ALOAD:
                // TODO get type from variables
                stack.push(new Parameter(null));
            break;

            case Opcodes.ISTORE:
            break;

            case Opcodes.LSTORE:
            break;

            case Opcodes.FSTORE:
            break;

            case Opcodes.DSTORE:
            break;

            case Opcodes.ASTORE:
            break;
        }
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {

        super.visitTypeInsn(opcode, type);

        switch (opcode) {
            case Opcodes.NEW:
            break;

            case Opcodes.ANEWARRAY:
            break;

            case Opcodes.CHECKCAST:
                stack.pop();
            break;

            case Opcodes.INSTANCEOF:
            break;
        }
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {

        super.visitFieldInsn(opcode, owner, name, descriptor);

        switch (opcode) {
            case Opcodes.GETSTATIC:
                stack.pop();
                stack.push(new Parameter(descriptor, new Field(owner, name, descriptor)));
            break;

            case Opcodes.PUTSTATIC:
                stack.pop();
            break;

            case Opcodes.GETFIELD:
                stack.pop();
                stack.push(new Parameter(descriptor, new Field(owner, name, descriptor)));
            break;

            case Opcodes.PUTFIELD:
                stack.pop(2);
            break;
        }

    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor) {

        super.visitMethodInsn(opcode, owner, name, descriptor);

        switch (opcode) {
            case Opcodes.INVOKEVIRTUAL:
            break;

            case Opcodes.INVOKESPECIAL:
            break;

            case Opcodes.INVOKESTATIC:
            break;

            case Opcodes.INVOKEINTERFACE:
            break;
        }

        List<String> parms = SignatureUtils.getParameterSignatures(descriptor);
        stack.pop(parms.size());

    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {

        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);

        switch (opcode) {
            case Opcodes.INVOKEVIRTUAL:
            break;

            case Opcodes.INVOKESPECIAL:
            break;

            case Opcodes.INVOKESTATIC:
            break;

            case Opcodes.INVOKEINTERFACE:
            break;
        }

        List<String> parms = SignatureUtils.getParameterSignatures(descriptor);
        stack.pop(parms.size());
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {

        super.visitJumpInsn(opcode, label);

        switch (opcode) {
            case Opcodes.IFEQ:
                stack.pop();
                stack.pop();
            break;

            case Opcodes.IFNE:
                stack.pop();
                stack.pop();
            break;

            case Opcodes.IFLT:
                stack.pop();
                stack.pop();
            break;

            case Opcodes.IFGE:
                stack.pop();
                stack.pop();
            break;

            case Opcodes.IFGT:
                stack.pop();
                stack.pop();
            break;

            case Opcodes.IFLE:
                stack.pop();
                stack.pop();
            break;

            case Opcodes.IF_ICMPEQ:
                stack.pop();
                stack.pop();
            break;

            case Opcodes.IF_ICMPNE:
                stack.pop();
                stack.pop();
            break;

            case Opcodes.IF_ICMPLT:
                stack.pop();
                stack.pop();
            break;

            case Opcodes.IF_ICMPGE:
                stack.pop();
                stack.pop();
            break;

            case Opcodes.IF_ICMPGT:
                stack.pop();
                stack.pop();
            break;

            case Opcodes.IF_ICMPLE:
                stack.pop();
                stack.pop();
            break;

            case Opcodes.IF_ACMPEQ:
                stack.pop();
                stack.pop();
            break;

            case Opcodes.IF_ACMPNE:
                stack.pop();
                stack.pop();
            break;

            case Opcodes.GOTO:
            break;

            case Opcodes.JSR:
            break;

            case Opcodes.IFNULL:
                stack.pop();
            break;

            case Opcodes.IFNONNULL:
                stack.pop();
            break;
        }
        super.visitJumpInsn(opcode, label);
    }

    @Override
    public void visitLabel(Label label) {
        super.visitLabel(label);
    }

    @Override
    public void visitLdcInsn(Object value) {

        super.visitLdcInsn(value);
        String type;
        if (value instanceof String) {
            type = "Ljava/lang/String;";
        } else if (value instanceof Integer) {
            type = "I";
        } else if (value instanceof Long) {
            type = "J";
        } else if (value instanceof Float) {
            type = "F";
        } else if (value instanceof Double) {
            type = "D";
        } else {
            type = null;
        }
        stack.push(new Parameter(type, value));
        super.visitLdcInsn(value);
    }

    @Override
    public void visitIincInsn(int var, int increment) {

        super.visitIincInsn(var, increment);

        Parameter parameter = stack.peek(0);
        Integer val = parameter.getValue();
        if (val != null) {
            parameter.setValue(val + 1);
        }
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        super.visitTableSwitchInsn(min, max, dflt, labels);

        stack.pop();
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        super.visitLookupSwitchInsn(dflt, keys, labels);

        stack.pop();
    }

    @Override
    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
        super.visitMultiANewArrayInsn(descriptor, numDimensions);
    }

    @Override
    public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        return super.visitInsnAnnotation(typeRef, typePath, descriptor, visible);
    }

    @Override
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        super.visitTryCatchBlock(start, end, handler, type);
    }

    @Override
    public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        return super.visitTryCatchAnnotation(typeRef, typePath, descriptor, visible);
    }

    @Override
    public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
        super.visitLocalVariable(name, descriptor, signature, start, end, index);
    }

    @Override
    public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String descriptor,
            boolean visible) {
        return super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, descriptor, visible);
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        super.visitLineNumber(line, start);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack, maxLocals);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        stack = null;
    }
}
