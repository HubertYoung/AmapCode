package org.java_websocket.exceptions;

public class InvalidHandshakeException extends InvalidDataException {
    private static final long serialVersionUID = -1426533877490484964L;

    public InvalidHandshakeException() {
        super(1002);
    }

    public InvalidHandshakeException(String str) {
        super(1002, str);
    }

    public InvalidHandshakeException(String str, Throwable th) {
        super(1002, str, th);
    }

    public InvalidHandshakeException(Throwable th) {
        super(1002, th);
    }
}
