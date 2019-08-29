package org.java_websocket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import org.java_websocket.READYSTATE;
import org.java_websocket.WebSocket;
import org.java_websocket.b;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.Framedata.Opcode;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.d;
import org.java_websocket.handshake.f;

public abstract class WebSocketClient extends org.java_websocket.a implements Runnable, WebSocket {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private CountDownLatch closeLatch;
    private CountDownLatch connectLatch;
    private int connectTimeout;
    private org.java_websocket.drafts.a draft;
    /* access modifiers changed from: private */
    public b engine;
    private Map<String, String> headers;
    private InputStream istream;
    /* access modifiers changed from: private */
    public OutputStream ostream;
    private Proxy proxy;
    private Socket socket;
    protected URI uri;
    private Thread writeThread;

    class a implements Runnable {
        private a() {
        }

        /* synthetic */ a(WebSocketClient webSocketClient, byte b) {
            this();
        }

        public void run() {
            Thread.currentThread().setName("WebsocketWriteThread");
            while (!Thread.interrupted()) {
                try {
                    ByteBuffer take = WebSocketClient.this.engine.f.take();
                    WebSocketClient.this.ostream.write(take.array(), 0, take.limit());
                    WebSocketClient.this.ostream.flush();
                } catch (IOException unused) {
                    WebSocketClient.this.engine.a();
                    return;
                } catch (InterruptedException unused2) {
                }
            }
        }
    }

    public WebSocketClient(URI uri2) {
        this(uri2, new Draft_17());
    }

    public WebSocketClient(URI uri2, org.java_websocket.drafts.a aVar) {
        this(uri2, aVar, null, 0);
    }

    public WebSocketClient(URI uri2, org.java_websocket.drafts.a aVar, Map<String, String> map, int i) {
        this.uri = null;
        this.engine = null;
        this.socket = null;
        this.proxy = Proxy.NO_PROXY;
        this.connectLatch = new CountDownLatch(1);
        this.closeLatch = new CountDownLatch(1);
        this.connectTimeout = 0;
        if (uri2 == null) {
            throw new IllegalArgumentException();
        } else if (aVar == null) {
            throw new IllegalArgumentException("null as draft is permitted for `WebSocketServer` only!");
        } else {
            this.uri = uri2;
            this.draft = aVar;
            this.headers = map;
            this.connectTimeout = i;
            this.engine = new b(this, aVar);
        }
    }

    private int getPort() {
        int port = this.uri.getPort();
        if (port != -1) {
            return port;
        }
        String scheme = this.uri.getScheme();
        if (scheme.equals("wss")) {
            return 443;
        }
        if (scheme.equals("ws")) {
            return 80;
        }
        throw new RuntimeException("unkonow scheme".concat(String.valueOf(scheme)));
    }

    public void close() {
        if (this.writeThread != null) {
            this.engine.b();
        }
    }

    public void closeBlocking() {
        close();
        this.closeLatch.await();
    }

    public void connect() {
        if (this.writeThread != null) {
            throw new IllegalStateException("WebSocketClient objects are not reuseable");
        }
        this.writeThread = new Thread(this);
        this.writeThread.start();
    }

    public boolean connectBlocking() {
        connect();
        this.connectLatch.await();
        return this.engine.c();
    }

    public WebSocket getConnection() {
        return this.engine;
    }

    public org.java_websocket.drafts.a getDraft() {
        return this.draft;
    }

    public InetSocketAddress getLocalSocketAddress() {
        return this.engine.getLocalSocketAddress();
    }

    public InetSocketAddress getLocalSocketAddress(WebSocket webSocket) {
        if (this.socket != null) {
            return (InetSocketAddress) this.socket.getLocalSocketAddress();
        }
        return null;
    }

    public InetSocketAddress getRemoteSocketAddress(WebSocket webSocket) {
        if (this.socket != null) {
            return (InetSocketAddress) this.socket.getRemoteSocketAddress();
        }
        return null;
    }

