package org.java_websocket.drafts;

import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.java_websocket.a.b;
import org.java_websocket.drafts.a.C0093a;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidFrameException;
import org.java_websocket.exceptions.NotSendableException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.Framedata.Opcode;
import org.java_websocket.framing.FramedataImpl1;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.a;
import org.java_websocket.handshake.f;
import org.java_websocket.handshake.h;

public class c extends a {
    protected boolean f = false;
    protected List<Framedata> g = new LinkedList();
    protected ByteBuffer h;
    private final Random i = new Random();

    public ByteBuffer a(Framedata framedata) {
        if (framedata.c() != Opcode.TEXT) {
            throw new RuntimeException("only text frames supported");
        }
        ByteBuffer d = framedata.d();
        ByteBuffer allocate = ByteBuffer.allocate(d.remaining() + 2);
        allocate.put(0);
        d.mark();
        allocate.put(d);
        d.reset();
        allocate.put(-1);
        allocate.flip();
        return allocate;
    }

    public final List<Framedata> a(String str, boolean z) {
        FramedataImpl1 framedataImpl1 = new FramedataImpl1();
        try {
            framedataImpl1.a(ByteBuffer.wrap(b.a(str)));
            framedataImpl1.a(true);
            framedataImpl1.a(Opcode.TEXT);
            framedataImpl1.b(z);
            return Collections.singletonList(framedataImpl1);
        } catch (InvalidDataException e) {
            throw new NotSendableException((Throwable) e);
        }
    }

    public List<Framedata> a(ByteBuffer byteBuffer) {
        List<Framedata> c = c(byteBuffer);
        if (c != null) {
            return c;
        }
        throw new InvalidDataException(1002);
    }

    public final List<Framedata> a(ByteBuffer byteBuffer, boolean z) {
        throw new RuntimeException("not yet implemented");
    }

    public a.b a(a aVar) {
        return (!aVar.c("Origin") || !a((f) aVar)) ? a.b.NOT_MATCHED : a.b.MATCHED;
    }

    public a.b a(a aVar, ServerHandshake serverHandshake) {
        return (!aVar.b("WebSocket-Origin").equals(serverHandshake.b("Origin")) || !a((f) serverHandshake)) ? a.b.NOT_MATCHED : a.b.MATCHED;
    }

    public a a() {
        return new c();
    }

    public org.java_websocket.handshake.b a(org.java_websocket.handshake.b bVar) {
        bVar.a("Upgrade", "WebSocket");
        bVar.a(H5AppHttpRequest.HEADER_CONNECTION, "Upgrade");
        if (!bVar.c("Origin")) {
            StringBuilder sb = new StringBuilder("random");
            sb.append(this.i.nextInt());
            bVar.a("Origin", sb.toString());
        }
        return bVar;
    }

    public org.java_websocket.handshake.c a(a aVar, h hVar) {
        hVar.a((String) "Web Socket Protocol Handshake");
        hVar.a("Upgrade", "WebSocket");
        hVar.a(H5AppHttpRequest.HEADER_CONNECTION, aVar.b(H5AppHttpRequest.HEADER_CONNECTION));
        hVar.a("WebSocket-Origin", aVar.b("Origin"));
        StringBuilder sb = new StringBuilder("ws://");
        sb.append(aVar.b("Host"));
        sb.append(aVar.a());
        hVar.a("WebSocket-Location", sb.toString());
        return hVar;
    }

    public final void b() {
        this.f = false;
        this.h = null;
    }

    public C0093a c() {
        return C0093a.NONE;
    }

    /* access modifiers changed from: protected */
    public final List<Framedata> c(ByteBuffer byteBuffer) {
        while (byteBuffer.hasRemaining()) {
            byte b = byteBuffer.get();
            if (b == 0) {
                if (this.f) {
                    throw new InvalidFrameException((String) "unexpected START_OF_FRAME");
                }
                this.f = true;
            } else if (b == -1) {
                if (!this.f) {
                    throw new InvalidFrameException((String) "unexpected END_OF_FRAME");
                }
                if (this.h != null) {
                    this.h.flip();
                    FramedataImpl1 framedataImpl1 = new FramedataImpl1();
                    framedataImpl1.a(this.h);
                    framedataImpl1.a(true);
                    framedataImpl1.a(Opcode.TEXT);
                    this.g.add(framedataImpl1);
                    this.h = null;
                    byteBuffer.mark();
                }
                this.f = false;
            } else if (!this.f) {
                return null;
            } else {
                if (this.h == null) {
                    this.h = ByteBuffer.allocate(b);
                } else if (!this.h.hasRemaining()) {
                    ByteBuffer byteBuffer2 = this.h;
                    byteBuffer2.flip();
                    ByteBuffer allocate = ByteBuffer.allocate(a(byteBuffer2.capacity() * 2));
                    allocate.put(byteBuffer2);
                    this.h = allocate;
                }
                this.h.put(b);
            }
        }
        List<Framedata> list = this.g;
        this.g = new LinkedList();
        return list;
    }
}
