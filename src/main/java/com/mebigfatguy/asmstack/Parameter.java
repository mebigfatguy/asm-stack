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

public class Parameter {

    private String typeSignature;
    private int variableSlot;
    private Field field;
    private Object constant;

    public Parameter(String signature) {
        typeSignature = signature;
    }

    Parameter(String signature, Field fld) {
        typeSignature = signature;
        field = fld;
    }

    Parameter(String signature, Object cons) {
        typeSignature = signature;
        constant = cons;
    }

    public String getTypeSignature() {
        return typeSignature;
    }

    public int getVariableSlot() {
        return variableSlot;
    }

    public Field getField() {
        return field;
    }
}
