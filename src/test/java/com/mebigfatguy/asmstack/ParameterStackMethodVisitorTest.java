package com.mebigfatguy.asmstack;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

public class ParameterStackMethodVisitorTest {

    @Test
    public void testSimpleReturn() {

        ParameterStackMethodVisitor v = new ParameterStackMethodVisitor(Opcodes.ASM6, Opcodes.ACC_PUBLIC, "test", "()Ljava/lang/Object;",
                "()Ljava/lang/Object;", new String[0]);
        v.visitCode();

        v.visitVarInsn(Opcodes.ALOAD, 1);
        v.visitInsn(Opcodes.ARETURN);

        v.visitEnd();
    }
}
