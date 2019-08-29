package com.alibaba.wireless.security.framework.utils;

public class b {
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0031 A[SYNTHETIC, Splitter:B:20:0x0031] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0038 A[SYNTHETIC, Splitter:B:28:0x0038] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.io.File r3) {
        /*
            r0 = 0
            if (r3 == 0) goto L_0x003d
            boolean r1 = r3.exists()
            if (r1 != 0) goto L_0x000a
            return r0
        L_0x000a:
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0035, all -> 0x002e }
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Exception -> 0x0035, all -> 0x002e }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0035, all -> 0x002e }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0035, all -> 0x002e }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0036, all -> 0x002b }
            r3.<init>()     // Catch:{ Exception -> 0x0036, all -> 0x002b }
        L_0x0019:
            java.lang.String r2 = r1.readLine()     // Catch:{ Exception -> 0x0036, all -> 0x002b }
            if (r2 == 0) goto L_0x0023
            r3.append(r2)     // Catch:{ Exception -> 0x0036, all -> 0x002b }
            goto L_0x0019
        L_0x0023:
            r1.close()     // Catch:{ Exception -> 0x0036, all -> 0x002b }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0035, all -> 0x002e }
            return r3
        L_0x002b:
            r3 = move-exception
            r0 = r1
            goto L_0x002f
        L_0x002e:
            r3 = move-exception
        L_0x002f:
            if (r0 == 0) goto L_0x0034
            r0.close()     // Catch:{ Exception -> 0x0034 }
        L_0x0034:
            throw r3
        L_0x0035:
            r1 = r0
        L_0x0036:
            if (r1 == 0) goto L_0x003b
            r1.close()     // Catch:{ Exception -> 0x003b }
        L_0x003b:
            r3 = r0
            return r3
        L_0x003d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.framework.utils.b.a(java.io.File):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x004a A[SYNTHETIC, Splitter:B:21:0x004a] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0050 A[SYNTHETIC, Splitter:B:27:0x0050] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.io.File r5, java.lang.String r6) {
        /*
            r0 = 0
            if (r5 == 0) goto L_0x0055
            if (r6 != 0) goto L_0x0006
            return r0
        L_0x0006:
            java.io.File r1 = new java.io.File
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = r5.getAbsolutePath()
            r2.append(r3)
            java.lang.String r3 = ".tmp"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            r2 = 0
            boolean r3 = r1.exists()     // Catch:{ Exception -> 0x004e, all -> 0x0047 }
            if (r3 != 0) goto L_0x002a
            r1.createNewFile()     // Catch:{ Exception -> 0x004e, all -> 0x0047 }
        L_0x002a:
            java.io.BufferedWriter r3 = new java.io.BufferedWriter     // Catch:{ Exception -> 0x004e, all -> 0x0047 }
            java.io.FileWriter r4 = new java.io.FileWriter     // Catch:{ Exception -> 0x004e, all -> 0x0047 }
            r4.<init>(r1, r0)     // Catch:{ Exception -> 0x004e, all -> 0x0047 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x004e, all -> 0x0047 }
            r3.write(r6)     // Catch:{ Exception -> 0x0045, all -> 0x0042 }
            r3.flush()     // Catch:{ Exception -> 0x0045, all -> 0x0042 }
            r3.close()     // Catch:{ Exception -> 0x0045, all -> 0x0042 }
            boolean r5 = r1.renameTo(r5)     // Catch:{ Exception -> 0x004e, all -> 0x0047 }
            return r5
        L_0x0042:
            r5 = move-exception
            r2 = r3
            goto L_0x0048
        L_0x0045:
            r2 = r3
            goto L_0x004e
        L_0x0047:
            r5 = move-exception
        L_0x0048:
            if (r2 == 0) goto L_0x004d
            r2.close()     // Catch:{ Exception -> 0x004d }
        L_0x004d:
            throw r5
        L_0x004e:
            if (r2 == 0) goto L_0x0053
            r2.close()     // Catch:{ Exception -> 0x0053 }
        L_0x0053:
            r5 = 0
            return r5
        L_0x0055:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.framework.utils.b.a(java.io.File, java.lang.String):boolean");
    }

    public static boolean a(String str) {
        return !str.contains("..") && !str.contains("\\") && !str.contains("%");
    }
}
