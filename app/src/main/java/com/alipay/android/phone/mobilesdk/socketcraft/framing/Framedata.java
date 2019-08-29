package com.alipay.android.phone.mobilesdk.socketcraft.framing;

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

    void append(Framedata framedata);

    Opcode getOpcode();

    ByteBuffer getPayloadData();

    boolean getTransfereMasked();

    boolean isFin();
}
