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

import org.junit.Test;
import org.objectweb.asm.Opcodes;

public class ParameterStackMethodVisitorTest {

    @Test
    public void testSimpleReturn() {

        ParameterStackMethodVisitor v = new ParameterStackMethodVisitor(Opcodes.ASM6, Opcodes.ACC_PUBLIC, "test", "(ILjava/lang/String;)Ljava/lang/Object;",
                "(ILjava/lang/String;)Ljava/lang/Object;", new String[0]);

        v.visitCode();

        v.visitParameter("myInt", 0);
        v.visitParameter("myString", 0);

        v.visitVarInsn(Opcodes.ALOAD, 1);
        v.visitInsn(Opcodes.ARETURN);

        v.visitEnd();
    }
}
