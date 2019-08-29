package com.alipay.mobile.common.transportext.biz.spdy;

import android.annotation.TargetApi;
import android.content.Context;
import com.alipay.mobile.common.transport.TransportStrategy;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.shared.io.ExtBufferedOutputStream;
import com.alipay.mobile.common.transportext.biz.spdy.ConnectionObservable.ConnectionEvent;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Platform;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpAuthenticator;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpEngine;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpTransport;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.RawHeaders;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.SpdyTransport;
import com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection;
import com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection.Builder;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Proxy.Type;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import javax.net.ssl.SSLSocket;

public final class Connection implements Closeable {
    private static final byte[] HTTP_11 = {104, 116, 116, 112, 47, 49, 46, 49};
    private static final byte[] NPN_PROTOCOLS = {6, 115, 112, 100, 121, 47, 51, 8, 104, 116, 116, 112, 47, 49, 46, 49};
    private static final byte[] SPDY3 = {115, 112, 100, 121, 47, 51};
    private boolean connected = false;
    private int httpMinorVersion = 1;
    private long idleStartTimeNs;
    private InputStream in;
    private Context mContext;
    private OutputStream out;
    private final Route route;
    private Socket socket;
    private SpdyConnection spdyConnection;

    public Connection(Route route2) {
        this.route = route2;
    }

    public final void connect(int connectTimeout, int readTimeout, TunnelRequest tunnelRequest, TransportContext netContext) {
        if (this.connected) {
            throw new IllegalStateException("already connected");
        }
        ConnectionObservable.getInstance().notifyObservers(new ConnectionEvent(2, this));
        this.connected = true;
        this.socket = createSocket();
        socketConnect(connectTimeout, netContext);
        setSoTimeout(this.socket, readTimeout);
        this.in = this.socket.getInputStream();
        this.out = this.socket.getOutputStream();
        upgradeToTlsSelector(tunnelRequest, netContext);
        ConnectionObservable.getInstance().notifyObservers(new ConnectionEvent(0, this));
    }

