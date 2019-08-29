package com.xiaomi.smack;

import com.xiaomi.smack.packet.g;
import com.xiaomi.smack.packet.h;
import java.io.PrintStream;
import java.io.PrintWriter;

public class l extends Exception {
    private g a = null;
    private h b = null;
    private Throwable c = null;

    public l() {
    }

    public l(g gVar) {
        this.a = gVar;
    }

    public l(String str) {
        super(str);
    }

    public l(String str, Throwable th) {
        super(str);
        this.c = th;
    }

    public l(Throwable th) {
        this.c = th;
    }

    public Throwable a() {
        return this.c;
    }

    public String getMessage() {
        String message = super.getMessage();
        if (message == null && this.b != null) {
            return this.b.toString();
        }
        if (message == null && this.a != null) {
            message = this.a.toString();
        }
        return message;
    }

    public void printStackTrace() {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream printStream) {
        super.printStackTrace(printStream);
        if (this.c != null) {
            this.c.printStackTrace(printStream);
        }
    }

    public void printStackTrace(PrintWriter printWriter) {
        super.printStackTrace(printWriter);
        if (this.c != null) {
            printWriter.println("Nested Exception: ");
            this.c.printStackTrace(printWriter);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String message = super.getMessage();
        if (message != null) {
            sb.append(message);
            sb.append(": ");
        }
        if (this.b != null) {
            sb.append(this.b);
        }
        if (this.a != null) {
            sb.append(this.a);
        }
        if (this.c != null) {
            sb.append("\n  -- caused by: ");
            sb.append(this.c);
        }
        return sb.toString();
    }
}
