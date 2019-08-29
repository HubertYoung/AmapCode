package com.alipay.android.phone.mobilesdk.socketcraft.exceptions;

public class NotSendableException extends RuntimeException {
    private static final long serialVersionUID = -6468967874576651628L;

    public NotSendableException() {
    }

    public NotSendableException(String message) {
        super(message);
    }

    public NotSendableException(Throwable cause) {
        super(cause);
    }

    public NotSendableException(String message, Throwable cause) {
        super(message, cause);
    }
}
