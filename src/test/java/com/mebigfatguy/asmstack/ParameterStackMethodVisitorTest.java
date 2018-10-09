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

import java.io.IOException;
import java.io.InputStream;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ParameterStackMethodVisitorTest {

    @Test
    public void testTest1() throws IOException {

        ParameterStackMethodVisitor psmv = new ParameterStackMethodVisitor(Opcodes.ASM6) {
            public void visitEnd() {
                Assert.assertTrue(getStack().isEmpty());
            }
        };

        try (InputStream clsStream = ParameterStackMethodVisitorTest.class
                .getResourceAsStream("/" + ParameterStackMethodVisitorTest.class.getName().replace('.', '/') + ".class")) {
            new ClassReader(clsStream).accept(new MethodPickingClassVisitor("test1", psmv), ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        }

        try (InputStream clsStream = ParameterStackMethodVisitorTest.class
                .getResourceAsStream("/" + ParameterStackMethodVisitorTest.class.getName().replace('.', '/') + ".class")) {
            new ClassReader(clsStream).accept(new MethodPickingClassVisitor("test2", psmv), ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        }
    }

    public Object test1(int i, String s) {
        return s;
    }

    public void test2(List<String> in, Deque<String> out) {
        for (String s : in) {
            out.addLast(s);
        }
    }

    class MethodPickingClassVisitor extends ClassVisitor {
        private String targetMethodName;
        private ParameterStackMethodVisitor methodVisitor;

        public MethodPickingClassVisitor(String methodName, ParameterStackMethodVisitor mv) {
            super(Opcodes.ASM6);
            targetMethodName = methodName;
            methodVisitor = mv;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if (name.equals(targetMethodName)) {
                methodVisitor.visitMethod(access, name, descriptor, signature, exceptions);
                return methodVisitor;
            }

            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }
    }
}
