package org.java_websocket.drafts;

import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.autonavi.link.protocol.http.MultipartUtility;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.Framedata.Opcode;
import org.java_websocket.framing.FramedataImpl1;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.c;
import org.java_websocket.handshake.f;
import org.java_websocket.handshake.h;

public abstract class a {
    public static int a = 1000;
    public static int b = 64;
    public static final byte[] c = org.java_websocket.a.b.a((String) "<policy-file-request/>\u0000");
    protected org.java_websocket.WebSocket.a d = null;
    protected Opcode e = null;

    /* renamed from: org.java_websocket.drafts.a$a reason: collision with other inner class name */
    public enum C0093a {
        NONE,
        ONEWAY,
        TWOWAY
    }

    public enum b {
        MATCHED,
        NOT_MATCHED
    }

    public static int a(int i) {
        if (i >= 0) {
            return i;
        }
        throw new InvalidDataException(1002, (String) "Negative count");
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x007f A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.java_websocket.handshake.c a(java.nio.ByteBuffer r6, org.java_websocket.WebSocket.a r7) {
        /*
            java.lang.String r0 = c(r6)
            if (r0 != 0) goto L_0x0012
            org.java_websocket.exceptions.IncompleteHandshakeException r7 = new org.java_websocket.exceptions.IncompleteHandshakeException
            int r6 = r6.capacity()
            int r6 = r6 + 128
            r7.<init>(r6)
            throw r7
        L_0x0012:
            java.lang.String r1 = " "
            r2 = 3
            java.lang.String[] r0 = r0.split(r1, r2)
            int r1 = r0.length
            if (r1 == r2) goto L_0x0022
            org.java_websocket.exceptions.InvalidHandshakeException r6 = new org.java_websocket.exceptions.InvalidHandshakeException
            r6.<init>()
            throw r6
        L_0x0022:
            org.java_websocket.WebSocket$a r1 = org.java_websocket.WebSocket.a.CLIENT
            r2 = 2
            r3 = 1
            if (r7 != r1) goto L_0x003f
            org.java_websocket.handshake.e r7 = new org.java_websocket.handshake.e
            r7.<init>()
            r1 = r7
            org.java_websocket.handshake.h r1 = (org.java_websocket.handshake.h) r1
            r4 = r0[r3]
            short r4 = java.lang.Short.parseShort(r4)
            r1.a(r4)
            r0 = r0[r2]
            r1.a(r0)
            goto L_0x0049
        L_0x003f:
            org.java_websocket.handshake.d r7 = new org.java_websocket.handshake.d
            r7.<init>()
            r0 = r0[r3]
            r7.a(r0)
        L_0x0049:
            java.lang.String r0 = c(r6)
            if (r0 == 0) goto L_0x0077
            int r1 = r0.length()
            if (r1 <= 0) goto L_0x0077
            java.lang.String r1 = ":"
            java.lang.String[] r0 = r0.split(r1, r2)
            int r1 = r0.length
            if (r1 == r2) goto L_0x0066
            org.java_websocket.exceptions.InvalidHandshakeException r6 = new org.java_websocket.exceptions.InvalidHandshakeException
            java.lang.String r7 = "not an http header"
            r6.<init>(r7)
            throw r6
        L_0x0066:
            r1 = 0
            r1 = r0[r1]
            r0 = r0[r3]
            java.lang.String r4 = "^ +"
            java.lang.String r5 = ""
            java.lang.String r0 = r0.replaceFirst(r4, r5)
            r7.a(r1, r0)
            goto L_0x0049
        L_0x0077:
            if (r0 != 0) goto L_0x007f
            org.java_websocket.exceptions.IncompleteHandshakeException r6 = new org.java_websocket.exceptions.IncompleteHandshakeException
            r6.<init>()
            throw r6
        L_0x007f:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.java_websocket.drafts.a.a(java.nio.ByteBuffer, org.java_websocket.WebSocket$a):org.java_websocket.handshake.c");
    }

    protected static boolean a(f fVar) {
        return fVar.b("Upgrade").equalsIgnoreCase("websocket") && fVar.b(H5AppHttpRequest.HEADER_CONNECTION).toLowerCase(Locale.ENGLISH).contains("upgrade");
    }

    public static List<ByteBuffer> b(f fVar) {
        return c(fVar);
    }

    public static List<ByteBuffer> c(f fVar) {
        String sb;
        StringBuilder sb2 = new StringBuilder(100);
        if (fVar instanceof org.java_websocket.handshake.a) {
            sb2.append("GET ");
            sb2.append(((org.java_websocket.handshake.a) fVar).a());
            sb = " HTTP/1.1";
        } else if (fVar instanceof ServerHandshake) {
            StringBuilder sb3 = new StringBuilder("HTTP/1.1 101 ");
            sb3.append(((ServerHandshake) fVar).a());
            sb = sb3.toString();
        } else {
            throw new RuntimeException("unknow role");
        }
        sb2.append(sb);
        sb2.append(MultipartUtility.LINE_FEED);
        Iterator<String> b2 = fVar.b();
        while (b2.hasNext()) {
            String next = b2.next();
            String b3 = fVar.b(next);
            sb2.append(next);
            sb2.append(": ");
            sb2.append(b3);
            sb2.append(MultipartUtility.LINE_FEED);
        }
        sb2.append(MultipartUtility.LINE_FEED);
        byte[] b4 = org.java_websocket.a.b.b(sb2.toString());
        byte[] c2 = fVar.c();
        ByteBuffer allocate = ByteBuffer.allocate((c2 == null ? 0 : c2.length) + b4.length);
        allocate.put(b4);
        if (c2 != null) {
            allocate.put(c2);
        }
        allocate.flip();
        return Collections.singletonList(allocate);
    }

    public abstract ByteBuffer a(Framedata framedata);

    public abstract List<Framedata> a(String str, boolean z);

    public abstract List<Framedata> a(ByteBuffer byteBuffer);

    public abstract List<Framedata> a(ByteBuffer byteBuffer, boolean z);

    public final List<Framedata> a(Opcode opcode, ByteBuffer byteBuffer, boolean z) {
        if (opcode == Opcode.BINARY || opcode == Opcode.TEXT || opcode == Opcode.TEXT) {
            if (this.e != null) {
                this.e = Opcode.CONTINUOUS;
            } else {
                this.e = opcode;
            }
            FramedataImpl1 framedataImpl1 = new FramedataImpl1(this.e);
            try {
                framedataImpl1.a(byteBuffer);
                framedataImpl1.a(z);
                if (z) {
                    opcode = null;
                }
                this.e = opcode;
                return Collections.singletonList(framedataImpl1);
            } catch (InvalidDataException e2) {
                throw new RuntimeException(e2);
            }
        } else {
            throw new IllegalArgumentException("Only Opcode.BINARY or  Opcode.TEXT are allowed");
        }
    }

    public abstract b a(org.java_websocket.handshake.a aVar);

    public abstract b a(org.java_websocket.handshake.a aVar, ServerHandshake serverHandshake);

    public abstract a a();

    public abstract org.java_websocket.handshake.b a(org.java_websocket.handshake.b bVar);

    public abstract c a(org.java_websocket.handshake.a aVar, h hVar);

    public final void a(org.java_websocket.WebSocket.a aVar) {
        this.d = aVar;
    }

    public f b(ByteBuffer byteBuffer) {
        return a(byteBuffer, this.d);
    }

    public abstract void b();

    public abstract C0093a c();

    private static String c(ByteBuffer byteBuffer) {
        ByteBuffer allocate = ByteBuffer.allocate(byteBuffer.remaining());
        byte b2 = 48;
        while (true) {
            if (!byteBuffer.hasRemaining()) {
                byteBuffer.position(byteBuffer.position() - allocate.position());
                allocate = null;
                break;
            }
            byte b3 = byteBuffer.get();
            allocate.put(b3);
            if (b2 == 13 && b3 == 10) {
                allocate.limit(allocate.position() - 2);
                allocate.position(0);
                break;
            }
            b2 = b3;
        }
        if (allocate == null) {
            return null;
        }
        return org.java_websocket.a.b.a(allocate.array(), allocate.limit());
    }
}
