package com.alipay.android.phone.mobilesdk.socketcraft.exceptions;

public class InvalidFrameException extends InvalidDataException {
    private static final long serialVersionUID = -9016496369828887591L;

    public InvalidFrameException() {
        super(1002);
    }

    public InvalidFrameException(String arg0) {
        super(1002, arg0);
    }

    public InvalidFrameException(Throwable arg0) {
        super(1002, arg0);
    }

    public InvalidFrameException(String arg0, Throwable arg1) {
        super(1002, arg0, arg1);
    }
}
