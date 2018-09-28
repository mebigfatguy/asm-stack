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
                stack.pop(2);
                stack.push(new Parameter("I", null));
            break;

            case Opcodes.LADD:
                stack.pop(2);
                stack.push(new Parameter("J", null));
            break;

            case Opcodes.FADD:
                stack.pop(2);
                stack.push(new Parameter("F", null));
            break;

            case Opcodes.DADD:
                stack.pop(2);
                stack.push(new Parameter("D", null));
            break;

            case Opcodes.ISUB:
                stack.pop(2);
                stack.push(new Parameter("I", null));
            break;

            case Opcodes.LSUB:
                stack.pop(2);
                stack.push(new Parameter("J", null));
            break;

            case Opcodes.FSUB:
                stack.pop(2);
                stack.push(new Parameter("F", null));
            break;

            case Opcodes.DSUB:
                stack.pop(2);
                stack.push(new Parameter("D", null));
            break;

            case Opcodes.IMUL:
                stack.pop(2);
                stack.push(new Parameter("I", null));
            break;

            case Opcodes.LMUL:
                stack.pop(2);
                stack.push(new Parameter("J", null));
            break;

            case Opcodes.FMUL:
                stack.pop(2);
                stack.push(new Parameter("F", null));
            break;

            case Opcodes.DMUL:
                stack.pop(2);
                stack.push(new Parameter("D", null));
            break;

            case Opcodes.IDIV:
                stack.pop(2);
                stack.push(new Parameter("I", null));
            break;

            case Opcodes.LDIV:
                stack.pop(2);
                stack.push(new Parameter("J", null));
            break;

            case Opcodes.FDIV:
                stack.pop(2);
                stack.push(new Parameter("F", null));
            break;

            case Opcodes.DDIV:
                stack.pop(2);
                stack.push(new Parameter("D", null));
            break;

            case Opcodes.IREM:
                stack.pop(2);
                stack.push(new Parameter("I", null));
            break;

            case Opcodes.LREM:
                stack.pop(2);
                stack.push(new Parameter("J", null));
            break;

            case Opcodes.FREM:
                stack.pop(2);
                stack.push(new Parameter("F", null));
            break;

            case Opcodes.DREM:
                stack.pop(2);
                stack.push(new Parameter("D", null));
            break;

            case Opcodes.INEG:
                stack.pop();
                stack.push(new Parameter("I", null));
            break;

            case Opcodes.LNEG:
                stack.pop();
                stack.push(new Parameter("J", null));
            break;

            case Opcodes.FNEG:
                stack.pop();
                stack.push(new Parameter("F", null));
            break;

            case Opcodes.DNEG:
                stack.pop();
                stack.push(new Parameter("D", null));
            break;

            case Opcodes.ISHL:
                stack.pop(2);
                stack.push(new Parameter("I", null));
            break;

            case Opcodes.LSHL:
                stack.pop(2);
                stack.push(new Parameter("J", null));
                break;

            case Opcodes.ISHR:
                stack.pop(2);
                stack.push(new Parameter("I", null));
                break;

            case Opcodes.LSHR:
                stack.pop(2);
                stack.push(new Parameter("J", null));
                break;

            case Opcodes.IUSHR:
                stack.pop(2);
                stack.push(new Parameter("I", null));
                break;

            case Opcodes.LUSHR:
                stack.pop(2);
                stack.push(new Parameter("J", null));
                break;

            case Opcodes.IAND:
                stack.pop(2);
                stack.push(new Parameter("I", null));
                break;

            case Opcodes.LAND:
                stack.pop(2);
                stack.push(new Parameter("J", null));
                break;

            case Opcodes.IOR:
                stack.pop(2);
                stack.push(new Parameter("I", null));
                break;

            case Opcodes.LOR:
                stack.pop(2);
                stack.push(new Parameter("J", null));
                break;

            case Opcodes.IXOR:
                stack.pop(2);
                stack.push(new Parameter("I", null));
                break;

            case Opcodes.LXOR:
                stack.pop(2);
                stack.push(new Parameter("J", null));
                break;

            case Opcodes.I2L:
                stack.pop();
                stack.push(new Parameter("J", null));
            break;

            case Opcodes.I2F:
                stack.pop();
                stack.push(new Parameter("F", null));
                break;

            case Opcodes.I2D:
                stack.pop();
                stack.push(new Parameter("D", null));
            break;

            case Opcodes.L2I:
                stack.pop();
                stack.push(new Parameter("I", null));
                break;

            case Opcodes.L2F:
                stack.pop();
                stack.push(new Parameter("F", null));
            break;

            case Opcodes.L2D:
                stack.pop();
                stack.push(new Parameter("D", null));
                break;

            case Opcodes.F2I:
                stack.pop();
                stack.push(new Parameter("I", null));
            break;

            case Opcodes.F2L:
                stack.pop();
                stack.push(new Parameter("J", null));
            break;

            case Opcodes.F2D:
                stack.pop();
                stack.push(new Parameter("D", null));
                break;

            case Opcodes.D2I:
                stack.pop();
                stack.push(new Parameter("I", null));
            break;

            case Opcodes.D2L:
                stack.pop();
                stack.push(new Parameter("J", null));
            break;

            case Opcodes.D2F:
                stack.pop();
                stack.push(new Parameter("F", null));
                break;

            case Opcodes.I2B:
                stack.pop();
                stack.push(new Parameter("B", null));
            break;

            case Opcodes.I2C:
                stack.pop();
                stack.push(new Parameter("C", null));
                break;

            case Opcodes.I2S:
                stack.push(new Parameter("S", null));
            break;

            case Opcodes.LCMP:
                stack.pop(2);
                stack.push(new Parameter("I", null));
            break;

            case Opcodes.FCMPL:
                stack.pop(2);
                stack.push(new Parameter("I", null));
            break;

            case Opcodes.FCMPG:
                stack.pop(2);
                stack.push(new Parameter("I", null));
            break;

            case Opcodes.DCMPL:
                stack.pop(2);
                stack.push(new Parameter("I", null));
            break;

            case Opcodes.DCMPG:
                stack.pop(2);
                stack.push(new Parameter("I", null));
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
                stack.pop();
                stack.push(new Parameter("I", null));
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
                stack.push(new Parameter("I", null));
                break;

            case Opcodes.LLOAD:
                stack.push(new Parameter("J", null));
                break;

            case Opcodes.FLOAD:
                stack.push(new Parameter("F", null));
            break;

            case Opcodes.DLOAD:
                stack.push(new Parameter("D", null));
            break;

            case Opcodes.ALOAD:
                // TODO get type from variables
                stack.push(new Parameter(null));
            break;

            case Opcodes.ISTORE:
                Parameter p = stack.pop();
            break;

            case Opcodes.LSTORE:
                Parameter p = stack.pop();
            break;

            case Opcodes.FSTORE:
                Parameter p = stack.pop();
            break;

            case Opcodes.DSTORE:
                Parameter p = stack.pop();
            break;

            case Opcodes.ASTORE:
                Parameter p = stack.pop();
            break;
        }
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {

        super.visitTypeInsn(opcode, type);

        switch (opcode) {
            case Opcodes.NEW:
                stack.push(new Parameter(type, null));
            break;

            case Opcodes.ANEWARRAY:
                stack.pop();
                stack.push(new Parameter(type, null));
            break;

            case Opcodes.CHECKCAST:
                stack.pop();
            break;

            case Opcodes.INSTANCEOF:
                stack.pop();
                stack.push(new Parameter("Z", null));
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
        stack.pop(parms.size() + (opcode == INVOKESTATIC) ? 0 : 1);

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
        stack.pop(parms.size() + (opcode == INVOKESTATIC) ? 0 : 1);
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);

        stack.pop(4);
        // need to pop more args
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
                // probably need some opaque type
                stack.push(new Parameter("I", null));
            break;

            case Opcodes.IFNULL:
                stack.pop();
            break;

            case Opcodes.IFNONNULL:
                stack.pop();
            break;
        }
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

        stack.pop(numDimensions);
        stack.push(new Parameter(descriptor, null));
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
