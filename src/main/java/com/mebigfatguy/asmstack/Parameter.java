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

public class Parameter {

    private String typeSignature;
    private int variableSlot;
    private Field field;
    private Object value;

    public Parameter(String signature) {
        typeSignature = signature;
    }

    Parameter(String signature, Field fld) {
        typeSignature = signature;
        field = fld;
    }

    Parameter(String signature, Object cons) {
        typeSignature = signature;
        value = cons;
    }

    public String getTypeSignature() {
        return typeSignature;
    }

    public void setTypeSignature(String sig) {
        typeSignature = sig;
    }

    public int getVariableSlot() {
        return variableSlot;
    }

    public Field getField() {
        return field;
    }

    public <T> T getValue() {
        return (T) value;
    }

    public void setValue(Object o) {
        value = o;
    }
}
