package defpackage;

import android.net.SSLCertificateSocketFactory;
import android.os.Build.VERSION;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* renamed from: da reason: default package */
/* compiled from: TlsSniSocketFactory */
public final class da extends SSLSocketFactory {
    private final String a = "awcn.TlsSniSocketFactory";
    private Method b = null;
    private String c;

    public final Socket createSocket() throws IOException {
        return null;
    }

    public final Socket createSocket(String str, int i) throws IOException, UnknownHostException {
        return null;
    }

    public final Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException, UnknownHostException {
        return null;
    }

    public final Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        return null;
    }

    public final Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
        return null;
    }

    public da(String str) {
        this.c = str;
    }

    public final String[] getDefaultCipherSuites() {
        return new String[0];
    }

    public final String[] getSupportedCipherSuites() {
        return new String[0];
    }

    public final Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        if (this.c == null) {
            this.c = str;
        }
        if (cl.a(1)) {
            cl.b("awcn.TlsSniSocketFactory", "customized createSocket", null, "host", this.c);
        }
        InetAddress inetAddress = socket.getInetAddress();
        if (z) {
            socket.close();
        }
        SSLCertificateSocketFactory sSLCertificateSocketFactory = (SSLCertificateSocketFactory) SSLCertificateSocketFactory.getDefault(0);
        SSLSocket sSLSocket = (SSLSocket) sSLCertificateSocketFactory.createSocket(inetAddress, i);
        sSLSocket.setEnabledProtocols(sSLSocket.getSupportedProtocols());
        if (VERSION.SDK_INT >= 17) {
            sSLCertificateSocketFactory.setHostname(sSLSocket, this.c);
        } else {
            try {
                if (this.b == null) {
                    this.b = sSLSocket.getClass().getMethod("setHostname", new Class[]{String.class});
                    this.b.setAccessible(true);
                }
                this.b.invoke(sSLSocket, new Object[]{this.c});
            } catch (Exception e) {
                cl.a("awcn.TlsSniSocketFactory", "SNI not useable", null, e, new Object[0]);
            }
        }
        SSLSession session = sSLSocket.getSession();
        if (cl.a(1)) {
            cl.a("awcn.TlsSniSocketFactory", null, null, "SSLSession PeerHost", session.getPeerHost());
        }
        return sSLSocket;
    }
}
