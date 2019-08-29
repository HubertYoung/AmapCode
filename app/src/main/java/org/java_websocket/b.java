package org.java_websocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.drafts.a;
import org.java_websocket.drafts.a.C0093a;
import org.java_websocket.drafts.c;
import org.java_websocket.drafts.d;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.Framedata.Opcode;
import org.java_websocket.handshake.f;

public class b implements WebSocket {
    public static int a = 16384;
    public static boolean b = false;
    public static final List<a> c;
    public static final /* synthetic */ boolean p = true;
    public SelectionKey d;
    public ByteChannel e;
    public final BlockingQueue<ByteBuffer> f;
    public final BlockingQueue<ByteBuffer> g;
    public volatile boolean h = false;
    public READYSTATE i = READYSTATE.NOT_YET_CONNECTED;
    public final c j;
    public a k = null;
    public WebSocket.a l;
    public ByteBuffer m = ByteBuffer.allocate(0);
    public org.java_websocket.handshake.a n = null;
    public String o = null;
    private List<a> q;
    private Opcode r = null;
    private String s = null;
    private Integer t = null;
    private Boolean u = null;

    static {
        ArrayList arrayList = new ArrayList(4);
        c = arrayList;
        arrayList.add(new Draft_17());
        c.add(new org.java_websocket.drafts.b());
        c.add(new d());
        c.add(new c());
    }

    public b(c cVar, a aVar) {
        if (aVar == null && this.l == WebSocket.a.SERVER) {
            throw new IllegalArgumentException("parameters must not be null");
        }
        this.f = new LinkedBlockingQueue();
        this.g = new LinkedBlockingQueue();
        this.j = cVar;
        this.l = WebSocket.a.CLIENT;
        if (aVar != null) {
            this.k = aVar.a();
        }
    }

    private void a(int i2) {
        b(i2, "", true);
    }

    private void a(InvalidDataException invalidDataException) {
        a(invalidDataException.getCloseCode(), invalidDataException.getMessage(), false);
    }

    private void a(f fVar) {
        this.i = READYSTATE.OPEN;
        try {
            this.j.onWebsocketOpen(this, fVar);
        } catch (RuntimeException e2) {
            this.j.onWebsocketError(this, e2);
        }
    }

    private synchronized void c(int i2, String str, boolean z) {
        if (!this.h) {
            this.t = Integer.valueOf(i2);
            this.s = str;
            this.u = Boolean.valueOf(z);
            this.h = true;
            this.j.onWriteDemand(this);
            try {
                this.j.onWebsocketClosing(this, i2, str, z);
            } catch (RuntimeException e2) {
                this.j.onWebsocketError(this, e2);
            }
            if (this.k != null) {
                this.k.b();
            }
            this.n = null;
        }
    }

    private void d(ByteBuffer byteBuffer) {
        this.f.add(byteBuffer);
        this.j.onWriteDemand(this);
    }

    public final void a(int i2, String str, boolean z) {
        if (!(this.i == READYSTATE.CLOSING || this.i == READYSTATE.CLOSED)) {
            if (this.i == READYSTATE.OPEN) {
                if (i2 != 1006) {
                    if (this.k.c() != C0093a.NONE) {
                        if (!z) {
                            try {
                                this.j.onWebsocketCloseInitiated(this, i2, str);
                            } catch (RuntimeException e2) {
                                try {
                                    this.j.onWebsocketError(this, e2);
                                } catch (InvalidDataException e3) {
                                    this.j.onWebsocketError(this, e3);
                                    c(1006, "generated frame is invalid", false);
                                }
                            }
                        }
                        sendFrame(new org.java_websocket.framing.b(i2, str));
                    }
                    c(i2, str, z);
                } else if (p || !z) {
                    this.i = READYSTATE.CLOSING;
                    c(i2, str, false);
                    return;
                } else {
                    throw new AssertionError();
                }
            } else if (i2 != -3) {
                c(-1, str, false);
            } else if (p || z) {
                c(-3, str, true);
            } else {
                throw new AssertionError();
            }
            if (i2 == 1002) {
                c(i2, str, z);
            }
            this.i = READYSTATE.CLOSING;
            this.m = null;
        }
    }

