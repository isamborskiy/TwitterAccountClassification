package com.samborskiy.entity;

/**
 * Created by Whiplash on 23.04.2015.
 */
public class Attribute {

    private final double value;
    private final String name;

    public Attribute(double value, String name) {
        this.value = value;
        this.name = name;
    }

    public Attribute(double value, Class clazz) {
        this(value, clazz.getSimpleName());
    }

    @Override
    public String toString() {
        return String.format("@attribute %s numeric", name);
    }

    public double getValue() {
        return value;
    }
}
