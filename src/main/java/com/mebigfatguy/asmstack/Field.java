package com.mebigfatguy.asmstack;

public class Field {
    private String owner;
    private String name;
    private String descriptor;

    public Field(String owner, String name, String descriptor) {
        this.owner = owner;
        this.name = name;
        this.descriptor = descriptor;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }
}
