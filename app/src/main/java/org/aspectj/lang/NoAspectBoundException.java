package org.aspectj.lang;

public class NoAspectBoundException extends RuntimeException {
    Throwable cause;

    public NoAspectBoundException(String aspectName, Throwable inner) {
        super(inner != null ? new StringBuffer("Exception while initializing ").append(aspectName).append(": ").append(inner).toString() : aspectName);
        this.cause = inner;
    }

    public NoAspectBoundException() {
    }

    public Throwable getCause() {
        return this.cause;
    }
}
