package com.alipay.android.phone.mobilesdk.socketcraft.exceptions;

public class InvalidDataException extends Exception {
    private static final long serialVersionUID = 3731842424390998726L;
    private int closecode;

    public InvalidDataException(int closecode2) {
        this.closecode = closecode2;
    }

    public InvalidDataException(int closecode2, String s) {
        super(s);
        this.closecode = closecode2;
    }

    public InvalidDataException(int closecode2, Throwable t) {
        super(t);
        this.closecode = closecode2;
    }

    public InvalidDataException(int closecode2, String s, Throwable t) {
        super(s, t);
        this.closecode = closecode2;
    }

    public int getCloseCode() {
        return this.closecode;
    }
}
