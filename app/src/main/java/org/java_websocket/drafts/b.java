package org.java_websocket.drafts;

import com.alipay.android.phone.mobilesdk.socketcraft.util.WsMessageConstants;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.java_websocket.drafts.a.C0093a;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidFrameException;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.exceptions.LimitExedeedException;
import org.java_websocket.exceptions.NotSendableException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.Framedata.Opcode;
import org.java_websocket.framing.FramedataImpl1;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.c;
import org.java_websocket.handshake.f;
import org.java_websocket.handshake.h;

public class b extends a {
    static final /* synthetic */ boolean f = true;
    private ByteBuffer g;
    private Framedata h = null;
    private final Random i = new Random();

    class a extends Throwable {
        int a;

        public a(int i) {
            this.a = i;
        }
    }

    private static String a(String str) {
        String trim = str.trim();
        StringBuilder sb = new StringBuilder();
        sb.append(trim);
        sb.append("258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
        String sb2 = sb.toString();
        try {
            return org.java_websocket.a.a.a(MessageDigest.getInstance("SHA1").digest(sb2.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] a(long j, int i2) {
        byte[] bArr = new byte[i2];
        int i3 = (i2 * 8) - 8;
        for (int i4 = 0; i4 < i2; i4++) {
            bArr[i4] = (byte) ((int) (j >>> (i3 - (i4 * 8))));
        }
        return bArr;
    }

    public static int d(f fVar) {
        String b = fVar.b("Sec-WebSocket-Version");
        if (b.length() > 0) {
            try {
                return new Integer(b.trim()).intValue();
            } catch (NumberFormatException unused) {
            }
        }
        return -1;
    }

    public final List<Framedata> a(String str, boolean z) {
        FramedataImpl1 framedataImpl1 = new FramedataImpl1();
        try {
            framedataImpl1.a(ByteBuffer.wrap(org.java_websocket.a.b.a(str)));
            framedataImpl1.a(true);
            framedataImpl1.a(Opcode.TEXT);
            framedataImpl1.b(z);
            return Collections.singletonList(framedataImpl1);
        } catch (InvalidDataException e) {
            throw new NotSendableException((Throwable) e);
        }
    }

    public final List<Framedata> a(ByteBuffer byteBuffer, boolean z) {
        FramedataImpl1 framedataImpl1 = new FramedataImpl1();
        try {
            framedataImpl1.a(byteBuffer);
            framedataImpl1.a(true);
            framedataImpl1.a(Opcode.BINARY);
            framedataImpl1.b(z);
            return Collections.singletonList(framedataImpl1);
        } catch (InvalidDataException e) {
            throw new NotSendableException((Throwable) e);
        }
    }

    public org.java_websocket.drafts.a.b a(org.java_websocket.handshake.a aVar) {
        int d = d(aVar);
        return (d == 7 || d == 8) ? a((f) aVar) ? org.java_websocket.drafts.a.b.MATCHED : org.java_websocket.drafts.a.b.NOT_MATCHED : org.java_websocket.drafts.a.b.NOT_MATCHED;
    }

    public final org.java_websocket.drafts.a.b a(org.java_websocket.handshake.a aVar, ServerHandshake serverHandshake) {
        if (!aVar.c("Sec-WebSocket-Key") || !serverHandshake.c("Sec-WebSocket-Accept")) {
            return org.java_websocket.drafts.a.b.NOT_MATCHED;
        }
        return a(aVar.b("Sec-WebSocket-Key")).equals(serverHandshake.b("Sec-WebSocket-Accept")) ? org.java_websocket.drafts.a.b.MATCHED : org.java_websocket.drafts.a.b.NOT_MATCHED;
    }

    public a a() {
        return new b();
    }

    public org.java_websocket.handshake.b a(org.java_websocket.handshake.b bVar) {
        bVar.a("Upgrade", "websocket");
        bVar.a(H5AppHttpRequest.HEADER_CONNECTION, "Upgrade");
        bVar.a("Sec-WebSocket-Version", "8");
        byte[] bArr = new byte[16];
        this.i.nextBytes(bArr);
        bVar.a("Sec-WebSocket-Key", org.java_websocket.a.a.a(bArr));
        return bVar;
    }

    public final c a(org.java_websocket.handshake.a aVar, h hVar) {
        hVar.a("Upgrade", "websocket");
        hVar.a(H5AppHttpRequest.HEADER_CONNECTION, aVar.b(H5AppHttpRequest.HEADER_CONNECTION));
        hVar.a((String) "Switching Protocols");
        String b = aVar.b("Sec-WebSocket-Key");
        if (b == null) {
            throw new InvalidHandshakeException((String) "missing Sec-WebSocket-Key");
        }
        hVar.a("Sec-WebSocket-Accept", a(b));
        return hVar;
    }

    public final void b() {
        this.g = null;
    }

    public final C0093a c() {
        return C0093a.TWOWAY;
    }

    public final ByteBuffer a(Framedata framedata) {
        byte b;
        byte b2;
        ByteBuffer d = framedata.d();
        int i2 = 0;
        boolean z = this.d == org.java_websocket.WebSocket.a.CLIENT;
        int i3 = d.remaining() <= 125 ? 1 : d.remaining() <= 65535 ? 2 : 8;
        ByteBuffer allocate = ByteBuffer.allocate((i3 > 1 ? i3 + 1 : i3) + 1 + (z ? 4 : 0) + d.remaining());
        Opcode c = framedata.c();
        if (c == Opcode.CONTINUOUS) {
            b = 0;
        } else if (c == Opcode.TEXT) {
            b = 1;
        } else if (c == Opcode.BINARY) {
            b = 2;
        } else if (c == Opcode.CLOSING) {
            b = 8;
        } else if (c == Opcode.PING) {
            b = 9;
        } else if (c == Opcode.PONG) {
            b = 10;
        } else {
            StringBuilder sb = new StringBuilder("Don't know how to handle ");
            sb.append(c.toString());
            throw new RuntimeException(sb.toString());
        }
        byte b3 = Byte.MIN_VALUE;
        allocate.put((byte) (((byte) (framedata.a() ? -128 : 0)) | b));
        byte[] a2 = a((long) d.remaining(), i3);
        if (f || a2.length == i3) {
            if (i3 == 1) {
                byte b4 = a2[0];
                if (!z) {
                    b3 = 0;
                }
                allocate.put((byte) (b4 | b3));
            } else {
                if (i3 == 2) {
                    if (!z) {
                        b3 = 0;
                    }
                    b2 = b3 | 126;
                } else if (i3 == 8) {
                    if (!z) {
                        b3 = 0;
                    }
                    b2 = b3 | Byte.MAX_VALUE;
                } else {
                    throw new RuntimeException("Size representation not supported/specified");
                }
                allocate.put((byte) b2);
                allocate.put(a2);
            }
            if (z) {
                ByteBuffer allocate2 = ByteBuffer.allocate(4);
                allocate2.putInt(this.i.nextInt());
                allocate.put(allocate2.array());
                while (d.hasRemaining()) {
                    allocate.put((byte) (d.get() ^ allocate2.get(i2 % 4)));
                    i2++;
                }
            } else {
                allocate.put(d);
            }
            if (f || allocate.remaining() == 0) {
                allocate.flip();
                return allocate;
            }
            throw new AssertionError(allocate.remaining());
        }
        throw new AssertionError();
    }

    public final List<Framedata> a(ByteBuffer byteBuffer) {
        LinkedList linkedList;
        while (true) {
            linkedList = new LinkedList();
            if (this.g == null) {
                break;
            }
            try {
                byteBuffer.mark();
                int remaining = byteBuffer.remaining();
                int remaining2 = this.g.remaining();
                if (remaining2 > remaining) {
                    this.g.put(byteBuffer.array(), byteBuffer.position(), remaining);
                    byteBuffer.position(byteBuffer.position() + remaining);
                    return Collections.emptyList();
                }
                this.g.put(byteBuffer.array(), byteBuffer.position(), remaining2);
                byteBuffer.position(byteBuffer.position() + remaining2);
                linkedList.add(c((ByteBuffer) this.g.duplicate().position(0)));
                this.g = null;
            } catch (a e) {
                this.g.limit();
                ByteBuffer allocate = ByteBuffer.allocate(a(e.a));
                if (f || allocate.limit() > this.g.limit()) {
                    this.g.rewind();
                    allocate.put(this.g);
                    this.g = allocate;
                } else {
                    throw new AssertionError();
                }
            }
        }
        while (byteBuffer.hasRemaining()) {
            byteBuffer.mark();
            try {
                linkedList.add(c(byteBuffer));
            } catch (a e2) {
                byteBuffer.reset();
                this.g = ByteBuffer.allocate(a(e2.a));
                this.g.put(byteBuffer);
            }
        }
        return linkedList;
    }

    private Framedata c(ByteBuffer byteBuffer) {
        Opcode opcode;
        org.java_websocket.framing.c cVar;
        int remaining = byteBuffer.remaining();
        int i2 = 2;
        if (remaining < 2) {
            throw new a(2);
        }
        byte b = byteBuffer.get();
        boolean z = (b >> 8) != 0;
        byte b2 = (byte) ((b & Byte.MAX_VALUE) >> 4);
        if (b2 != 0) {
            throw new InvalidFrameException("bad rsv ".concat(String.valueOf(b2)));
        }
        byte b3 = byteBuffer.get();
        boolean z2 = (b3 & Byte.MIN_VALUE) != 0;
        int i3 = (byte) (b3 & Byte.MAX_VALUE);
        byte b4 = (byte) (b & 15);
        switch (b4) {
            case 0:
                opcode = Opcode.CONTINUOUS;
                break;
            case 1:
                opcode = Opcode.TEXT;
                break;
            case 2:
                opcode = Opcode.BINARY;
                break;
            default:
                switch (b4) {
                    case 8:
                        opcode = Opcode.CLOSING;
                        break;
                    case 9:
                        opcode = Opcode.PING;
                        break;
                    case 10:
                        opcode = Opcode.PONG;
                        break;
                    default:
                        StringBuilder sb = new StringBuilder("unknow optcode ");
                        sb.append((short) b4);
                        throw new InvalidFrameException(sb.toString());
                }
        }
        if (z || !(opcode == Opcode.PING || opcode == Opcode.PONG || opcode == Opcode.CLOSING)) {
            if (i3 < 0 || i3 > 125) {
                if (opcode == Opcode.PING || opcode == Opcode.PONG || opcode == Opcode.CLOSING) {
                    throw new InvalidFrameException((String) "more than 125 octets");
                } else if (i3 == 126) {
                    if (remaining < 4) {
                        throw new a(4);
                    }
                    byte[] bArr = new byte[3];
                    bArr[1] = byteBuffer.get();
                    bArr[2] = byteBuffer.get();
                    i3 = new BigInteger(bArr).intValue();
                    i2 = 4;
                } else if (remaining < 10) {
                    throw new a(10);
                } else {
                    byte[] bArr2 = new byte[8];
                    for (int i4 = 0; i4 < 8; i4++) {
                        bArr2[i4] = byteBuffer.get();
                    }
                    long longValue = new BigInteger(bArr2).longValue();
                    if (longValue > 2147483647L) {
                        throw new LimitExedeedException(WsMessageConstants.MSG_PAYLOAD_SIZE_BIG);
                    }
                    i3 = (int) longValue;
                    i2 = 10;
                }
            }
            int i5 = i2 + (z2 ? 4 : 0) + i3;
            if (remaining < i5) {
                throw new a(i5);
            }
            ByteBuffer allocate = ByteBuffer.allocate(a(i3));
            if (z2) {
                byte[] bArr3 = new byte[4];
                byteBuffer.get(bArr3);
                for (int i6 = 0; i6 < i3; i6++) {
                    allocate.put((byte) (byteBuffer.get() ^ bArr3[i6 % 4]));
                }
            } else {
                allocate.put(byteBuffer.array(), byteBuffer.position(), allocate.limit());
                byteBuffer.position(byteBuffer.position() + allocate.limit());
            }
            if (opcode == Opcode.CLOSING) {
                cVar = new org.java_websocket.framing.b();
            } else {
                cVar = new FramedataImpl1();
                cVar.a(z);
                cVar.a(opcode);
            }
            allocate.flip();
            cVar.a(allocate);
            return cVar;
        }
        throw new InvalidFrameException((String) "control frames may no be fragmented");
    }
}
