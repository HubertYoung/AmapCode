package org.java_websocket.framing;

import java.nio.ByteBuffer;
import org.java_websocket.framing.Framedata.Opcode;

public interface c extends Framedata {
    void a(ByteBuffer byteBuffer);

    void a(Opcode opcode);

    void a(boolean z);

    void b(boolean z);
}
