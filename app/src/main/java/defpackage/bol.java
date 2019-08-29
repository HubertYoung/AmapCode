package defpackage;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: bol reason: default package */
/* compiled from: HurlRequester */
public final class bol {
    final String a;
    bot b;
    public bou c;
    public long d = 0;
    private URL e;
    private HttpURLConnection f;
    private a g;
    private bom h;
    private int i = 0;

    /* renamed from: bol$a */
    /* compiled from: HurlRequester */
    static class a extends FilterInputStream {
        private HttpURLConnection a;

        protected a(HttpURLConnection httpURLConnection, InputStream inputStream) {
            super(inputStream);
            this.a = httpURLConnection;
        }

        public final void close() throws IOException {
            super.close();
            this.a.disconnect();
            if (bpv.a(3)) {
                StringBuilder sb = new StringBuilder("disconnection: ");
                sb.append(this.a.toString());
                bpv.b("ANet-HurlRequester", sb.toString());
            }
        }
    }

    public bol(String str, String str2, bot bot) throws MalformedURLException {
        this.e = new URL(str);
        this.a = str2;
        this.b = bot;
        this.h = new bom(bot.d);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002a, code lost:
        if ((r4.b != null && (r4.b instanceof defpackage.boq)) == false) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003d, code lost:
        if (defpackage.bom.a.contains(r2.getClass()) == false) goto L_0x003f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0079 A[LOOP:0: B:1:0x0004->B:30:0x0079, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0078 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final defpackage.bov a() throws java.lang.Exception {
        /*
            r8 = this;
            r0 = 0
            r1 = 1
            r2 = 1
            r3 = 0
        L_0x0004:
            if (r2 == 0) goto L_0x0081
            bov r2 = r8.e()     // Catch:{ Throwable -> 0x000b }
            return r2
        L_0x000b:
            r2 = move-exception
            bom r4 = r8.h
            int r3 = r3 + r1
            int r4 = r4.b
            if (r3 > r4) goto L_0x0041
            java.lang.String r4 = r8.a
            java.lang.String r5 = "POST"
            if (r4 != r5) goto L_0x002d
            bot r4 = r8.b
            if (r4 == 0) goto L_0x0041
            boo r5 = r4.b
            if (r5 == 0) goto L_0x0029
            boo r4 = r4.b
            boolean r4 = r4 instanceof defpackage.boq
            if (r4 == 0) goto L_0x0029
            r4 = 1
            goto L_0x002a
        L_0x0029:
            r4 = 0
        L_0x002a:
            if (r4 != 0) goto L_0x0041
            goto L_0x003f
        L_0x002d:
            java.lang.String r4 = r8.a
            java.lang.String r5 = "GET"
            if (r4 != r5) goto L_0x0041
            java.util.HashSet<java.lang.Class<?>> r4 = defpackage.bom.a
            java.lang.Class r5 = r2.getClass()
            boolean r4 = r4.contains(r5)
            if (r4 != 0) goto L_0x0041
        L_0x003f:
            r4 = 1
            goto L_0x0042
        L_0x0041:
            r4 = 0
        L_0x0042:
            r5 = 4
            boolean r5 = defpackage.bpv.a(r5)
            if (r5 == 0) goto L_0x0076
            java.lang.String r5 = "ANet-HurlRequester"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "retry, retryTimes: "
            r6.<init>(r7)
            r6.append(r3)
            java.lang.String r7 = "error: "
            r6.append(r7)
            java.lang.String r7 = r2.getLocalizedMessage()
            r6.append(r7)
            java.lang.String r7 = ", url: "
            r6.append(r7)
            java.net.URL r7 = r8.e
            java.lang.String r7 = r7.toString()
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            defpackage.bpv.c(r5, r6)
        L_0x0076:
            if (r4 != 0) goto L_0x0079
            throw r2
        L_0x0079:
            r5 = 0
            r8.d = r5
            r8.i = r3
            r2 = r4
            goto L_0x0004
        L_0x0081:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bol.a():bov");
    }

    public final InputStream b() throws Exception {
        if (this.g == null && this.f != null) {
            this.g = new a(this.f, this.f.getInputStream());
        }
        return this.g;
    }

    public final int c() throws Exception {
        if (this.f != null) {
            return this.f.getResponseCode();
        }
        return b() != null ? 200 : 404;
    }

    public final Map<String, List<String>> d() {
        if (this.f != null) {
            return this.f.getHeaderFields();
        }
        return null;
    }

    /* JADX INFO: finally extract failed */
    private bov e() throws Exception {
        int i2;
        HttpURLConnection httpURLConnection = (HttpURLConnection) this.e.openConnection();
        httpURLConnection.setInstanceFollowRedirects(HttpURLConnection.getFollowRedirects());
        this.f = httpURLConnection;
        boolean z = false;
        try {
            HttpURLConnection httpURLConnection2 = this.f;
            bot bot = this.b;
            int i3 = HttpConstants.CONNECTION_TIME_OUT;
            if (bot != null) {
                i2 = this.b.c;
            } else {
                i2 = HttpConstants.CONNECTION_TIME_OUT;
            }
            httpURLConnection2.setConnectTimeout(i2);
            HttpURLConnection httpURLConnection3 = this.f;
            if (this.b != null) {
                i3 = this.b.c;
            }
            httpURLConnection3.setReadTimeout(i3);
            this.f.setInstanceFollowRedirects(true);
            HttpURLConnection httpURLConnection4 = this.f;
            bot bot2 = this.b;
            if (bot2 != null) {
                HashMap<String, String> hashMap = bot2.a;
                if (hashMap != null) {
                    for (Entry next : hashMap.entrySet()) {
                        httpURLConnection4.setRequestProperty((String) next.getKey(), (String) next.getValue());
                    }
                }
            }
            this.f.setRequestMethod(this.a);
            if (this.a == "POST" && this.b != null) {
                this.f.setDoOutput(true);
                HttpURLConnection httpURLConnection5 = this.f;
                boo boo = this.b.b;
                long j = 0;
                if (boo != null) {
                    String b2 = boo.b();
                    if (!TextUtils.isEmpty(b2)) {
                        httpURLConnection5.setRequestProperty("Content-Type", b2);
                    }
                    if (boo instanceof boq) {
                        ((boq) boo).a(this.c);
                    }
                    if (boo instanceof bop) {
                        int a2 = ((bop) boo).a();
                        if (a2 > 0) {
                            httpURLConnection5.setFixedLengthStreamingMode(a2);
                        }
                    } else if (boo instanceof boq) {
                        httpURLConnection5.setChunkedStreamingMode(0);
                    }
                    j = 0 + boo.a(httpURLConnection5.getOutputStream());
                }
                this.d = j;
            }
            int c2 = c();
            if (c2 == -1) {
                throw new IOException("Could not retrieve response code from HttpUrlConnection.");
            } else if (c2 == bok.e) {
                bov bov = new bov(new ByteArrayInputStream(new byte[0]), c2, Collections.EMPTY_MAP);
                if (bpv.a(5)) {
                    StringBuilder sb = new StringBuilder("do not need keepConnection:");
                    sb.append(this.f);
                    bpv.b("ANet-HurlRequester", sb.toString());
                }
                this.f.disconnect();
                return bov;
            } else {
                InputStream b3 = b();
                if (b3 != null) {
                    z = true;
                }
                bov bov2 = new bov(b3, c2, d());
                if (!z) {
                    if (bpv.a(5)) {
                        StringBuilder sb2 = new StringBuilder("do not need keepConnection:");
                        sb2.append(this.f);
                        bpv.b("ANet-HurlRequester", sb2.toString());
                    }
                    this.f.disconnect();
                }
                return bov2;
            }
        } catch (Throwable th) {
            if (0 == 0) {
                if (bpv.a(5)) {
                    StringBuilder sb3 = new StringBuilder("do not need keepConnection:");
                    sb3.append(this.f);
                    bpv.b("ANet-HurlRequester", sb3.toString());
                }
                this.f.disconnect();
            }
            throw th;
        }
    }

    public final String toString() {
        return this.e.toString();
    }
}
