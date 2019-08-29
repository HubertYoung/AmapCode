package defpackage;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import com.alipay.sdk.util.h;
import com.danikula.videocache.InterruptedProxyCacheException;
import com.danikula.videocache.ProxyCacheException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map.Entry;

/* renamed from: eto reason: default package */
/* compiled from: HttpUrlSource */
public final class eto implements etu {
    private final eui a;
    private final euf b;
    private etv c;
    private HttpURLConnection d;
    private InputStream e;

    private eto(String str, eui eui) {
        this(str, eui, new eue());
    }

    public eto(String str, eui eui, euf euf) {
        this.a = (eui) etr.a(eui);
        this.b = (euf) etr.a(euf);
        etv a2 = eui.a(str);
        if (a2 == null) {
            MimeTypeMap singleton = MimeTypeMap.getSingleton();
            String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(str);
            a2 = new etv(str, -2147483648L, TextUtils.isEmpty(fileExtensionFromUrl) ? null : singleton.getMimeTypeFromExtension(fileExtensionFromUrl));
        }
        this.c = a2;
    }

    public eto(eto eto) {
        this.c = eto.c;
        this.a = eto.a;
        this.b = eto.b;
    }

    public final synchronized long a() throws ProxyCacheException {
        try {
            if (this.c.b == -2147483648L) {
                d();
            }
        }
        return this.c.b;
    }

    public final void a(long j) throws ProxyCacheException {
        try {
            this.d = a(j, -1);
            String contentType = this.d.getContentType();
            this.e = new BufferedInputStream(this.d.getInputStream(), 8192);
            this.c = new etv(this.c.a, a(this.d, j, this.d.getResponseCode()), contentType);
            this.a.a(this.c.a, this.c);
        } catch (IOException e2) {
            StringBuilder sb = new StringBuilder("Error opening connection for ");
            sb.append(this.c.a);
            sb.append(" with offset ");
            sb.append(j);
            throw new ProxyCacheException(sb.toString(), e2);
        }
    }

    private long a(HttpURLConnection httpURLConnection, long j, int i) throws IOException {
        long a2 = a(httpURLConnection);
        if (i == 200) {
            return a2;
        }
        if (i == 206) {
            return a2 + j;
        }
        return this.c.b;
    }

    private static long a(HttpURLConnection httpURLConnection) {
        String headerField = httpURLConnection.getHeaderField("Content-Length");
        if (headerField == null) {
            return -1;
        }
        return Long.parseLong(headerField);
    }

    public final void b() throws ProxyCacheException {
        if (this.d != null) {
            try {
                this.d.disconnect();
            } catch (IllegalArgumentException | NullPointerException e2) {
                throw new RuntimeException("Wait... but why? WTF!? Really shouldn't happen any more after fixing https://github.com/danikula/AndroidVideoCache/issues/43. If you read it on your device log, please, notify me danikula@gmail.com or create issue here https://github.com/danikula/AndroidVideoCache/issues.", e2);
            } catch (ArrayIndexOutOfBoundsException unused) {
            }
        }
    }

