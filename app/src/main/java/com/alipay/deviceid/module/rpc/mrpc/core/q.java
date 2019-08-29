package com.alipay.deviceid.module.rpc.mrpc.core;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.CookieManager;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Callable;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public final class q implements Callable<u> {
    private static final HttpRequestRetryHandler e = new ad();
    protected l a;
    protected Context b;
    protected o c;
    String d;
    private HttpUriRequest f;
    private HttpContext g = new BasicHttpContext();
    private CookieStore h = new BasicCookieStore();
    private CookieManager i;
    private AbstractHttpEntity j;
    private HttpHost k;
    private URL l;
    private int m = 0;
    private boolean n = false;
    private boolean o = false;
    private String p = null;
    private String q;

    public q(l lVar, o oVar) {
        this.a = lVar;
        this.b = this.a.a;
        this.c = oVar;
    }

    private static long a(String[] strArr) {
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if ("max-age".equalsIgnoreCase(strArr[i2])) {
                int i3 = i2 + 1;
                if (strArr[i3] != null) {
                    try {
                        return Long.parseLong(strArr[i3]);
                    } catch (Exception unused) {
                    }
                } else {
                    continue;
                }
            }
        }
        return 0;
    }

    private static HttpUrlHeader a(HttpResponse httpResponse) {
        Header[] allHeaders;
        HttpUrlHeader httpUrlHeader = new HttpUrlHeader();
        for (Header header : httpResponse.getAllHeaders()) {
            httpUrlHeader.setHead(header.getName(), header.getValue());
        }
        return httpUrlHeader;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00c3 A[SYNTHETIC, Splitter:B:24:0x00c3] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.alipay.deviceid.module.rpc.mrpc.core.u a(org.apache.http.HttpResponse r10, int r11, java.lang.String r12) {
        /*
            r9 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "开始handle，handleResponse-1,"
            r0.<init>(r1)
            java.lang.Thread r1 = java.lang.Thread.currentThread()
            long r1 = r1.getId()
            r0.append(r1)
            org.apache.http.HttpEntity r0 = r10.getEntity()
            r1 = 0
            if (r0 == 0) goto L_0x00d5
            org.apache.http.StatusLine r2 = r10.getStatusLine()
            int r2 = r2.getStatusCode()
            r3 = 200(0xc8, float:2.8E-43)
            if (r2 != r3) goto L_0x00d5
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "200，开始处理，handleResponse-2,threadid = "
            r2.<init>(r3)
            java.lang.Thread r3 = java.lang.Thread.currentThread()
            long r3 = r3.getId()
            r2.append(r3)
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x00bf }
            r2.<init>()     // Catch:{ all -> 0x00bf }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00bd }
            r9.a(r0, r2)     // Catch:{ all -> 0x00bd }
            byte[] r0 = r2.toByteArray()     // Catch:{ all -> 0x00bd }
            r5 = 0
            r9.o = r5     // Catch:{ all -> 0x00bd }
            com.alipay.deviceid.module.rpc.mrpc.core.l r5 = r9.a     // Catch:{ all -> 0x00bd }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00bd }
            r8 = 0
            long r6 = r6 - r3
            r5.c(r6)     // Catch:{ all -> 0x00bd }
            com.alipay.deviceid.module.rpc.mrpc.core.l r3 = r9.a     // Catch:{ all -> 0x00bd }
            int r4 = r0.length     // Catch:{ all -> 0x00bd }
            long r4 = (long) r4     // Catch:{ all -> 0x00bd }
            r3.a(r4)     // Catch:{ all -> 0x00bd }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bd }
            java.lang.String r4 = "res:"
            r3.<init>(r4)     // Catch:{ all -> 0x00bd }
            int r4 = r0.length     // Catch:{ all -> 0x00bd }
            r3.append(r4)     // Catch:{ all -> 0x00bd }
            com.alipay.deviceid.module.rpc.mrpc.core.p r3 = new com.alipay.deviceid.module.rpc.mrpc.core.p     // Catch:{ all -> 0x00bd }
            com.alipay.deviceid.module.rpc.mrpc.core.HttpUrlHeader r4 = a(r10)     // Catch:{ all -> 0x00bd }
            r3.<init>(r4, r11, r12, r0)     // Catch:{ all -> 0x00bd }
            long r11 = b(r10)     // Catch:{ all -> 0x00bd }
            org.apache.http.HttpEntity r10 = r10.getEntity()     // Catch:{ all -> 0x00bd }
            org.apache.http.Header r10 = r10.getContentType()     // Catch:{ all -> 0x00bd }
            if (r10 == 0) goto L_0x009a
            java.lang.String r10 = r10.getValue()     // Catch:{ all -> 0x00bd }
            java.util.HashMap r10 = a(r10)     // Catch:{ all -> 0x00bd }
            java.lang.String r0 = "charset"
            java.lang.Object r0 = r10.get(r0)     // Catch:{ all -> 0x00bd }
            r1 = r0
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x00bd }
            java.lang.String r0 = "Content-Type"
            java.lang.Object r10 = r10.get(r0)     // Catch:{ all -> 0x00bd }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ all -> 0x00bd }
            goto L_0x009b
        L_0x009a:
            r10 = r1
        L_0x009b:
            r3.b(r10)     // Catch:{ all -> 0x00bd }
            r3.a(r1)     // Catch:{ all -> 0x00bd }
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00bd }
            r3.a(r0)     // Catch:{ all -> 0x00bd }
            r3.b(r11)     // Catch:{ all -> 0x00bd }
            r2.close()     // Catch:{ IOException -> 0x00b0 }
            r1 = r3
            return r1
        L_0x00b0:
            r10 = move-exception
            java.lang.RuntimeException r11 = new java.lang.RuntimeException
            java.lang.String r12 = "ArrayOutputStream close error!"
            java.lang.Throwable r10 = r10.getCause()
            r11.<init>(r12, r10)
            throw r11
        L_0x00bd:
            r10 = move-exception
            goto L_0x00c1
        L_0x00bf:
            r10 = move-exception
            r2 = r1
        L_0x00c1:
            if (r2 == 0) goto L_0x00d4
            r2.close()     // Catch:{ IOException -> 0x00c7 }
            goto L_0x00d4
        L_0x00c7:
            r10 = move-exception
            java.lang.RuntimeException r11 = new java.lang.RuntimeException
            java.lang.String r12 = "ArrayOutputStream close error!"
            java.lang.Throwable r10 = r10.getCause()
            r11.<init>(r12, r10)
            throw r11
        L_0x00d4:
            throw r10
        L_0x00d5:
            if (r0 != 0) goto L_0x00de
            org.apache.http.StatusLine r10 = r10.getStatusLine()
            r10.getStatusCode()
        L_0x00de:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.deviceid.module.rpc.mrpc.core.q.a(org.apache.http.HttpResponse, int, java.lang.String):com.alipay.deviceid.module.rpc.mrpc.core.u");
    }

    private static HashMap<String, String> a(String str) {
        HashMap<String, String> hashMap = new HashMap<>();
        String[] split = str.split(";");
        int length = split.length;
        for (int i2 = 0; i2 < length; i2++) {
            String str2 = split[i2];
            String[] split2 = str2.indexOf(61) == -1 ? new String[]{"Content-Type", str2} : str2.split("=");
            hashMap.put(split2[0], split2[1]);
        }
        return hashMap;
    }

    private void a(HttpEntity httpEntity, OutputStream outputStream) {
        InputStream a2 = b.a(httpEntity);
        long contentLength = httpEntity.getContentLength();
        try {
            byte[] bArr = new byte[2048];
            while (true) {
                int read = a2.read(bArr);
                if (read == -1 || this.c.h()) {
                    outputStream.flush();
                } else {
                    outputStream.write(bArr, 0, read);
                    if (this.c.f() != null && contentLength > 0) {
                        this.c.f();
                    }
                }
            }
            outputStream.flush();
            r.a(a2);
        } catch (Exception e2) {
            e2.getCause();
            StringBuilder sb = new StringBuilder("HttpWorker Request Error!");
            sb.append(e2.getLocalizedMessage());
            throw new IOException(sb.toString());
        } catch (Throwable th) {
            r.a(a2);
            throw th;
        }
    }

    private static long b(HttpResponse httpResponse) {
        Header firstHeader = httpResponse.getFirstHeader("Cache-Control");
        if (firstHeader != null) {
            String[] split = firstHeader.getValue().split("=");
            if (split.length >= 2) {
                try {
                    return a(split);
                } catch (NumberFormatException unused) {
                }
            }
        }
        Header firstHeader2 = httpResponse.getFirstHeader("Expires");
        if (firstHeader2 != null) {
            return b.b(firstHeader2.getValue()) - System.currentTimeMillis();
        }
        return 0;
    }

    private URI b() {
        String a2 = this.c.a();
        if (this.d != null) {
            a2 = this.d;
        }
        if (a2 != null) {
            return new URI(a2);
        }
        throw new RuntimeException("url should not be null");
    }

    private HttpUriRequest c() {
        if (this.f != null) {
            return this.f;
        }
        if (this.j == null) {
            byte[] b2 = this.c.b();
            String b3 = this.c.b("gzip");
            if (b2 != null) {
                if (TextUtils.equals(b3, "true")) {
                    this.j = b.a(b2);
                } else {
                    this.j = new ByteArrayEntity(b2);
                }
                this.j.setContentType(this.c.c());
            }
        }
        AbstractHttpEntity abstractHttpEntity = this.j;
        if (abstractHttpEntity != null) {
            HttpPost httpPost = new HttpPost(b());
            httpPost.setEntity(abstractHttpEntity);
            this.f = httpPost;
        } else {
            this.f = new HttpGet(b());
        }
        return this.f;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x0299, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x029a, code lost:
        e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x029f, code lost:
        if (r13.m <= 0) goto L_0x02a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x02a1, code lost:
        r13.m++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x02a8, code lost:
        new java.lang.StringBuilder().append(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x02bd, code lost:
        throw new com.alipay.deviceid.module.rpc.mrpc.core.HttpException(java.lang.Integer.valueOf(0), java.lang.String.valueOf(r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x02be, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x02bf, code lost:
        e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x02c8, code lost:
        if (r13.c.f() != null) goto L_0x02ca;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x02ca, code lost:
        r13.c.f();
        new java.lang.StringBuilder().append(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x02d7, code lost:
        new java.lang.StringBuilder().append(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x02ec, code lost:
        throw new com.alipay.deviceid.module.rpc.mrpc.core.HttpException(java.lang.Integer.valueOf(6), java.lang.String.valueOf(r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x02ed, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x02ee, code lost:
        e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x02f7, code lost:
        if (r13.c.f() != null) goto L_0x02f9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x02f9, code lost:
        r13.c.f();
        new java.lang.StringBuilder().append(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x0306, code lost:
        new java.lang.StringBuilder().append(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x031d, code lost:
        throw new com.alipay.deviceid.module.rpc.mrpc.core.HttpException(java.lang.Integer.valueOf(9), java.lang.String.valueOf(r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x031e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x031f, code lost:
        e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x0328, code lost:
        if (r13.c.f() != null) goto L_0x032a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x032a, code lost:
        r13.c.f();
        new java.lang.StringBuilder().append(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x0346, code lost:
        throw new com.alipay.deviceid.module.rpc.mrpc.core.HttpException(java.lang.Integer.valueOf(8), java.lang.String.valueOf(r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x0347, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x0348, code lost:
        e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x0351, code lost:
        if (r13.c.f() != null) goto L_0x0353;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x0353, code lost:
        r13.c.f();
        new java.lang.StringBuilder().append(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x0360, code lost:
        new java.lang.StringBuilder().append(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x0376, code lost:
        throw new com.alipay.deviceid.module.rpc.mrpc.core.HttpException(java.lang.Integer.valueOf(5), java.lang.String.valueOf(r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x0377, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x0378, code lost:
        e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x0381, code lost:
        if (r13.c.f() != null) goto L_0x0383;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x0383, code lost:
        r13.c.f();
        new java.lang.StringBuilder().append(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x0390, code lost:
        new java.lang.StringBuilder().append(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x03a6, code lost:
        throw new com.alipay.deviceid.module.rpc.mrpc.core.HttpException(java.lang.Integer.valueOf(4), java.lang.String.valueOf(r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x03a7, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x03a8, code lost:
        e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x03b1, code lost:
        if (r13.c.f() != null) goto L_0x03b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x03b3, code lost:
        r13.c.f();
        new java.lang.StringBuilder().append(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x03c0, code lost:
        new java.lang.StringBuilder().append(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x03d5, code lost:
        throw new com.alipay.deviceid.module.rpc.mrpc.core.HttpException(java.lang.Integer.valueOf(3), java.lang.String.valueOf(r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x03d6, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x03d7, code lost:
        e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x03e0, code lost:
        if (r13.c.f() != null) goto L_0x03e2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x03e2, code lost:
        r13.c.f();
        new java.lang.StringBuilder().append(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x03ef, code lost:
        new java.lang.StringBuilder().append(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x0404, code lost:
        throw new com.alipay.deviceid.module.rpc.mrpc.core.HttpException(java.lang.Integer.valueOf(3), java.lang.String.valueOf(r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x0405, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x0406, code lost:
        e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x040f, code lost:
        if (r13.c.f() != null) goto L_0x0411;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x0411, code lost:
        r13.c.f();
        new java.lang.StringBuilder().append(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x041e, code lost:
        new java.lang.StringBuilder().append(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x0433, code lost:
        throw new com.alipay.deviceid.module.rpc.mrpc.core.HttpException(java.lang.Integer.valueOf(6), java.lang.String.valueOf(r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x0434, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x0435, code lost:
        e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x043e, code lost:
        if (r13.c.f() != null) goto L_0x0440;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x0440, code lost:
        r13.c.f();
        new java.lang.StringBuilder().append(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x044d, code lost:
        new java.lang.StringBuilder().append(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x0462, code lost:
        throw new com.alipay.deviceid.module.rpc.mrpc.core.HttpException(java.lang.Integer.valueOf(2), java.lang.String.valueOf(r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x0463, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x0464, code lost:
        e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x046d, code lost:
        if (r13.c.f() != null) goto L_0x046f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x046f, code lost:
        r13.c.f();
        new java.lang.StringBuilder().append(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x047c, code lost:
        new java.lang.StringBuilder().append(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x0491, code lost:
        throw new com.alipay.deviceid.module.rpc.mrpc.core.HttpException(java.lang.Integer.valueOf(2), java.lang.String.valueOf(r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x0492, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x049e, code lost:
        throw new java.lang.RuntimeException("Url parser error!", r0.getCause());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x049f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x04a0, code lost:
        e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x04a9, code lost:
        if (r13.c.f() != null) goto L_0x04ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x04ab, code lost:
        r13.c.f();
        r0.getCode();
        r0.getMsg();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x04b6, code lost:
        new java.lang.StringBuilder().append(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x04be, code lost:
        throw r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:88:0x024b */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x0299 A[ExcHandler: NullPointerException (r0v22 'e' java.lang.NullPointerException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x02be A[ExcHandler: IOException (r0v20 'e' java.io.IOException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x02ed A[ExcHandler: UnknownHostException (r0v18 'e' java.net.UnknownHostException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x031e A[ExcHandler: HttpHostConnectException (r0v16 'e' org.apache.http.conn.HttpHostConnectException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x0347 A[ExcHandler: NoHttpResponseException (r0v14 'e' org.apache.http.NoHttpResponseException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x0377 A[ExcHandler: SocketTimeoutException (r0v12 'e' java.net.SocketTimeoutException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x03a7 A[ExcHandler: ConnectTimeoutException (r0v10 'e' org.apache.http.conn.ConnectTimeoutException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x03d6 A[ExcHandler: ConnectionPoolTimeoutException (r0v8 'e' org.apache.http.conn.ConnectionPoolTimeoutException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x0405 A[ExcHandler: SSLException (r0v6 'e' javax.net.ssl.SSLException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0434 A[ExcHandler: SSLPeerUnverifiedException (r1v8 'e' javax.net.ssl.SSLPeerUnverifiedException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:161:0x0463 A[ExcHandler: SSLHandshakeException (r1v6 'e' javax.net.ssl.SSLHandshakeException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:167:0x0492 A[ExcHandler: URISyntaxException (r0v2 'e' java.net.URISyntaxException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x049f A[ExcHandler: HttpException (r0v1 'e' com.alipay.deviceid.module.rpc.mrpc.core.HttpException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x0032 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x003e A[Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0124 A[Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0144 A[Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0178 A[Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0185 A[Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x01fa A[Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }] */
    /* renamed from: d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alipay.deviceid.module.rpc.mrpc.core.u call() {
        /*
            r13 = this;
        L_0x0000:
            r0 = 2
            r1 = 3
            r2 = 6
            r3 = 1
            r4 = 0
            android.content.Context r5 = r13.b     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r6 = "connectivity"
            java.lang.Object r5 = r5.getSystemService(r6)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            android.net.ConnectivityManager r5 = (android.net.ConnectivityManager) r5     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            android.net.NetworkInfo[] r5 = r5.getAllNetworkInfo()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r5 != 0) goto L_0x0017
        L_0x0015:
            r5 = 0
            goto L_0x0030
        L_0x0017:
            int r6 = r5.length     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r7 = 0
        L_0x0019:
            if (r7 >= r6) goto L_0x0015
            r8 = r5[r7]     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r8 == 0) goto L_0x002d
            boolean r9 = r8.isAvailable()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r9 == 0) goto L_0x002d
            boolean r8 = r8.isConnectedOrConnecting()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r8 == 0) goto L_0x002d
            r5 = 1
            goto L_0x0030
        L_0x002d:
            int r7 = r7 + 1
            goto L_0x0019
        L_0x0030:
            if (r5 != 0) goto L_0x003e
            com.alipay.deviceid.module.rpc.mrpc.core.HttpException r5 = new com.alipay.deviceid.module.rpc.mrpc.core.HttpException     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r3)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r7 = "The network is not available"
            r5.<init>(r6, r7)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            throw r5     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
        L_0x003e:
            com.alipay.deviceid.module.rpc.mrpc.core.o r5 = r13.c     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            com.alipay.deviceid.module.rpc.mrpc.core.ac r5 = r5.f()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r5 == 0) goto L_0x004b
            com.alipay.deviceid.module.rpc.mrpc.core.o r5 = r13.c     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r5.f()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
        L_0x004b:
            com.alipay.deviceid.module.rpc.mrpc.core.o r5 = r13.c     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.util.ArrayList r5 = r5.d()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r5 == 0) goto L_0x0071
            boolean r6 = r5.isEmpty()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r6 != 0) goto L_0x0071
            java.util.Iterator r5 = r5.iterator()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
        L_0x005d:
            boolean r6 = r5.hasNext()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r6 == 0) goto L_0x0071
            java.lang.Object r6 = r5.next()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            org.apache.http.Header r6 = (org.apache.http.Header) r6     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            org.apache.http.client.methods.HttpUriRequest r7 = r13.c()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r7.addHeader(r6)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            goto L_0x005d
        L_0x0071:
            org.apache.http.client.methods.HttpUriRequest r5 = r13.c()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            com.alipay.deviceid.module.rpc.mrpc.core.b.a(r5)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            org.apache.http.client.methods.HttpUriRequest r5 = r13.c()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            com.alipay.deviceid.module.rpc.mrpc.core.b.b(r5)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            org.apache.http.client.methods.HttpUriRequest r5 = r13.c()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r6 = "cookie"
            android.webkit.CookieManager r7 = r13.i()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            com.alipay.deviceid.module.rpc.mrpc.core.o r8 = r13.c     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r8 = r8.a()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r7 = r7.getCookie(r8)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r5.addHeader(r6, r7)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            org.apache.http.protocol.HttpContext r5 = r13.g     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r6 = "http.cookie-store"
            org.apache.http.client.CookieStore r7 = r13.h     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r5.setAttribute(r6, r7)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            com.alipay.deviceid.module.rpc.mrpc.core.l r5 = r13.a     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            com.alipay.deviceid.module.rpc.mrpc.core.b r5 = r5.a()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            org.apache.http.client.HttpRequestRetryHandler r6 = e     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r5.a(r6)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r8 = "By Http/Https to request. operationType="
            r7.<init>(r8)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r8 = r13.f()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r7.append(r8)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r8 = " url="
            r7.append(r8)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            org.apache.http.client.methods.HttpUriRequest r8 = r13.f     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.net.URI r8 = r8.getURI()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r8 = r8.toString()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r7.append(r8)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            com.alipay.deviceid.module.rpc.mrpc.core.l r7 = r13.a     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            com.alipay.deviceid.module.rpc.mrpc.core.b r7 = r7.a()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            org.apache.http.params.HttpParams r7 = r7.getParams()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r8 = "http.route.default-proxy"
            android.content.Context r9 = r13.b     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r10 = "connectivity"
            java.lang.Object r9 = r9.getSystemService(r10)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            android.net.ConnectivityManager r9 = (android.net.ConnectivityManager) r9     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            android.net.NetworkInfo r9 = r9.getActiveNetworkInfo()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r10 = 0
            if (r9 == 0) goto L_0x0101
            boolean r9 = r9.isAvailable()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r9 == 0) goto L_0x0101
            java.lang.String r9 = android.net.Proxy.getDefaultHost()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            int r11 = android.net.Proxy.getDefaultPort()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r9 == 0) goto L_0x0101
            org.apache.http.HttpHost r12 = new org.apache.http.HttpHost     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r12.<init>(r9, r11)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            goto L_0x0102
        L_0x0101:
            r12 = r10
        L_0x0102:
            if (r12 == 0) goto L_0x0119
            java.lang.String r9 = r12.getHostName()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r11 = "127.0.0.1"
            boolean r9 = android.text.TextUtils.equals(r9, r11)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r9 == 0) goto L_0x0119
            int r9 = r12.getPort()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r11 = 8087(0x1f97, float:1.1332E-41)
            if (r9 != r11) goto L_0x0119
            goto L_0x011a
        L_0x0119:
            r10 = r12
        L_0x011a:
            r7.setParameter(r8, r10)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            org.apache.http.HttpHost r7 = r13.k     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r7 == 0) goto L_0x0124
        L_0x0121:
            org.apache.http.HttpHost r7 = r13.k     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            goto L_0x013c
        L_0x0124:
            java.net.URL r7 = r13.h()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            org.apache.http.HttpHost r8 = new org.apache.http.HttpHost     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r9 = r7.getHost()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            int r10 = r13.g()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r7 = r7.getProtocol()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r8.<init>(r9, r10, r7)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r13.k = r8     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            goto L_0x0121
        L_0x013c:
            int r8 = r13.g()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r9 = 80
            if (r8 != r9) goto L_0x0151
            org.apache.http.HttpHost r7 = new org.apache.http.HttpHost     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.net.URL r8 = r13.h()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r8 = r8.getHost()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r7.<init>(r8)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
        L_0x0151:
            com.alipay.deviceid.module.rpc.mrpc.core.l r8 = r13.a     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            com.alipay.deviceid.module.rpc.mrpc.core.b r8 = r8.a()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            org.apache.http.client.methods.HttpUriRequest r9 = r13.f     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            org.apache.http.protocol.HttpContext r10 = r13.g     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            org.apache.http.HttpResponse r7 = r8.execute(r7, r9, r10)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            com.alipay.deviceid.module.rpc.mrpc.core.l r10 = r13.a     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r11 = 0
            long r8 = r8 - r5
            r10.b(r8)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            org.apache.http.client.CookieStore r5 = r13.h     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.util.List r5 = r5.getCookies()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            com.alipay.deviceid.module.rpc.mrpc.core.o r6 = r13.c     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            boolean r6 = r6.e()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r6 == 0) goto L_0x017f
            android.webkit.CookieManager r6 = r13.i()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r6.removeAllCookie()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
        L_0x017f:
            boolean r6 = r5.isEmpty()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r6 != 0) goto L_0x01e6
            java.util.Iterator r5 = r5.iterator()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
        L_0x0189:
            boolean r6 = r5.hasNext()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r6 == 0) goto L_0x01e6
            java.lang.Object r6 = r5.next()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            org.apache.http.cookie.Cookie r6 = (org.apache.http.cookie.Cookie) r6     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r8 = r6.getDomain()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r8 == 0) goto L_0x0189
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r8.<init>()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r9 = r6.getName()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r8.append(r9)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r9 = "="
            r8.append(r9)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r9 = r6.getValue()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r8.append(r9)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r9 = "; domain="
            r8.append(r9)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r9 = r6.getDomain()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r8.append(r9)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            boolean r6 = r6.isSecure()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r6 == 0) goto L_0x01c8
            java.lang.String r6 = "; Secure"
            goto L_0x01ca
        L_0x01c8:
            java.lang.String r6 = ""
        L_0x01ca:
            r8.append(r6)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r6 = r8.toString()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            android.webkit.CookieManager r8 = r13.i()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            com.alipay.deviceid.module.rpc.mrpc.core.o r9 = r13.c     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r9 = r9.a()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r8.setCookie(r9, r6)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            android.webkit.CookieSyncManager r6 = android.webkit.CookieSyncManager.getInstance()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r6.sync()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            goto L_0x0189
        L_0x01e6:
            org.apache.http.StatusLine r5 = r7.getStatusLine()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            int r5 = r5.getStatusCode()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            org.apache.http.StatusLine r6 = r7.getStatusLine()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r6 = r6.getReasonPhrase()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r8 = 200(0xc8, float:2.8E-43)
            if (r5 == r8) goto L_0x021d
            r8 = 304(0x130, float:4.26E-43)
            if (r5 != r8) goto L_0x0200
            r8 = 1
            goto L_0x0201
        L_0x0200:
            r8 = 0
        L_0x0201:
            if (r8 != 0) goto L_0x021d
            com.alipay.deviceid.module.rpc.mrpc.core.HttpException r5 = new com.alipay.deviceid.module.rpc.mrpc.core.HttpException     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            org.apache.http.StatusLine r6 = r7.getStatusLine()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            int r6 = r6.getStatusCode()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            org.apache.http.StatusLine r7 = r7.getStatusLine()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r7 = r7.getReasonPhrase()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r5.<init>(r6, r7)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            throw r5     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
        L_0x021d:
            com.alipay.deviceid.module.rpc.mrpc.core.u r5 = r13.a(r7, r5, r6)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r6 = -1
            if (r5 == 0) goto L_0x0232
            byte[] r8 = r5.b()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r8 == 0) goto L_0x0232
            byte[] r8 = r5.b()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            int r8 = r8.length     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            long r8 = (long) r8     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            goto L_0x0233
        L_0x0232:
            r8 = r6
        L_0x0233:
            int r6 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r6 != 0) goto L_0x024b
            boolean r6 = r5 instanceof com.alipay.deviceid.module.rpc.mrpc.core.p     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r6 == 0) goto L_0x024b
            r6 = r5
            com.alipay.deviceid.module.rpc.mrpc.core.p r6 = (com.alipay.deviceid.module.rpc.mrpc.core.p) r6     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            com.alipay.deviceid.module.rpc.mrpc.core.HttpUrlHeader r6 = r6.a()     // Catch:{ Exception -> 0x024b, HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299 }
            java.lang.String r7 = "Content-Length"
            java.lang.String r6 = r6.getHead(r7)     // Catch:{ Exception -> 0x024b, HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299 }
            java.lang.Long.parseLong(r6)     // Catch:{ Exception -> 0x024b, HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299 }
        L_0x024b:
            com.alipay.deviceid.module.rpc.mrpc.core.o r6 = r13.c     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r6 = r6.a()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r6 == 0) goto L_0x0271
            java.lang.String r7 = r13.f()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            if (r7 != 0) goto L_0x0271
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r7.<init>()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r7.append(r6)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r6 = "#"
            r7.append(r6)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            java.lang.String r6 = r13.f()     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
            r7.append(r6)     // Catch:{ HttpException -> 0x049f, URISyntaxException -> 0x0492, SSLHandshakeException -> 0x0463, SSLPeerUnverifiedException -> 0x0434, SSLException -> 0x0405, ConnectionPoolTimeoutException -> 0x03d6, ConnectTimeoutException -> 0x03a7, SocketTimeoutException -> 0x0377, NoHttpResponseException -> 0x0347, HttpHostConnectException -> 0x031e, UnknownHostException -> 0x02ed, IOException -> 0x02be, NullPointerException -> 0x0299, Exception -> 0x0272 }
        L_0x0271:
            return r5
        L_0x0272:
            r0 = move-exception
            r13.e()
            com.alipay.deviceid.module.rpc.mrpc.core.o r1 = r13.c
            com.alipay.deviceid.module.rpc.mrpc.core.ac r1 = r1.f()
            if (r1 == 0) goto L_0x028b
            com.alipay.deviceid.module.rpc.mrpc.core.o r1 = r13.c
            r1.f()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
        L_0x028b:
            com.alipay.deviceid.module.rpc.mrpc.core.HttpException r1 = new com.alipay.deviceid.module.rpc.mrpc.core.HttpException
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r1.<init>(r2, r0)
            throw r1
        L_0x0299:
            r0 = move-exception
            r13.e()
            int r1 = r13.m
            if (r1 > 0) goto L_0x02a8
            int r0 = r13.m
            int r0 = r0 + r3
            r13.m = r0
            goto L_0x0000
        L_0x02a8:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            com.alipay.deviceid.module.rpc.mrpc.core.HttpException r1 = new com.alipay.deviceid.module.rpc.mrpc.core.HttpException
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r1.<init>(r2, r0)
            throw r1
        L_0x02be:
            r0 = move-exception
            r13.e()
            com.alipay.deviceid.module.rpc.mrpc.core.o r1 = r13.c
            com.alipay.deviceid.module.rpc.mrpc.core.ac r1 = r1.f()
            if (r1 == 0) goto L_0x02d7
            com.alipay.deviceid.module.rpc.mrpc.core.o r1 = r13.c
            r1.f()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
        L_0x02d7:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            com.alipay.deviceid.module.rpc.mrpc.core.HttpException r1 = new com.alipay.deviceid.module.rpc.mrpc.core.HttpException
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r1.<init>(r2, r0)
            throw r1
        L_0x02ed:
            r0 = move-exception
            r13.e()
            com.alipay.deviceid.module.rpc.mrpc.core.o r1 = r13.c
            com.alipay.deviceid.module.rpc.mrpc.core.ac r1 = r1.f()
            if (r1 == 0) goto L_0x0306
            com.alipay.deviceid.module.rpc.mrpc.core.o r1 = r13.c
            r1.f()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
        L_0x0306:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            com.alipay.deviceid.module.rpc.mrpc.core.HttpException r1 = new com.alipay.deviceid.module.rpc.mrpc.core.HttpException
            r2 = 9
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r1.<init>(r2, r0)
            throw r1
        L_0x031e:
            r0 = move-exception
            r13.e()
            com.alipay.deviceid.module.rpc.mrpc.core.o r1 = r13.c
            com.alipay.deviceid.module.rpc.mrpc.core.ac r1 = r1.f()
            if (r1 == 0) goto L_0x0337
            com.alipay.deviceid.module.rpc.mrpc.core.o r1 = r13.c
            r1.f()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
        L_0x0337:
            com.alipay.deviceid.module.rpc.mrpc.core.HttpException r1 = new com.alipay.deviceid.module.rpc.mrpc.core.HttpException
            r2 = 8
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r1.<init>(r2, r0)
            throw r1
        L_0x0347:
            r0 = move-exception
            r13.e()
            com.alipay.deviceid.module.rpc.mrpc.core.o r1 = r13.c
            com.alipay.deviceid.module.rpc.mrpc.core.ac r1 = r1.f()
            if (r1 == 0) goto L_0x0360
            com.alipay.deviceid.module.rpc.mrpc.core.o r1 = r13.c
            r1.f()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
        L_0x0360:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            com.alipay.deviceid.module.rpc.mrpc.core.HttpException r1 = new com.alipay.deviceid.module.rpc.mrpc.core.HttpException
            r2 = 5
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r1.<init>(r2, r0)
            throw r1
        L_0x0377:
            r0 = move-exception
            r13.e()
            com.alipay.deviceid.module.rpc.mrpc.core.o r1 = r13.c
            com.alipay.deviceid.module.rpc.mrpc.core.ac r1 = r1.f()
            if (r1 == 0) goto L_0x0390
            com.alipay.deviceid.module.rpc.mrpc.core.o r1 = r13.c
            r1.f()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
        L_0x0390:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            com.alipay.deviceid.module.rpc.mrpc.core.HttpException r1 = new com.alipay.deviceid.module.rpc.mrpc.core.HttpException
            r2 = 4
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r1.<init>(r2, r0)
            throw r1
        L_0x03a7:
            r0 = move-exception
            r13.e()
            com.alipay.deviceid.module.rpc.mrpc.core.o r2 = r13.c
            com.alipay.deviceid.module.rpc.mrpc.core.ac r2 = r2.f()
            if (r2 == 0) goto L_0x03c0
            com.alipay.deviceid.module.rpc.mrpc.core.o r2 = r13.c
            r2.f()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
        L_0x03c0:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            com.alipay.deviceid.module.rpc.mrpc.core.HttpException r2 = new com.alipay.deviceid.module.rpc.mrpc.core.HttpException
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r2.<init>(r1, r0)
            throw r2
        L_0x03d6:
            r0 = move-exception
            r13.e()
            com.alipay.deviceid.module.rpc.mrpc.core.o r2 = r13.c
            com.alipay.deviceid.module.rpc.mrpc.core.ac r2 = r2.f()
            if (r2 == 0) goto L_0x03ef
            com.alipay.deviceid.module.rpc.mrpc.core.o r2 = r13.c
            r2.f()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
        L_0x03ef:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            com.alipay.deviceid.module.rpc.mrpc.core.HttpException r2 = new com.alipay.deviceid.module.rpc.mrpc.core.HttpException
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r2.<init>(r1, r0)
            throw r2
        L_0x0405:
            r0 = move-exception
            r13.e()
            com.alipay.deviceid.module.rpc.mrpc.core.o r1 = r13.c
            com.alipay.deviceid.module.rpc.mrpc.core.ac r1 = r1.f()
            if (r1 == 0) goto L_0x041e
            com.alipay.deviceid.module.rpc.mrpc.core.o r1 = r13.c
            r1.f()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
        L_0x041e:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            com.alipay.deviceid.module.rpc.mrpc.core.HttpException r1 = new com.alipay.deviceid.module.rpc.mrpc.core.HttpException
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r1.<init>(r2, r0)
            throw r1
        L_0x0434:
            r1 = move-exception
            r13.e()
            com.alipay.deviceid.module.rpc.mrpc.core.o r2 = r13.c
            com.alipay.deviceid.module.rpc.mrpc.core.ac r2 = r2.f()
            if (r2 == 0) goto L_0x044d
            com.alipay.deviceid.module.rpc.mrpc.core.o r2 = r13.c
            r2.f()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r1)
        L_0x044d:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r1)
            com.alipay.deviceid.module.rpc.mrpc.core.HttpException r2 = new com.alipay.deviceid.module.rpc.mrpc.core.HttpException
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r2.<init>(r0, r1)
            throw r2
        L_0x0463:
            r1 = move-exception
            r13.e()
            com.alipay.deviceid.module.rpc.mrpc.core.o r2 = r13.c
            com.alipay.deviceid.module.rpc.mrpc.core.ac r2 = r2.f()
            if (r2 == 0) goto L_0x047c
            com.alipay.deviceid.module.rpc.mrpc.core.o r2 = r13.c
            r2.f()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r1)
        L_0x047c:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r1)
            com.alipay.deviceid.module.rpc.mrpc.core.HttpException r2 = new com.alipay.deviceid.module.rpc.mrpc.core.HttpException
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r2.<init>(r0, r1)
            throw r2
        L_0x0492:
            r0 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "Url parser error!"
            java.lang.Throwable r0 = r0.getCause()
            r1.<init>(r2, r0)
            throw r1
        L_0x049f:
            r0 = move-exception
            r13.e()
            com.alipay.deviceid.module.rpc.mrpc.core.o r1 = r13.c
            com.alipay.deviceid.module.rpc.mrpc.core.ac r1 = r1.f()
            if (r1 == 0) goto L_0x04b6
            com.alipay.deviceid.module.rpc.mrpc.core.o r1 = r13.c
            r1.f()
            r0.getCode()
            r0.getMsg()
        L_0x04b6:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.deviceid.module.rpc.mrpc.core.q.call():com.alipay.deviceid.module.rpc.mrpc.core.u");
    }

    private void e() {
        if (this.f != null) {
            this.f.abort();
        }
    }

    private String f() {
        if (!TextUtils.isEmpty(this.q)) {
            return this.q;
        }
        this.q = this.c.b(TransportConstants.KEY_OPERATION_TYPE);
        return this.q;
    }

    private int g() {
        URL h2 = h();
        return h2.getPort() == -1 ? h2.getDefaultPort() : h2.getPort();
    }

    private URL h() {
        if (this.l != null) {
            return this.l;
        }
        this.l = new URL(this.c.a());
        return this.l;
    }

    private CookieManager i() {
        if (this.i != null) {
            return this.i;
        }
        this.i = CookieManager.getInstance();
        return this.i;
    }

    public final o a() {
        return this.c;
    }
}