    public final void a(Collection<Framedata> collection) {
        if (!c()) {
            throw new WebsocketNotConnectedException();
        }
        for (Framedata sendFrame : collection) {
            sendFrame(sendFrame);
        }
    }

    public final void a(List<ByteBuffer> list) {
        for (ByteBuffer d2 : list) {
            d(d2);
        }
    }

    public final void b() {
        a(1000, "", false);
    }

    public final synchronized void b(int i2, String str, boolean z) {
        if (this.i != READYSTATE.CLOSED) {
            if (this.d != null) {
                this.d.cancel();
            }
            if (this.e != null) {
                try {
                    this.e.close();
                } catch (IOException e2) {
                    this.j.onWebsocketError(this, e2);
                }
            }
            try {
                this.j.onWebsocketClose(this, i2, str, z);
            } catch (RuntimeException e3) {
                this.j.onWebsocketError(this, e3);
            }
            if (this.k != null) {
                this.k.b();
            }
            this.n = null;
            this.i = READYSTATE.CLOSED;
            this.f.clear();
            return;
        }
        return;
    }

    public final void b(ByteBuffer byteBuffer) {
        c cVar;
        try {
            for (Framedata next : this.k.a(byteBuffer)) {
                Opcode c2 = next.c();
                boolean a2 = next.a();
                if (c2 == Opcode.CLOSING) {
                    int i2 = 1005;
                    String str = "";
                    if (next instanceof org.java_websocket.framing.a) {
                        org.java_websocket.framing.a aVar = (org.java_websocket.framing.a) next;
                        i2 = aVar.e();
                        str = aVar.f();
                    }
                    if (this.i == READYSTATE.CLOSING) {
                        b(i2, str, true);
                    } else if (this.k.c() == C0093a.TWOWAY) {
                        a(i2, str, true);
                    } else {
                        c(i2, str, false);
                    }
                } else if (c2 == Opcode.PING) {
                    this.j.onWebsocketPing(this, next);
                } else if (c2 == Opcode.PONG) {
                    this.j.onWebsocketPong(this, next);
                } else {
                    if (a2) {
                        if (c2 != Opcode.CONTINUOUS) {
                            if (this.r != null) {
                                throw new InvalidDataException(1002, (String) "Continuous frame sequence not completed.");
                            } else if (c2 == Opcode.TEXT) {
                                try {
                                    this.j.onWebsocketMessage((WebSocket) this, org.java_websocket.a.b.a(next.d()));
                                } catch (RuntimeException e2) {
                                    e = e2;
                                    cVar = this.j;
                                    cVar.onWebsocketError(this, e);
                                }
                            } else if (c2 == Opcode.BINARY) {
                                try {
                                    this.j.onWebsocketMessage((WebSocket) this, next.d());
                                } catch (RuntimeException e3) {
                                    e = e3;
                                    cVar = this.j;
                                    cVar.onWebsocketError(this, e);
                                }
                            } else {
                                throw new InvalidDataException(1002, (String) "non control or continious frame expected");
                            }
                        }
                    }
                    if (c2 != Opcode.CONTINUOUS) {
                        if (this.r != null) {
                            throw new InvalidDataException(1002, (String) "Previous continuous frame sequence not completed.");
                        }
                        this.r = c2;
                    } else if (a2) {
                        if (this.r == null) {
                            throw new InvalidDataException(1002, (String) "Continuous frame sequence was not started.");
                        }
                        this.r = null;
                    } else if (this.r == null) {
                        throw new InvalidDataException(1002, (String) "Continuous frame sequence was not started.");
                    }
                    try {
                        this.j.onWebsocketMessageFragment(this, next);
                    } catch (RuntimeException e4) {
                        e = e4;
                        cVar = this.j;
                        cVar.onWebsocketError(this, e);
                    }
                }
            }
        } catch (InvalidDataException e5) {
            this.j.onWebsocketError(this, e5);
            a(e5);
        }
    }

