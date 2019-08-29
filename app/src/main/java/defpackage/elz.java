package defpackage;

/* renamed from: elz reason: default package */
/* compiled from: Utils */
public final class elz {
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0047 A[SYNTHETIC, Splitter:B:23:0x0047] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x004f A[Catch:{ Exception -> 0x004b }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x005b A[SYNTHETIC, Splitter:B:35:0x005b] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0063 A[Catch:{ Exception -> 0x005f }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r5) {
        /*
            r0 = 0
            java.lang.Runtime r1 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x0057, all -> 0x0043 }
            java.lang.String r2 = "getprop "
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x0057, all -> 0x0043 }
            java.lang.String r5 = r2.concat(r5)     // Catch:{ Throwable -> 0x0057, all -> 0x0043 }
            java.lang.Process r5 = r1.exec(r5)     // Catch:{ Throwable -> 0x0057, all -> 0x0043 }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0041, all -> 0x003c }
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0041, all -> 0x003c }
            java.io.InputStream r3 = r5.getInputStream()     // Catch:{ Throwable -> 0x0041, all -> 0x003c }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0041, all -> 0x003c }
            r3 = 1024(0x400, float:1.435E-42)
            r1.<init>(r2, r3)     // Catch:{ Throwable -> 0x0041, all -> 0x003c }
            java.lang.String r2 = r1.readLine()     // Catch:{ Throwable -> 0x0059, all -> 0x0036 }
            r1.close()     // Catch:{ Exception -> 0x0030 }
            if (r5 == 0) goto L_0x0034
            r5.destroy()     // Catch:{ Exception -> 0x0030 }
            goto L_0x0034
        L_0x0030:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0034:
            r0 = r2
            goto L_0x006a
        L_0x0036:
            r0 = move-exception
            r4 = r1
            r1 = r5
            r5 = r0
            r0 = r4
            goto L_0x0045
        L_0x003c:
            r1 = move-exception
            r4 = r1
            r1 = r5
            r5 = r4
            goto L_0x0045
        L_0x0041:
            r1 = r0
            goto L_0x0059
        L_0x0043:
            r5 = move-exception
            r1 = r0
        L_0x0045:
            if (r0 == 0) goto L_0x004d
            r0.close()     // Catch:{ Exception -> 0x004b }
            goto L_0x004d
        L_0x004b:
            r0 = move-exception
            goto L_0x0053
        L_0x004d:
            if (r1 == 0) goto L_0x0056
            r1.destroy()     // Catch:{ Exception -> 0x004b }
            goto L_0x0056
        L_0x0053:
            r0.printStackTrace()
        L_0x0056:
            throw r5
        L_0x0057:
            r5 = r0
            r1 = r5
        L_0x0059:
            if (r1 == 0) goto L_0x0061
            r1.close()     // Catch:{ Exception -> 0x005f }
            goto L_0x0061
        L_0x005f:
            r5 = move-exception
            goto L_0x0067
        L_0x0061:
            if (r5 == 0) goto L_0x006a
            r5.destroy()     // Catch:{ Exception -> 0x005f }
            goto L_0x006a
        L_0x0067:
            r5.printStackTrace()
        L_0x006a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.elz.a(java.lang.String):java.lang.String");
    }

    public static boolean a() {
        int i;
        int i2;
        try {
            String a = a("ro.build.version.emui");
            if (a != null) {
                String[] split = a.split("_");
                if (split != null && split.length > 1) {
                    String[] split2 = split[1].split("\\.");
                    if (split2 != null) {
                        for (int i3 = 0; i3 < split2.length; i3++) {
                            if (split2[0] != null) {
                                i = Integer.parseInt(split2[0]);
                                if (i > 3) {
                                    return true;
                                }
                                if (i < 3) {
                                    return false;
                                }
                                if (i == 3 && split2[1] != null) {
                                    try {
                                        i2 = Integer.parseInt(split2[1]);
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                        i2 = 0;
                                    }
                                    if (i2 > 1) {
                                        return true;
                                    }
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            i = 0;
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return false;
    }
}
