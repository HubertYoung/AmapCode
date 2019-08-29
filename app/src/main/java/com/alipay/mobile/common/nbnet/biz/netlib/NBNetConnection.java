package com.alipay.mobile.common.nbnet.biz.netlib;

import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.biz.log.MonitorLogUtil;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.ProtocolUtils;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Map;
import javax.net.ssl.SSLSocket;

public class NBNetConnection implements Closeable {
    private Socket a;
    private NBNetRoute b;
    private boolean c = false;
    private InputStream d;
    private OutputStream e;
    private long f;
    private long g = -1;
    private NBNetContext h;
    private long i = -1;
    private long j = -1;

    public NBNetConnection(NBNetRoute nbNetRoute) {
        this.b = nbNetRoute;
    }

    public final void a(int connectTimeout, int handshakTimeout, NBNetContext nbNetContext) {
        if (this.c) {
            throw new IllegalStateException("already connected");
        }
        this.h = nbNetContext;
        this.c = true;
        this.a = l();
        c(connectTimeout);
        this.d = this.a.getInputStream();
        this.e = this.a.getOutputStream();
        if (n()) {
            a(new NBNetTunnelRequest(this.b.a(), this.b.b(), "android-nbnet"));
        }
        b(handshakTimeout);
        m();
        this.g = System.currentTimeMillis();
    }

    /* JADX INFO: finally extract failed */
    private void b(int handshakTimeout) {
        if (this.b.c() == null) {
            NBNetLogCat.a((String) H5AppHttpRequest.HEADER_CONNECTION, (String) "ssl socket factory no exist!");
            return;
        }
        this.a = this.b.c().createSocket(this.a, this.b.a(), this.b.b(), true);
        SSLSocket sslSocket = (SSLSocket) this.a;
        NBNetPlatform.a(sslSocket, this.b.a());
        long startTime = System.currentTimeMillis();
        a(sslSocket, handshakTimeout);
        try {
            sslSocket.startHandshake();
            this.j = System.currentTimeMillis() - startTime;
            a(sslSocket, 0);
            MonitorLogUtil.e(this.h, this.j);
            this.e = sslSocket.getOutputStream();
            this.d = sslSocket.getInputStream();
            NBNetLogCat.a((String) H5AppHttpRequest.HEADER_CONNECTION, "tlsHandShake. action=HandShaked, target_ip=" + this.b.e().getAddress().getHostAddress());
        } catch (Throwable th) {
            this.j = System.currentTimeMillis() - startTime;
            a(sslSocket, 0);
            MonitorLogUtil.e(this.h, this.j);
            throw th;
        }
    }

    private void c(int connectTimeout) {
        long startTime = System.currentTimeMillis();
        try {
            NBNetLogCat.a((String) H5AppHttpRequest.HEADER_CONNECTION, "connectSocket. action=connecting, hostName=" + this.b.a() + ", target_ip=" + this.b.e().getAddress().getHostAddress() + ", port=" + this.b.b() + ", ssl_model=" + (this.b.c() != null));
            this.a.connect(this.b.e(), connectTimeout);
            NBNetLogCat.a((String) H5AppHttpRequest.HEADER_CONNECTION, "connectSocket. action=connected, target_ip=" + this.b.e().getAddress().getHostAddress() + ", local_ip=" + this.a.getLocalAddress().getHostAddress() + ", local_port=" + this.a.getLocalPort());
            this.i = System.currentTimeMillis() - startTime;
            MonitorLogUtil.d(this.h, this.i);
        } catch (IOException e2) {
            ConnectException connectException = new ConnectException("connectSocket fail");
            connectException.initCause(e2);
            throw connectException;
        } catch (Throwable th) {
            this.i = System.currentTimeMillis() - startTime;
            MonitorLogUtil.d(this.h, this.i);
            throw th;
        }
    }

    private Socket l() {
        Socket localSocket = (this.b.d().type() == Type.HTTP || this.b.d() == Proxy.NO_PROXY) ? new Socket() : new Socket(this.b.d());
        try {
            localSocket.setTcpNoDelay(true);
            localSocket.setKeepAlive(true);
        } catch (Throwable e2) {
            NBNetLogCat.a((String) H5AppHttpRequest.HEADER_CONNECTION, e2);
        }
        return localSocket;
    }

    public void close() {
        try {
            if (this.a != null && a()) {
                try {
                    this.a.shutdownInput();
                } catch (Throwable th) {
                }
                try {
                    this.a.shutdownOutput();
                } catch (Throwable th2) {
                }
                try {
                    this.d.close();
                } catch (Throwable th3) {
                }
                try {
                    this.e.close();
                } catch (Throwable th4) {
                }
                this.a.close();
                this.a = null;
                if (this.c) {
                    this.c = false;
                }
            }
        } catch (Throwable e2) {
            NBNetLogCat.a((String) H5AppHttpRequest.HEADER_CONNECTION, e2);
        }
    }

