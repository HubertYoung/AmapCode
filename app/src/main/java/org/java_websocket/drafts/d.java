package org.java_websocket.drafts;

import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.a.C0093a;
import org.java_websocket.exceptions.IncompleteHandshakeException;
import org.java_websocket.exceptions.InvalidFrameException;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.Framedata.Opcode;
import org.java_websocket.framing.b;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.a;
import org.java_websocket.handshake.c;
import org.java_websocket.handshake.f;
import org.java_websocket.handshake.h;

public class d extends c {
    private static final byte[] j = {-1, 0};
    private boolean i = false;
    private final Random k = new Random();

    private static byte[] a(String str) {
        try {
            long parseLong = Long.parseLong(str.replaceAll("[^0-9]", ""));
            long length = (long) (str.split(Token.SEPARATOR).length - 1);
            if (length == 0) {
                throw new InvalidHandshakeException((String) "invalid Sec-WebSocket-Key (/key2/)");
            }
            long longValue = new Long(parseLong / length).longValue();
            return new byte[]{(byte) ((int) (longValue >> 24)), (byte) ((int) ((longValue << 8) >> 24)), (byte) ((int) ((longValue << 16) >> 24)), (byte) ((int) ((longValue << 24) >> 24))};
        } catch (NumberFormatException unused) {
            throw new InvalidHandshakeException((String) "invalid Sec-WebSocket-Key (/key1/ or /key2/)");
        }
    }

    private static byte[] a(String str, String str2, byte[] bArr) {
        byte[] a = a(str);
        byte[] a2 = a(str2);
        try {
            return MessageDigest.getInstance("MD5").digest(new byte[]{a[0], a[1], a[2], a[3], a2[0], a2[1], a2[2], a2[3], bArr[0], bArr[1], bArr[2], bArr[3], bArr[4], bArr[5], bArr[6], bArr[7]});
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String d() {
        Random random = new Random();
        long nextInt = (long) (random.nextInt(12) + 1);
        int nextInt2 = random.nextInt(12) + 1;
        String l = Long.toString(((long) (random.nextInt(Math.abs(new Long(4294967295L / nextInt).intValue())) + 1)) * nextInt);
        for (int i2 = 0; i2 < nextInt2; i2++) {
            int abs = Math.abs(random.nextInt(l.length()));
            char nextInt3 = (char) (random.nextInt(95) + 33);
            if (nextInt3 >= '0' && nextInt3 <= '9') {
                nextInt3 = (char) (nextInt3 - 15);
            }
            l = new StringBuilder(l).insert(abs, nextInt3).toString();
        }
        for (int i3 = 0; ((long) i3) < nextInt; i3++) {
            l = new StringBuilder(l).insert(Math.abs(random.nextInt(l.length() - 1) + 1), Token.SEPARATOR).toString();
        }
        return l;
    }

    public final ByteBuffer a(Framedata framedata) {
        return framedata.c() == Opcode.CLOSING ? ByteBuffer.wrap(j) : super.a(framedata);
    }

    public final List<Framedata> a(ByteBuffer byteBuffer) {
        byteBuffer.mark();
        List<Framedata> c = super.c(byteBuffer);
        if (c != null) {
            return c;
        }
        byteBuffer.reset();
        List<Framedata> list = this.g;
        this.f = true;
        if (this.h == null) {
            this.h = ByteBuffer.allocate(2);
            if (byteBuffer.remaining() > this.h.remaining()) {
                throw new InvalidFrameException();
            }
            this.h.put(byteBuffer);
            if (this.h.hasRemaining()) {
                this.g = new LinkedList();
                return list;
            } else if (Arrays.equals(this.h.array(), j)) {
                list.add(new b(0));
                return list;
            } else {
                throw new InvalidFrameException();
            }
        } else {
            throw new InvalidFrameException();
        }
    }

    public final a.b a(a aVar) {
        return (!aVar.b("Upgrade").equals("WebSocket") || !aVar.b(H5AppHttpRequest.HEADER_CONNECTION).contains("Upgrade") || aVar.b("Sec-WebSocket-Key1").length() <= 0 || aVar.b("Sec-WebSocket-Key2").isEmpty() || !aVar.c("Origin")) ? a.b.NOT_MATCHED : a.b.MATCHED;
    }

    public final a.b a(a aVar, ServerHandshake serverHandshake) {
        if (this.i) {
            return a.b.NOT_MATCHED;
        }
        try {
            if (serverHandshake.b("Sec-WebSocket-Origin").equals(aVar.b("Origin"))) {
                if (a((f) serverHandshake)) {
                    byte[] c = serverHandshake.c();
                    if (c != null) {
                        if (c.length != 0) {
                            return Arrays.equals(c, a(aVar.b("Sec-WebSocket-Key1"), aVar.b("Sec-WebSocket-Key2"), aVar.c())) ? a.b.MATCHED : a.b.NOT_MATCHED;
                        }
                    }
                    throw new IncompleteHandshakeException();
                }
            }
            return a.b.NOT_MATCHED;
        } catch (InvalidHandshakeException e) {
            throw new RuntimeException("bad handshakerequest", e);
        }
    }

    public final a a() {
        return new d();
    }

    public final org.java_websocket.handshake.b a(org.java_websocket.handshake.b bVar) {
        bVar.a("Upgrade", "WebSocket");
        bVar.a(H5AppHttpRequest.HEADER_CONNECTION, "Upgrade");
        bVar.a("Sec-WebSocket-Key1", d());
        bVar.a("Sec-WebSocket-Key2", d());
        if (!bVar.c("Origin")) {
            StringBuilder sb = new StringBuilder("random");
            sb.append(this.k.nextInt());
            bVar.a("Origin", sb.toString());
        }
        byte[] bArr = new byte[8];
        this.k.nextBytes(bArr);
        bVar.a(bArr);
        return bVar;
    }

    public final c a(a aVar, h hVar) {
        hVar.a((String) "WebSocket Protocol Handshake");
        hVar.a("Upgrade", "WebSocket");
        hVar.a(H5AppHttpRequest.HEADER_CONNECTION, aVar.b(H5AppHttpRequest.HEADER_CONNECTION));
        hVar.a("Sec-WebSocket-Origin", aVar.b("Origin"));
        StringBuilder sb = new StringBuilder("ws://");
        sb.append(aVar.b("Host"));
        sb.append(aVar.a());
        hVar.a("Sec-WebSocket-Location", sb.toString());
        String b = aVar.b("Sec-WebSocket-Key1");
        String b2 = aVar.b("Sec-WebSocket-Key2");
        byte[] c = aVar.c();
        if (b == null || b2 == null || c == null || c.length != 8) {
            throw new InvalidHandshakeException((String) "Bad keys");
        }
        hVar.a(a(b, b2, c));
        return hVar;
    }

    public final f b(ByteBuffer byteBuffer) {
        c a = a(byteBuffer, this.d);
        if ((!a.c("Sec-WebSocket-Key1") && this.d != WebSocket.a.CLIENT) || a.c("Sec-WebSocket-Version")) {
            return a;
        }
        byte[] bArr = new byte[(this.d == WebSocket.a.SERVER ? 8 : 16)];
        try {
            byteBuffer.get(bArr);
            a.a(bArr);
            return a;
        } catch (BufferUnderflowException unused) {
            throw new IncompleteHandshakeException(byteBuffer.capacity() + 16);
        }
    }

    public final C0093a c() {
        return C0093a.ONEWAY;
    }
}
