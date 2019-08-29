package org.java_websocket.framing;

import java.nio.ByteBuffer;

public interface Framedata {

    public enum Opcode {
        CONTINUOUS,
        TEXT,
        BINARY,
        PING,
        PONG,
        CLOSING
    }

    boolean a();

    boolean b();

    Opcode c();

    ByteBuffer d();
}
