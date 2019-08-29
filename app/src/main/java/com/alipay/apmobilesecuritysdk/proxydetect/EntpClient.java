package com.alipay.apmobilesecuritysdk.proxydetect;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.storage.SettingsStorage;
import com.alipay.rdssecuritysdk.constant.CONST;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class EntpClient {
    private static TraceLogger a = LoggerFactory.f();
    private static String b = "";

    public static void a(Context context, final String str, final String str2) {
        if (context != null) {
            try {
                String d = SettingsStorage.d(context);
                a.b((String) CONST.LOG_TAG, "agent switch = ".concat(String.valueOf(d)));
                if (d != null) {
                    if (d.equals("1") || d.equals("2")) {
                        if ("1".equals(d)) {
                            b = "https://entphz.alipay.com/postToken.json";
                        } else {
                            b = "https://entpsz.alipay.com/postToken.json";
                        }
                        final String packageName = context.getPackageName();
                        TraceLogger traceLogger = a;
                        StringBuilder sb = new StringBuilder("package name = ");
                        sb.append(packageName);
                        sb.append(" , apdidToken = ");
                        sb.append(str);
                        sb.append(" , clientKey = ");
                        sb.append(str2);
                        traceLogger.b((String) CONST.LOG_TAG, sb.toString());
                        TraceLogger traceLogger2 = a;
                        StringBuilder sb2 = new StringBuilder("agent url = ");
                        sb2.append(b(str, packageName, str2));
                        traceLogger2.b((String) CONST.LOG_TAG, sb2.toString());
                        if (!CommonUtils.isBlank(str) || !CommonUtils.isBlank(str2)) {
                            new Thread(new Runnable() {
                                public final void run() {
                                    EntpClient.a(str, packageName, str2);
                                }
                            }).start();
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
    }

    private static String b(String str, String str2, String str3) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append(b);
        sb.append("?tk=");
        sb.append(URLEncoder.encode(str, "utf-8"));
        sb.append("&ck=");
        sb.append(URLEncoder.encode(str3, "utf-8"));
        sb.append("&app=");
        sb.append(str2);
        sb.append("&t=");
        sb.append(System.currentTimeMillis());
        return sb.toString();
    }

    private static HttpClient a(HttpParams httpParams) {
        try {
            HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams, schemeRegistry), httpParams);
            a.b((String) CONST.LOG_TAG, (String) "send agent request, create https socket success.");
            return defaultHttpClient;
        } catch (Exception unused) {
            DefaultHttpClient defaultHttpClient2 = new DefaultHttpClient(httpParams);
            a.b((String) CONST.LOG_TAG, (String) "send agent request, create https socket error.create default socket.");
            return defaultHttpClient2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x005e A[SYNTHETIC, Splitter:B:16:0x005e] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0073 A[SYNTHETIC, Splitter:B:24:0x0073] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(java.lang.String r7, java.lang.String r8, java.lang.String r9) {
        /*
            r0 = 0
            org.apache.http.params.BasicHttpParams r1 = new org.apache.http.params.BasicHttpParams     // Catch:{ Exception -> 0x0071, all -> 0x005b }
            r1.<init>()     // Catch:{ Exception -> 0x0071, all -> 0x005b }
            java.lang.String r2 = "http.connection.timeout"
            r3 = 30000(0x7530, float:4.2039E-41)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)     // Catch:{ Exception -> 0x0071, all -> 0x005b }
            r1.setParameter(r2, r4)     // Catch:{ Exception -> 0x0071, all -> 0x005b }
            java.lang.String r2 = "http.socket.timeout"
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Exception -> 0x0071, all -> 0x005b }
            r1.setParameter(r2, r3)     // Catch:{ Exception -> 0x0071, all -> 0x005b }
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r2 = a     // Catch:{ Exception -> 0x0071, all -> 0x005b }
            java.lang.String r3 = "APSecuritySdk"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0071, all -> 0x005b }
            java.lang.String r5 = "agent request start time = "
            r4.<init>(r5)     // Catch:{ Exception -> 0x0071, all -> 0x005b }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0071, all -> 0x005b }
            r4.append(r5)     // Catch:{ Exception -> 0x0071, all -> 0x005b }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0071, all -> 0x005b }
            r2.b(r3, r4)     // Catch:{ Exception -> 0x0071, all -> 0x005b }
            org.apache.http.client.HttpClient r1 = a(r1)     // Catch:{ Exception -> 0x0071, all -> 0x005b }
            org.apache.http.client.methods.HttpGet r0 = new org.apache.http.client.methods.HttpGet     // Catch:{ Exception -> 0x0059, all -> 0x0056 }
            java.lang.String r7 = b(r7, r8, r9)     // Catch:{ Exception -> 0x0059, all -> 0x0056 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x0059, all -> 0x0056 }
            r1.execute(r0)     // Catch:{ Exception -> 0x0059, all -> 0x0056 }
            org.apache.http.conn.ClientConnectionManager r7 = r1.getConnectionManager()     // Catch:{ Exception -> 0x0081 }
            if (r7 == 0) goto L_0x0081
            r7.shutdown()     // Catch:{ Exception -> 0x0081 }
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r7 = a     // Catch:{ Exception -> 0x0081 }
            java.lang.String r8 = "APSecuritySdk"
        L_0x0050:
            java.lang.String r9 = "httpclient closed successfully."
            r7.b(r8, r9)     // Catch:{ Exception -> 0x0081 }
            goto L_0x0081
        L_0x0056:
            r7 = move-exception
            r0 = r1
            goto L_0x005c
        L_0x0059:
            r0 = r1
            goto L_0x0071
        L_0x005b:
            r7 = move-exception
        L_0x005c:
            if (r0 == 0) goto L_0x0070
            org.apache.http.conn.ClientConnectionManager r8 = r0.getConnectionManager()     // Catch:{ Exception -> 0x0070 }
            if (r8 == 0) goto L_0x0070
            r8.shutdown()     // Catch:{ Exception -> 0x0070 }
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r8 = a     // Catch:{ Exception -> 0x0070 }
            java.lang.String r9 = "APSecuritySdk"
            java.lang.String r0 = "httpclient closed successfully."
            r8.b(r9, r0)     // Catch:{ Exception -> 0x0070 }
        L_0x0070:
            throw r7
        L_0x0071:
            if (r0 == 0) goto L_0x0081
            org.apache.http.conn.ClientConnectionManager r7 = r0.getConnectionManager()     // Catch:{ Exception -> 0x0081 }
            if (r7 == 0) goto L_0x0081
            r7.shutdown()     // Catch:{ Exception -> 0x0081 }
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r7 = a     // Catch:{ Exception -> 0x0081 }
            java.lang.String r8 = "APSecuritySdk"
            goto L_0x0050
        L_0x0081:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r7 = a
            java.lang.String r8 = "APSecuritySdk"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r0 = "agent request end time = "
            r9.<init>(r0)
            long r0 = java.lang.System.currentTimeMillis()
            r9.append(r0)
            java.lang.String r9 = r9.toString()
            r7.b(r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.apmobilesecuritysdk.proxydetect.EntpClient.a(java.lang.String, java.lang.String, java.lang.String):void");
    }
}
