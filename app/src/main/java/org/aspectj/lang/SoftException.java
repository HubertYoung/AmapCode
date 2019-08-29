package org.aspectj.lang;

import java.io.PrintStream;
import java.io.PrintWriter;

public class SoftException extends RuntimeException {
    private static final boolean HAVE_JAVA_14;
    Throwable inner;

    static {
        boolean java14 = false;
        try {
            Class.forName("java.nio.Buffer");
            java14 = true;
        } catch (Throwable th) {
        }
        HAVE_JAVA_14 = java14;
    }

    public SoftException(Throwable inner2) {
        this.inner = inner2;
    }

    public Throwable getWrappedThrowable() {
        return this.inner;
    }

    public Throwable getCause() {
        return this.inner;
    }

    public void printStackTrace() {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream stream) {
        super.printStackTrace(stream);
        Throwable _inner = this.inner;
        if (!HAVE_JAVA_14 && _inner != null) {
            stream.print("Caused by: ");
            _inner.printStackTrace(stream);
        }
    }

    public void printStackTrace(PrintWriter stream) {
        super.printStackTrace(stream);
        Throwable _inner = this.inner;
        if (!HAVE_JAVA_14 && _inner != null) {
            stream.print("Caused by: ");
            _inner.printStackTrace(stream);
        }
    }
}
