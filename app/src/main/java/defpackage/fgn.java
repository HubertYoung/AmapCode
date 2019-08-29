package defpackage;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.network.domain.Request;

/* renamed from: fgn reason: default package */
/* compiled from: DefaultCallImpl */
public final class fgn extends fgd {
    ExecutorService i;

    /* renamed from: fgn$a */
    /* compiled from: DefaultCallImpl */
    class a implements Runnable {
        Request a;
        fgf b;

        public a(Request request, fgf fgf) {
            this.a = request;
            this.b = fgf;
        }

        public final void run() {
            try {
                if (fgn.this.c) {
                    TBSdkLog.a((String) "mtopsdk.DefaultCallImpl", fgn.this.h, (String) "call task is canceled.");
                    this.b.a((fge) fgn.this);
                    return;
                }
                fgi c2 = fgn.this.c();
                if (c2 == null) {
                    this.b.a(fgn.this, new Exception("response is null"));
                } else {
                    this.b.a(c2);
                }
            } catch (CancellationException unused) {
                this.b.a((fge) fgn.this);
            } catch (InterruptedException unused2) {
                this.b.a((fge) fgn.this);
            } catch (Exception e) {
                this.b.a(fgn.this, e);
                TBSdkLog.b("mtopsdk.DefaultCallImpl", fgn.this.h, "do call.execute failed.", e);
            }
        }
    }