    private Socket createSocket() {
        Socket localSocket = this.route.proxy.type() != Type.HTTP ? new Socket(this.route.proxy) : new Socket();
        try {
            localSocket.setTcpNoDelay(true);
            localSocket.setKeepAlive(true);
        } catch (Throwable e) {
            LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "createSocket exception: " + e.toString());
        }
        return localSocket;
    }

    public final void socketConnect(int connectTimeout, TransportContext netContext) {
        InnerLogUtil.log4AtlsTest("ATLS SOCKET CONNECTION!");
        InnerLogUtil.log4AtlsTest("ATLS SOCKET IPCONNECTION,MODE2:" + this.route.inetSocketAddress.getAddress().getHostAddress());
        netContext.getCurrentDataContainer().timeItemDot("TCP_TIME");
        Platform.get().connectSocket(this.socket, this.route.inetSocketAddress, connectTimeout);
        netContext.getCurrentDataContainer().timeItemRelease("TCP_TIME");
        InnerLogUtil.log4AtlsTest("ATLS SOCKET CONNECTION OVER!");
    }

    @TargetApi(9)
    private void upgradeToTlsSelector(TunnelRequest tunnelRequest, TransportContext netContext) {
        if (this.route.address.getSslSocketFactory() != null) {
            try {
                upgradeToTls(tunnelRequest, netContext);
            } catch (Exception e) {
                InnerLogUtil.logError4AtlsTest("upgradeToAtls 发生异常,降级通过upgradeToTls尝试握手。", e);
                if (e instanceof IOException) {
                    throw ((IOException) e);
                }
                throw new IOException(e);
            }
        } else {
            getStreamAndSpdyConnection();
        }
    }

    private void getStreamAndSpdyConnection() {
        streamWrapper();
        this.spdyConnection = new Builder(this.route.address.getUriHost(), true, this.in, this.out).connection(this).build();
        this.spdyConnection.sendConnectionHeader();
    }

    /* JADX INFO: finally extract failed */
    private void upgradeToTls(TunnelRequest tunnelRequest, TransportContext netContext) {
        LogCatUtil.info(InnerLogUtil.ALTS_TEST_LOG, "TLS HandShake!");
        Platform platform = Platform.get();
        if (requiresTunnel()) {
            makeTunnel(tunnelRequest);
        }
        this.socket = this.route.address.sslSocketFactory.createSocket(this.socket, this.route.address.uriHost, this.route.address.uriPort, true);
        SSLSocket sslSocket = (SSLSocket) this.socket;
        if (this.route.modernTls) {
            LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "SSL modernTls");
            platform.enableTlsExtensions(sslSocket, this.route.address.uriHost);
        } else {
            LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "SSL V3");
            platform.supportTlsIntolerantServer(sslSocket);
        }
        setSoTimeout(sslSocket, TransportStrategy.getHandshakTimeout());
        netContext.getCurrentDataContainer().timeItemDot("SSL_TIME");
        try {
            sslSocket.startHandshake();
            setSoTimeout(sslSocket, 0);
            netContext.getCurrentDataContainer().timeItemRelease("SSL_TIME");
            this.out = sslSocket.getOutputStream();
            this.in = sslSocket.getInputStream();
            getStreamAndSpdyConnection();
        } catch (Throwable th) {
            setSoTimeout(sslSocket, 0);
            throw th;
        }
    }

    private void setSoTimeout(Socket socket2, int handshakTimeout) {
        try {
            if (!socket2.isClosed()) {
                socket2.setSoTimeout(handshakTimeout);
            }
        } catch (Exception e) {
            LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "setSoTimeout exception: " + e.toString());
        }
    }

    public final boolean isConnected() {
        return this.connected;
    }

    public final void close() {
        try {
            if (this.socket != null) {
                this.socket.close();
                if (this.connected) {
                    this.connected = false;
                }
                ConnectionObservable.getInstance().notifyObservers(new ConnectionEvent(1, this));
                LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "Connection. invoked close() ");
            }
        } catch (Exception e) {
            LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, (Throwable) e);
        }
    }

    public final Route getRoute() {
        return this.route;
    }

    public final Socket getSocket() {
        return this.socket;
    }

    public final boolean isAlive() {
        return !this.socket.isClosed() && !this.socket.isInputShutdown() && !this.socket.isOutputShutdown();
    }

    public final boolean isReadable() {
        int readTimeout;
        if (!(this.in instanceof BufferedInputStream) || isSpdy()) {
            return true;
        }
        BufferedInputStream bufferedInputStream = (BufferedInputStream) this.in;
        try {
            readTimeout = this.socket.getSoTimeout();
            this.socket.setSoTimeout(1);
            bufferedInputStream.mark(1);
            if (bufferedInputStream.read() == -1) {
                this.socket.setSoTimeout(readTimeout);
                return false;
            }
            bufferedInputStream.reset();
            this.socket.setSoTimeout(readTimeout);
            return true;
        } catch (SocketTimeoutException e) {
            return true;
        } catch (IOException e2) {
            return false;
        } catch (Throwable th) {
            this.socket.setSoTimeout(readTimeout);
            throw th;
        }
    }

    public final void resetIdleStartTime() {
        if (this.spdyConnection != null) {
            throw new IllegalStateException("spdyConnection != null");
        }
        this.idleStartTimeNs = System.nanoTime();
    }

    public final boolean isIdle() {
        return this.spdyConnection == null || this.spdyConnection.isIdle();
    }

    public final boolean isExpired(long keepAliveDurationNs) {
        return getIdleStartTimeNs() < System.nanoTime() - keepAliveDurationNs;
    }

    public final long getIdleStartTimeNs() {
        return this.spdyConnection == null ? this.idleStartTimeNs : this.spdyConnection.getIdleStartTimeNs();
    }

    public final Object newTransport(HttpEngine httpEngine) {
        return this.spdyConnection != null ? new SpdyTransport(httpEngine, this.spdyConnection) : new HttpTransport(httpEngine, this.out, this.in);
    }

    public final boolean isSpdy() {
        return this.spdyConnection != null;
    }

    public final SpdyConnection getSpdyConnection() {
        return this.spdyConnection;
    }

    public final int getHttpMinorVersion() {
        return this.httpMinorVersion;
    }

    public final void setHttpMinorVersion(int httpMinorVersion2) {
        this.httpMinorVersion = httpMinorVersion2;
    }

    public final boolean requiresTunnel() {
        return this.route.address.sslSocketFactory != null && this.route.proxy.type() == Type.HTTP;
    }

    public final void updateReadTimeout(int newTimeout) {
        if (!this.connected) {
            throw new IllegalStateException("updateReadTimeout - not connected");
        }
        setSoTimeout(this.socket, newTimeout);
    }

    private void makeTunnel(TunnelRequest tunnelRequest) {
        RawHeaders requestHeaders = tunnelRequest.getRequestHeaders();
        while (true) {
            RawHeaders requestHeaders2 = requestHeaders;
            this.out.write(requestHeaders2.toBytes());
            RawHeaders responseHeaders = RawHeaders.fromBytes(this.in);
            switch (responseHeaders.getResponseCode()) {
                case 200:
                    return;
                case 407:
                    requestHeaders = new RawHeaders(requestHeaders2);
                    if (!HttpAuthenticator.processAuthHeader(this.route.address.authenticator, 407, responseHeaders, requestHeaders, this.route.proxy, new URL("https", tunnelRequest.host, tunnelRequest.port, "/"))) {
                        throw new IOException("Failed to authenticate with proxy");
                    }
                default:
                    throw new IOException("Unexpected response code for CONNECT: " + responseHeaders.getResponseCode());
            }
        }
    }

    private void streamWrapper() {
        int mtu = Platform.get().getMtu(this.socket);
        if (mtu < 1024) {
            mtu = 1024;
        }
        if (mtu > 8192) {
            mtu = 8192;
        }
        this.in = new BufferedInputStream(this.in, mtu);
        this.out = new ExtBufferedOutputStream(this.out, mtu);
    }

    public final void setContext(Context mContext2) {
        this.mContext = mContext2;
    }

    public final Context getmContext() {
        return this.mContext;
    }
}
