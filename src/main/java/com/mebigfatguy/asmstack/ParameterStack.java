package com.mebigfatguy.asmstack;

import java.util.ArrayList;
import java.util.List;

public class ParameterStack {

    private List<Parameter> stack = new ArrayList<>();

    void push(Parameter p) {
        stack.add(p);
    }

    Parameter pop() {
        return stack.remove(stack.size() - 1);
    }

}
