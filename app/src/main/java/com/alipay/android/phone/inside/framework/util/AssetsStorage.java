package com.alipay.android.phone.inside.framework.util;

public class AssetsStorage {
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0026 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:18:0x002f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r4, android.content.res.AssetManager r5) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.io.InputStream r4 = r5.open(r4)     // Catch:{ Throwable -> 0x000a }
            goto L_0x000b
        L_0x000a:
            r4 = 0
        L_0x000b:
            if (r4 == 0) goto L_0x0032
            java.io.BufferedReader r5 = new java.io.BufferedReader
            java.io.InputStreamReader r1 = new java.io.InputStreamReader
            r1.<init>(r4)
            r5.<init>(r1)
            r1 = 2048(0x800, float:2.87E-42)
            char[] r1 = new char[r1]
        L_0x001b:
            int r2 = r5.read(r1)     // Catch:{ Throwable -> 0x0026, all -> 0x002a }
            if (r2 <= 0) goto L_0x0026
            r3 = 0
            r0.append(r1, r3, r2)     // Catch:{ Throwable -> 0x0026, all -> 0x002a }
            goto L_0x001b
        L_0x0026:
            r5.close()     // Catch:{ Exception -> 0x002f }
            goto L_0x002f
        L_0x002a:
            r4 = move-exception
            r5.close()     // Catch:{ Exception -> 0x002e }
        L_0x002e:
            throw r4
        L_0x002f:
            r4.close()     // Catch:{ Throwable -> 0x0032 }
        L_0x0032:
            java.lang.String r4 = r0.toString()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.framework.util.AssetsStorage.a(java.lang.String, android.content.res.AssetManager):java.lang.String");
    }
}