    public final int a(byte[] bArr) throws ProxyCacheException {
        if (this.e == null) {
            StringBuilder sb = new StringBuilder("Error reading data from ");
            sb.append(this.c.a);
            sb.append(": connection is absent!");
            throw new ProxyCacheException(sb.toString());
        }
        try {
            return this.e.read(bArr, 0, bArr.length);
        } catch (InterruptedIOException e2) {
            StringBuilder sb2 = new StringBuilder("Reading source ");
            sb2.append(this.c.a);
            sb2.append(" is interrupted");
            throw new InterruptedProxyCacheException(sb2.toString(), e2);
        } catch (IOException e3) {
            StringBuilder sb3 = new StringBuilder("Error reading data from ");
            sb3.append(this.c.a);
            throw new ProxyCacheException(sb3.toString(), e3);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x004d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void d() throws com.danikula.videocache.ProxyCacheException {
        /*
            r7 = this;
            r0 = 0
            r2 = 10000(0x2710, float:1.4013E-41)
            r3 = 0
            java.net.HttpURLConnection r0 = r7.a(r0, r2)     // Catch:{ IOException -> 0x0047, all -> 0x003b }
            long r1 = a(r0)     // Catch:{ IOException -> 0x0048, all -> 0x0038 }
            java.lang.String r4 = r0.getContentType()     // Catch:{ IOException -> 0x0048, all -> 0x0038 }
            java.io.InputStream r5 = r0.getInputStream()     // Catch:{ IOException -> 0x0048, all -> 0x0038 }
            etv r3 = new etv     // Catch:{ IOException -> 0x0036, all -> 0x0034 }
            etv r6 = r7.c     // Catch:{ IOException -> 0x0036, all -> 0x0034 }
            java.lang.String r6 = r6.a     // Catch:{ IOException -> 0x0036, all -> 0x0034 }
            r3.<init>(r6, r1, r4)     // Catch:{ IOException -> 0x0036, all -> 0x0034 }
            r7.c = r3     // Catch:{ IOException -> 0x0036, all -> 0x0034 }
            eui r1 = r7.a     // Catch:{ IOException -> 0x0036, all -> 0x0034 }
            etv r2 = r7.c     // Catch:{ IOException -> 0x0036, all -> 0x0034 }
            java.lang.String r2 = r2.a     // Catch:{ IOException -> 0x0036, all -> 0x0034 }
            etv r3 = r7.c     // Catch:{ IOException -> 0x0036, all -> 0x0034 }
            r1.a(r2, r3)     // Catch:{ IOException -> 0x0036, all -> 0x0034 }
            defpackage.ett.a(r5)
            if (r0 == 0) goto L_0x0051
            r0.disconnect()
            return
        L_0x0034:
            r1 = move-exception
            goto L_0x003e
        L_0x0036:
            r3 = r5
            goto L_0x0048
        L_0x0038:
            r1 = move-exception
            r5 = r3
            goto L_0x003e
        L_0x003b:
            r1 = move-exception
            r0 = r3
            r5 = r0
        L_0x003e:
            defpackage.ett.a(r5)
            if (r0 == 0) goto L_0x0046
            r0.disconnect()
        L_0x0046:
            throw r1
        L_0x0047:
            r0 = r3
        L_0x0048:
            defpackage.ett.a(r3)
            if (r0 == 0) goto L_0x0051
            r0.disconnect()
            return
        L_0x0051:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eto.d():void");
    }

    private HttpURLConnection a(long j, int i) throws IOException, ProxyCacheException {
        HttpURLConnection httpURLConnection;
        boolean z;
        String str = this.c.a;
        int i2 = 0;
        do {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            b(httpURLConnection);
            if (j > 0) {
                StringBuilder sb = new StringBuilder("bytes=");
                sb.append(j);
                sb.append("-");
                httpURLConnection.setRequestProperty("Range", sb.toString());
            }
            if (i > 0) {
                httpURLConnection.setConnectTimeout(i);
                httpURLConnection.setReadTimeout(i);
            }
            int responseCode = httpURLConnection.getResponseCode();
            z = responseCode == 301 || responseCode == 302 || responseCode == 303;
            if (z) {
                str = httpURLConnection.getHeaderField("Location");
                i2++;
                httpURLConnection.disconnect();
            }
            if (i2 > 5) {
                throw new ProxyCacheException("Too many redirects: ".concat(String.valueOf(i2)));
            }
        } while (z);
        return httpURLConnection;
    }

    private void b(HttpURLConnection httpURLConnection) {
        for (Entry next : this.b.a().entrySet()) {
            httpURLConnection.setRequestProperty((String) next.getKey(), (String) next.getValue());
        }
    }

    public final synchronized String c() throws ProxyCacheException {
        try {
            if (TextUtils.isEmpty(this.c.c)) {
                d();
            }
        }
        return this.c.c;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("HttpUrlSource{sourceInfo='");
        sb.append(this.c);
        sb.append(h.d);
        return sb.toString();
    }

    public eto(String str) {
        this(str, new euh());
    }
}
