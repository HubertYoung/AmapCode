package defpackage;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* renamed from: cr reason: default package */
/* compiled from: HttpSslUtil */
public final class cr {
    static SSLSocketFactory a;
    static HostnameVerifier b;
    public static final HostnameVerifier c = new a(0);
    public static final SSLSocketFactory d = b.a();

    /* renamed from: cr$a */
    /* compiled from: HttpSslUtil */
    static class a implements HostnameVerifier {
        public final boolean verify(String str, SSLSession sSLSession) {
            return true;
        }

        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }
    }

    /* renamed from: cr$b */
    /* compiled from: HttpSslUtil */
    static class b {

        /* renamed from: cr$b$a */
        /* compiled from: HttpSslUtil */
        static class a implements X509TrustManager {
            public final void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            }

            public final void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            }

            public final X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            private a() {
            }

            /* synthetic */ a(byte b) {
                this();
            }
        }

        public static SSLSocketFactory a() {
            try {
                SSLContext instance = SSLContext.getInstance("TLS");
                instance.init(null, new TrustManager[]{new a(0)}, null);
                return instance.getSocketFactory();
            } catch (Throwable th) {
                StringBuilder sb = new StringBuilder("getSocketFactory error :");
                sb.append(th.getMessage());
                cl.c("awcn.SSLTrustAllSocketFactory", sb.toString(), null, new Object[0]);
                return null;
            }
        }
    }

    public static SSLSocketFactory a() {
        return a;
    }

    public static HostnameVerifier b() {
        return b;
    }
}
