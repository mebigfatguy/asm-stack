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

import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SignatureUtils {

    private static final char ARRAY_PREFIX = '[';
    private static final char CLASS_PREFIX = 'L';
    private static final String CLASS_SUFFIX = ";";

    static List<String> getParameterSignatures(String descriptor) {
        int start = descriptor.indexOf('(') + 1;
        int limit = descriptor.lastIndexOf(')');

        if ((limit - start) == 0) {
            return Collections.emptyList();
        }

        List<String> parmSignatures = new ArrayList<>();
        for (int i = start; i < limit; i++) {
            char prefix = descriptor.charAt(i);
            if ((prefix == ARRAY_PREFIX) || (prefix == CLASS_PREFIX)) {
                int semiPos = descriptor.indexOf(CLASS_SUFFIX, i + 1);
                parmSignatures.add(descriptor.substring(i, i = semiPos + 1));
            } else {
                parmSignatures.add(descriptor.substring(i, ++i));
            }
        }

        return parmSignatures;
    }

    static String getReturnSignature(String descriptor) {
        int start = descriptor.indexOf(')') + 1;
        return descriptor.substring(start);
    }

    static String typeToSignature(int type) {
        switch (type) {
            case Opcodes.T_BOOLEAN:
                return "Z";

            case Opcodes.T_CHAR:
                return "C";

            case Opcodes.T_FLOAT:
                return "F";

            case Opcodes.T_DOUBLE:
                return "D";

            case Opcodes.T_BYTE:
                return "B";

            case Opcodes.T_SHORT:
                return "S";

            case Opcodes.T_INT:
                return "I";

            case Opcodes.T_LONG:
                return "J";

            default:
                return "V";
        }
    }
}
