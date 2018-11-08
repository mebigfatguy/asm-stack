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
        opcodes.set(19); // LDC_W
        opcodes.set(20); // LDC2_W
        opcodes.set(26); // ILOAD_0
        opcodes.set(27); // ILOAD_1
        opcodes.set(28); // ILOAD_2
        opcodes.set(29); // ILOAD_3
        opcodes.set(30); // LLOAD_0
        opcodes.set(31); // LLOAD_1
        opcodes.set(32); // LLOAD_2
        opcodes.set(33); // LLOAD_3
        opcodes.set(34); // FLOAD_0
        opcodes.set(35); // FLOAD_1
        opcodes.set(36); // FLOAD_2
        opcodes.set(37); // FLOAD_3
        opcodes.set(38); // DLOAD_0
        opcodes.set(39); // DLOAD_1
        opcodes.set(40); // DLOAD_2
        opcodes.set(41); // DLOAD_3
        opcodes.set(42); // ALOAD_0
        opcodes.set(43); // ALOAD_1
        opcodes.set(44); // ALOAD_2
        opcodes.set(45); // ALOAD_3
        opcodes.set(59); // ISTORE_0
        opcodes.set(60); // ISTORE_1
        opcodes.set(61); // ISTORE_2
        opcodes.set(62); // ISTORE_3
        opcodes.set(63); // LSTORE_0
        opcodes.set(64); // LSTORE_1
        opcodes.set(65); // LSTORE_2
        opcodes.set(66); // LSTORE_3
        opcodes.set(67); // FSTORE_0
        opcodes.set(68); // FSTORE_1
        opcodes.set(69); // FSTORE_2
        opcodes.set(70); // FSTORE_3
        opcodes.set(71); // DSTORE_0
        opcodes.set(72); // DSTORE_1
        opcodes.set(73); // DSTORE_2
        opcodes.set(74); // DSTORE_3
        opcodes.set(75); // ASTORE_0
        opcodes.set(76); // ASTORE_1
        opcodes.set(77); // ASTORE_2
        opcodes.set(78); // ASTORE_3

        opcodes.set(90); // DUP_X1 -- it's not clear if this opcode is used by javac
        opcodes.set(91); // DUP_X2 -- it's not clear if this opcode is used by javac
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
    public void testArrays() throws Exception {
        ParameterStackMethodVisitor psmv = new ParameterStackMethodVisitor(Opcodes.ASM6, false) {
            public void visitEnd() {
                Assert.assertTrue(getStack().isEmpty());
            }
        };

        OpcodeCollectingMethodVisitor ocmv = new OpcodeCollectingMethodVisitor(psmv, opcodes);

        try (InputStream clsStream = ParameterStackMethodVisitorTest.class
                .getResourceAsStream("/" + ParameterStackMethodVisitorTest.class.getName().replace('.', '/') + ".class")) {
            new ClassReader(clsStream).accept(new MethodPickingClassVisitor("arrays", ocmv), ClassReader.SKIP_FRAMES);
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

    public void arrays() {
        int[] ia = new int[] { 0, 1, 2, 3, 4};
        ia[0] = ia[1] + ia[2] + ia[3] + ia[4];
        long[] la = new long[] { 0, 1, 2, 3, 4};
        la[0] = la[1] + la[2] + la[3] + la[4];
        float[] fa = new float[] { 0, 1, 2, 3, 4};
        fa[0] = fa[1] + fa[2] + fa[3] + fa[4];
        double[] da = new double[] { 0, 1, 2, 3, 4};
        da[0] = da[1] + da[2] + da[3] + da[4];
        String[] oa = new String[] { "Hi", "Hello", "Howdy"};
        oa[0] = oa[1] + oa[2];
        byte[] ba = new byte[] { 0, 1, 2, 3, 4};
        ba[0] = (byte) (ba[1] + ba[2] + ba[3] + ba[4]);
        short[] sa = new short[] { 0, 1, 2, 3, 4};
        sa[0] = (short) (sa[1] + sa[2] + sa[3] + sa[4]);
        char[] ca = new char[] { '0', '1', '2', '3', '4'};
        ca[0] = (char) (ca[1] + ca[2] + ca[3] + ca[4]);



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
        short s = (short) 300;
        int r = 70000;

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

    public long test3(int i, int j) {
        return sm(i) | sm(j);
    }

    public String test4(List<String> s, String x) {
        synchronized (s) {
            s.remove(x);
            s.add(x + x);
            test3(0, 0);
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
