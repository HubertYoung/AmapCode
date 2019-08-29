package com.sijla.g.b.b.a;

import java.io.File;

public class a implements com.sijla.g.b.b.a {
    private File a;

    public a(File file) {
        this.a = file;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0047 A[Catch:{ all -> 0x0031 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.sijla.g.b.a.a a(int r7, java.io.InputStream r8) {
        /*
            r6 = this;
            com.sijla.g.b.a.a r7 = new com.sijla.g.b.a.a
            r7.<init>()
            r0 = 1
            r1 = 0
            r2 = 0
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0033 }
            java.io.File r4 = r6.a     // Catch:{ Exception -> 0x0033 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0033 }
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r2]     // Catch:{ Exception -> 0x002e, all -> 0x002b }
        L_0x0013:
            int r4 = r8.read(r2)     // Catch:{ Exception -> 0x002e, all -> 0x002b }
            r5 = -1
            if (r4 == r5) goto L_0x001e
            r3.write(r2, r1, r4)     // Catch:{ Exception -> 0x002e, all -> 0x002b }
            goto L_0x0013
        L_0x001e:
            r3.flush()     // Catch:{ Exception -> 0x002e, all -> 0x002b }
            java.io.File r8 = r6.a     // Catch:{ Exception -> 0x002e, all -> 0x002b }
            r7.a(r8)     // Catch:{ Exception -> 0x002e, all -> 0x002b }
            java.io.Closeable[] r8 = new java.io.Closeable[r0]
            r8[r1] = r3
            goto L_0x0050
        L_0x002b:
            r7 = move-exception
            r2 = r3
            goto L_0x0054
        L_0x002e:
            r8 = move-exception
            r2 = r3
            goto L_0x0034
        L_0x0031:
            r7 = move-exception
            goto L_0x0054
        L_0x0033:
            r8 = move-exception
        L_0x0034:
            r8.printStackTrace()     // Catch:{ all -> 0x0031 }
            r3 = 414(0x19e, float:5.8E-43)
            r7.a(r3)     // Catch:{ all -> 0x0031 }
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x0031 }
            r7.a(r8)     // Catch:{ all -> 0x0031 }
            java.io.File r8 = r6.a     // Catch:{ all -> 0x0031 }
            if (r8 == 0) goto L_0x004c
            java.io.File r8 = r6.a     // Catch:{ all -> 0x0031 }
            r8.delete()     // Catch:{ all -> 0x0031 }
        L_0x004c:
            java.io.Closeable[] r8 = new java.io.Closeable[r0]
            r8[r1] = r2
        L_0x0050:
            com.sijla.g.b.a(r8)
            return r7
        L_0x0054:
            java.io.Closeable[] r8 = new java.io.Closeable[r0]
            r8[r1] = r2
            com.sijla.g.b.a(r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sijla.g.b.b.a.a.a(int, java.io.InputStream):com.sijla.g.b.a.a");
    }
}
