package com.alipay.android.phone.mobilesdk.socketcraft.client;

import com.alipay.android.phone.mobilesdk.socketcraft.WebSocket;
import com.alipay.android.phone.mobilesdk.socketcraft.WebSocket.READYSTATE;
import com.alipay.android.phone.mobilesdk.socketcraft.WebSocketAdapter;
import com.alipay.android.phone.mobilesdk.socketcraft.WebSocketImpl;
import com.alipay.android.phone.mobilesdk.socketcraft.WebSocketListener;
import com.alipay.android.phone.mobilesdk.socketcraft.drafts.Draft;
import com.alipay.android.phone.mobilesdk.socketcraft.drafts.Draft_17;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.Framedata;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.Framedata.Opcode;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.HandshakeImpl1Client;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.Handshakedata;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.ServerHandshake;
import com.alipay.android.phone.mobilesdk.socketcraft.platform.logcat.SCLogCatUtil;
import com.alipay.android.phone.mobilesdk.socketcraft.platform.ssl.SSLExtensionsFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public abstract class WebSocketClient extends WebSocketAdapter implements WebSocket, Runnable {
    static final /* synthetic */ boolean $assertionsDisabled = (!WebSocketClient.class.desiredAssertionStatus());
    private static final String TAG = "WebSocketClient";
    private CountDownLatch closeLatch;
    private CountDownLatch connectLatch;
    private int connectTimeout;
    private Draft draft;
    /* access modifiers changed from: private */
    public WebSocketImpl engine;
    private Map<String, String> headers;
    private InputStream istream;
    /* access modifiers changed from: private */
    public OutputStream ostream;
    private Proxy proxy;
    private Socket socket;
    private SSLSocketFactory sslSocketFactory;
    protected URI uri;
    private Thread writeThread;

    class WebsocketWriteThread implements Runnable {
        private WebsocketWriteThread() {
        }

        public void run() {
            Thread.currentThread().setName("WebsocketWriteThread");
            while (!Thread.interrupted()) {
                try {
                    ByteBuffer buffer = WebSocketClient.this.engine.outQueue.take();
                    WebSocketClient.this.ostream.write(buffer.array(), 0, buffer.limit());
                    WebSocketClient.this.ostream.flush();
                } catch (IOException e) {
                    WebSocketClient.this.engine.eot();
                    return;
                } catch (InterruptedException e2) {
                    return;
                }
            }
        }
    }

    public abstract void onClose(int i, String str, boolean z);

    public abstract void onError(Exception exc);

    public abstract void onMessage(String str);

    public abstract void onOpen(ServerHandshake serverHandshake);

    public WebSocketClient(URI serverURI) {
        this(serverURI, new Draft_17());
    }

    public WebSocketClient(URI serverUri, Draft draft2) {
        this(serverUri, draft2, null, 0);
    }

    public WebSocketClient(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders, int connectTimeout2) {
        this.uri = null;
        this.engine = null;
        this.socket = null;
        this.proxy = Proxy.NO_PROXY;
        this.connectLatch = new CountDownLatch(1);
        this.closeLatch = new CountDownLatch(1);
        this.connectTimeout = 0;
        if (serverUri == null) {
            throw new IllegalArgumentException();
        } else if (protocolDraft == null) {
            throw new IllegalArgumentException("null as draft is permitted for `WebSocketServer` only!");
        } else {
            this.uri = serverUri;
            this.draft = protocolDraft;
            this.headers = httpHeaders;
            this.connectTimeout = connectTimeout2;
            this.engine = new WebSocketImpl((WebSocketListener) this, protocolDraft);
        }
    }

    public URI getURI() {
        return this.uri;
    }

    public Draft getDraft() {
        return this.draft;
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
        return this.engine.isOpen();
    }

    public void close() {
        if (this.writeThread != null) {
            this.engine.close(1000);
        }
    }

    public void closeBlocking() {
        close();
        this.closeLatch.await();
    }

    public void send(String text) {
        this.engine.send(text);
    }

    public void send(byte[] data) {
        this.engine.send(data);
    }

    public void run() {
        long wsHandShakeStartTime;
        try {
            if (this.socket == null) {
                this.socket = new Socket(this.proxy);
            } else if (this.socket.isClosed()) {
                throw new SocketException("Socket is closed");
            }
            if (!this.socket.isBound()) {
                connectNetwork();
            }
            this.istream = this.socket.getInputStream();
            this.ostream = this.socket.getOutputStream();
            wsHandShakeStartTime = System.currentTimeMillis();
            sendHandshake();
            long wsHandShakeCost = System.currentTimeMillis() - wsHandShakeStartTime;
            onWsHandshake(wsHandShakeCost);
            SCLogCatUtil.info(TAG, "WebSocketHandshark timing: " + wsHandShakeCost);
            this.writeThread = new Thread(new WebsocketWriteThread());
            this.writeThread.start();
            byte[] rawbuffer = new byte[WebSocketImpl.RCVBUF];
            while (!isClosed() && !isClosing()) {
                try {
                    int readBytes = this.istream.read(rawbuffer);
                    if (readBytes != -1) {
                        this.engine.decode(ByteBuffer.wrap(rawbuffer, 0, readBytes));
                    }
                } catch (IOException e) {
                    this.engine.eot();
                } catch (RuntimeException e2) {
                    onError(e2);
                    this.engine.closeConnection(1006, e2.getMessage());
                }
            }
            this.engine.eot();
            if (!$assertionsDisabled && !this.socket.isClosed()) {
                throw new AssertionError();
            }
        } catch (Exception e3) {
            onWebsocketError(this.engine, e3);
            this.engine.closeConnection(-1, e3.getMessage());
        } catch (Throwable th) {
            long wsHandShakeCost2 = System.currentTimeMillis() - wsHandShakeStartTime;
            onWsHandshake(wsHandShakeCost2);
            SCLogCatUtil.info(TAG, "WebSocketHandshark timing: " + wsHandShakeCost2);
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    private void connectNetwork() {
        long dnsStartTime = System.currentTimeMillis();
        try {
            InetAddress[] inetAddresses = InetAddress.getAllByName(this.uri.getHost());
            if (inetAddresses == null || inetAddresses.length <= 0) {
                throw new UnknownHostException("Unknown host : " + this.uri.getHost());
            }
            InetAddress inetAddress = inetAddresses[0];
            long dnsCost = System.currentTimeMillis() - dnsStartTime;
            String ip = inetAddress != null ? inetAddress.getHostAddress() : " null ";
            onDns(ip, dnsCost);
            SCLogCatUtil.info(TAG, "DNS timing: " + dnsCost + ", ip: " + ip);
            long connStartTime = System.currentTimeMillis();
            try {
                Socket socket2 = this.socket;
                InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, getPort());
                socket2.connect(inetSocketAddress, this.connectTimeout);
                long dnsCost2 = System.currentTimeMillis() - connStartTime;
                String ip2 = inetAddress.getHostAddress();
                onTcpConnect(ip2, dnsCost2);
                SCLogCatUtil.info(TAG, "Connection timing: " + dnsCost2 + ", ip: " + ip2);
                if (this.sslSocketFactory != null) {
                    SSLSocket sslSocket = (SSLSocket) this.sslSocketFactory.createSocket(this.socket, this.uri.getHost(), getPort(), true);
                    SSLExtensionsFactory.getInstance().enableTlsExtensions(sslSocket, this.uri.getHost());
                    long sslStartTime = System.currentTimeMillis();
                    try {
                        sslSocket.startHandshake();
                        long cost = System.currentTimeMillis() - sslStartTime;
                        onSSLHandshake(cost);
                        SCLogCatUtil.info(TAG, "SSL timing: " + cost + ", ip: " + inetAddress.getHostAddress());
                        this.socket = sslSocket;
                    } catch (Throwable th) {
                        long cost2 = System.currentTimeMillis() - sslStartTime;
                        onSSLHandshake(cost2);
                        SCLogCatUtil.info(TAG, "SSL timing: " + cost2 + ", ip: " + inetAddress.getHostAddress());
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                long dnsCost3 = System.currentTimeMillis() - connStartTime;
                String ip3 = inetAddress.getHostAddress();
                onTcpConnect(ip3, dnsCost3);
                SCLogCatUtil.info(TAG, "Connection timing: " + dnsCost3 + ", ip: " + ip3);
                throw th2;
            }
        } catch (Throwable th3) {
            long dnsCost4 = System.currentTimeMillis() - dnsStartTime;
            onDns(" null ", dnsCost4);
            SCLogCatUtil.info(TAG, "DNS timing: " + dnsCost4 + ", ip: " + " null ");
            throw th3;
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
        throw new RuntimeException("unkonow scheme" + scheme);
    }

    private void sendHandshake() {
        String path;
        String part1 = this.uri.getPath();
        String part2 = this.uri.getQuery();
        if (part1 == null || part1.length() == 0) {
            path = "/";
        } else {
            path = part1;
        }
        if (part2 != null) {
            path = path + "?" + part2;
        }
        int port = getPort();
        String host = this.uri.getHost() + (port != 80 ? ":" + port : "");
        HandshakeImpl1Client handshake = new HandshakeImpl1Client();
        handshake.setResourceDescriptor(path);
        handshake.put("Host", host);
        if (this.headers != null) {
            for (Entry kv : this.headers.entrySet()) {
                handshake.put((String) kv.getKey(), (String) kv.getValue());
            }
        }
        this.engine.startHandshake(handshake);
    }

    public READYSTATE getReadyState() {
        return this.engine.getReadyState();
    }

    public final void onWebsocketMessage(WebSocket conn, String message) {
        onMessage(message);
    }

    public final void onWebsocketMessage(WebSocket conn, ByteBuffer blob) {
        onMessage(blob);
    }

    public void onWebsocketMessageFragment(WebSocket conn, Framedata frame) {
        onFragment(frame);
    }

    public final void onWebsocketOpen(WebSocket conn, Handshakedata handshake) {
        this.connectLatch.countDown();
        onOpen((ServerHandshake) handshake);
    }

    public final void onWebsocketClose(WebSocket conn, int code, String reason, boolean remote) {
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
        onClose(code, reason, remote);
    }

    public final void onWebsocketError(WebSocket conn, Exception ex) {
        onError(ex);
    }

    public final void onWriteDemand(WebSocket conn) {
    }

    public void onWebsocketCloseInitiated(WebSocket conn, int code, String reason) {
        onCloseInitiated(code, reason);
    }

    public void onWebsocketClosing(WebSocket conn, int code, String reason, boolean remote) {
        onClosing(code, reason, remote);
    }

    public void onCloseInitiated(int code, String reason) {
    }

    public void onClosing(int code, String reason, boolean remote) {
    }

    public void onDns(String ip, long cost) {
    }

    public void onTcpConnect(String ip, long cost) {
    }

    public void onSSLHandshake(long cost) {
    }

    public void onWsHandshake(long cost) {
    }

    public WebSocket getConnection() {
        return this.engine;
    }

    public InetSocketAddress getLocalSocketAddress(WebSocket conn) {
        if (this.socket != null) {
            return (InetSocketAddress) this.socket.getLocalSocketAddress();
        }
        return null;
    }

    public InetSocketAddress getRemoteSocketAddress(WebSocket conn) {
        if (this.socket != null) {
            return (InetSocketAddress) this.socket.getRemoteSocketAddress();
        }
        return null;
    }

    public void onMessage(ByteBuffer bytes) {
    }

    public void onFragment(Framedata frame) {
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

    public void setSslSocketFactory(SSLSocketFactory sslSocketFactory2) {
        this.sslSocketFactory = sslSocketFactory2;
    }

    public void sendFragmentedFrame(Opcode op, ByteBuffer buffer, boolean fin) {
        this.engine.sendFragmentedFrame(op, buffer, fin);
    }

    public boolean isOpen() {
        return this.engine.isOpen();
    }

    public boolean isFlushAndClose() {
        return this.engine.isFlushAndClose();
    }

    public boolean isClosed() {
        return this.engine.isClosed();
    }

    public boolean isClosing() {
        return this.engine.isClosing();
    }

    public boolean isConnecting() {
        return this.engine.isConnecting();
    }

    public boolean hasBufferedData() {
        return this.engine.hasBufferedData();
    }

    public void close(int code) {
        this.engine.close();
    }

    public void close(int code, String message) {
        this.engine.close(code, message);
    }

    public void closeConnection(int code, String message) {
        this.engine.closeConnection(code, message);
    }

    public void send(ByteBuffer bytes) {
        this.engine.send(bytes);
    }

    public void sendFrame(Framedata framedata) {
        this.engine.sendFrame(framedata);
    }

    public InetSocketAddress getLocalSocketAddress() {
        return this.engine.getLocalSocketAddress();
    }

    public InetSocketAddress getRemoteSocketAddress() {
        return this.engine.getRemoteSocketAddress();
    }

    public String getResourceDescriptor() {
        return this.uri.getPath();
    }
}
