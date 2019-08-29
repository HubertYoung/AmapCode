package org.aspectj.lang.reflect;

public class NoSuchPointcutException extends Exception {
    private static final long serialVersionUID = 3256444698657634352L;
    private String name;

    public NoSuchPointcutException(String name2) {
        this.name = name2;
    }

    public String getName() {
        return this.name;
    }
}
