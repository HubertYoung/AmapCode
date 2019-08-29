package com.alipay.android.phone.mobilesdk.socketcraft.exceptions;

public class IncompleteHandshakeException extends RuntimeException {
    private static final long serialVersionUID = 7906596804233893092L;
    private int newsize;

    public IncompleteHandshakeException(int newsize2) {
        this.newsize = newsize2;
    }

    public IncompleteHandshakeException() {
        this.newsize = 0;
    }

    public int getPreferedSize() {
        return this.newsize;
    }
}
