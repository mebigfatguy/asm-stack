package com.mebigfatguy.asmstack;

public class Parameter {

    private int variableSlot;
    private Field field;
    private Object constant;

    public Parameter() {
    }

    Parameter(Field fld) {
        field = fld;
    }

    Parameter(Object cons) {
        constant = cons;
    }

    public int getVariableSlot() {
        return variableSlot;
    }

    public Field getField() {
        return field;
    }
}
