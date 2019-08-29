package com.ta.audid.upload;

import android.net.SSLCertificateSocketFactory;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.ta.audid.utils.UtdidLogger;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

class UtdidSslSocketFactory extends SSLSocketFactory {
    private HostnameVerifier hostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
    private String peerHost;
    private Method setHostNameMethod = null;

    public Socket createSocket() throws IOException {
        return null;
    }

    public Socket createSocket(String str, int i) throws IOException {
        return null;
    }

    public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException {
        return null;
    }

    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        return null;
    }

    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
        return null;
    }

    public UtdidSslSocketFactory(String str) {
        this.peerHost = str;
    }

    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    public String[] getSupportedCipherSuites() {
        return new String[0];
    }

    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        SSLSocket sSLSocket;
        UtdidLogger.sd("", "peerHost", this.peerHost, "host", str, "port", Integer.valueOf(i), "autoClose", Boolean.valueOf(z));
        SSLCertificateSocketFactory sSLCertificateSocketFactory = (SSLCertificateSocketFactory) SSLCertificateSocketFactory.getDefault(0);
        sSLCertificateSocketFactory.setTrustManagers(UtdidTrustManager.getTrustManagers());
        UtdidLogger.d((String) "", "createSocket");
        if (VERSION.SDK_INT < 23) {
            InetAddress inetAddress = socket.getInetAddress();
            if (z) {
                socket.close();
            }
            sSLSocket = (SSLSocket) sSLCertificateSocketFactory.createSocket(inetAddress, i);
        } else {
            sSLSocket = (SSLSocket) sSLCertificateSocketFactory.createSocket(socket, this.peerHost, i, true);
        }
        UtdidLogger.d((String) "", "createSocket end");
        sSLSocket.setEnabledProtocols(sSLSocket.getSupportedProtocols());
        if (VERSION.SDK_INT >= 17) {
            sSLCertificateSocketFactory.setHostname(sSLSocket, this.peerHost);
        } else {
            try {
                if (this.setHostNameMethod == null) {
                    this.setHostNameMethod = sSLSocket.getClass().getMethod("setHostname", new Class[]{String.class});
                    this.setHostNameMethod.setAccessible(true);
                }
                this.setHostNameMethod.invoke(sSLSocket, new Object[]{this.peerHost});
            } catch (Exception e) {
                UtdidLogger.d((String) "", "SNI not useable", e);
            }
        }
        SSLSession session = sSLSocket.getSession();
        if (!this.hostnameVerifier.verify(this.peerHost, session)) {
            StringBuilder sb = new StringBuilder("Cannot verify hostname: ");
            sb.append(this.peerHost);
            throw new SSLPeerUnverifiedException(sb.toString());
        }
        UtdidLogger.sd("", "SSLSession PeerHost", session.getPeerHost());
        return sSLSocket;
    }

    public boolean equals(Object obj) {
        if (TextUtils.isEmpty(this.peerHost) || !(obj instanceof UtdidSslSocketFactory)) {
            return false;
        }
        String str = ((UtdidSslSocketFactory) obj).peerHost;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return this.peerHost.equals(str);
    }
}
