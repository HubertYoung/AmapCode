package org.java_websocket.exceptions;

public class NotSendableException extends RuntimeException {
    private static final long serialVersionUID = -6468967874576651628L;

    public NotSendableException() {
    }

    public NotSendableException(String str) {
        super(str);
    }

    public NotSendableException(String str, Throwable th) {
        super(str, th);
    }

    public NotSendableException(Throwable th) {
        super(th);
    }
}
