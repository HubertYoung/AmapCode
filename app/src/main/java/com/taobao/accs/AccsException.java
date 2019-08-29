package com.taobao.accs;

import java.io.PrintStream;
import java.io.PrintWriter;

public class AccsException extends Exception {
    private int mErrorCode;

    public AccsException(int i) {
        this.mErrorCode = i;
    }

    public AccsException(String str) {
        super(str);
    }

    public AccsException(String str, int i) {
        super(str);
        this.mErrorCode = i;
    }

    public AccsException(String str, Throwable th, int i) {
        super(str, th);
        this.mErrorCode = i;
    }

    public AccsException(Throwable th, int i) {
        super(th);
        this.mErrorCode = i;
    }

    public void printStackTrace(PrintStream printStream) {
        new StringBuilder("errorCode = ").append(this.mErrorCode);
        super.printStackTrace(printStream);
    }

    public void printStackTrace(PrintWriter printWriter) {
        StringBuilder sb = new StringBuilder("errorCode = ");
        sb.append(this.mErrorCode);
        printWriter.println(sb.toString());
        super.printStackTrace(printWriter);
    }
}
