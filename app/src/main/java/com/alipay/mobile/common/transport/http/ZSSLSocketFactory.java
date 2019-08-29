package com.alipay.mobile.common.transport.http;

import android.os.Build.VERSION;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import com.alipay.mobile.common.netsdkextdependapi.deviceinfo.DeviceInfoUtil;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.SSLSocketUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class ZSSLSocketFactory implements LayeredSocketFactory {
    private final SSLSocketFactory a = HttpsURLConnection.getDefaultSSLSocketFactory();
    private X509HostnameVerifier b = org.apache.http.conn.ssl.SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;

    /* JADX INFO: finally extract failed */
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) {
        SSLSocket sslSocket = (SSLSocket) this.a.createSocket(socket, host, port, autoClose);
        if (MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.LOW_VERSION_ENABLE_SSL)) && VERSION.SDK_INT < 20) {
            String[] supportedProtocols = sslSocket.getSupportedProtocols();
            sslSocket.setEnabledProtocols(supportedProtocols);
            LogCatUtil.debug("ZSSLSocketFactory", "force enable: " + Arrays.toString(supportedProtocols));
        }
        SSLSocketUtil.enableTlsExtensions(sslSocket, host);
        int soTimeout = socket.getSoTimeout();
        a(socket, HttpConstants.CONNECTION_TIME_OUT);
        try {
            sslSocket.startHandshake();
            a(socket, soTimeout);
            this.b.verify(host, sslSocket);
            return sslSocket;
        } catch (Throwable th) {
            a(socket, soTimeout);
            throw th;
        }
    }

    public Socket createSocket() {
        return (SSLSocket) this.a.createSocket();
    }

    public Socket connectSocket(Socket sock, String host, int port, InetAddress localAddress, int localPort, HttpParams params) {
        Socket createSocket;
        if (host == null) {
            throw new IllegalArgumentException("Target host may not be null.");
        } else if (params == null) {
            throw new IllegalArgumentException("Parameters may not be null.");
        } else {
            if (sock != null) {
                createSocket = sock;
            } else {
                createSocket = createSocket();
            }
            SSLSocket sslsock = (SSLSocket) createSocket;
            SSLSocketUtil.enableTlsExtensions(sslsock, host);
            if (localAddress != null || localPort > 0) {
                if (localPort < 0) {
                    localPort = 0;
                }
                sslsock.bind(new InetSocketAddress(localAddress, localPort));
            }
            int connTimeout = HttpConnectionParams.getConnectionTimeout(params);
            int soTimeout = HttpConnectionParams.getSoTimeout(params);
            sslsock.connect(new InetSocketAddress(host, port), connTimeout);
            sslsock.setSoTimeout(soTimeout);
            try {
                this.b.verify(host, sslsock);
                return sslsock;
            } catch (IOException iox) {
                try {
                    sslsock.close();
                } catch (Exception e) {
                }
                throw iox;
            }
        }
    }

    public boolean isSecure(Socket sock) {
        if (sock == null) {
            throw new IllegalArgumentException("Socket may not be null.");
        } else if (!(sock instanceof SSLSocket)) {
            throw new IllegalArgumentException("Socket not created by this factory.");
        } else if (!sock.isClosed()) {
            return true;
        } else {
            throw new IllegalArgumentException("Socket is closed.");
        }
    }

    public void setHostnameVerifier(X509HostnameVerifier hostnameVerifier) {
        if (hostnameVerifier == null) {
            throw new IllegalArgumentException("Hostname verifier may not be null");
        }
        this.b = hostnameVerifier;
    }

    public X509HostnameVerifier getHostnameVerifier() {
        return this.b;
    }

    private static void a(Socket socket, int handshakTimeout) {
        try {
            if (!socket.isClosed()) {
                socket.setSoTimeout(handshakTimeout);
            }
        } catch (Exception e) {
            LogCatUtil.warn((String) "ZSSLSocketFactory", "setSoTimeout exception : " + e.toString());
        }
    }
}
