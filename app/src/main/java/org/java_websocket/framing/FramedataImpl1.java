package org.java_websocket.framing;

import com.alipay.sdk.util.h;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.java_websocket.a.b;
import org.java_websocket.framing.Framedata.Opcode;

public class FramedataImpl1 implements c {
    protected static byte[] a = new byte[0];
    protected boolean b;
    protected Opcode c;
    protected boolean d;
    private ByteBuffer e;

    public FramedataImpl1() {
    }

    public FramedataImpl1(Opcode opcode) {
        this.c = opcode;
        this.e = ByteBuffer.wrap(a);
    }

    public FramedataImpl1(Framedata framedata) {
        this.b = framedata.a();
        this.c = framedata.c();
        this.e = framedata.d();
        this.d = framedata.b();
    }

    public void a(ByteBuffer byteBuffer) {
        this.e = byteBuffer;
    }

    public final void a(Opcode opcode) {
        this.c = opcode;
    }

    public final void a(boolean z) {
        this.b = z;
    }

    public final boolean a() {
        return this.b;
    }

    public final void b(boolean z) {
        this.d = z;
    }

    public final boolean b() {
        return this.d;
    }

    public final Opcode c() {
        return this.c;
    }

    public ByteBuffer d() {
        return this.e;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Framedata{ optcode:");
        sb.append(this.c);
        sb.append(", fin:");
        sb.append(this.b);
        sb.append(", payloadlength:[pos:");
        sb.append(this.e.position());
        sb.append(", len:");
        sb.append(this.e.remaining());
        sb.append("], payload:");
        sb.append(Arrays.toString(b.a(new String(this.e.array()))));
        sb.append(h.d);
        return sb.toString();
    }
}
