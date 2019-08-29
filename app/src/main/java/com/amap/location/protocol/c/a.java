package com.amap.location.protocol.c;

import android.content.Context;
import android.text.TextUtils;
import com.amap.location.common.f.e;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* compiled from: RequestLogger */
public class a {
    private static final SimpleDateFormat c = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.ENGLISH);
    private static final Object e = new Object();
    private Context a;
    private String b;
    private boh d;

    public a(Context context) {
        this.a = context;
        try {
            this.b = b();
            synchronized (e) {
                this.d = boh.a(new File(this.b), com.amap.location.protocol.b.a.i, com.amap.location.protocol.b.a.j);
            }
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0055, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(@android.support.annotation.NonNull byte[] r7) {
        /*
            r6 = this;
            java.lang.Object r0 = e
            monitor-enter(r0)
            java.lang.String r1 = r6.b     // Catch:{ all -> 0x0056 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0056 }
            if (r1 != 0) goto L_0x0054
            if (r7 == 0) goto L_0x0054
            boh r1 = r6.d     // Catch:{ all -> 0x0056 }
            if (r1 != 0) goto L_0x0012
            goto L_0x0054
        L_0x0012:
            r1 = 0
            boh r2 = r6.d     // Catch:{ Exception -> 0x0047 }
            java.lang.String r3 = r6.c()     // Catch:{ Exception -> 0x0047 }
            boh$a r2 = r2.b(r3)     // Catch:{ Exception -> 0x0047 }
            java.io.OutputStream r3 = r2.a()     // Catch:{ Exception -> 0x0047 }
            r3.write(r7)     // Catch:{ Exception -> 0x0041, all -> 0x003f }
            r2.b()     // Catch:{ Exception -> 0x0041, all -> 0x003f }
            boh r7 = r6.d     // Catch:{ Exception -> 0x0041, all -> 0x003f }
            long r1 = r7.a()     // Catch:{ Exception -> 0x0041, all -> 0x003f }
            r4 = 100
            long r1 = r1 % r4
            r4 = 0
            int r7 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r7 != 0) goto L_0x003b
            boh r7 = r6.d     // Catch:{ Exception -> 0x0041, all -> 0x003f }
            r7.b()     // Catch:{ Exception -> 0x0041, all -> 0x003f }
        L_0x003b:
            com.amap.location.common.f.g.a(r3)     // Catch:{ all -> 0x0056 }
            goto L_0x004e
        L_0x003f:
            r7 = move-exception
            goto L_0x0050
        L_0x0041:
            r7 = move-exception
            r1 = r3
            goto L_0x0048
        L_0x0044:
            r7 = move-exception
            r3 = r1
            goto L_0x0050
        L_0x0047:
            r7 = move-exception
        L_0x0048:
            com.amap.location.common.d.a.a(r7)     // Catch:{ all -> 0x0044 }
            com.amap.location.common.f.g.a(r1)     // Catch:{ all -> 0x0056 }
        L_0x004e:
            monitor-exit(r0)     // Catch:{ all -> 0x0056 }
            return
        L_0x0050:
            com.amap.location.common.f.g.a(r3)     // Catch:{ all -> 0x0056 }
            throw r7     // Catch:{ all -> 0x0056 }
        L_0x0054:
            monitor-exit(r0)     // Catch:{ all -> 0x0056 }
            return
        L_0x0056:
            r7 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0056 }
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.protocol.c.a.a(byte[]):void");
    }

    public void a() {
        synchronized (e) {
            try {
                if (this.d != null) {
                    this.d.close();
                    this.d = null;
                }
            } catch (Exception e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
            }
        }
    }

    private String b() {
        StringBuilder sb = new StringBuilder();
        String a2 = e.a(this.a);
        if (!TextUtils.isEmpty(a2)) {
            sb.append(a2);
            sb.append(File.separator);
            sb.append("req_5.2");
        }
        return sb.toString();
    }

    private String c() {
        String format;
        synchronized (c) {
            format = c.format(new Date());
        }
        return format;
    }
}
