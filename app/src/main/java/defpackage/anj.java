package defpackage;

/* renamed from: anj reason: default package */
/* compiled from: AMapSignature */
public final class anj {
    private static Boolean a;

    public static boolean a() {
        return !b();
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0055  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean b() {
        /*
            java.lang.Boolean r0 = a
            if (r0 != 0) goto L_0x005d
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            r1 = 13686(0x3576, float:1.9178E-41)
            r2 = 1
            r3 = 0
            android.content.pm.PackageManager r4 = r0.getPackageManager()     // Catch:{ Exception -> 0x0049, all -> 0x0046 }
            java.lang.String r0 = r0.getPackageName()     // Catch:{ Exception -> 0x0049, all -> 0x0046 }
            r5 = 64
            android.content.pm.PackageInfo r0 = r4.getPackageInfo(r0, r5)     // Catch:{ Exception -> 0x0049, all -> 0x0046 }
            if (r0 == 0) goto L_0x003a
            android.content.pm.Signature[] r0 = r0.signatures     // Catch:{ Exception -> 0x0049, all -> 0x0046 }
            int r4 = r0.length     // Catch:{ Exception -> 0x0049, all -> 0x0046 }
            r5 = 0
        L_0x0020:
            if (r5 >= r4) goto L_0x003a
            r6 = r0[r5]     // Catch:{ Exception -> 0x0049, all -> 0x0046 }
            byte[] r6 = r6.toByteArray()     // Catch:{ Exception -> 0x0049, all -> 0x0046 }
            int r7 = r6.length     // Catch:{ Exception -> 0x0049, all -> 0x0046 }
            r8 = 0
            r9 = 0
        L_0x002b:
            if (r8 >= r7) goto L_0x0035
            byte r10 = r6[r8]     // Catch:{ Exception -> 0x0033 }
            int r9 = r9 + r10
            int r8 = r8 + 1
            goto L_0x002b
        L_0x0033:
            r0 = move-exception
            goto L_0x004b
        L_0x0035:
            if (r9 == r1) goto L_0x003b
            int r5 = r5 + 1
            goto L_0x0020
        L_0x003a:
            r9 = 0
        L_0x003b:
            if (r1 != r9) goto L_0x003e
            goto L_0x003f
        L_0x003e:
            r2 = 0
        L_0x003f:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r2)
            a = r0
            goto L_0x005d
        L_0x0046:
            r0 = move-exception
            r9 = 0
            goto L_0x0052
        L_0x0049:
            r0 = move-exception
            r9 = 0
        L_0x004b:
            java.lang.RuntimeException r4 = new java.lang.RuntimeException     // Catch:{ all -> 0x0051 }
            r4.<init>(r0)     // Catch:{ all -> 0x0051 }
            throw r4     // Catch:{ all -> 0x0051 }
        L_0x0051:
            r0 = move-exception
        L_0x0052:
            if (r1 != r9) goto L_0x0055
            goto L_0x0056
        L_0x0055:
            r2 = 0
        L_0x0056:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r2)
            a = r1
            throw r0
        L_0x005d:
            java.lang.Boolean r0 = a
            boolean r0 = r0.booleanValue()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.anj.b():boolean");
    }
}
