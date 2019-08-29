package com.autonavi.link.protocol.http;

import android.text.TextUtils;
import com.autonavi.link.connect.a.b;
import com.autonavi.link.transmit.proxy.LinkProxy;
import com.autonavi.link.utils.Logger;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URLEncoder;
import java.util.Map;

public class HttpClientManager {
    private static final String a = "HttpClientManager";
    private final String b;
    private HttpURLConnection c;
    private DataOutputStream d;

    public HttpClientManager() {
        StringBuilder sb = new StringBuilder("===");
        sb.append(System.currentTimeMillis());
        sb.append("===");
        this.b = sb.toString();
    }

    private Proxy a() {
        return new Proxy(Type.HTTP, new InetSocketAddress("127.0.0.1", LinkProxy.getInstance().getProxyPort()));
    }

    private String a(String str, String str2, Map<String, String> map, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(AjxHttpLoader.DOMAIN_HTTP);
        sb.append(str);
        sb.append(str2);
        sb.append("?");
        if (z) {
            sb.append("connectionId=");
            sb.append(LinkProxy.getInstance().getConnectId());
            sb.append("&");
        } else {
            sb.append("connectionId=");
            sb.append(b.a().a(str));
            sb.append("&");
        }
        if (map != null && map.size() > 0) {
            int i = 0;
            for (String next : map.keySet()) {
                i++;
                String str3 = map.get(next);
                if (!TextUtils.isEmpty(str3)) {
                    try {
                        sb.append(next);
                        sb.append("=");
                        sb.append(URLEncoder.encode(str3, "UTF-8"));
                        if (i < map.size()) {
                            sb.append("&");
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sb.toString();
    }

    private long a(InputStream inputStream, long j) {
        byte[] bArr = new byte[2048];
        if (j <= 0) {
            return 0;
        }
        long j2 = j;
        int i = 0;
        while (j2 > 0) {
            try {
                i = inputStream.read(bArr, 0, (int) Math.min(2048, j2));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (i < 0) {
                break;
            }
            j2 -= (long) i;
        }
        return j - j2;
    }

    public byte[] postFiles(String str, String str2, Map<String, String> map, Map<String[], Long> map2, HttpProgresser httpProgresser) throws Exception {
        Proxy proxy = null;
        if (map2 == null) {
            return null;
        }
        if (str.contains("127.0.0.1")) {
            proxy = a();
        }
        return a(a(str, str2, map, proxy != null), proxy, map2, httpProgresser);
    }

    /* JADX WARNING: Removed duplicated region for block: B:56:0x02f7 A[SYNTHETIC, Splitter:B:56:0x02f7] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0303 A[Catch:{ Exception -> 0x0309 }] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0318  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0343  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0385 A[SYNTHETIC, Splitter:B:84:0x0385] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0391 A[Catch:{ Exception -> 0x0397 }] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:53:0x02f2=Splitter:B:53:0x02f2, B:78:0x0356=Splitter:B:78:0x0356} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private byte[] a(java.lang.String r27, java.net.Proxy r28, java.util.Map<java.lang.String[], java.lang.Long> r29, com.autonavi.link.protocol.http.HttpProgresser r30) throws java.lang.Exception {
        /*
            r26 = this;
            r1 = r26
            r2 = r28
            java.util.Set r3 = r29.entrySet()
            java.util.Iterator r3 = r3.iterator()
            r6 = 0
        L_0x000e:
            boolean r8 = r3.hasNext()
            r9 = 1
            r10 = 0
            if (r8 == 0) goto L_0x008b
            java.lang.Object r8 = r3.next()
            java.util.Map$Entry r8 = (java.util.Map.Entry) r8
            java.lang.Object r11 = r8.getKey()
            java.lang.String[] r11 = (java.lang.String[]) r11
            if (r11 == 0) goto L_0x000e
            int r12 = r11.length
            if (r12 <= r9) goto L_0x000e
            r10 = r11[r10]
            r9 = r11[r9]
            java.io.File r11 = new java.io.File
            r11.<init>(r9)
            java.lang.String r9 = r11.getName()
            java.lang.Object r8 = r8.getValue()
            java.lang.Long r8 = (java.lang.Long) r8
            long r12 = r8.longValue()
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r14 = "--"
            r8.<init>(r14)
            java.lang.String r14 = r1.b
            r8.append(r14)
            java.lang.String r8 = r8.toString()
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            java.lang.String r15 = "Content-Disposition: form-data; name=\""
            r14.<init>(r15)
            r14.append(r10)
            java.lang.String r10 = "\"; filename=\""
            r14.append(r10)
            r14.append(r9)
            java.lang.String r9 = "\""
            r14.append(r9)
            java.lang.String r9 = r14.toString()
            java.lang.String r10 = "Content-Type: application/octet-stream"
            java.lang.String r14 = "Content-Transfer-Encoding: binary"
            int r8 = r8.length()
            int r9 = r9.length()
            int r8 = r8 + r9
            int r9 = r10.length()
            int r8 = r8 + r9
            int r9 = r14.length()
            int r8 = r8 + r9
            int r8 = r8 + 12
            long r8 = (long) r8
            long r10 = r11.length()
            long r8 = r8 + r10
            long r8 = r8 - r12
            long r6 = r6 + r8
            goto L_0x000e
        L_0x008b:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r8 = "--"
            r3.<init>(r8)
            java.lang.String r8 = r1.b
            r3.append(r8)
            java.lang.String r8 = "--"
            r3.append(r8)
            java.lang.String r3 = r3.toString()
            int r8 = r3.length()
            int r8 = r8 + 2
            long r11 = (long) r8
            long r6 = r6 + r11
            java.net.URL r8 = new java.net.URL
            r11 = r27
            r8.<init>(r11)
            if (r2 != 0) goto L_0x00ba
            java.net.URLConnection r2 = r8.openConnection()
            java.net.HttpURLConnection r2 = (java.net.HttpURLConnection) r2
            r1.c = r2
            goto L_0x00c2
        L_0x00ba:
            java.net.URLConnection r2 = r8.openConnection(r2)
            java.net.HttpURLConnection r2 = (java.net.HttpURLConnection) r2
            r1.c = r2
        L_0x00c2:
            java.net.HttpURLConnection r2 = r1.c
            r2.setUseCaches(r10)
            java.net.HttpURLConnection r2 = r1.c
            r2.setDoOutput(r9)
            java.net.HttpURLConnection r2 = r1.c
            r2.setDoInput(r9)
            java.net.HttpURLConnection r2 = r1.c
            java.lang.String r8 = "Content-Type"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "multipart/form-data; boundary="
            r11.<init>(r12)
            java.lang.String r12 = r1.b
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            r2.setRequestProperty(r8, r11)
            java.net.HttpURLConnection r2 = r1.c
            java.lang.String r8 = "User-Agent"
            java.lang.String r11 = "CodeJava Agent"
            r2.setRequestProperty(r8, r11)
            java.net.HttpURLConnection r2 = r1.c
            java.lang.String r8 = "Test"
            java.lang.String r11 = "Bonjour"
            r2.setRequestProperty(r8, r11)
            int r2 = android.os.Build.VERSION.SDK_INT
            r8 = 19
            if (r2 < r8) goto L_0x0106
            java.net.HttpURLConnection r2 = r1.c
            r2.setFixedLengthStreamingMode(r6)
            goto L_0x011b
        L_0x0106:
            java.net.HttpURLConnection r2 = r1.c
            java.lang.String r8 = "Content-Length"
            java.lang.String r11 = "%d"
            java.lang.Object[] r12 = new java.lang.Object[r9]
            java.lang.Long r13 = java.lang.Long.valueOf(r6)
            r12[r10] = r13
            java.lang.String r11 = java.lang.String.format(r11, r12)
            r2.setRequestProperty(r8, r11)
        L_0x011b:
            java.io.DataOutputStream r2 = new java.io.DataOutputStream
            java.net.HttpURLConnection r8 = r1.c
            java.io.OutputStream r8 = r8.getOutputStream()
            r2.<init>(r8)
            r1.d = r2
            long r11 = java.lang.System.currentTimeMillis()     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r13 = a     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r14.<init>()     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r15 = a     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r14.append(r15)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r15 = " HttpClientManager prepare to read-->may be total length--> "
            r14.append(r15)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r14.append(r6)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r6 = r14.toString()     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.Object[] r7 = new java.lang.Object[r10]     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            com.autonavi.link.utils.Logger.d(r13, r6, r7)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.util.Set r6 = r29.entrySet()     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.util.Iterator r6 = r6.iterator()     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r13 = 0
        L_0x0153:
            boolean r7 = r6.hasNext()     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            if (r7 == 0) goto L_0x02a0
            java.lang.Object r7 = r6.next()     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.util.Map$Entry r7 = (java.util.Map.Entry) r7     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.Object r15 = r7.getKey()     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String[] r15 = (java.lang.String[]) r15     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r8 = r15[r10]     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r15 = r15[r9]     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.io.File r9 = new java.io.File     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r9.<init>(r15)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r15 = r9.getName()     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.Object r7 = r7.getValue()     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.Long r7 = (java.lang.Long) r7     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r16 = r11
            long r10 = r7.longValue()     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r12 = "--"
            r7.<init>(r12)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r12 = r1.b     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r7.append(r12)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r7 = r7.toString()     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r2 = "Content-Disposition: form-data; name=\""
            r12.<init>(r2)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r12.append(r8)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r2 = "\"; filename=\""
            r12.append(r2)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r12.append(r15)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r2 = "\""
            r12.append(r2)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r2 = r12.toString()     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r8 = "Content-Type: application/octet-stream"
            java.lang.String r12 = "Content-Transfer-Encoding: binary"
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r4.<init>(r9)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r5 = 4096(0x1000, float:5.74E-42)
            byte[] r5 = new byte[r5]     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.io.DataOutputStream r9 = r1.d     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            r9.writeBytes(r7)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.io.DataOutputStream r7 = r1.d     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.lang.String r9 = "\r\n"
            r7.writeBytes(r9)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.io.DataOutputStream r7 = r1.d     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            r7.writeBytes(r2)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.io.DataOutputStream r2 = r1.d     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.lang.String r7 = "\r\n"
            r2.writeBytes(r7)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.io.DataOutputStream r2 = r1.d     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            r2.writeBytes(r8)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.io.DataOutputStream r2 = r1.d     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.lang.String r7 = "\r\n"
            r2.writeBytes(r7)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.io.DataOutputStream r2 = r1.d     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            r2.writeBytes(r12)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.io.DataOutputStream r2 = r1.d     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.lang.String r7 = "\r\n"
            r2.writeBytes(r7)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.io.DataOutputStream r2 = r1.d     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.lang.String r7 = "\r\n"
            r2.writeBytes(r7)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.io.DataOutputStream r2 = r1.d     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            r2.flush()     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            r7 = 0
            int r2 = (r10 > r7 ? 1 : (r10 == r7 ? 0 : -1))
            if (r2 <= 0) goto L_0x01fb
            r1.a(r4, r10)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
        L_0x01fb:
            long r9 = java.lang.System.currentTimeMillis()     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
        L_0x01ff:
            int r2 = r4.read(r5)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            r11 = -1
            if (r2 == r11) goto L_0x0245
            java.io.DataOutputStream r11 = r1.d     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            r12 = 0
            r11.write(r5, r12, r2)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            long r11 = (long) r2     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            long r11 = r11 + r13
            if (r30 == 0) goto L_0x0239
            int r2 = r2 + 0
            long r13 = (long) r2     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            double r7 = (double) r11     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            r18 = 4652218415073722368(0x4090000000000000, double:1024.0)
            long r20 = java.lang.System.currentTimeMillis()     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            r2 = 0
            r25 = r5
            r24 = r6
            long r5 = r20 - r9
            double r5 = (double) r5     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            double r5 = r5 * r18
            double r7 = r7 / r5
            r5 = 4652007308841189376(0x408f400000000000, double:1000.0)
            double r7 = r7 * r5
            float r2 = (float) r7     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            r18 = r30
            r19 = r13
            r21 = r11
            r23 = r2
            r18.onProgress(r19, r21, r23)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            goto L_0x023d
        L_0x0239:
            r25 = r5
            r24 = r6
        L_0x023d:
            r13 = r11
            r6 = r24
            r5 = r25
            r7 = 0
            goto L_0x01ff
        L_0x0245:
            r24 = r6
            java.lang.String r2 = a     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            r5.<init>()     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.lang.String r6 = a     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            r5.append(r6)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.lang.String r6 = " fileName....--> "
            r5.append(r6)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            r5.append(r15)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.lang.String r6 = " , time---> "
            r5.append(r6)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            r8 = 0
            long r6 = r6 - r16
            r5.append(r6)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.lang.String r6 = " , readNum--> "
            r5.append(r6)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            r5.append(r13)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.lang.String r5 = r5.toString()     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            r6 = 0
            java.lang.Object[] r7 = new java.lang.Object[r6]     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            com.autonavi.link.utils.Logger.d(r2, r5, r7)     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            r4.close()     // Catch:{ SocketException -> 0x029b, Exception -> 0x0297, all -> 0x0293 }
            java.io.DataOutputStream r2 = r1.d     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r4 = "\r\n"
            r2.writeBytes(r4)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.io.DataOutputStream r2 = r1.d     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r2.flush()     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r11 = r16
            r6 = r24
            r9 = 1
            r10 = 0
            goto L_0x0153
        L_0x0293:
            r0 = move-exception
            r2 = r0
            goto L_0x0383
        L_0x0297:
            r0 = move-exception
            r2 = r0
            r8 = r4
            goto L_0x02f2
        L_0x029b:
            r0 = move-exception
            r2 = r0
            r8 = r4
            goto L_0x0356
        L_0x02a0:
            r16 = r11
            java.lang.String r2 = a     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r4.<init>()     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r5 = a     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r4.append(r5)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r5 = " read end --> readNum--> "
            r4.append(r5)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r4.append(r13)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r5 = " ,time--> "
            r4.append(r5)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r7 = 0
            long r5 = r5 - r16
            r4.append(r5)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r4 = r4.toString()     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r5 = 0
            java.lang.Object[] r6 = new java.lang.Object[r5]     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            com.autonavi.link.utils.Logger.d(r2, r4, r6)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.io.DataOutputStream r2 = r1.d     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r2.flush()     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.io.DataOutputStream r2 = r1.d     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            r2.writeBytes(r3)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.io.DataOutputStream r2 = r1.d     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.lang.String r3 = "\r\n"
            r2.writeBytes(r3)     // Catch:{ SocketException -> 0x0353, Exception -> 0x02ef, all -> 0x02ea }
            java.io.DataOutputStream r2 = r1.d     // Catch:{ Exception -> 0x0309 }
            if (r2 == 0) goto L_0x030e
            java.io.DataOutputStream r2 = r1.d     // Catch:{ Exception -> 0x0309 }
            r2.close()     // Catch:{ Exception -> 0x0309 }
            goto L_0x030e
        L_0x02ea:
            r0 = move-exception
            r2 = r0
            r4 = 0
            goto L_0x0383
        L_0x02ef:
            r0 = move-exception
            r2 = r0
            r8 = 0
        L_0x02f2:
            r2.printStackTrace()     // Catch:{ all -> 0x0380 }
            if (r8 == 0) goto L_0x02ff
            r8.close()     // Catch:{ Exception -> 0x02fb }
            goto L_0x02ff
        L_0x02fb:
            r0 = move-exception
            r0.printStackTrace()
        L_0x02ff:
            java.io.DataOutputStream r2 = r1.d     // Catch:{ Exception -> 0x0309 }
            if (r2 == 0) goto L_0x030e
            java.io.DataOutputStream r2 = r1.d     // Catch:{ Exception -> 0x0309 }
            r2.close()     // Catch:{ Exception -> 0x0309 }
            goto L_0x030e
        L_0x0309:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x030e:
            java.net.HttpURLConnection r2 = r1.c
            int r2 = r2.getResponseCode()
            r3 = 200(0xc8, float:2.8E-43)
            if (r2 != r3) goto L_0x0343
            java.net.HttpURLConnection r2 = r1.c
            java.io.InputStream r2 = r2.getInputStream()
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream
            r3.<init>()
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r4 = new byte[r4]
        L_0x0327:
            int r5 = r2.read(r4)
            r6 = -1
            if (r5 == r6) goto L_0x0333
            r7 = 0
            r3.write(r4, r7, r5)
            goto L_0x0327
        L_0x0333:
            byte[] r4 = r3.toByteArray()
            r2.close()
            r3.close()
            java.net.HttpURLConnection r2 = r1.c
            r2.disconnect()
            return r4
        L_0x0343:
            java.io.IOException r3 = new java.io.IOException
            java.lang.String r4 = "Server returned non-OK status: "
            java.lang.String r2 = java.lang.String.valueOf(r2)
            java.lang.String r2 = r4.concat(r2)
            r3.<init>(r2)
            throw r3
        L_0x0353:
            r0 = move-exception
            r2 = r0
            r8 = 0
        L_0x0356:
            r2.printStackTrace()     // Catch:{ all -> 0x0380 }
            java.lang.String r3 = a     // Catch:{ all -> 0x0380 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0380 }
            r4.<init>()     // Catch:{ all -> 0x0380 }
            java.lang.String r5 = a     // Catch:{ all -> 0x0380 }
            r4.append(r5)     // Catch:{ all -> 0x0380 }
            java.lang.String r5 = "SocketException----> use cancel--> "
            r4.append(r5)     // Catch:{ all -> 0x0380 }
            r4.append(r2)     // Catch:{ all -> 0x0380 }
            java.lang.String r2 = r4.toString()     // Catch:{ all -> 0x0380 }
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0380 }
            com.autonavi.link.utils.Logger.d(r3, r2, r4)     // Catch:{ all -> 0x0380 }
            java.lang.Exception r2 = new java.lang.Exception     // Catch:{ all -> 0x0380 }
            java.lang.String r3 = "use cancel"
            r2.<init>(r3)     // Catch:{ all -> 0x0380 }
            throw r2     // Catch:{ all -> 0x0380 }
        L_0x0380:
            r0 = move-exception
            r2 = r0
            r4 = r8
        L_0x0383:
            if (r4 == 0) goto L_0x038d
            r4.close()     // Catch:{ Exception -> 0x0389 }
            goto L_0x038d
        L_0x0389:
            r0 = move-exception
            r0.printStackTrace()
        L_0x038d:
            java.io.DataOutputStream r3 = r1.d     // Catch:{ Exception -> 0x0397 }
            if (r3 == 0) goto L_0x039c
            java.io.DataOutputStream r3 = r1.d     // Catch:{ Exception -> 0x0397 }
            r3.close()     // Catch:{ Exception -> 0x0397 }
            goto L_0x039c
        L_0x0397:
            r0 = move-exception
            r3 = r0
            r3.printStackTrace()
        L_0x039c:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.link.protocol.http.HttpClientManager.a(java.lang.String, java.net.Proxy, java.util.Map, com.autonavi.link.protocol.http.HttpProgresser):byte[]");
    }

    public synchronized void cancel() {
        String str = a;
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append("do cancel----> ");
        Logger.d(str, sb.toString(), new Object[0]);
        try {
            if (this.d != null) {
                String str2 = a;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(a);
                sb2.append("do cancel----> mDataOutputStream.flush()");
                Logger.d(str2, sb2.toString(), new Object[0]);
                this.d.close();
            }
        } catch (Exception e) {
            String str3 = a;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a);
            sb3.append("mDataOutputStream.flush()---> ");
            sb3.append(e);
            Logger.d(str3, sb3.toString(), new Object[0]);
            e.printStackTrace();
        }
        try {
            if (this.c != null) {
                String str4 = a;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(a);
                sb4.append("do cancel----> disconnect");
                Logger.d(str4, sb4.toString(), new Object[0]);
                this.c.disconnect();
            }
        } catch (Exception e2) {
            String str5 = a;
            StringBuilder sb5 = new StringBuilder();
            sb5.append(a);
            sb5.append("do cancel----> disconnect--> ");
            sb5.append(e2);
            Logger.d(str5, sb5.toString(), new Object[0]);
            e2.printStackTrace();
            return;
        }
        return;
    }
}