    private static void a(Socket socket, int handshakTimeout) {
        try {
            if (!socket.isClosed()) {
                socket.setSoTimeout(handshakTimeout);
            }
        } catch (Exception e2) {
            NBNetLogCat.d(H5AppHttpRequest.HEADER_CONNECTION, "setSoTimeout(" + handshakTimeout + ") exception: " + e2.toString());
        }
    }

    private void m() {
        if (this.a != null) {
            this.d = new BufferedInputStream(this.d, 65536);
            this.e = new BufferedOutputStream(this.e, 65536);
        }
    }

    public final boolean a() {
        if (this.a != null && !this.a.isClosed() && !this.a.isInputShutdown() && !this.a.isOutputShutdown()) {
            return true;
        }
        return false;
    }

    public final boolean b() {
        return this.f < System.currentTimeMillis() - StatisticConfig.MIN_UPLOAD_INTERVAL;
    }

    public final boolean c() {
        return this.f != Long.MAX_VALUE;
    }

    public final void a(boolean idleState) {
        if (idleState) {
            this.f = System.currentTimeMillis();
        } else {
            this.f = Long.MAX_VALUE;
        }
    }

    public final void a(int newTimeout) {
        if (!this.c) {
            throw new IllegalStateException("updateReadTimeout - not connected");
        }
        a(this.a, newTimeout);
    }

    public final boolean d() {
        int readTimeout;
        long startTime;
        if (!(this.d instanceof BufferedInputStream)) {
            return true;
        }
        BufferedInputStream bufferedInputStream = (BufferedInputStream) this.d;
        try {
            if (!a()) {
                return false;
            }
            readTimeout = this.a.getSoTimeout();
            startTime = System.currentTimeMillis();
            this.a.setSoTimeout(1);
            bufferedInputStream.mark(1);
            if (bufferedInputStream.read() == -1) {
                a(this.a, readTimeout);
                NBNetLogCat.a((String) H5AppHttpRequest.HEADER_CONNECTION, "checkStale timeing: " + (System.currentTimeMillis() - startTime));
                return false;
            }
            bufferedInputStream.reset();
            a(this.a, readTimeout);
            NBNetLogCat.a((String) H5AppHttpRequest.HEADER_CONNECTION, "checkStale timeing: " + (System.currentTimeMillis() - startTime));
            return true;
        } catch (SocketTimeoutException e2) {
            NBNetLogCat.a((String) H5AppHttpRequest.HEADER_CONNECTION, "checkStale e1: " + e2.toString());
            return true;
        } catch (IOException e3) {
            NBNetLogCat.a((String) H5AppHttpRequest.HEADER_CONNECTION, "checkStale e2: " + e3.toString());
            return false;
        } catch (Throwable th) {
            a(this.a, readTimeout);
            NBNetLogCat.a((String) H5AppHttpRequest.HEADER_CONNECTION, "checkStale timeing: " + (System.currentTimeMillis() - startTime));
            throw th;
        }
    }

    private boolean n() {
        if ((this.b.f() || this.b.c() != null) && this.b.d() != null && this.b.d().type() == Type.HTTP && this.b.f()) {
            return true;
        }
        return false;
    }

    public final InputStream e() {
        return this.d;
    }

    public final OutputStream f() {
        return this.e;
    }

    public final long g() {
        return this.g;
    }

    public final NBNetRoute h() {
        return this.b;
    }

    public final Socket i() {
        return this.a;
    }

    public final String j() {
        try {
            return this.b.e().getAddress().getHostAddress();
        } catch (Throwable e2) {
            NBNetLogCat.d(H5AppHttpRequest.HEADER_CONNECTION, "getHostAddress exception: " + e2.toString());
            return "";
        }
    }

    private int o() {
        return h().b();
    }

    public final String k() {
        return j() + ":" + o();
    }

    private void a(NBNetTunnelRequest tunnelRequest) {
        this.e.write(ProtocolUtils.a((Map<String, String>) tunnelRequest.a(), tunnelRequest.b()));
        Map headers = ProtocolUtils.a(this.d);
        NBNetLogCat.f(H5AppHttpRequest.HEADER_CONNECTION, "makeTunnel response: " + headers.toString());
        int responseCode = Integer.parseInt(headers.get("responseCode"));
        if (responseCode == 200) {
            NBNetLogCat.a((String) H5AppHttpRequest.HEADER_CONNECTION, (String) "makeTunnel success.");
            return;
        }
        throw new IOException("Unexpected response code for CONNECT: " + responseCode);
    }
}
