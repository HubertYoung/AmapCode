package org.java_websocket.exceptions;

public class IncompleteHandshakeException extends RuntimeException {
    private static final long serialVersionUID = 7906596804233893092L;
    private int newsize;

    public IncompleteHandshakeException() {
        this.newsize = 0;
    }

    public IncompleteHandshakeException(int i) {
        this.newsize = i;
    }

    public int getPreferedSize() {
        return this.newsize;
    }
}
