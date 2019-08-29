package defpackage;

import android.database.sqlite.SQLiteDatabase;

/* renamed from: tx reason: default package */
/* compiled from: CommonOfflineDbHelper */
public final class tx {
    private static volatile tx b;
    public tz a;
    private SQLiteDatabase c;

    /* JADX WARNING: Missing exception handler attribute for start block: B:20:0x0064 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private tx() {
        /*
            r7 = this;
            r7.<init>()
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x0086 }
            android.content.Context r0 = r0.getApplicationContext()     // Catch:{ Exception -> 0x0086 }
            java.lang.String r1 = "OfflineDbV6.db"
            java.io.File r0 = r0.getDatabasePath(r1)     // Catch:{ Throwable -> 0x0064 }
            java.lang.String r0 = r0.getPath()     // Catch:{ Throwable -> 0x0064 }
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x0064 }
            r1.<init>(r0)     // Catch:{ Throwable -> 0x0064 }
            boolean r1 = r1.exists()     // Catch:{ Throwable -> 0x0064 }
            if (r1 != 0) goto L_0x0021
            goto L_0x0064
        L_0x0021:
            r1 = 16
            r2 = 0
            android.database.sqlite.SQLiteDatabase r0 = android.database.sqlite.SQLiteDatabase.openDatabase(r0, r2, r1, r2)     // Catch:{ Throwable -> 0x0064 }
            if (r0 != 0) goto L_0x002b
            goto L_0x0064
        L_0x002b:
            java.util.Locale r1 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x005b }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x005b }
            android.content.ContentValues r3 = new android.content.ContentValues     // Catch:{ Exception -> 0x005b }
            r3.<init>()     // Catch:{ Exception -> 0x005b }
            java.lang.String r4 = "locale"
            r3.put(r4, r1)     // Catch:{ Exception -> 0x005b }
            java.lang.String r4 = "android_metadata"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005b }
            java.lang.String r6 = "locale!='"
            r5.<init>(r6)     // Catch:{ Exception -> 0x005b }
            r5.append(r1)     // Catch:{ Exception -> 0x005b }
            java.lang.String r1 = "'"
            r5.append(r1)     // Catch:{ Exception -> 0x005b }
            java.lang.String r1 = r5.toString()     // Catch:{ Exception -> 0x005b }
            r0.update(r4, r3, r1, r2)     // Catch:{ Exception -> 0x005b }
        L_0x0055:
            r0.close()     // Catch:{ Throwable -> 0x0064 }
            goto L_0x0064
        L_0x0059:
            r1 = move-exception
            goto L_0x0060
        L_0x005b:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ all -> 0x0059 }
            goto L_0x0055
        L_0x0060:
            r0.close()     // Catch:{ Throwable -> 0x0064 }
            throw r1     // Catch:{ Throwable -> 0x0064 }
        L_0x0064:
            ty$a r0 = new ty$a     // Catch:{ Exception -> 0x0086 }
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x0086 }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Exception -> 0x0086 }
            java.lang.String r2 = "OfflineDbV6.db"
            r0.<init>(r1, r2)     // Catch:{ Exception -> 0x0086 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Exception -> 0x0086 }
            r7.c = r0     // Catch:{ Exception -> 0x0086 }
            ty r0 = new ty     // Catch:{ Exception -> 0x0086 }
            android.database.sqlite.SQLiteDatabase r1 = r7.c     // Catch:{ Exception -> 0x0086 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x0086 }
            tz r0 = r0.newSession()     // Catch:{ Exception -> 0x0086 }
            r7.a = r0     // Catch:{ Exception -> 0x0086 }
        L_0x0086:
            java.lang.String r0 = "CommonOfflineDbHelper"
            java.lang.String r1 = "CommonOfflineDbHelper newInstance"
            com.amap.bundle.logs.AMapLog.e(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.tx.<init>():void");
    }

    public static synchronized tx a() {
        tx txVar;
        synchronized (tx.class) {
            try {
                if (b == null) {
                    b = new tx();
                }
                txVar = b;
            }
        }
        return txVar;
    }
}
