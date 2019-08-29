package defpackage;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: fgj reason: default package */
/* compiled from: ResponseBody */
public abstract class fgj {
    private byte[] a = null;

    public abstract long a() throws IOException;

    public abstract InputStream b();

    public byte[] c() throws IOException {
        if (this.a == null) {
            this.a = d();
        }
        return this.a;
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0067 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0068  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private byte[] d() throws java.io.IOException {
        /*
            r8 = this;
            long r0 = r8.a()
            r2 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x001b
            java.io.IOException r2 = new java.io.IOException
            java.lang.String r3 = "Cannot buffer entire body for content length: "
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r0 = r3.concat(r0)
            r2.<init>(r0)
            throw r2
        L_0x001b:
            java.io.InputStream r2 = r8.b()
            r3 = 0
            java.io.DataInputStream r4 = new java.io.DataInputStream     // Catch:{ Exception -> 0x0054, all -> 0x0050 }
            r4.<init>(r2)     // Catch:{ Exception -> 0x0054, all -> 0x0050 }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x004d, all -> 0x004a }
            r2.<init>()     // Catch:{ Exception -> 0x004d, all -> 0x004a }
            r5 = 1024(0x400, float:1.435E-42)
            byte[] r5 = new byte[r5]     // Catch:{ Exception -> 0x0048 }
        L_0x002e:
            int r6 = r4.read(r5)     // Catch:{ Exception -> 0x0048 }
            r7 = -1
            if (r6 == r7) goto L_0x003a
            r7 = 0
            r2.write(r5, r7, r6)     // Catch:{ Exception -> 0x0048 }
            goto L_0x002e
        L_0x003a:
            r2.flush()     // Catch:{ Exception -> 0x0048 }
            byte[] r5 = r2.toByteArray()     // Catch:{ Exception -> 0x0048 }
            defpackage.fgp.a(r4)
            defpackage.fgp.a(r2)
            goto L_0x0065
        L_0x0048:
            r5 = move-exception
            goto L_0x0057
        L_0x004a:
            r0 = move-exception
            r2 = r3
            goto L_0x007e
        L_0x004d:
            r5 = move-exception
            r2 = r3
            goto L_0x0057
        L_0x0050:
            r0 = move-exception
            r2 = r3
            r4 = r2
            goto L_0x007e
        L_0x0054:
            r5 = move-exception
            r2 = r3
            r4 = r2
        L_0x0057:
            java.lang.String r6 = "mtopsdk.ResponseBody"
            java.lang.String r7 = "[readBytes] read bytes from byteStream error."
            mtopsdk.common.util.TBSdkLog.b(r6, r7, r5)     // Catch:{ all -> 0x007d }
            defpackage.fgp.a(r4)
            defpackage.fgp.a(r2)
            r5 = r3
        L_0x0065:
            if (r5 != 0) goto L_0x0068
            return r3
        L_0x0068:
            r2 = -1
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 == 0) goto L_0x007c
            int r2 = r5.length
            long r2 = (long) r2
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 == 0) goto L_0x007c
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "Content-Length and stream length disagree"
            r0.<init>(r1)
            throw r0
        L_0x007c:
            return r5
        L_0x007d:
            r0 = move-exception
        L_0x007e:
            defpackage.fgp.a(r4)
            defpackage.fgp.a(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fgj.d():byte[]");
    }
}
