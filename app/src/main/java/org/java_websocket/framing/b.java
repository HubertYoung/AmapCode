package org.java_websocket.framing;

import java.nio.ByteBuffer;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidFrameException;
import org.java_websocket.framing.Framedata.Opcode;

public class b extends FramedataImpl1 implements a {
    static final ByteBuffer e = ByteBuffer.allocate(0);
    private int f;
    private String g;

    public b() {
        super(Opcode.CLOSING);
        a(true);
    }

    public b(byte b) {
        super(Opcode.CLOSING);
        a(true);
        a(1000, "");
    }

    public b(int i, String str) {
        super(Opcode.CLOSING);
        a(true);
        a(i, str);
    }

    private void a(int i, String str) {
        if (str == null) {
            str = "";
        }
        if (i == 1015) {
            str = "";
            i = 1005;
        }
        if (i != 1005) {
            byte[] a = org.java_websocket.a.b.a(str);
            ByteBuffer allocate = ByteBuffer.allocate(4);
            allocate.putInt(i);
            allocate.position(2);
            ByteBuffer allocate2 = ByteBuffer.allocate(a.length + 2);
            allocate2.put(allocate);
            allocate2.put(a);
            allocate2.rewind();
            a(allocate2);
        } else if (str.length() > 0) {
            throw new InvalidDataException(1002, (String) "A close frame must have a closecode if it has a reason");
        }
    }

    private void g() {
        this.f = 1005;
        ByteBuffer d = super.d();
        d.mark();
        if (d.remaining() >= 2) {
            ByteBuffer allocate = ByteBuffer.allocate(4);
            allocate.position(2);
            allocate.putShort(d.getShort());
            allocate.position(0);
            this.f = allocate.getInt();
            if (this.f == 1006 || this.f == 1015 || this.f == 1005 || this.f > 4999 || this.f < 1000 || this.f == 1004) {
                StringBuilder sb = new StringBuilder("closecode must not be sent over the wire: ");
                sb.append(this.f);
                throw new InvalidFrameException(sb.toString());
            }
        }
        d.reset();
    }

    private void h() {
        if (this.f == 1005) {
            this.g = org.java_websocket.a.b.a(super.d());
            return;
        }
        ByteBuffer d = super.d();
        int position = d.position();
        try {
            d.position(d.position() + 2);
            this.g = org.java_websocket.a.b.a(d);
            d.position(position);
        } catch (IllegalArgumentException e2) {
            throw new InvalidFrameException((Throwable) e2);
        } catch (Throwable th) {
            d.position(position);
            throw th;
        }
    }

    public final void a(ByteBuffer byteBuffer) {
        super.a(byteBuffer);
        g();
        h();
    }

    public final ByteBuffer d() {
        return this.f == 1005 ? e : super.d();
    }

    public final int e() {
        return this.f;
    }

    public final String f() {
        return this.g;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("code: ");
        sb.append(this.f);
        return sb.toString();
    }
}