    public final void c(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            throw new IllegalArgumentException("Cannot send 'null' data to a WebSocketImpl.");
        }
        a((Collection<Framedata>) this.k.a(byteBuffer, this.l == WebSocket.a.CLIENT));
    }

    public final boolean c() {
        if (p || this.i != READYSTATE.OPEN || !this.h) {
            return this.i == READYSTATE.OPEN;
        }
        throw new AssertionError();
    }

    public final boolean d() {
        return this.i == READYSTATE.CLOSING;
    }

    public InetSocketAddress getLocalSocketAddress() {
        return this.j.getLocalSocketAddress(this);
    }

    public int hashCode() {
        return super.hashCode();
    }

    public void sendFrame(Framedata framedata) {
        d(this.k.a(framedata));
    }

    public String toString() {
        return super.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0085 A[SYNTHETIC, Splitter:B:28:0x0085] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(java.nio.ByteBuffer r11) {
        /*
            r10 = this;
            java.nio.ByteBuffer r0 = r10.m
            int r0 = r0.capacity()
            if (r0 != 0) goto L_0x000a
            r0 = r11
            goto L_0x003d
        L_0x000a:
            java.nio.ByteBuffer r0 = r10.m
            int r0 = r0.remaining()
            int r1 = r11.remaining()
            if (r0 >= r1) goto L_0x0031
            java.nio.ByteBuffer r0 = r10.m
            int r0 = r0.capacity()
            int r1 = r11.remaining()
            int r0 = r0 + r1
            java.nio.ByteBuffer r0 = java.nio.ByteBuffer.allocate(r0)
            java.nio.ByteBuffer r1 = r10.m
            r1.flip()
            java.nio.ByteBuffer r1 = r10.m
            r0.put(r1)
            r10.m = r0
        L_0x0031:
            java.nio.ByteBuffer r0 = r10.m
            r0.put(r11)
            java.nio.ByteBuffer r0 = r10.m
            r0.flip()
            java.nio.ByteBuffer r0 = r10.m
        L_0x003d:
            r0.mark()
            r1 = 0
            org.java_websocket.drafts.a r2 = r10.k     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            r3 = 1
            if (r2 != 0) goto L_0x00a5
            r0.mark()     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            int r2 = r0.limit()     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            byte[] r4 = org.java_websocket.drafts.a.c     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            int r4 = r4.length     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            if (r2 <= r4) goto L_0x0055
        L_0x0052:
            org.java_websocket.drafts.a$b r2 = org.java_websocket.drafts.a.b.NOT_MATCHED     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            goto L_0x0081
        L_0x0055:
            int r2 = r0.limit()     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            byte[] r4 = org.java_websocket.drafts.a.c     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            int r4 = r4.length     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            if (r2 >= r4) goto L_0x0067
            org.java_websocket.exceptions.IncompleteHandshakeException r2 = new org.java_websocket.exceptions.IncompleteHandshakeException     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            byte[] r3 = org.java_websocket.drafts.a.c     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            int r3 = r3.length     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            r2.<init>(r3)     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            throw r2     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
        L_0x0067:
            r2 = 0
        L_0x0068:
            boolean r4 = r0.hasRemaining()     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            if (r4 == 0) goto L_0x007f
            byte[] r4 = org.java_websocket.drafts.a.c     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            byte r4 = r4[r2]     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            byte r5 = r0.get()     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            if (r4 == r5) goto L_0x007c
            r0.reset()     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            goto L_0x0052
        L_0x007c:
            int r2 = r2 + 1
            goto L_0x0068
        L_0x007f:
            org.java_websocket.drafts.a$b r2 = org.java_websocket.drafts.a.b.MATCHED     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
        L_0x0081:
            org.java_websocket.drafts.a$b r4 = org.java_websocket.drafts.a.b.MATCHED     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            if (r2 != r4) goto L_0x00a5
            org.java_websocket.c r2 = r10.j     // Catch:{ InvalidDataException -> 0x009d }
            java.lang.String r2 = r2.getFlashPolicy(r10)     // Catch:{ InvalidDataException -> 0x009d }
            byte[] r2 = org.java_websocket.a.b.a(r2)     // Catch:{ InvalidDataException -> 0x009d }
            java.nio.ByteBuffer r2 = java.nio.ByteBuffer.wrap(r2)     // Catch:{ InvalidDataException -> 0x009d }
            r10.d(r2)     // Catch:{ InvalidDataException -> 0x009d }
            java.lang.String r2 = ""
            r4 = -3
            r10.a(r4, r2, r1)     // Catch:{ InvalidDataException -> 0x009d }
            goto L_0x00a4
        L_0x009d:
            r2 = 1006(0x3ee, float:1.41E-42)
            java.lang.String r4 = "remote peer closed connection before flashpolicy could be transmitted"
            r10.a(r2, r4, r3)     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
        L_0x00a4:
            return r1
        L_0x00a5:
            org.java_websocket.WebSocket$a r2 = r10.l     // Catch:{ InvalidHandshakeException -> 0x01be }
            org.java_websocket.WebSocket$a r4 = org.java_websocket.WebSocket.a.SERVER     // Catch:{ InvalidHandshakeException -> 0x01be }
            r5 = -1
            r6 = 1002(0x3ea, float:1.404E-42)
            if (r2 != r4) goto L_0x0153
            org.java_websocket.drafts.a r2 = r10.k     // Catch:{ InvalidHandshakeException -> 0x01be }
            if (r2 != 0) goto L_0x012b
            java.util.List<org.java_websocket.drafts.a> r2 = r10.q     // Catch:{ InvalidHandshakeException -> 0x01be }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ InvalidHandshakeException -> 0x01be }
        L_0x00b8:
            boolean r4 = r2.hasNext()     // Catch:{ InvalidHandshakeException -> 0x01be }
            if (r4 == 0) goto L_0x0121
            java.lang.Object r4 = r2.next()     // Catch:{ InvalidHandshakeException -> 0x01be }
            org.java_websocket.drafts.a r4 = (org.java_websocket.drafts.a) r4     // Catch:{ InvalidHandshakeException -> 0x01be }
            org.java_websocket.drafts.a r4 = r4.a()     // Catch:{ InvalidHandshakeException -> 0x01be }
            org.java_websocket.WebSocket$a r7 = r10.l     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            r4.a(r7)     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            r0.reset()     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            org.java_websocket.handshake.f r7 = r4.b(r0)     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            boolean r8 = r7 instanceof org.java_websocket.handshake.a     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            if (r8 != 0) goto L_0x00df
            java.lang.String r4 = "wrong http function"
            r10.c(r6, r4, r1)     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            return r1
        L_0x00df:
            org.java_websocket.handshake.a r7 = (org.java_websocket.handshake.a) r7     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            org.java_websocket.drafts.a$b r8 = r4.a(r7)     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            org.java_websocket.drafts.a$b r9 = org.java_websocket.drafts.a.b.MATCHED     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            if (r8 != r9) goto L_0x00b8
            java.lang.String r8 = r7.a()     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            r10.o = r8     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            org.java_websocket.c r8 = r10.j     // Catch:{ InvalidDataException -> 0x0114, RuntimeException -> 0x0106 }
            org.java_websocket.handshake.h r8 = r8.onWebsocketHandshakeReceivedAsServer(r10, r4, r7)     // Catch:{ InvalidDataException -> 0x0114, RuntimeException -> 0x0106 }
            org.java_websocket.handshake.c r8 = r4.a(r7, r8)     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            java.util.List r8 = org.java_websocket.drafts.a.c(r8)     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            r10.a(r8)     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            r10.k = r4     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            r10.a(r7)     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            return r3
        L_0x0106:
            r4 = move-exception
            org.java_websocket.c r7 = r10.j     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            r7.onWebsocketError(r10, r4)     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            java.lang.String r4 = r4.getMessage()     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            r10.c(r5, r4, r1)     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            return r1
        L_0x0114:
            r4 = move-exception
            int r7 = r4.getCloseCode()     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            java.lang.String r4 = r4.getMessage()     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            r10.c(r7, r4, r1)     // Catch:{ InvalidHandshakeException -> 0x00b8 }
            return r1
        L_0x0121:
            org.java_websocket.drafts.a r2 = r10.k     // Catch:{ InvalidHandshakeException -> 0x01be }
            if (r2 != 0) goto L_0x012a
            java.lang.String r2 = "no draft matches"
            r10.a(r6, r2, r1)     // Catch:{ InvalidHandshakeException -> 0x01be }
        L_0x012a:
            return r1
        L_0x012b:
            org.java_websocket.drafts.a r2 = r10.k     // Catch:{ InvalidHandshakeException -> 0x01be }
            org.java_websocket.handshake.f r2 = r2.b(r0)     // Catch:{ InvalidHandshakeException -> 0x01be }
            boolean r4 = r2 instanceof org.java_websocket.handshake.a     // Catch:{ InvalidHandshakeException -> 0x01be }
            if (r4 != 0) goto L_0x013c
            java.lang.String r2 = "wrong http function"
            r10.c(r6, r2, r1)     // Catch:{ InvalidHandshakeException -> 0x01be }
            return r1
        L_0x013c:
            org.java_websocket.handshake.a r2 = (org.java_websocket.handshake.a) r2     // Catch:{ InvalidHandshakeException -> 0x01be }
            org.java_websocket.drafts.a r4 = r10.k     // Catch:{ InvalidHandshakeException -> 0x01be }
            org.java_websocket.drafts.a$b r4 = r4.a(r2)     // Catch:{ InvalidHandshakeException -> 0x01be }
            org.java_websocket.drafts.a$b r5 = org.java_websocket.drafts.a.b.MATCHED     // Catch:{ InvalidHandshakeException -> 0x01be }
            if (r4 != r5) goto L_0x014c
            r10.a(r2)     // Catch:{ InvalidHandshakeException -> 0x01be }
            return r3
        L_0x014c:
            java.lang.String r2 = "the handshake did finaly not match"
            r10.a(r6, r2, r1)     // Catch:{ InvalidHandshakeException -> 0x01be }
            return r1
        L_0x0153:
            org.java_websocket.WebSocket$a r2 = r10.l     // Catch:{ InvalidHandshakeException -> 0x01be }
            org.java_websocket.WebSocket$a r4 = org.java_websocket.WebSocket.a.CLIENT     // Catch:{ InvalidHandshakeException -> 0x01be }
            if (r2 != r4) goto L_0x0212
            org.java_websocket.drafts.a r2 = r10.k     // Catch:{ InvalidHandshakeException -> 0x01be }
            org.java_websocket.WebSocket$a r4 = r10.l     // Catch:{ InvalidHandshakeException -> 0x01be }
            r2.a(r4)     // Catch:{ InvalidHandshakeException -> 0x01be }
            org.java_websocket.drafts.a r2 = r10.k     // Catch:{ InvalidHandshakeException -> 0x01be }
            org.java_websocket.handshake.f r2 = r2.b(r0)     // Catch:{ InvalidHandshakeException -> 0x01be }
            boolean r4 = r2 instanceof org.java_websocket.handshake.ServerHandshake     // Catch:{ InvalidHandshakeException -> 0x01be }
            if (r4 != 0) goto L_0x0171
            java.lang.String r2 = "wrong http function"
            r10.c(r6, r2, r1)     // Catch:{ InvalidHandshakeException -> 0x01be }
            return r1
        L_0x0171:
            org.java_websocket.handshake.ServerHandshake r2 = (org.java_websocket.handshake.ServerHandshake) r2     // Catch:{ InvalidHandshakeException -> 0x01be }
            org.java_websocket.drafts.a r4 = r10.k     // Catch:{ InvalidHandshakeException -> 0x01be }
            org.java_websocket.handshake.a r7 = r10.n     // Catch:{ InvalidHandshakeException -> 0x01be }
            org.java_websocket.drafts.a$b r4 = r4.a(r7, r2)     // Catch:{ InvalidHandshakeException -> 0x01be }
            org.java_websocket.drafts.a$b r7 = org.java_websocket.drafts.a.b.MATCHED     // Catch:{ InvalidHandshakeException -> 0x01be }
            if (r4 != r7) goto L_0x01a5
            org.java_websocket.c r4 = r10.j     // Catch:{ InvalidDataException -> 0x0198, RuntimeException -> 0x018a }
            org.java_websocket.handshake.a r6 = r10.n     // Catch:{ InvalidDataException -> 0x0198, RuntimeException -> 0x018a }
            r4.onWebsocketHandshakeReceivedAsClient(r10, r6, r2)     // Catch:{ InvalidDataException -> 0x0198, RuntimeException -> 0x018a }
            r10.a(r2)     // Catch:{ InvalidHandshakeException -> 0x01be }
            return r3
        L_0x018a:
            r2 = move-exception
            org.java_websocket.c r3 = r10.j     // Catch:{ InvalidHandshakeException -> 0x01be }
            r3.onWebsocketError(r10, r2)     // Catch:{ InvalidHandshakeException -> 0x01be }
            java.lang.String r2 = r2.getMessage()     // Catch:{ InvalidHandshakeException -> 0x01be }
            r10.c(r5, r2, r1)     // Catch:{ InvalidHandshakeException -> 0x01be }
            return r1
        L_0x0198:
            r2 = move-exception
            int r3 = r2.getCloseCode()     // Catch:{ InvalidHandshakeException -> 0x01be }
            java.lang.String r2 = r2.getMessage()     // Catch:{ InvalidHandshakeException -> 0x01be }
            r10.c(r3, r2, r1)     // Catch:{ InvalidHandshakeException -> 0x01be }
            return r1
        L_0x01a5:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ InvalidHandshakeException -> 0x01be }
            java.lang.String r3 = "draft "
            r2.<init>(r3)     // Catch:{ InvalidHandshakeException -> 0x01be }
            org.java_websocket.drafts.a r3 = r10.k     // Catch:{ InvalidHandshakeException -> 0x01be }
            r2.append(r3)     // Catch:{ InvalidHandshakeException -> 0x01be }
            java.lang.String r3 = " refuses handshake"
            r2.append(r3)     // Catch:{ InvalidHandshakeException -> 0x01be }
            java.lang.String r2 = r2.toString()     // Catch:{ InvalidHandshakeException -> 0x01be }
            r10.a(r6, r2, r1)     // Catch:{ InvalidHandshakeException -> 0x01be }
            goto L_0x0212
        L_0x01be:
            r2 = move-exception
            r10.a(r2)     // Catch:{ IncompleteHandshakeException -> 0x01c3 }
            goto L_0x0212
        L_0x01c3:
            r2 = move-exception
            java.nio.ByteBuffer r3 = r10.m
            int r3 = r3.capacity()
            if (r3 != 0) goto L_0x01fc
            r0.reset()
            int r3 = r2.getPreferedSize()
            if (r3 != 0) goto L_0x01dc
            int r0 = r0.capacity()
            int r3 = r0 + 16
            goto L_0x01f0
        L_0x01dc:
            boolean r4 = p
            if (r4 != 0) goto L_0x01f0
            int r2 = r2.getPreferedSize()
            int r0 = r0.remaining()
            if (r2 >= r0) goto L_0x01f0
            java.lang.AssertionError r11 = new java.lang.AssertionError
            r11.<init>()
            throw r11
        L_0x01f0:
            java.nio.ByteBuffer r0 = java.nio.ByteBuffer.allocate(r3)
            r10.m = r0
            java.nio.ByteBuffer r0 = r10.m
            r0.put(r11)
            goto L_0x0212
        L_0x01fc:
            java.nio.ByteBuffer r11 = r10.m
            java.nio.ByteBuffer r0 = r10.m
            int r0 = r0.limit()
            r11.position(r0)
            java.nio.ByteBuffer r11 = r10.m
            java.nio.ByteBuffer r0 = r10.m
            int r0 = r0.capacity()
            r11.limit(r0)
        L_0x0212:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.java_websocket.b.a(java.nio.ByteBuffer):boolean");
    }

    public final void a() {
        if (this.i == READYSTATE.NOT_YET_CONNECTED) {
            a(-1);
        } else if (this.h) {
            b(this.t.intValue(), this.s, this.u.booleanValue());
        } else if (this.k.c() == C0093a.NONE) {
            a(1000);
        } else if (this.k.c() != C0093a.ONEWAY || this.l == WebSocket.a.SERVER) {
            a(1006);
        } else {
            a(1000);
        }
    }
}