    public fgn(Request request, ExecutorService executorService) {
        super(request, null);
        this.i = executorService;
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x013b  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0146 A[LOOP:0: B:12:0x003e->B:54:0x0146, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0139 A[EDGE_INSN: B:55:0x0139->B:51:0x0139 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final defpackage.fgi c() throws java.lang.InterruptedException {
        /*
            r13 = this;
            mtopsdk.network.domain.Request r1 = r13.a()
            boolean r0 = f
            if (r0 == 0) goto L_0x003a
            boolean r0 = e
            if (r0 == 0) goto L_0x003a
            java.lang.String r0 = r1.n
            ffc r0 = r13.a(r0)
            if (r0 == 0) goto L_0x003a
            int r2 = r0.b
            java.util.Map<java.lang.String, java.util.List<java.lang.String>> r4 = r0.c
            byte[] r5 = r0.d
            mtopsdk.common.util.TBSdkLog$LogEnable r3 = mtopsdk.common.util.TBSdkLog.LogEnable.InfoEnable
            boolean r3 = mtopsdk.common.util.TBSdkLog.a(r3)
            if (r3 == 0) goto L_0x0033
            java.lang.String r3 = "mtopsdk.DefaultCallImpl"
            java.lang.String r6 = r13.h
            java.lang.String r7 = "[execute]get MockResponse succeed.mockResponse="
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r0 = r7.concat(r0)
            mtopsdk.common.util.TBSdkLog.b(r3, r6, r0)
        L_0x0033:
            r3 = 0
            r0 = r13
            fgi r0 = r0.a(r1, r2, r3, r4, r5)
            return r0
        L_0x003a:
            r0 = 0
            r2 = 0
            r3 = r2
            r4 = 0
        L_0x003e:
            java.net.URL r5 = new java.net.URL     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            java.lang.String r6 = r1.a     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            r5.<init>(r6)     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            java.net.URLConnection r5 = r5.openConnection()     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            java.net.HttpURLConnection r5 = (java.net.HttpURLConnection) r5     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            int r6 = r1.f     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            r5.setConnectTimeout(r6)     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            int r6 = r1.g     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            r5.setReadTimeout(r6)     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            a(r5, r1)     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            java.lang.Thread r6 = java.lang.Thread.currentThread()     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            boolean r6 = r6.isInterrupted()     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            if (r6 == 0) goto L_0x0073
            java.lang.String r3 = "mtopsdk.DefaultCallImpl"
            java.lang.String r4 = r13.h     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            java.lang.String r5 = "[readResponse]call task is canceled."
            mtopsdk.common.util.TBSdkLog.a(r3, r4, r5)     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            java.util.concurrent.CancellationException r3 = new java.util.concurrent.CancellationException     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            java.lang.String r4 = "call canceled"
            r3.<init>(r4)     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            throw r3     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
        L_0x0073:
            int r6 = r5.getResponseCode()     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            java.lang.String r7 = r5.getResponseMessage()     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            if (r7 != 0) goto L_0x007f
            java.lang.String r7 = ""
        L_0x007f:
            java.util.Map r8 = r5.getHeaderFields()     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            java.lang.String r9 = r1.a     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            a(r9, r8)     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            mtopsdk.common.util.TBSdkLog$LogEnable r9 = mtopsdk.common.util.TBSdkLog.LogEnable.InfoEnable     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            boolean r9 = mtopsdk.common.util.TBSdkLog.a(r9)     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            if (r9 == 0) goto L_0x00a1
            java.lang.String r9 = "mtopsdk.DefaultCallImpl"
            java.lang.String r10 = r1.e     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            java.lang.String r11 = "response headers:"
            java.lang.String r12 = java.lang.String.valueOf(r8)     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            java.lang.String r11 = r11.concat(r12)     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            mtopsdk.common.util.TBSdkLog.b(r9, r10, r11)     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
        L_0x00a1:
            java.lang.String r9 = r5.getContentType()     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            int r10 = r5.getContentLength()     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            boolean r11 = defpackage.fgp.a(r8)     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            r12 = 400(0x190, float:5.6E-43)
            if (r6 < r12) goto L_0x00b6
            java.io.InputStream r5 = r5.getErrorStream()     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            goto L_0x00c7
        L_0x00b6:
            if (r11 == 0) goto L_0x00c3
            java.util.zip.GZIPInputStream r11 = new java.util.zip.GZIPInputStream     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            java.io.InputStream r5 = r5.getInputStream()     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            r11.<init>(r5)     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            r5 = r11
            goto L_0x00c7
        L_0x00c3:
            java.io.InputStream r5 = r5.getInputStream()     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
        L_0x00c7:
            fgn$1 r11 = new fgn$1     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            r11.<init>(r9, r10, r5)     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            java.lang.Thread r5 = java.lang.Thread.currentThread()     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            boolean r5 = r5.isInterrupted()     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            if (r5 == 0) goto L_0x00e7
            java.lang.String r3 = "mtopsdk.DefaultCallImpl"
            java.lang.String r4 = r13.h     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            java.lang.String r5 = "[readResponse]call task is canceled."
            mtopsdk.common.util.TBSdkLog.a(r3, r4, r5)     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            java.util.concurrent.CancellationException r3 = new java.util.concurrent.CancellationException     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            java.lang.String r4 = "call canceled"
            r3.<init>(r4)     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            throw r3     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
        L_0x00e7:
            r11.c()     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            fgi$a r5 = new fgi$a     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            r5.<init>()     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            r5.a = r1     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            r5.b = r6     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            r5.c = r7     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            java.util.Map r6 = defpackage.fcz.a(r8)     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            r5.d = r6     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            r5.e = r11     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            fgi r5 = r5.a()     // Catch:{ UnknownHostException -> 0x012d, SocketTimeoutException -> 0x0126, ConnectTimeoutException -> 0x011f, SSLHandshakeException -> 0x0118, SSLException -> 0x0111, ConnectException -> 0x010a, Exception -> 0x0103 }
            r2 = r5
            goto L_0x0139
        L_0x0103:
            r3 = move-exception
            r4 = -7
            java.lang.String r3 = r3.getMessage()
            goto L_0x0133
        L_0x010a:
            r3 = move-exception
            r4 = -6
            java.lang.String r3 = r3.getMessage()
            goto L_0x0133
        L_0x0111:
            r3 = move-exception
            r4 = -5
            java.lang.String r3 = r3.getMessage()
            goto L_0x0133
        L_0x0118:
            r3 = move-exception
            r4 = -4
            java.lang.String r3 = r3.getMessage()
            goto L_0x0133
        L_0x011f:
            r3 = move-exception
            r4 = -3
            java.lang.String r3 = r3.getMessage()
            goto L_0x0133
        L_0x0126:
            r3 = move-exception
            r4 = -2
            java.lang.String r3 = r3.getMessage()
            goto L_0x0133
        L_0x012d:
            r3 = move-exception
            r4 = -1
            java.lang.String r3 = r3.getMessage()
        L_0x0133:
            int r5 = r0 + 1
            int r6 = r1.h
            if (r0 < r6) goto L_0x0146
        L_0x0139:
            if (r2 != 0) goto L_0x0145
            r5 = 0
            r6 = 0
            r0 = r13
            r2 = r4
            r4 = r5
            r5 = r6
            fgi r2 = r0.a(r1, r2, r3, r4, r5)
        L_0x0145:
            return r2
        L_0x0146:
            r0 = r5
            goto L_0x003e
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fgn.c():fgi");
    }

    public final void a(fgf fgf) {
        if (this.i != null) {
            try {
                this.d = this.i.submit(new a(a(), fgf));
            } catch (Exception e) {
                fgf.a(this, e);
            }
        } else {
            fgf.a(this, new Exception("miss executorService in CallImpl "));
        }
    }

    private static void a(HttpURLConnection httpURLConnection, Request request) throws IOException {
        httpURLConnection.setRequestMethod(request.b);
        for (Entry next : request.c.entrySet()) {
            if (!((String) next.getKey()).equalsIgnoreCase("Cookie")) {
                httpURLConnection.addRequestProperty((String) next.getKey(), (String) next.getValue());
            }
        }
        if (!fdd.b(fgg.a(request.a))) {
            httpURLConnection.addRequestProperty("Cookie", fgg.a(request.a));
        }
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            String str = request.e;
            StringBuilder sb = new StringBuilder("request url =");
            sb.append(request.a);
            TBSdkLog.b((String) "mtopsdk.DefaultCallImpl", str, sb.toString());
            String str2 = request.e;
            StringBuilder sb2 = new StringBuilder("request headers =");
            sb2.append(httpURLConnection.getRequestProperties());
            TBSdkLog.b((String) "mtopsdk.DefaultCallImpl", str2, sb2.toString());
        }
        if ("POST".equalsIgnoreCase(request.b)) {
            httpURLConnection.setDoOutput(true);
        }
        fgh fgh = request.d;
        if (fgh != null) {
            httpURLConnection.setDoOutput(true);
            httpURLConnection.addRequestProperty("Content-Type", fgh.a());
            long b = fgh.b();
            if (b != -1) {
                httpURLConnection.setFixedLengthStreamingMode((int) b);
                httpURLConnection.addRequestProperty("Content-Length", String.valueOf(b));
            }
            OutputStream outputStream = httpURLConnection.getOutputStream();
            try {
                fgh.a(outputStream);
            } catch (Exception e) {
                TBSdkLog.b((String) "mtopsdk.DefaultCallImpl", (String) "write outputstream error.", (Throwable) e);
            } finally {
                fgp.a((Closeable) outputStream);
            }
        }
    }

    private static void a(String str, Map<String, List<String>> map) {
        if (str != null && map != null) {
            try {
                for (Entry next : map.entrySet()) {
                    String str2 = (String) next.getKey();
                    if (str2 != null && (str2.equalsIgnoreCase("Set-Cookie") || str2.equalsIgnoreCase("Set-Cookie2"))) {
                        for (String a2 : (List) next.getValue()) {
                            fgg.a(str, a2);
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
    }
}
