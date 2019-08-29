package com.alipay.sdk.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.util.Constants;
import java.net.URL;
import org.apache.http.HttpHost;

public final class a {
    public static final String a = "application/octet-stream;binary/octet-stream";
    public String b;
    private Context c;

    private a(Context context) {
        this(context, null);
    }

    public a(Context context, String str) {
        if (context != null) {
            this.c = context.getApplicationContext();
        } else {
            this.c = context;
        }
        this.b = str;
    }

    private void a(String str) {
        this.b = str;
    }

    private String a() {
        return this.b;
    }

    private URL b() {
        try {
            return new URL(this.b);
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0080 A[Catch:{ Throwable -> 0x0104, Throwable -> 0x0114 }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0087 A[Catch:{ Throwable -> 0x0104, Throwable -> 0x0114 }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00c2 A[Catch:{ Throwable -> 0x0104, Throwable -> 0x0114 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final org.apache.http.HttpResponse a(byte[] r7, java.util.List<org.apache.http.Header> r8) throws java.lang.Throwable {
        /*
            r6 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "requestUrl : "
            r0.<init>(r1)
            java.lang.String r1 = r6.b
            r0.append(r1)
            com.alipay.sdk.net.b r0 = com.alipay.sdk.net.b.a()
            r1 = 0
            if (r0 != 0) goto L_0x0015
            return r1
        L_0x0015:
            org.apache.http.impl.client.DefaultHttpClient r2 = r0.c     // Catch:{ Throwable -> 0x0104 }
            org.apache.http.params.HttpParams r2 = r2.getParams()     // Catch:{ Throwable -> 0x0104 }
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0104 }
            r4 = 11
            if (r3 < r4) goto L_0x005d
            java.lang.String r3 = r6.g()     // Catch:{ Throwable -> 0x0104 }
            if (r3 == 0) goto L_0x0032
            java.lang.String r4 = "wap"
            boolean r3 = r3.contains(r4)     // Catch:{ Throwable -> 0x0104 }
            if (r3 != 0) goto L_0x0032
        L_0x0030:
            r5 = r1
            goto L_0x007e
        L_0x0032:
            java.net.URL r3 = r6.b()     // Catch:{ Throwable -> 0x0104 }
            if (r3 == 0) goto L_0x0030
            java.lang.String r3 = r3.getProtocol()     // Catch:{ Throwable -> 0x0104 }
            java.lang.String r4 = "https"
            r4.equalsIgnoreCase(r3)     // Catch:{ Throwable -> 0x0104 }
            java.lang.String r3 = "https.proxyHost"
            java.lang.String r3 = java.lang.System.getProperty(r3)     // Catch:{ Throwable -> 0x0104 }
            java.lang.String r4 = "https.proxyPort"
            java.lang.String r4 = java.lang.System.getProperty(r4)     // Catch:{ Throwable -> 0x0104 }
            boolean r5 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x0104 }
            if (r5 != 0) goto L_0x0030
            org.apache.http.HttpHost r5 = new org.apache.http.HttpHost     // Catch:{ Throwable -> 0x0104 }
            int r4 = java.lang.Integer.parseInt(r4)     // Catch:{ Throwable -> 0x0104 }
            r5.<init>(r3, r4)     // Catch:{ Throwable -> 0x0104 }
            goto L_0x007e
        L_0x005d:
            android.net.NetworkInfo r3 = r6.f()     // Catch:{ Throwable -> 0x0104 }
            if (r3 == 0) goto L_0x0030
            boolean r4 = r3.isAvailable()     // Catch:{ Throwable -> 0x0104 }
            if (r4 == 0) goto L_0x0030
            int r3 = r3.getType()     // Catch:{ Throwable -> 0x0104 }
            if (r3 != 0) goto L_0x0030
            java.lang.String r3 = android.net.Proxy.getDefaultHost()     // Catch:{ Throwable -> 0x0104 }
            int r4 = android.net.Proxy.getDefaultPort()     // Catch:{ Throwable -> 0x0104 }
            if (r3 == 0) goto L_0x0030
            org.apache.http.HttpHost r5 = new org.apache.http.HttpHost     // Catch:{ Throwable -> 0x0104 }
            r5.<init>(r3, r4)     // Catch:{ Throwable -> 0x0104 }
        L_0x007e:
            if (r5 == 0) goto L_0x0085
            java.lang.String r3 = "http.route.default-proxy"
            r2.setParameter(r3, r5)     // Catch:{ Throwable -> 0x0104 }
        L_0x0085:
            if (r7 == 0) goto L_0x00b9
            int r2 = r7.length     // Catch:{ Throwable -> 0x0104 }
            if (r2 != 0) goto L_0x008b
            goto L_0x00b9
        L_0x008b:
            org.apache.http.client.methods.HttpPost r2 = new org.apache.http.client.methods.HttpPost     // Catch:{ Throwable -> 0x0104 }
            java.lang.String r3 = r6.b     // Catch:{ Throwable -> 0x0104 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0104 }
            org.apache.http.entity.ByteArrayEntity r3 = new org.apache.http.entity.ByteArrayEntity     // Catch:{ Throwable -> 0x0104 }
            r3.<init>(r7)     // Catch:{ Throwable -> 0x0104 }
            java.lang.String r7 = "application/octet-stream;binary/octet-stream"
            r3.setContentType(r7)     // Catch:{ Throwable -> 0x0104 }
            r7 = r2
            org.apache.http.client.methods.HttpPost r7 = (org.apache.http.client.methods.HttpPost) r7     // Catch:{ Throwable -> 0x0104 }
            r7.setEntity(r3)     // Catch:{ Throwable -> 0x0104 }
            java.lang.String r7 = "Accept-Charset"
            java.lang.String r3 = "UTF-8"
            r2.addHeader(r7, r3)     // Catch:{ Throwable -> 0x0104 }
            java.lang.String r7 = "Connection"
            java.lang.String r3 = "Keep-Alive"
            r2.addHeader(r7, r3)     // Catch:{ Throwable -> 0x0104 }
            java.lang.String r7 = "Keep-Alive"
            java.lang.String r3 = "timeout=180, max=100"
            r2.addHeader(r7, r3)     // Catch:{ Throwable -> 0x0104 }
            goto L_0x00c0
        L_0x00b9:
            org.apache.http.client.methods.HttpGet r2 = new org.apache.http.client.methods.HttpGet     // Catch:{ Throwable -> 0x0104 }
            java.lang.String r7 = r6.b     // Catch:{ Throwable -> 0x0104 }
            r2.<init>(r7)     // Catch:{ Throwable -> 0x0104 }
        L_0x00c0:
            if (r8 == 0) goto L_0x00d6
            java.util.Iterator r7 = r8.iterator()     // Catch:{ Throwable -> 0x0104 }
        L_0x00c6:
            boolean r8 = r7.hasNext()     // Catch:{ Throwable -> 0x0104 }
            if (r8 == 0) goto L_0x00d6
            java.lang.Object r8 = r7.next()     // Catch:{ Throwable -> 0x0104 }
            org.apache.http.Header r8 = (org.apache.http.Header) r8     // Catch:{ Throwable -> 0x0104 }
            r2.addHeader(r8)     // Catch:{ Throwable -> 0x0104 }
            goto L_0x00c6
        L_0x00d6:
            org.apache.http.HttpResponse r7 = r0.a(r2)     // Catch:{ Throwable -> 0x0104 }
            java.lang.String r8 = "X-Hostname"
            org.apache.http.Header[] r8 = r7.getHeaders(r8)     // Catch:{ Throwable -> 0x0104 }
            r2 = 0
            if (r8 == 0) goto L_0x00ef
            int r3 = r8.length     // Catch:{ Throwable -> 0x0104 }
            if (r3 <= 0) goto L_0x00ef
            r8 = r8[r2]     // Catch:{ Throwable -> 0x0104 }
            if (r8 == 0) goto L_0x00ef
            java.lang.String r8 = "X-Hostname"
            r7.getHeaders(r8)     // Catch:{ Throwable -> 0x0104 }
        L_0x00ef:
            java.lang.String r8 = "X-ExecuteTime"
            org.apache.http.Header[] r8 = r7.getHeaders(r8)     // Catch:{ Throwable -> 0x0104 }
            if (r8 == 0) goto L_0x0103
            int r3 = r8.length     // Catch:{ Throwable -> 0x0104 }
            if (r3 <= 0) goto L_0x0103
            r8 = r8[r2]     // Catch:{ Throwable -> 0x0104 }
            if (r8 == 0) goto L_0x0103
            java.lang.String r8 = "X-ExecuteTime"
            r7.getHeaders(r8)     // Catch:{ Throwable -> 0x0104 }
        L_0x0103:
            return r7
        L_0x0104:
            r7 = move-exception
            if (r0 == 0) goto L_0x0114
            org.apache.http.impl.client.DefaultHttpClient r8 = r0.c     // Catch:{ Throwable -> 0x0114 }
            org.apache.http.conn.ClientConnectionManager r8 = r8.getConnectionManager()     // Catch:{ Throwable -> 0x0114 }
            if (r8 == 0) goto L_0x0114
            r8.shutdown()     // Catch:{ Throwable -> 0x0114 }
            com.alipay.sdk.net.b.b = r1     // Catch:{ Throwable -> 0x0114 }
        L_0x0114:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.net.a.a(byte[], java.util.List):org.apache.http.HttpResponse");
    }

    private HttpHost c() {
        HttpHost httpHost = null;
        if (VERSION.SDK_INT >= 11) {
            String g = g();
            if (g != null && !g.contains("wap")) {
                return null;
            }
            URL b2 = b();
            if (b2 != null) {
                "https".equalsIgnoreCase(b2.getProtocol());
                String property = System.getProperty("https.proxyHost");
                String property2 = System.getProperty("https.proxyPort");
                if (!TextUtils.isEmpty(property)) {
                    httpHost = new HttpHost(property, Integer.parseInt(property2));
                }
            }
            return httpHost;
        }
        NetworkInfo f = f();
        if (f != null && f.isAvailable() && f.getType() == 0) {
            String defaultHost = Proxy.getDefaultHost();
            int defaultPort = Proxy.getDefaultPort();
            if (defaultHost != null) {
                httpHost = new HttpHost(defaultHost, defaultPort);
            }
        }
        return httpHost;
    }

    private HttpHost d() {
        NetworkInfo f = f();
        if (f != null && f.isAvailable() && f.getType() == 0) {
            String defaultHost = Proxy.getDefaultHost();
            int defaultPort = Proxy.getDefaultPort();
            if (defaultHost != null) {
                return new HttpHost(defaultHost, defaultPort);
            }
        }
        return null;
    }

    private HttpHost e() {
        String g = g();
        HttpHost httpHost = null;
        if (g != null && !g.contains("wap")) {
            return null;
        }
        URL b2 = b();
        if (b2 != null) {
            "https".equalsIgnoreCase(b2.getProtocol());
            String property = System.getProperty("https.proxyHost");
            String property2 = System.getProperty("https.proxyPort");
            if (!TextUtils.isEmpty(property)) {
                httpHost = new HttpHost(property, Integer.parseInt(property2));
            }
        }
        return httpHost;
    }

    private NetworkInfo f() {
        try {
            return ((ConnectivityManager) this.c.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception unused) {
            return null;
        }
    }

    private String g() {
        try {
            NetworkInfo f = f();
            if (f == null || !f.isAvailable()) {
                return Constants.ANIMATOR_NONE;
            }
            if (f.getType() == 1) {
                return "wifi";
            }
            return f.getExtraInfo().toLowerCase();
        } catch (Exception unused) {
            return Constants.ANIMATOR_NONE;
        }
    }
}
