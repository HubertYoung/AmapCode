package defpackage;

/* renamed from: dbz reason: default package */
/* compiled from: QRScanUtils */
public final class dbz {
    /* JADX WARNING: Removed duplicated region for block: B:14:0x001d  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a() {
        /*
            r0 = 0
            android.hardware.Camera r1 = android.hardware.Camera.open()     // Catch:{ Exception -> 0x0021, all -> 0x0017 }
            android.hardware.Camera$Parameters r0 = r1.getParameters()     // Catch:{ Exception -> 0x0015, all -> 0x0013 }
            r1.setParameters(r0)     // Catch:{ Exception -> 0x0015, all -> 0x0013 }
            if (r1 == 0) goto L_0x0011
            r1.release()
        L_0x0011:
            r0 = 1
            goto L_0x0028
        L_0x0013:
            r0 = move-exception
            goto L_0x001b
        L_0x0015:
            r0 = r1
            goto L_0x0021
        L_0x0017:
            r1 = move-exception
            r2 = r1
            r1 = r0
            r0 = r2
        L_0x001b:
            if (r1 == 0) goto L_0x0020
            r1.release()
        L_0x0020:
            throw r0
        L_0x0021:
            r1 = 0
            if (r0 == 0) goto L_0x0027
            r0.release()
        L_0x0027:
            r0 = 0
        L_0x0028:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dbz.a():boolean");
    }
}
