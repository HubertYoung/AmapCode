package org.java_websocket.handshake;

public class d extends g implements b {
    private String a = "*";

    public final String a() {
        return this.a;
    }

    public final void a(String str) {
        if (str == null) {
            throw new IllegalArgumentException("http resource descriptor must not be null");
        }
        this.a = str;
    }
}
