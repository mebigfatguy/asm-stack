/*
 * asm-stack - an asm visitor that tracks the parameter stack
 * Copyright 2018-2019 MeBigFatGuy.com
 * Copyright 2018-2019 Dave Brosius
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

import org.objectweb.asm.Label;

public class Variable {
    private int slot;
    private String name;
    private String signature;
    private Label start;
    private Label end;

    public Variable(int variableSlot, String variableName, String variableSignature, Label variableStart, Label variableEnd) {
        slot = variableSlot;
        name = variableName;
        signature = variableSignature;
        start = variableStart;
        end = variableEnd;
    }

    public int getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public String getSignature() {
        return signature;
    }

    public Label getStart() { return start; }

    public Label getEnd() { return end; }
}
