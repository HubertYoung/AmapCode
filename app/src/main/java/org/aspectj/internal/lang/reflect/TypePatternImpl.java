package org.aspectj.internal.lang.reflect;

import org.aspectj.lang.reflect.TypePattern;

public class TypePatternImpl implements TypePattern {
    private String a;

    public TypePatternImpl(String pattern) {
        this.a = pattern;
    }

    public String asString() {
        return this.a;
    }

    public String toString() {
        return asString();
    }
}
