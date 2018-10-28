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

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;
import java.util.Deque;
import java.util.List;


public class ParameterStackMethodVisitorTest {

    private static BitSet opcodes;

    @BeforeClass
    public static void beforeClass() {
        opcodes = new BitSet();
        opcodes.set(Opcodes.NOP);
    }

    @AfterClass
    public static void afterClass() {
        boolean fail = false;
        for (int i = 0; i < 256; i++) {
            if (!opcodes.get(i)) {
                System.out.println("Opcode: " + i + " was not tested");
                fail = true;
            }
        }

        if (fail) {
            Assert.fail("Not all opcodes parsed");
        }
    }

    @Test
    public void testConsts() throws Exception {
        ParameterStackMethodVisitor psmv = new ParameterStackMethodVisitor(Opcodes.ASM6, false) {
            public void visitEnd() {
                Assert.assertTrue(getStack().isEmpty());
            }
        };

        OpcodeCollectingMethodVisitor ocmv = new OpcodeCollectingMethodVisitor(psmv, opcodes);

        try (InputStream clsStream = ParameterStackMethodVisitorTest.class
                .getResourceAsStream("/" + ParameterStackMethodVisitorTest.class.getName().replace('.', '/') + ".class")) {
            new ClassReader(clsStream).accept(new MethodPickingClassVisitor("consts", ocmv), ClassReader.SKIP_FRAMES);
        }
    }

    @Test
    public void testTest1() throws IOException {

        ParameterStackMethodVisitor psmv = new ParameterStackMethodVisitor(Opcodes.ASM6, false) {
            public void visitEnd() {
                Assert.assertTrue(getStack().isEmpty());
            }
        };

        OpcodeCollectingMethodVisitor ocmv = new OpcodeCollectingMethodVisitor(psmv, opcodes);

        try (InputStream clsStream = ParameterStackMethodVisitorTest.class
                .getResourceAsStream("/" + ParameterStackMethodVisitorTest.class.getName().replace('.', '/') + ".class")) {
            new ClassReader(clsStream).accept(new MethodPickingClassVisitor("test1", ocmv), ClassReader.SKIP_FRAMES);
        }
    }

    @Test
    public void testTest2() throws IOException {

        ParameterStackMethodVisitor psmv = new ParameterStackMethodVisitor(Opcodes.ASM6, false) {
            public void visitEnd() {
                Assert.assertTrue(getStack().isEmpty());
            }
        };

        OpcodeCollectingMethodVisitor ocmv = new OpcodeCollectingMethodVisitor(psmv, opcodes);

        try (InputStream clsStream = ParameterStackMethodVisitorTest.class
                .getResourceAsStream("/" + ParameterStackMethodVisitorTest.class.getName().replace('.', '/') + ".class")) {
            new ClassReader(clsStream).accept(new MethodPickingClassVisitor("test2", ocmv), ClassReader.SKIP_FRAMES);
        }
    }

    @Test
    public void testTest3() throws IOException {

        ParameterStackMethodVisitor psmv = new ParameterStackMethodVisitor(Opcodes.ASM6, false) {
            public void visitEnd() {
                Assert.assertTrue(getStack().isEmpty());
            }
        };

        OpcodeCollectingMethodVisitor ocmv = new OpcodeCollectingMethodVisitor(psmv, opcodes);

        try (InputStream clsStream = ParameterStackMethodVisitorTest.class
                .getResourceAsStream("/" + ParameterStackMethodVisitorTest.class.getName().replace('.', '/') + ".class")) {
            new ClassReader(clsStream).accept(new MethodPickingClassVisitor("test3", ocmv), ClassReader.SKIP_FRAMES);
        }
    }

    @Test
    public void testTest4() throws IOException {

        ParameterStackMethodVisitor psmv = new ParameterStackMethodVisitor(Opcodes.ASM6, false) {
            public void visitEnd() {
                Assert.assertTrue(getStack().isEmpty());
            }
        };

        OpcodeCollectingMethodVisitor ocmv = new OpcodeCollectingMethodVisitor(psmv, opcodes);

        try (InputStream clsStream = ParameterStackMethodVisitorTest.class
                .getResourceAsStream("/" + ParameterStackMethodVisitorTest.class.getName().replace('.', '/') + ".class")) {
            new ClassReader(clsStream).accept(new MethodPickingClassVisitor("test4", ocmv), ClassReader.SKIP_FRAMES);
        }
    }

    @Test
    public void testTest5() throws IOException {

        ParameterStackMethodVisitor psmv = new ParameterStackMethodVisitor(Opcodes.ASM6, false) {
            public void visitEnd() {
                Assert.assertTrue(getStack().isEmpty());
            }
        };

        OpcodeCollectingMethodVisitor ocmv = new OpcodeCollectingMethodVisitor(psmv, opcodes);

        try (InputStream clsStream = ParameterStackMethodVisitorTest.class
                .getResourceAsStream("/" + ParameterStackMethodVisitorTest.class.getName().replace('.', '/') + ".class")) {
            new ClassReader(clsStream).accept(new MethodPickingClassVisitor("test5", ocmv), ClassReader.SKIP_FRAMES);
        }
    }

    public int consts() {
        Object oo = null;
        int minus1 = -1;
        int zero = 0;
        int one = 1;
        int two = 2;
        int three = 3;
        int four = 4;
        int five = 5;
        long lZero = 0L;
        long lOne = 1L;
        float fZero = 0.0f;
        float fOne = 1.0f;
        float fTwo = 2.0f;
        double dZero = 0.0;
        double dOne = 1.0;
        byte b = (byte) 10;
        short s = (short) 10;
        int r = 42;

        long l = lZero & lOne;
        float f = fZero + fOne + fTwo;
        double d = dZero + dOne;
        oo = new Object();
        return ((minus1 & one | zero ^ two & three & (~four) | five) > oo.hashCode()) ? (int) f : (int) l;
    }

    public Object test1(int i, String s) {
        return s;
    }

    public void test2(List<String> in, Deque<String> out) {
        for (String s : in) {
            out.addLast(s);
        }
    }

    public int test3(int i, int j) {
        return sm(i) | sm(j);
    }

    public String test4(List<String> s, String x) {
        synchronized (s) {
            s.remove(x);
            s.add(x + x);
        }
        return s.get(0);
    }

    public String test5(int i, int j) {
        int k = i & j;
        int[][][] a = new int[i][j][k];

        return a.toString();
    }

    public static int sm(int i) {
        return i & 5;
    }

    class MethodPickingClassVisitor extends ClassVisitor {
        private String targetMethodName;
        private MethodVisitor methodVisitor;

        public MethodPickingClassVisitor(String methodName, MethodVisitor mv) {
            super(Opcodes.ASM6);
            targetMethodName = methodName;
            methodVisitor = mv;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if (name.equals(targetMethodName)) {
                return methodVisitor;
            }

            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }
    }
}
