package com.amap.location.sdk.setting;

import com.amap.bundle.cloudconfig.appinit.request.AppInitCallback;

/* compiled from: AgooSharePreference */
public class a {
    private static final String[] a = {"startTime", AppInitCallback.SP_KEY_endTime};

    /* renamed from: com.amap.location.sdk.setting.a$a reason: collision with other inner class name */
    /* compiled from: AgooSharePreference */
    public static class C0038a {
        public long a = -1;
        public long b = -1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0043, code lost:
        if (r9 != null) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0045, code lost:
        r9.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0048, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0034, code lost:
        if (r9 != null) goto L_0x0045;
     */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x004c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.amap.location.sdk.setting.a.C0038a a(android.content.Context r9) {
        /*
            com.amap.location.sdk.setting.a$a r0 = new com.amap.location.sdk.setting.a$a
            r0.<init>()
            r1 = 0
            android.content.ContentResolver r2 = r9.getContentResolver()     // Catch:{ Throwable -> 0x003c, all -> 0x0039 }
            android.net.Uri r3 = com.amap.location.sdk.setting.SdkSpContentProvider.a     // Catch:{ Throwable -> 0x003c, all -> 0x0039 }
            java.lang.String[] r4 = a     // Catch:{ Throwable -> 0x003c, all -> 0x0039 }
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r9 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x003c, all -> 0x0039 }
            boolean r1 = r9.moveToNext()     // Catch:{ Throwable -> 0x0037 }
            if (r1 == 0) goto L_0x0034
            java.lang.String r1 = "startTime"
            int r1 = r9.getColumnIndex(r1)     // Catch:{ Throwable -> 0x0037 }
            long r1 = r9.getLong(r1)     // Catch:{ Throwable -> 0x0037 }
            r0.a = r1     // Catch:{ Throwable -> 0x0037 }
            java.lang.String r1 = "endTime"
            int r1 = r9.getColumnIndex(r1)     // Catch:{ Throwable -> 0x0037 }
            long r1 = r9.getLong(r1)     // Catch:{ Throwable -> 0x0037 }
            r0.b = r1     // Catch:{ Throwable -> 0x0037 }
        L_0x0034:
            if (r9 == 0) goto L_0x0048
            goto L_0x0045
        L_0x0037:
            r1 = move-exception
            goto L_0x0040
        L_0x0039:
            r0 = move-exception
            r9 = r1
            goto L_0x004a
        L_0x003c:
            r9 = move-exception
            r8 = r1
            r1 = r9
            r9 = r8
        L_0x0040:
            com.amap.location.common.d.a.a(r1)     // Catch:{ all -> 0x0049 }
            if (r9 == 0) goto L_0x0048
        L_0x0045:
            r9.close()
        L_0x0048:
            return r0
        L_0x0049:
            r0 = move-exception
        L_0x004a:
            if (r9 == 0) goto L_0x004f
            r9.close()
        L_0x004f:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.sdk.setting.a.a(android.content.Context):com.amap.location.sdk.setting.a$a");
    }
}
