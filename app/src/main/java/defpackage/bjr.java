package defpackage;

import android.content.Context;
import android.graphics.Bitmap.CompressFormat;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: bjr reason: default package */
/* compiled from: ImageDiskCache */
public final class bjr {
    public static final CompressFormat a = CompressFormat.PNG;
    private static String f;
    protected int b = 32768;
    protected CompressFormat c = a;
    protected int d = 100;
    /* access modifiers changed from: private */
    public boh e;

    public bjr(final Context context, String str) {
        f = str;
        new Thread(new Runnable() {
            public final void run() {
                try {
                    File a2 = bjr.a(context);
                    bjr.this.e = boh.a(a2, bkh.a(a2), 2000);
                } catch (IOException unused) {
                }
            }
        }).start();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0032, code lost:
        return r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean a(java.lang.String r4, android.graphics.Bitmap r5) throws java.io.IOException {
        /*
            r3 = this;
            monitor-enter(r3)
            boh r0 = r3.e     // Catch:{ all -> 0x0038 }
            r1 = 0
            if (r0 != 0) goto L_0x0008
            monitor-exit(r3)
            return r1
        L_0x0008:
            boh r0 = r3.e     // Catch:{ all -> 0x0038 }
            boh$a r4 = r0.b(r4)     // Catch:{ all -> 0x0038 }
            if (r4 != 0) goto L_0x0012
            monitor-exit(r3)
            return r1
        L_0x0012:
            java.io.BufferedOutputStream r0 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x0038 }
            java.io.OutputStream r1 = r4.a()     // Catch:{ all -> 0x0038 }
            int r2 = r3.b     // Catch:{ all -> 0x0038 }
            r0.<init>(r1, r2)     // Catch:{ all -> 0x0038 }
            android.graphics.Bitmap$CompressFormat r1 = r3.c     // Catch:{ all -> 0x0033 }
            int r2 = r3.d     // Catch:{ all -> 0x0033 }
            boolean r5 = r5.compress(r1, r2, r0)     // Catch:{ all -> 0x0033 }
            defpackage.bkh.a(r0)     // Catch:{ all -> 0x0038 }
            if (r5 == 0) goto L_0x002e
            r4.b()     // Catch:{ all -> 0x0038 }
            goto L_0x0031
        L_0x002e:
            r4.c()     // Catch:{ all -> 0x0038 }
        L_0x0031:
            monitor-exit(r3)
            return r5
        L_0x0033:
            r4 = move-exception
            defpackage.bkh.a(r0)     // Catch:{ all -> 0x0038 }
            throw r4     // Catch:{ all -> 0x0038 }
        L_0x0038:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bjr.a(java.lang.String, android.graphics.Bitmap):boolean");
    }

    public final synchronized InputStream a(String str) {
        if (this.e == null) {
            return null;
        }
        try {
            c a2 = this.e.a(str);
            if (a2 == null) {
                return null;
            }
            return a2.a[0];
        } catch (IOException unused) {
            return null;
        }
    }

    static /* synthetic */ File a(Context context) {
        String a2 = bpw.a(context);
        if (TextUtils.isEmpty(f)) {
            StringBuilder sb = new StringBuilder();
            sb.append(a2);
            sb.append(File.separator);
            sb.append("autonavi");
            sb.append(File.separator);
            sb.append("httpcache");
            sb.append(File.separator);
            sb.append("image");
            return new File(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(a2);
        sb2.append(File.separator);
        sb2.append(f);
        return new File(sb2.toString());
    }
}
