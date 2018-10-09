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

import java.util.ArrayList;
import java.util.List;

public class ParameterStack {

    private List<Parameter> stack = new ArrayList<>();

    void push(Parameter p) {
        stack.add(p);
    }

    public Parameter pop() {
        return stack.remove(stack.size() - 1);
    }

    public void pop(int numItems) {

        while (numItems-- > 0) {
            stack.remove(stack.size() - 1);
        }
    }

    public Parameter peek(int offset) {
        return stack.get(stack.size() - offset - 1);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

}
