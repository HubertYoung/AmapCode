package defpackage;

import java.io.File;

/* renamed from: eom reason: default package */
/* compiled from: Utils */
public final class eom {
    private static void a(File file) {
        if (file != null && !file.exists()) {
            File file2 = new File(file.getParent());
            if (!file2.exists()) {
                file2.mkdirs();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0035 A[SYNTHETIC, Splitter:B:20:0x0035] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0041 A[SYNTHETIC, Splitter:B:27:0x0041] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(java.io.File r3, java.lang.String r4) {
        /*
            a(r3)
            r0 = 0
            java.io.FileWriter r1 = new java.io.FileWriter     // Catch:{ IOException -> 0x002f }
            r2 = 1
            r1.<init>(r3, r2)     // Catch:{ IOException -> 0x002f }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0029, all -> 0x0027 }
            r3.<init>()     // Catch:{ IOException -> 0x0029, all -> 0x0027 }
            r3.append(r4)     // Catch:{ IOException -> 0x0029, all -> 0x0027 }
            java.lang.String r4 = "\n"
            r3.append(r4)     // Catch:{ IOException -> 0x0029, all -> 0x0027 }
            java.lang.String r3 = r3.toString()     // Catch:{ IOException -> 0x0029, all -> 0x0027 }
            r1.write(r3)     // Catch:{ IOException -> 0x0029, all -> 0x0027 }
            r1.close()     // Catch:{ IOException -> 0x0022 }
            return
        L_0x0022:
            r3 = move-exception
            r3.printStackTrace()
            return
        L_0x0027:
            r3 = move-exception
            goto L_0x003f
        L_0x0029:
            r3 = move-exception
            r0 = r1
            goto L_0x0030
        L_0x002c:
            r3 = move-exception
            r1 = r0
            goto L_0x003f
        L_0x002f:
            r3 = move-exception
        L_0x0030:
            r3.printStackTrace()     // Catch:{ all -> 0x002c }
            if (r0 == 0) goto L_0x003e
            r0.close()     // Catch:{ IOException -> 0x0039 }
            goto L_0x003e
        L_0x0039:
            r3 = move-exception
            r3.printStackTrace()
            return
        L_0x003e:
            return
        L_0x003f:
            if (r1 == 0) goto L_0x0049
            r1.close()     // Catch:{ IOException -> 0x0045 }
            goto L_0x0049
        L_0x0045:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0049:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eom.a(java.io.File, java.lang.String):void");
    }
}
