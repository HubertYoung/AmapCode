package org.aspectj.internal.lang.reflect;

import org.aspectj.lang.reflect.SignaturePattern;

public class SignaturePatternImpl implements SignaturePattern {
    private String a;

    public SignaturePatternImpl(String pattern) {
        this.a = pattern;
    }

    public String asString() {
        return this.a;
    }

    public String toString() {
        return asString();
    }
}
