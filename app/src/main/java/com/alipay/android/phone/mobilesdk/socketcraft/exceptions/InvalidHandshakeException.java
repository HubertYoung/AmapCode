package com.alipay.android.phone.mobilesdk.socketcraft.exceptions;

public class InvalidHandshakeException extends InvalidDataException {
    private static final long serialVersionUID = -1426533877490484964L;

    public InvalidHandshakeException() {
        super(1002);
    }

    public InvalidHandshakeException(String arg0, Throwable arg1) {
        super(1002, arg0, arg1);
    }

    public InvalidHandshakeException(String arg0) {
        super(1002, arg0);
    }

    public InvalidHandshakeException(Throwable arg0) {
        super(1002, arg0);
    }
}