    public String getResourceDescriptor() {
        return this.uri.getPath();
    }

    public URI getURI() {
        return this.uri;
    }

    public boolean isClosing() {
        return this.engine.d();
    }

    public boolean isOpen() {
        return this.engine.c();
    }

    public abstract void onClose(int i, String str, boolean z);

    public void onCloseInitiated(int i, String str) {
    }

    public void onClosing(int i, String str, boolean z) {
    }

    public abstract void onError(Exception exc);

    public void onFragment(Framedata framedata) {
    }

    public abstract void onMessage(String str);

    public void onMessage(ByteBuffer byteBuffer) {
    }

    public abstract void onOpen(ServerHandshake serverHandshake);

    public final void onWebsocketClose(WebSocket webSocket, int i, String str, boolean z) {
        this.connectLatch.countDown();
        this.closeLatch.countDown();
        if (this.writeThread != null) {
            this.writeThread.interrupt();
        }
        try {
            if (this.socket != null) {
                this.socket.close();
            }
        } catch (IOException e) {
            onWebsocketError(this, e);
        }
        onClose(i, str, z);
    }

    public void onWebsocketCloseInitiated(WebSocket webSocket, int i, String str) {
        onCloseInitiated(i, str);
    }

    public void onWebsocketClosing(WebSocket webSocket, int i, String str, boolean z) {
        onClosing(i, str, z);
    }

    public final void onWebsocketError(WebSocket webSocket, Exception exc) {
        onError(exc);
    }

    public final void onWebsocketMessage(WebSocket webSocket, String str) {
        onMessage(str);
    }

    public final void onWebsocketMessage(WebSocket webSocket, ByteBuffer byteBuffer) {
        onMessage(byteBuffer);
    }

    public void onWebsocketMessageFragment(WebSocket webSocket, Framedata framedata) {
        onFragment(framedata);
    }

    public final void onWebsocketOpen(WebSocket webSocket, f fVar) {
        this.connectLatch.countDown();
        onOpen((ServerHandshake) fVar);
    }

    public final void onWriteDemand(WebSocket webSocket) {
    }

    public void send(ByteBuffer byteBuffer) {
        this.engine.c(byteBuffer);
    }

    public void sendFrame(Framedata framedata) {
        this.engine.sendFrame(framedata);
    }

    public void setProxy(Proxy proxy2) {
        if (proxy2 == null) {
            throw new IllegalArgumentException();
        }
        this.proxy = proxy2;
    }

    public void setSocket(Socket socket2) {
        if (this.socket != null) {
            throw new IllegalStateException("socket has already been set");
        }
        this.socket = socket2;
    }

    public void send(String str) {
        b bVar = this.engine;
        if (str == null) {
            throw new IllegalArgumentException("Cannot send 'null' data to a WebSocketImpl.");
        }
        bVar.a((Collection<Framedata>) bVar.k.a(str, bVar.l == org.java_websocket.WebSocket.a.CLIENT));
    }

    public void send(byte[] bArr) {
        this.engine.c(ByteBuffer.wrap(bArr));
    }

