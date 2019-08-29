package com.tencent.open.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.autonavi.link.protocol.http.MultipartUtility;
import com.tencent.connect.a.a;
import com.tencent.connect.auth.QQToken;
import com.tencent.open.a.f;
import com.tencent.open.utils.Util.Statistic;
import com.tencent.tauth.IRequestListener;
import java.io.ByteArrayOutputStream;
import java.io.CharConversionException;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.NotActiveException;
import java.io.NotSerializableException;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.io.SyncFailedException;
import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;
import java.io.WriteAbortedException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileLockInterruptionException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.UnmappableCharacterException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.InvalidPropertiesFormatException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLKeyException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLProtocolException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.ConnectionClosedException;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.MalformedChunkCodingException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class HttpUtils {

    /* compiled from: ProGuard */
    public static class CustomSSLSocketFactory extends SSLSocketFactory {
        private final SSLContext a = SSLContext.getInstance("TLS");

        public CustomSSLSocketFactory(KeyStore keyStore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            TrustManager trustManager;
            super(keyStore);
            try {
                trustManager = new MyX509TrustManager();
            } catch (Exception unused) {
                trustManager = null;
            }
            this.a.init(null, new TrustManager[]{trustManager}, null);
        }

        public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException, UnknownHostException {
            return this.a.getSocketFactory().createSocket(socket, str, i, z);
        }

        public Socket createSocket() throws IOException {
            return this.a.getSocketFactory().createSocket();
        }
    }

    /* compiled from: ProGuard */
    public static class HttpStatusException extends Exception {
        public static final String ERROR_INFO = "http status code error:";

        public HttpStatusException(String str) {
            super(str);
        }
    }

    /* compiled from: ProGuard */
    public static class MyX509TrustManager implements X509TrustManager {
        X509TrustManager a;

        /* JADX WARNING: Removed duplicated region for block: B:16:0x0039  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        MyX509TrustManager() throws java.lang.Exception {
            /*
                r4 = this;
                r4.<init>()
                r0 = 0
                java.lang.String r1 = "JKS"
                java.security.KeyStore r1 = java.security.KeyStore.getInstance(r1)     // Catch:{ Exception -> 0x000b }
                goto L_0x000c
            L_0x000b:
                r1 = r0
            L_0x000c:
                if (r1 == 0) goto L_0x003d
                java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ all -> 0x0034 }
                java.lang.String r3 = "trustedCerts"
                r2.<init>(r3)     // Catch:{ all -> 0x0034 }
                java.lang.String r0 = "passphrase"
                char[] r0 = r0.toCharArray()     // Catch:{ all -> 0x0032 }
                r1.load(r2, r0)     // Catch:{ all -> 0x0032 }
                java.lang.String r0 = "SunX509"
                java.lang.String r3 = "SunJSSE"
                javax.net.ssl.TrustManagerFactory r0 = javax.net.ssl.TrustManagerFactory.getInstance(r0, r3)     // Catch:{ all -> 0x0032 }
                r0.init(r1)     // Catch:{ all -> 0x0032 }
                javax.net.ssl.TrustManager[] r0 = r0.getTrustManagers()     // Catch:{ all -> 0x0032 }
                r2.close()
                goto L_0x004c
            L_0x0032:
                r0 = move-exception
                goto L_0x0037
            L_0x0034:
                r1 = move-exception
                r2 = r0
                r0 = r1
            L_0x0037:
                if (r2 == 0) goto L_0x003c
                r2.close()
            L_0x003c:
                throw r0
            L_0x003d:
                java.lang.String r1 = javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm()
                javax.net.ssl.TrustManagerFactory r1 = javax.net.ssl.TrustManagerFactory.getInstance(r1)
                r1.init(r0)
                javax.net.ssl.TrustManager[] r0 = r1.getTrustManagers()
            L_0x004c:
                r1 = 0
            L_0x004d:
                int r2 = r0.length
                if (r1 >= r2) goto L_0x0060
                r2 = r0[r1]
                boolean r2 = r2 instanceof javax.net.ssl.X509TrustManager
                if (r2 == 0) goto L_0x005d
                r0 = r0[r1]
                javax.net.ssl.X509TrustManager r0 = (javax.net.ssl.X509TrustManager) r0
                r4.a = r0
                return
            L_0x005d:
                int r1 = r1 + 1
                goto L_0x004d
            L_0x0060:
                java.lang.Exception r0 = new java.lang.Exception
                java.lang.String r1 = "Couldn't initialize"
                r0.<init>(r1)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.utils.HttpUtils.MyX509TrustManager.<init>():void");
        }

        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            this.a.checkClientTrusted(x509CertificateArr, str);
        }

        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            this.a.checkServerTrusted(x509CertificateArr, str);
        }

        public X509Certificate[] getAcceptedIssuers() {
            return this.a.getAcceptedIssuers();
        }
    }

    /* compiled from: ProGuard */
    public static class NetworkProxy {
        public final String host;
        public final int port;

        private NetworkProxy(String str, int i) {
            this.host = str;
            this.port = i;
        }
    }

    /* compiled from: ProGuard */
    public static class NetworkUnavailableException extends Exception {
        public static final String ERROR_INFO = "network unavailable";

        public NetworkUnavailableException(String str) {
            super(str);
        }
    }

    private HttpUtils() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00d8, code lost:
        r2 = -4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00fb, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00fc, code lost:
        r10 = r0;
        r10.printStackTrace();
        com.tencent.open.b.g.a().a(r4, r6, 0, 0, getErrorCodeFromException(r10));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0113, code lost:
        throw r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0114, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0115, code lost:
        r10 = r0;
        r10.printStackTrace();
        com.tencent.open.b.g.a().a(r4, r6, 0, 0, -3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x012a, code lost:
        throw r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x012b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x012c, code lost:
        r1 = r0;
        r1.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0130, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0131, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0132, code lost:
        r10 = r0;
        r10.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0146, code lost:
        r9 = java.lang.Integer.parseInt(r10.getMessage().replace(com.tencent.open.utils.HttpUtils.HttpStatusException.ERROR_INFO, ""));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0148, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0149, code lost:
        r0.printStackTrace();
        r9 = -9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0161, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0162, code lost:
        r10 = r0;
        r14 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0181, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0182, code lost:
        r10 = r0;
        r14 = r2;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00fb A[ExcHandler: IOException (r0v6 'e' java.io.IOException A[CUSTOM_DECLARE]), Splitter:B:9:0x00bf] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0114 A[ExcHandler: MalformedURLException (r0v5 'e' java.net.MalformedURLException A[CUSTOM_DECLARE]), Splitter:B:9:0x00bf] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x012b A[ExcHandler: NetworkUnavailableException (r0v4 'e' com.tencent.open.utils.HttpUtils$NetworkUnavailableException A[CUSTOM_DECLARE]), Splitter:B:9:0x00bf] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0131 A[ExcHandler: HttpStatusException (r0v2 'e' com.tencent.open.utils.HttpUtils$HttpStatusException A[CUSTOM_DECLARE]), Splitter:B:9:0x00bf] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x016a  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x01a3 A[LOOP:0: B:8:0x00b9->B:61:0x01a3, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0191 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x016f A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.json.JSONObject request(com.tencent.connect.auth.QQToken r18, android.content.Context r19, java.lang.String r20, android.os.Bundle r21, java.lang.String r22) throws java.io.IOException, org.json.JSONException, com.tencent.open.utils.HttpUtils.NetworkUnavailableException, com.tencent.open.utils.HttpUtils.HttpStatusException {
        /*
            r1 = r19
            r2 = r20
            java.lang.String r3 = "openSDK_LOG.HttpUtils"
            java.lang.String r4 = "OpenApi request"
            com.tencent.open.a.f.a(r3, r4)
            java.lang.String r3 = r20.toLowerCase()
            java.lang.String r4 = "http"
            boolean r3 = r3.startsWith(r4)
            if (r3 != 0) goto L_0x004d
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            com.tencent.open.utils.ServerSetting r4 = com.tencent.open.utils.ServerSetting.getInstance()
            java.lang.String r5 = "https://openmobile.qq.com/"
            java.lang.String r4 = r4.getEnvUrl(r1, r5)
            r3.append(r4)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            com.tencent.open.utils.ServerSetting r5 = com.tencent.open.utils.ServerSetting.getInstance()
            java.lang.String r6 = "https://openmobile.qq.com/"
            java.lang.String r5 = r5.getEnvUrl(r1, r6)
            r4.append(r5)
            r4.append(r2)
            java.lang.String r4 = r4.toString()
            r5 = r3
            r3 = r18
            goto L_0x0051
        L_0x004d:
            r3 = r18
            r4 = r2
            r5 = r4
        L_0x0051:
            a(r1, r3, r2)
            r2 = 0
            long r6 = android.os.SystemClock.elapsedRealtime()
            r8 = 0
            java.lang.String r9 = r18.getAppId()
            com.tencent.open.utils.OpenConfig r9 = com.tencent.open.utils.OpenConfig.getInstance(r1, r9)
            java.lang.String r10 = "Common_HttpRetryCount"
            int r9 = r9.getInt(r10)
            java.lang.String r10 = "OpenConfig_test"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "config 1:Common_HttpRetryCount            config_value:"
            r11.<init>(r12)
            r11.append(r9)
            java.lang.String r12 = "   appid:"
            r11.append(r12)
            java.lang.String r12 = r18.getAppId()
            r11.append(r12)
            java.lang.String r12 = "     url:"
            r11.append(r12)
            r11.append(r4)
            java.lang.String r11 = r11.toString()
            com.tencent.open.a.f.a(r10, r11)
            if (r9 != 0) goto L_0x0092
            r9 = 3
        L_0x0092:
            java.lang.String r10 = "OpenConfig_test"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "config 1:Common_HttpRetryCount            result_value:"
            r11.<init>(r12)
            r11.append(r9)
            java.lang.String r12 = "   appid:"
            r11.append(r12)
            java.lang.String r3 = r18.getAppId()
            r11.append(r3)
            java.lang.String r3 = "     url:"
            r11.append(r3)
            r11.append(r4)
            java.lang.String r3 = r11.toString()
            com.tencent.open.a.f.a(r10, r3)
        L_0x00b9:
            int r8 = r8 + 1
            r3 = r21
            r12 = r22
            com.tencent.open.utils.Util$Statistic r13 = openUrl2(r1, r5, r12, r3)     // Catch:{ ConnectTimeoutException -> 0x0181, SocketTimeoutException -> 0x0161, HttpStatusException -> 0x0131, NetworkUnavailableException -> 0x012b, MalformedURLException -> 0x0114, IOException -> 0x00fb, JSONException -> 0x00e4 }
            java.lang.String r14 = r13.response     // Catch:{ ConnectTimeoutException -> 0x0181, SocketTimeoutException -> 0x0161, HttpStatusException -> 0x0131, NetworkUnavailableException -> 0x012b, MalformedURLException -> 0x0114, IOException -> 0x00fb, JSONException -> 0x00e4 }
            org.json.JSONObject r14 = com.tencent.open.utils.Util.parseJson(r14)     // Catch:{ ConnectTimeoutException -> 0x0181, SocketTimeoutException -> 0x0161, HttpStatusException -> 0x0131, NetworkUnavailableException -> 0x012b, MalformedURLException -> 0x0114, IOException -> 0x00fb, JSONException -> 0x00e4 }
            java.lang.String r2 = "ret"
            int r2 = r14.getInt(r2)     // Catch:{ JSONException -> 0x00d8, ConnectTimeoutException -> 0x00d4, SocketTimeoutException -> 0x00d0, HttpStatusException -> 0x0131, NetworkUnavailableException -> 0x012b, MalformedURLException -> 0x0114, IOException -> 0x00fb }
            goto L_0x00d9
        L_0x00d0:
            r0 = move-exception
            r10 = r0
            goto L_0x0164
        L_0x00d4:
            r0 = move-exception
            r10 = r0
            goto L_0x0184
        L_0x00d8:
            r2 = -4
        L_0x00d9:
            long r10 = r13.reqSize     // Catch:{ ConnectTimeoutException -> 0x00d4, SocketTimeoutException -> 0x00d0, HttpStatusException -> 0x0131, NetworkUnavailableException -> 0x012b, MalformedURLException -> 0x0114, IOException -> 0x00fb, JSONException -> 0x00e4 }
            r17 = r2
            long r1 = r13.rspSize     // Catch:{ ConnectTimeoutException -> 0x00d4, SocketTimeoutException -> 0x00d0, HttpStatusException -> 0x0131, NetworkUnavailableException -> 0x012b, MalformedURLException -> 0x0114, IOException -> 0x00fb, JSONException -> 0x00e4 }
            r15 = r1
            r9 = r17
            goto L_0x0197
        L_0x00e4:
            r0 = move-exception
            r10 = r0
            r10.printStackTrace()
            com.tencent.open.b.g r1 = com.tencent.open.b.g.a()
            r8 = 0
            r11 = 0
            r13 = -4
            r2 = r4
            r3 = r6
            r5 = r8
            r7 = r11
            r9 = r13
            r1.a(r2, r3, r5, r7, r9)
            throw r10
        L_0x00fb:
            r0 = move-exception
            r10 = r0
            r10.printStackTrace()
            int r9 = getErrorCodeFromException(r10)
            com.tencent.open.b.g r1 = com.tencent.open.b.g.a()
            r11 = 0
            r13 = 0
            r2 = r4
            r3 = r6
            r5 = r11
            r7 = r13
            r1.a(r2, r3, r5, r7, r9)
            throw r10
        L_0x0114:
            r0 = move-exception
            r10 = r0
            r10.printStackTrace()
            com.tencent.open.b.g r1 = com.tencent.open.b.g.a()
            r8 = 0
            r11 = 0
            r13 = -3
            r2 = r4
            r3 = r6
            r5 = r8
            r7 = r11
            r9 = r13
            r1.a(r2, r3, r5, r7, r9)
            throw r10
        L_0x012b:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            throw r1
        L_0x0131:
            r0 = move-exception
            r10 = r0
            r10.printStackTrace()
            java.lang.String r1 = r10.getMessage()
            java.lang.String r2 = "http status code error:"
            java.lang.String r3 = ""
            java.lang.String r1 = r1.replace(r2, r3)     // Catch:{ Exception -> 0x0148 }
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ Exception -> 0x0148 }
            r9 = r1
            goto L_0x0151
        L_0x0148:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            r1 = -9
            r9 = -9
        L_0x0151:
            com.tencent.open.b.g r1 = com.tencent.open.b.g.a()
            r11 = 0
            r13 = 0
            r2 = r4
            r3 = r6
            r5 = r11
            r7 = r13
            r1.a(r2, r3, r5, r7, r9)
            throw r10
        L_0x0161:
            r0 = move-exception
            r10 = r0
            r14 = r2
        L_0x0164:
            r10.printStackTrace()
            r1 = -8
            if (r8 >= r9) goto L_0x016f
            long r6 = android.os.SystemClock.elapsedRealtime()
            goto L_0x018e
        L_0x016f:
            com.tencent.open.b.g r1 = com.tencent.open.b.g.a()
            r8 = 0
            r11 = 0
            r13 = -8
            r2 = r4
            r3 = r6
            r5 = r8
            r7 = r11
            r9 = r13
            r1.a(r2, r3, r5, r7, r9)
            throw r10
        L_0x0181:
            r0 = move-exception
            r10 = r0
            r14 = r2
        L_0x0184:
            r10.printStackTrace()
            r1 = -7
            if (r8 >= r9) goto L_0x01a7
            long r6 = android.os.SystemClock.elapsedRealtime()
        L_0x018e:
            r2 = r14
            if (r8 < r9) goto L_0x01a3
            r9 = r1
            r14 = r2
            r10 = 0
            r15 = 0
        L_0x0197:
            com.tencent.open.b.g r1 = com.tencent.open.b.g.a()
            r2 = r4
            r3 = r6
            r5 = r10
            r7 = r15
            r1.a(r2, r3, r5, r7, r9)
            return r14
        L_0x01a3:
            r1 = r19
            goto L_0x00b9
        L_0x01a7:
            com.tencent.open.b.g r1 = com.tencent.open.b.g.a()
            r8 = 0
            r11 = 0
            r13 = -7
            r2 = r4
            r3 = r6
            r5 = r8
            r7 = r11
            r9 = r13
            r1.a(r2, r3, r5, r7, r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.utils.HttpUtils.request(com.tencent.connect.auth.QQToken, android.content.Context, java.lang.String, android.os.Bundle, java.lang.String):org.json.JSONObject");
    }

    public static void requestAsync(QQToken qQToken, Context context, String str, Bundle bundle, String str2, IRequestListener iRequestListener) {
        f.a("openSDK_LOG.HttpUtils", "OpenApi requestAsync");
        final QQToken qQToken2 = qQToken;
        final Context context2 = context;
        final String str3 = str;
        final Bundle bundle2 = bundle;
        final String str4 = str2;
        final IRequestListener iRequestListener2 = iRequestListener;
        AnonymousClass1 r2 = new Thread() {
            public final void run() {
                try {
                    JSONObject request = HttpUtils.request(qQToken2, context2, str3, bundle2, str4);
                    if (iRequestListener2 != null) {
                        iRequestListener2.onComplete(request);
                        f.b("openSDK_LOG.HttpUtils", "OpenApi onComplete");
                    }
                } catch (MalformedURLException e2) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onMalformedURLException(e2);
                        f.b("openSDK_LOG.HttpUtils", "OpenApi requestAsync MalformedURLException", e2);
                    }
                } catch (ConnectTimeoutException e3) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onConnectTimeoutException(e3);
                        f.b("openSDK_LOG.HttpUtils", "OpenApi requestAsync onConnectTimeoutException", e3);
                    }
                } catch (SocketTimeoutException e4) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onSocketTimeoutException(e4);
                        f.b("openSDK_LOG.HttpUtils", "OpenApi requestAsync onSocketTimeoutException", e4);
                    }
                } catch (NetworkUnavailableException e5) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onNetworkUnavailableException(e5);
                        f.b("openSDK_LOG.HttpUtils", "OpenApi requestAsync onNetworkUnavailableException", e5);
                    }
                } catch (HttpStatusException e6) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onHttpStatusException(e6);
                        f.b("openSDK_LOG.HttpUtils", "OpenApi requestAsync onHttpStatusException", e6);
                    }
                } catch (IOException e7) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onIOException(e7);
                        f.b("openSDK_LOG.HttpUtils", "OpenApi requestAsync IOException", e7);
                    }
                } catch (JSONException e8) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onJSONException(e8);
                        f.b("openSDK_LOG.HttpUtils", "OpenApi requestAsync JSONException", e8);
                    }
                } catch (Exception e9) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onUnknowException(e9);
                        f.b("openSDK_LOG.HttpUtils", "OpenApi requestAsync onUnknowException", e9);
                    }
                }
            }
        };
        r2.start();
    }

    private static void a(Context context, QQToken qQToken, String str) {
        if (str.indexOf("add_share") >= 0 || str.indexOf("upload_pic") >= 0 || str.indexOf("add_topic") >= 0 || str.indexOf("set_user_face") >= 0 || str.indexOf("add_t") >= 0 || str.indexOf("add_pic_t") >= 0 || str.indexOf("add_pic_url") >= 0 || str.indexOf("add_video") >= 0) {
            a.a(context, qQToken, "requireApi", str);
        }
    }

    public static int getErrorCodeFromException(IOException iOException) {
        if (iOException instanceof CharConversionException) {
            return -20;
        }
        if (iOException instanceof MalformedInputException) {
            return -21;
        }
        if (iOException instanceof UnmappableCharacterException) {
            return -22;
        }
        if (iOException instanceof HttpResponseException) {
            return -23;
        }
        if (iOException instanceof ClosedChannelException) {
            return -24;
        }
        if (iOException instanceof ConnectionClosedException) {
            return -25;
        }
        if (iOException instanceof EOFException) {
            return -26;
        }
        if (iOException instanceof FileLockInterruptionException) {
            return -27;
        }
        if (iOException instanceof FileNotFoundException) {
            return -28;
        }
        if (iOException instanceof HttpRetryException) {
            return -29;
        }
        if (iOException instanceof ConnectTimeoutException) {
            return -7;
        }
        if (iOException instanceof SocketTimeoutException) {
            return -8;
        }
        if (iOException instanceof InvalidPropertiesFormatException) {
            return -30;
        }
        if (iOException instanceof MalformedChunkCodingException) {
            return -31;
        }
        if (iOException instanceof MalformedURLException) {
            return -3;
        }
        if (iOException instanceof NoHttpResponseException) {
            return -32;
        }
        if (iOException instanceof InvalidClassException) {
            return -33;
        }
        if (iOException instanceof InvalidObjectException) {
            return -34;
        }
        if (iOException instanceof NotActiveException) {
            return -35;
        }
        if (iOException instanceof NotSerializableException) {
            return -36;
        }
        if (iOException instanceof OptionalDataException) {
            return -37;
        }
        if (iOException instanceof StreamCorruptedException) {
            return -38;
        }
        if (iOException instanceof WriteAbortedException) {
            return -39;
        }
        if (iOException instanceof ProtocolException) {
            return -40;
        }
        if (iOException instanceof SSLHandshakeException) {
            return -41;
        }
        if (iOException instanceof SSLKeyException) {
            return -42;
        }
        if (iOException instanceof SSLPeerUnverifiedException) {
            return -43;
        }
        if (iOException instanceof SSLProtocolException) {
            return -44;
        }
        if (iOException instanceof BindException) {
            return -45;
        }
        if (iOException instanceof ConnectException) {
            return -46;
        }
        if (iOException instanceof NoRouteToHostException) {
            return -47;
        }
        if (iOException instanceof PortUnreachableException) {
            return -48;
        }
        if (iOException instanceof SyncFailedException) {
            return -49;
        }
        if (iOException instanceof UTFDataFormatException) {
            return -50;
        }
        if (iOException instanceof UnknownHostException) {
            return -51;
        }
        if (iOException instanceof UnknownServiceException) {
            return -52;
        }
        if (iOException instanceof UnsupportedEncodingException) {
            return -53;
        }
        return iOException instanceof ZipException ? -54 : -2;
    }

    public static Statistic openUrl2(Context context, String str, String str2, Bundle bundle) throws MalformedURLException, IOException, NetworkUnavailableException, HttpStatusException {
        Bundle bundle2;
        String str3;
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
                    throw new NetworkUnavailableException(NetworkUnavailableException.ERROR_INFO);
                }
            }
        }
        if (bundle != null) {
            bundle2 = new Bundle(bundle);
        } else {
            bundle2 = new Bundle();
        }
        String string = bundle2.getString("appid_for_getting_config");
        bundle2.remove("appid_for_getting_config");
        HttpClient httpClient = getHttpClient(context, string, str);
        HttpGet httpGet = null;
        int i = -1;
        int i2 = 0;
        if (str2.equals("GET")) {
            String encodeUrl = encodeUrl(bundle2);
            i2 = 0 + encodeUrl.length();
            f.a("openSDK_LOG.HttpUtils", "-->openUrl2 before url =".concat(String.valueOf(str)));
            if (str.indexOf("?") == -1) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("?");
                str3 = sb.toString();
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append("&");
                str3 = sb2.toString();
            }
            StringBuilder sb3 = new StringBuilder("-->openUrl2 encodedParam =");
            sb3.append(encodeUrl);
            sb3.append(" -- url = ");
            sb3.append(str3);
            f.a("openSDK_LOG.HttpUtils", sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str3);
            sb4.append(encodeUrl);
            httpGet = new HttpGet(sb4.toString());
            httpGet.addHeader("Accept-Encoding", "gzip");
        } else if (str2.equals("POST")) {
            httpGet = new HttpPost(str);
            httpGet.addHeader("Accept-Encoding", "gzip");
            Bundle bundle3 = new Bundle();
            for (String str4 : bundle2.keySet()) {
                Object obj = bundle2.get(str4);
                if (obj instanceof byte[]) {
                    bundle3.putByteArray(str4, (byte[]) obj);
                }
            }
            if (!bundle2.containsKey("method")) {
                bundle2.putString("method", str2);
            }
            httpGet.setHeader("Content-Type", "multipart/form-data; boundary=3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f");
            httpGet.setHeader(H5AppHttpRequest.HEADER_CONNECTION, "Keep-Alive");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(Util.getBytesUTF8("--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n"));
            byteArrayOutputStream.write(Util.getBytesUTF8(encodePostBody(bundle2, "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f")));
            if (!bundle3.isEmpty()) {
                int size = bundle3.size();
                byteArrayOutputStream.write(Util.getBytesUTF8("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n"));
                for (String str5 : bundle3.keySet()) {
                    i++;
                    StringBuilder sb5 = new StringBuilder("Content-Disposition: form-data; name=\"");
                    sb5.append(str5);
                    sb5.append("\"; filename=\"");
                    sb5.append(str5);
                    sb5.append("\"\r\n");
                    byteArrayOutputStream.write(Util.getBytesUTF8(sb5.toString()));
                    byteArrayOutputStream.write(Util.getBytesUTF8("Content-Type: content/unknown\r\n\r\n"));
                    byte[] byteArray = bundle3.getByteArray(str5);
                    if (byteArray != null) {
                        byteArrayOutputStream.write(byteArray);
                    }
                    if (i < size - 1) {
                        byteArrayOutputStream.write(Util.getBytesUTF8("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n"));
                    }
                }
            }
            byteArrayOutputStream.write(Util.getBytesUTF8("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f--\r\n"));
            byte[] byteArray2 = byteArrayOutputStream.toByteArray();
            i2 = 0 + byteArray2.length;
            byteArrayOutputStream.close();
            httpGet.setEntity(new ByteArrayEntity(byteArray2));
        }
        HttpResponse execute = httpClient.execute(httpGet);
        int statusCode = execute.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            return new Statistic(a(execute), i2);
        }
        throw new HttpStatusException(HttpStatusException.ERROR_INFO.concat(String.valueOf(statusCode)));
    }

    private static String a(HttpResponse httpResponse) throws IllegalStateException, IOException {
        InputStream content = httpResponse.getEntity().getContent();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Header firstHeader = httpResponse.getFirstHeader(TransportConstants.KEY_X_CONTENT_ENCODING);
        InputStream gZIPInputStream = (firstHeader == null || firstHeader.getValue().toLowerCase().indexOf("gzip") < 0) ? content : new GZIPInputStream(content);
        byte[] bArr = new byte[512];
        while (true) {
            int read = gZIPInputStream.read(bArr);
            if (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                String str = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
                gZIPInputStream.close();
                return str;
            }
        }
    }

    public static HttpClient getHttpClient(Context context, String str, String str2) {
        int i;
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        OpenConfig openConfig = null;
        if (VERSION.SDK_INT < 16) {
            try {
                KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
                instance.load(null, null);
                CustomSSLSocketFactory customSSLSocketFactory = new CustomSSLSocketFactory(instance);
                customSSLSocketFactory.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
                schemeRegistry.register(new Scheme("https", customSSLSocketFactory, 443));
            } catch (Exception unused) {
                schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
            }
        } else {
            schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        }
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        if (context != null) {
            openConfig = OpenConfig.getInstance(context, str);
        }
        int i2 = 0;
        if (openConfig != null) {
            i2 = openConfig.getInt("Common_HttpConnectionTimeout");
            i = openConfig.getInt("Common_SocketConnectionTimeout");
        } else {
            i = 0;
        }
        if (i2 == 0) {
            i2 = HttpConstants.CONNECTION_TIME_OUT;
        }
        if (i == 0) {
            i = 30000;
        }
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, i2);
        HttpConnectionParams.setSoTimeout(basicHttpParams, i);
        HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(basicHttpParams, "UTF-8");
        StringBuilder sb = new StringBuilder("AndroidSDK_");
        sb.append(VERSION.SDK);
        sb.append("_");
        sb.append(Build.DEVICE);
        sb.append("_");
        sb.append(VERSION.RELEASE);
        HttpProtocolParams.setUserAgent(basicHttpParams, sb.toString());
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
        NetworkProxy proxy = getProxy(context);
        if (proxy != null) {
            defaultHttpClient.getParams().setParameter("http.route.default-proxy", new HttpHost(proxy.host, proxy.port));
        }
        return defaultHttpClient;
    }

    public static String encodeUrl(Bundle bundle) {
        if (bundle == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if ((obj instanceof String) || (obj instanceof String[])) {
                if (obj instanceof String[]) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append("&");
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(URLEncoder.encode(str));
                    sb2.append("=");
                    sb.append(sb2.toString());
                    String[] stringArray = bundle.getStringArray(str);
                    if (stringArray != null) {
                        for (int i = 0; i < stringArray.length; i++) {
                            if (i == 0) {
                                sb.append(URLEncoder.encode(stringArray[i]));
                            } else {
                                StringBuilder sb3 = new StringBuilder(",");
                                sb3.append(stringArray[i]);
                                sb.append(URLEncoder.encode(sb3.toString()));
                            }
                        }
                    }
                } else {
                    if (z) {
                        z = false;
                    } else {
                        sb.append("&");
                    }
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(URLEncoder.encode(str));
                    sb4.append("=");
                    sb4.append(URLEncoder.encode(bundle.getString(str)));
                    sb.append(sb4.toString());
                }
            }
        }
        return sb.toString();
    }

    public static String encodePostBody(Bundle bundle, String str) {
        if (bundle == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int size = bundle.size();
        for (String str2 : bundle.keySet()) {
            i++;
            Object obj = bundle.get(str2);
            if (obj instanceof String) {
                StringBuilder sb2 = new StringBuilder("Content-Disposition: form-data; name=\"");
                sb2.append(str2);
                sb2.append("\"\r\n\r\n");
                sb2.append((String) obj);
                sb.append(sb2.toString());
                if (i < size - 1) {
                    StringBuilder sb3 = new StringBuilder("\r\n--");
                    sb3.append(str);
                    sb3.append(MultipartUtility.LINE_FEED);
                    sb.append(sb3.toString());
                }
            }
        }
        return sb.toString();
    }

    public static NetworkProxy getProxy(Context context) {
        if (context == null) {
            return null;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return null;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.getType() == 0) {
            String b = b(context);
            int a = a(context);
            if (!TextUtils.isEmpty(b) && a >= 0) {
                return new NetworkProxy(b, a);
            }
        }
        return null;
    }

    private static int a(Context context) {
        if (VERSION.SDK_INT >= 11) {
            String property = System.getProperty("http.proxyPort");
            if (!TextUtils.isEmpty(property)) {
                try {
                    return Integer.parseInt(property);
                } catch (NumberFormatException unused) {
                }
            }
            return -1;
        } else if (context == null) {
            return Proxy.getDefaultPort();
        } else {
            int port = Proxy.getPort(context);
            if (port < 0) {
                return Proxy.getDefaultPort();
            }
            return port;
        }
    }

    private static String b(Context context) {
        if (VERSION.SDK_INT >= 11) {
            return System.getProperty("http.proxyHost");
        }
        if (context == null) {
            return Proxy.getDefaultHost();
        }
        String host = Proxy.getHost(context);
        if (TextUtils.isEmpty(host)) {
            return Proxy.getDefaultHost();
        }
        return host;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00cf, code lost:
        r2 = -4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00ee, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00ef, code lost:
        r10 = r0;
        r10.printStackTrace();
        com.tencent.open.b.g.a().a(r4, r6, 0, 0, getErrorCodeFromException(r10));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0106, code lost:
        throw r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0107, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0108, code lost:
        r10 = r0;
        r10.printStackTrace();
        com.tencent.open.b.g.a().a(r4, r6, 0, 0, -3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x011d, code lost:
        throw r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x011e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x011f, code lost:
        r1 = r0;
        r1.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0123, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0124, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0125, code lost:
        r10 = r0;
        r10.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0139, code lost:
        r9 = java.lang.Integer.parseInt(r10.getMessage().replace(com.tencent.open.utils.HttpUtils.HttpStatusException.ERROR_INFO, ""));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x013b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x013c, code lost:
        r0.printStackTrace();
        r9 = -9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0154, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0155, code lost:
        r10 = r0;
        r13 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x015d, code lost:
        r6 = android.os.SystemClock.elapsedRealtime();
        r2 = r13;
        r10 = -8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0164, code lost:
        com.tencent.open.b.g.a().a(r4, r6, 0, 0, -8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0175, code lost:
        throw r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0176, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0177, code lost:
        r10 = r0;
        r13 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0187, code lost:
        r13 = r2;
        r9 = r10;
        r10 = 0;
        r14 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0185, code lost:
        continue;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00ee A[ExcHandler: IOException (r0v6 'e' java.io.IOException A[CUSTOM_DECLARE]), Splitter:B:9:0x00b6] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0107 A[ExcHandler: MalformedURLException (r0v5 'e' java.net.MalformedURLException A[CUSTOM_DECLARE]), Splitter:B:9:0x00b6] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x011e A[ExcHandler: NetworkUnavailableException (r0v4 'e' com.tencent.open.utils.HttpUtils$NetworkUnavailableException A[CUSTOM_DECLARE]), Splitter:B:9:0x00b6] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0124 A[ExcHandler: HttpStatusException (r0v2 'e' com.tencent.open.utils.HttpUtils$HttpStatusException A[CUSTOM_DECLARE]), Splitter:B:9:0x00b6] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x015d  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0187 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0164 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.json.JSONObject upload(com.tencent.connect.auth.QQToken r17, android.content.Context r18, java.lang.String r19, android.os.Bundle r20) throws java.io.IOException, org.json.JSONException, com.tencent.open.utils.HttpUtils.NetworkUnavailableException, com.tencent.open.utils.HttpUtils.HttpStatusException {
        /*
            r1 = r18
            r2 = r19
            java.lang.String r3 = r19.toLowerCase()
            java.lang.String r4 = "http"
            boolean r3 = r3.startsWith(r4)
            if (r3 != 0) goto L_0x0046
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            com.tencent.open.utils.ServerSetting r4 = com.tencent.open.utils.ServerSetting.getInstance()
            java.lang.String r5 = "https://openmobile.qq.com/"
            java.lang.String r4 = r4.getEnvUrl(r1, r5)
            r3.append(r4)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            com.tencent.open.utils.ServerSetting r5 = com.tencent.open.utils.ServerSetting.getInstance()
            java.lang.String r6 = "https://openmobile.qq.com/"
            java.lang.String r5 = r5.getEnvUrl(r1, r6)
            r4.append(r5)
            r4.append(r2)
            java.lang.String r4 = r4.toString()
            r5 = r3
            r3 = r17
            goto L_0x004a
        L_0x0046:
            r3 = r17
            r4 = r2
            r5 = r4
        L_0x004a:
            a(r1, r3, r2)
            r2 = 0
            long r6 = android.os.SystemClock.elapsedRealtime()
            r8 = 0
            java.lang.String r9 = r17.getAppId()
            com.tencent.open.utils.OpenConfig r9 = com.tencent.open.utils.OpenConfig.getInstance(r1, r9)
            java.lang.String r10 = "Common_HttpRetryCount"
            int r9 = r9.getInt(r10)
            java.lang.String r10 = "OpenConfig_test"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "config 1:Common_HttpRetryCount            config_value:"
            r11.<init>(r12)
            r11.append(r9)
            java.lang.String r12 = "   appid:"
            r11.append(r12)
            java.lang.String r12 = r17.getAppId()
            r11.append(r12)
            java.lang.String r12 = "     url:"
            r11.append(r12)
            r11.append(r4)
            java.lang.String r11 = r11.toString()
            com.tencent.open.a.f.a(r10, r11)
            if (r9 != 0) goto L_0x008b
            r9 = 3
        L_0x008b:
            java.lang.String r10 = "OpenConfig_test"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "config 1:Common_HttpRetryCount            result_value:"
            r11.<init>(r12)
            r11.append(r9)
            java.lang.String r12 = "   appid:"
            r11.append(r12)
            java.lang.String r3 = r17.getAppId()
            r11.append(r3)
            java.lang.String r3 = "     url:"
            r11.append(r3)
            r11.append(r4)
            java.lang.String r3 = r11.toString()
            com.tencent.open.a.f.a(r10, r3)
        L_0x00b2:
            int r8 = r8 + 1
            r3 = r20
            com.tencent.open.utils.Util$Statistic r12 = com.tencent.open.utils.Util.upload(r1, r5, r3)     // Catch:{ ConnectTimeoutException -> 0x0176, SocketTimeoutException -> 0x0154, HttpStatusException -> 0x0124, NetworkUnavailableException -> 0x011e, MalformedURLException -> 0x0107, IOException -> 0x00ee, JSONException -> 0x00d7 }
            java.lang.String r13 = r12.response     // Catch:{ ConnectTimeoutException -> 0x0176, SocketTimeoutException -> 0x0154, HttpStatusException -> 0x0124, NetworkUnavailableException -> 0x011e, MalformedURLException -> 0x0107, IOException -> 0x00ee, JSONException -> 0x00d7 }
            org.json.JSONObject r13 = com.tencent.open.utils.Util.parseJson(r13)     // Catch:{ ConnectTimeoutException -> 0x0176, SocketTimeoutException -> 0x0154, HttpStatusException -> 0x0124, NetworkUnavailableException -> 0x011e, MalformedURLException -> 0x0107, IOException -> 0x00ee, JSONException -> 0x00d7 }
            java.lang.String r2 = "ret"
            int r2 = r13.getInt(r2)     // Catch:{ JSONException -> 0x00cf, ConnectTimeoutException -> 0x00cb, SocketTimeoutException -> 0x00c7, HttpStatusException -> 0x0124, NetworkUnavailableException -> 0x011e, MalformedURLException -> 0x0107, IOException -> 0x00ee }
            goto L_0x00d0
        L_0x00c7:
            r0 = move-exception
            r10 = r0
            goto L_0x0157
        L_0x00cb:
            r0 = move-exception
            r10 = r0
            goto L_0x0179
        L_0x00cf:
            r2 = -4
        L_0x00d0:
            long r14 = r12.reqSize     // Catch:{ ConnectTimeoutException -> 0x00cb, SocketTimeoutException -> 0x00c7, HttpStatusException -> 0x0124, NetworkUnavailableException -> 0x011e, MalformedURLException -> 0x0107, IOException -> 0x00ee, JSONException -> 0x00d7 }
            long r10 = r12.rspSize     // Catch:{ ConnectTimeoutException -> 0x00cb, SocketTimeoutException -> 0x00c7, HttpStatusException -> 0x0124, NetworkUnavailableException -> 0x011e, MalformedURLException -> 0x0107, IOException -> 0x00ee, JSONException -> 0x00d7 }
            r9 = r2
            goto L_0x018d
        L_0x00d7:
            r0 = move-exception
            r10 = r0
            r10.printStackTrace()
            com.tencent.open.b.g r1 = com.tencent.open.b.g.a()
            r8 = 0
            r11 = 0
            r13 = -4
            r2 = r4
            r3 = r6
            r5 = r8
            r7 = r11
            r9 = r13
            r1.a(r2, r3, r5, r7, r9)
            throw r10
        L_0x00ee:
            r0 = move-exception
            r10 = r0
            r10.printStackTrace()
            int r9 = getErrorCodeFromException(r10)
            com.tencent.open.b.g r1 = com.tencent.open.b.g.a()
            r11 = 0
            r13 = 0
            r2 = r4
            r3 = r6
            r5 = r11
            r7 = r13
            r1.a(r2, r3, r5, r7, r9)
            throw r10
        L_0x0107:
            r0 = move-exception
            r10 = r0
            r10.printStackTrace()
            com.tencent.open.b.g r1 = com.tencent.open.b.g.a()
            r8 = 0
            r11 = 0
            r13 = -3
            r2 = r4
            r3 = r6
            r5 = r8
            r7 = r11
            r9 = r13
            r1.a(r2, r3, r5, r7, r9)
            throw r10
        L_0x011e:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            throw r1
        L_0x0124:
            r0 = move-exception
            r10 = r0
            r10.printStackTrace()
            java.lang.String r1 = r10.getMessage()
            java.lang.String r2 = "http status code error:"
            java.lang.String r3 = ""
            java.lang.String r1 = r1.replace(r2, r3)     // Catch:{ Exception -> 0x013b }
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ Exception -> 0x013b }
            r9 = r1
            goto L_0x0144
        L_0x013b:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            r1 = -9
            r9 = -9
        L_0x0144:
            com.tencent.open.b.g r1 = com.tencent.open.b.g.a()
            r11 = 0
            r13 = 0
            r2 = r4
            r3 = r6
            r5 = r11
            r7 = r13
            r1.a(r2, r3, r5, r7, r9)
            throw r10
        L_0x0154:
            r0 = move-exception
            r10 = r0
            r13 = r2
        L_0x0157:
            r10.printStackTrace()
            r2 = -8
            if (r8 >= r9) goto L_0x0164
            long r6 = android.os.SystemClock.elapsedRealtime()
            r2 = r13
            r10 = -8
            goto L_0x0185
        L_0x0164:
            com.tencent.open.b.g r1 = com.tencent.open.b.g.a()
            r8 = 0
            r11 = 0
            r13 = -8
            r2 = r4
            r3 = r6
            r5 = r8
            r7 = r11
            r9 = r13
            r1.a(r2, r3, r5, r7, r9)
            throw r10
        L_0x0176:
            r0 = move-exception
            r10 = r0
            r13 = r2
        L_0x0179:
            r10.printStackTrace()
            r2 = -7
            if (r8 >= r9) goto L_0x0199
            long r6 = android.os.SystemClock.elapsedRealtime()
            r2 = r13
            r10 = -7
        L_0x0185:
            if (r8 < r9) goto L_0x00b2
            r13 = r2
            r9 = r10
            r10 = 0
            r14 = 0
        L_0x018d:
            com.tencent.open.b.g r1 = com.tencent.open.b.g.a()
            r2 = r4
            r3 = r6
            r5 = r14
            r7 = r10
            r1.a(r2, r3, r5, r7, r9)
            return r13
        L_0x0199:
            com.tencent.open.b.g r1 = com.tencent.open.b.g.a()
            r8 = 0
            r11 = 0
            r13 = -7
            r2 = r4
            r3 = r6
            r5 = r8
            r7 = r11
            r9 = r13
            r1.a(r2, r3, r5, r7, r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.utils.HttpUtils.upload(com.tencent.connect.auth.QQToken, android.content.Context, java.lang.String, android.os.Bundle):org.json.JSONObject");
    }
}
