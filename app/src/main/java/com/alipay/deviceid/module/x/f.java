package com.alipay.deviceid.module.x;

public final class f {
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003c, code lost:
        if (r4 != null) goto L_0x002d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0037 A[SYNTHETIC, Splitter:B:16:0x0037] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r4, java.lang.String r5) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ IOException -> 0x003b, all -> 0x0034 }
            r2.<init>(r4, r5)     // Catch:{ IOException -> 0x003b, all -> 0x0034 }
            boolean r4 = r2.exists()     // Catch:{ IOException -> 0x003b, all -> 0x0034 }
            if (r4 != 0) goto L_0x0012
            return r1
        L_0x0012:
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ IOException -> 0x003b, all -> 0x0034 }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x003b, all -> 0x0034 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ IOException -> 0x003b, all -> 0x0034 }
            r3.<init>(r2)     // Catch:{ IOException -> 0x003b, all -> 0x0034 }
            java.lang.String r2 = "UTF-8"
            r5.<init>(r3, r2)     // Catch:{ IOException -> 0x003b, all -> 0x0034 }
            r4.<init>(r5)     // Catch:{ IOException -> 0x003b, all -> 0x0034 }
        L_0x0023:
            java.lang.String r5 = r4.readLine()     // Catch:{ IOException -> 0x003c, all -> 0x0031 }
            if (r5 == 0) goto L_0x002d
            r0.append(r5)     // Catch:{ IOException -> 0x003c, all -> 0x0031 }
            goto L_0x0023
        L_0x002d:
            r4.close()     // Catch:{ Throwable -> 0x003f }
            goto L_0x003f
        L_0x0031:
            r5 = move-exception
            r1 = r4
            goto L_0x0035
        L_0x0034:
            r5 = move-exception
        L_0x0035:
            if (r1 == 0) goto L_0x003a
            r1.close()     // Catch:{ Throwable -> 0x003a }
        L_0x003a:
            throw r5
        L_0x003b:
            r4 = r1
        L_0x003c:
            if (r4 == 0) goto L_0x003f
            goto L_0x002d
        L_0x003f:
            java.lang.String r4 = r0.toString()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.deviceid.module.x.f.a(java.lang.String, java.lang.String):java.lang.String");
    }
}