    public void run() {
        try {
            if (this.socket == null) {
                this.socket = new Socket(this.proxy);
            } else if (this.socket.isClosed()) {
                throw new IOException();
            }
            if (!this.socket.isBound()) {
                this.socket.connect(new InetSocketAddress(this.uri.getHost(), getPort()), this.connectTimeout);
            }
            this.istream = this.socket.getInputStream();
            this.ostream = this.socket.getOutputStream();
            sendHandshake();
            this.writeThread = new Thread(new a(this, 0));
            this.writeThread.start();
            byte[] bArr = new byte[b.a];
            while (!isClosed()) {
                try {
                    int read = this.istream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    b bVar = this.engine;
                    ByteBuffer wrap = ByteBuffer.wrap(bArr, 0, read);
                    if (b.p || wrap.hasRemaining()) {
                        if (bVar.i == READYSTATE.NOT_YET_CONNECTED) {
                            if (bVar.a(wrap)) {
                                if (!b.p && bVar.m.hasRemaining() == wrap.hasRemaining() && wrap.hasRemaining()) {
                                    throw new AssertionError();
                                } else if (!wrap.hasRemaining()) {
                                    if (bVar.m.hasRemaining()) {
                                        bVar.b(bVar.m);
                                    }
                                }
                            }
                            if (b.p && !bVar.d() && !bVar.h && wrap.hasRemaining()) {
                                throw new AssertionError();
                            }
                        }
                        bVar.b(wrap);
                        if (b.p) {
                        }
                    } else {
                        throw new AssertionError();
                    }
                } catch (IOException unused) {
                    this.engine.a();
                } catch (RuntimeException e) {
                    onError(e);
                    this.engine.b(1006, e.getMessage(), false);
                }
            }
            this.engine.a();
        } catch (Exception e2) {
            onWebsocketError(this.engine, e2);
            this.engine.b(-1, e2.getMessage(), false);
        }
    }

    private void sendHandshake() {
        String path = this.uri.getPath();
        String query = this.uri.getQuery();
        if (path == null || path.length() == 0) {
            path = "/";
        }
        if (query != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(path);
            sb.append("?");
            sb.append(query);
            path = sb.toString();
        }
        int port = getPort();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.uri.getHost());
        sb2.append(port != 80 ? ":".concat(String.valueOf(port)) : "");
        String sb3 = sb2.toString();
        d dVar = new d();
        dVar.a(path);
        dVar.a("Host", sb3);
        if (this.headers != null) {
            for (Entry next : this.headers.entrySet()) {
                dVar.a((String) next.getKey(), (String) next.getValue());
            }
        }
        b bVar = this.engine;
        if (b.p || bVar.i != READYSTATE.CONNECTING) {
            bVar.n = bVar.k.a((org.java_websocket.handshake.b) dVar);
            bVar.o = dVar.a();
            if (b.p || bVar.o != null) {
                try {
                    bVar.j.onWebsocketHandshakeSentAsClient(bVar, bVar.n);
                    bVar.a(org.java_websocket.drafts.a.b((f) bVar.n));
                } catch (InvalidDataException unused) {
                    throw new InvalidHandshakeException((String) "Handshake data rejected by client.");
                } catch (RuntimeException e) {
                    bVar.j.onWebsocketError(bVar, e);
                    throw new InvalidHandshakeException("rejected because of".concat(String.valueOf(e)));
                }
            } else {
                throw new AssertionError();
            }
        } else {
            throw new AssertionError("shall only be called once");
        }
    }

    public READYSTATE getReadyState() {
        return this.engine.i;
    }

    public void sendFragmentedFrame(Opcode opcode, ByteBuffer byteBuffer, boolean z) {
        b bVar = this.engine;
        bVar.a((Collection<Framedata>) bVar.k.a(opcode, byteBuffer, z));
    }

    public boolean isFlushAndClose() {
        return this.engine.h;
    }

    public boolean isClosed() {
        return this.engine.i == READYSTATE.CLOSED;
    }

    public boolean isConnecting() {
        b bVar = this.engine;
        if (b.p || !bVar.h || bVar.i == READYSTATE.CONNECTING) {
            return bVar.i == READYSTATE.CONNECTING;
        }
        throw new AssertionError();
    }

    public boolean hasBufferedData() {
        return !this.engine.f.isEmpty();
    }

    public void close(int i) {
        this.engine.b();
    }

    public void close(int i, String str) {
        this.engine.a(i, str, false);
    }

    public void closeConnection(int i, String str) {
        this.engine.b(i, str, false);
    }

    public InetSocketAddress getRemoteSocketAddress() {
        b bVar = this.engine;
        return bVar.j.getRemoteSocketAddress(bVar);
    }
}
