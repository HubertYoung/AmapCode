package defpackage;

import java.io.Closeable;
import java.io.IOException;

/* renamed from: cwf reason: default package */
/* compiled from: IOUtils */
public final class cwf {
    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                cwl.a((String) "IOUtils", (T[]) new IOException[]{e});
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0027 A[SYNTHETIC, Splitter:B:16:0x0027] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0032 A[SYNTHETIC, Splitter:B:23:0x0032] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r3) {
        /*
            if (r3 == 0) goto L_0x0044
            int r0 = r3.length()
            if (r0 != 0) goto L_0x0009
            goto L_0x0044
        L_0x0009:
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>()
            r1 = 0
            java.util.zip.GZIPOutputStream r2 = new java.util.zip.GZIPOutputStream     // Catch:{ IOException -> 0x0030, all -> 0x0024 }
            r2.<init>(r0)     // Catch:{ IOException -> 0x0030, all -> 0x0024 }
            byte[] r3 = r3.getBytes()     // Catch:{ IOException -> 0x0022, all -> 0x001f }
            r2.write(r3)     // Catch:{ IOException -> 0x0022, all -> 0x001f }
            r2.close()     // Catch:{ IOException -> 0x0036 }
            goto L_0x003a
        L_0x001f:
            r3 = move-exception
            r1 = r2
            goto L_0x0025
        L_0x0022:
            r1 = r2
            goto L_0x0030
        L_0x0024:
            r3 = move-exception
        L_0x0025:
            if (r1 == 0) goto L_0x002f
            r1.close()     // Catch:{ IOException -> 0x002b }
            goto L_0x002f
        L_0x002b:
            r0 = move-exception
            r0.printStackTrace()
        L_0x002f:
            throw r3
        L_0x0030:
            if (r1 == 0) goto L_0x003a
            r1.close()     // Catch:{ IOException -> 0x0036 }
            goto L_0x003a
        L_0x0036:
            r3 = move-exception
            r3.printStackTrace()
        L_0x003a:
            byte[] r3 = r0.toByteArray()
            r0 = 0
            java.lang.String r3 = android.util.Base64.encodeToString(r3, r0)
            return r3
        L_0x0044:
            java.lang.String r3 = "null"
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cwf.a(java.lang.String):java.lang.String");
    }
}
