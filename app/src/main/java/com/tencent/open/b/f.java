package com.tencent.open.b;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.tencent.open.utils.Global;

/* compiled from: ProGuard */
public class f extends SQLiteOpenHelper {
    protected static final String[] a = {"key"};
    protected static f b;

    public static synchronized f a() {
        f fVar;
        synchronized (f.class) {
            try {
                if (b == null) {
                    b = new f(Global.getContext());
                }
                fVar = b;
            }
        }
        return fVar;
    }

    public f(Context context) {
        super(context, "sdk_report.db", null, 2);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS via_cgi_report( _id INTEGER PRIMARY KEY,key TEXT,type TEXT,blob BLOB);");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS via_cgi_report");
        onCreate(sQLiteDatabase);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:28|(0)|34|35|36|37) */
    /* JADX WARNING: Can't wrap try/catch for region: R(8:41|(0)|44|45|46|(0)|52|(0)) */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0087, code lost:
        if (r1 == null) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0089, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x009d, code lost:
        if (r1 != null) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x00a1, code lost:
        return r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x0056 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:34:0x0063 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:36:0x0066 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:44:0x006d */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0060 A[SYNTHETIC, Splitter:B:32:0x0060] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x006a A[SYNTHETIC, Splitter:B:42:0x006a] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0073 A[SYNTHETIC, Splitter:B:50:0x0073] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0084 A[SYNTHETIC, Splitter:B:58:0x0084] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x00a4 A[SYNTHETIC, Splitter:B:75:0x00a4] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x00a9 A[Catch:{ all -> 0x00ad }] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x0082 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.util.List<java.io.Serializable> a(java.lang.String r12) {
        /*
            r11 = this;
            monitor-enter(r11)
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x00ad }
            r0.<init>()     // Catch:{ all -> 0x00ad }
            java.util.List r0 = java.util.Collections.synchronizedList(r0)     // Catch:{ all -> 0x00ad }
            boolean r1 = android.text.TextUtils.isEmpty(r12)     // Catch:{ all -> 0x00ad }
            if (r1 == 0) goto L_0x0012
            monitor-exit(r11)
            return r0
        L_0x0012:
            android.database.sqlite.SQLiteDatabase r1 = r11.getReadableDatabase()     // Catch:{ all -> 0x00ad }
            if (r1 != 0) goto L_0x001a
            monitor-exit(r11)
            return r0
        L_0x001a:
            r10 = 0
            java.lang.String r3 = "via_cgi_report"
            r4 = 0
            java.lang.String r5 = "type = ?"
            r2 = 1
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0090 }
            r2 = 0
            r6[r2] = r12     // Catch:{ Exception -> 0x0090 }
            r7 = 0
            r8 = 0
            r9 = 0
            r2 = r1
            android.database.Cursor r12 = r2.query(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ Exception -> 0x0090 }
            if (r12 == 0) goto L_0x0082
            int r2 = r12.getCount()     // Catch:{ Exception -> 0x007f, all -> 0x007d }
            if (r2 <= 0) goto L_0x0082
            r12.moveToFirst()     // Catch:{ Exception -> 0x007f, all -> 0x007d }
        L_0x0039:
            java.lang.String r2 = "blob"
            int r2 = r12.getColumnIndex(r2)     // Catch:{ Exception -> 0x007f, all -> 0x007d }
            byte[] r2 = r12.getBlob(r2)     // Catch:{ Exception -> 0x007f, all -> 0x007d }
            java.io.ByteArrayInputStream r3 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x007f, all -> 0x007d }
            r3.<init>(r2)     // Catch:{ Exception -> 0x007f, all -> 0x007d }
            java.io.ObjectInputStream r2 = new java.io.ObjectInputStream     // Catch:{ Exception -> 0x0067, all -> 0x005c }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0067, all -> 0x005c }
            java.lang.Object r4 = r2.readObject()     // Catch:{ Exception -> 0x0068, all -> 0x005a }
            java.io.Serializable r4 = (java.io.Serializable) r4     // Catch:{ Exception -> 0x0068, all -> 0x005a }
            r2.close()     // Catch:{ IOException -> 0x0056 }
        L_0x0056:
            r3.close()     // Catch:{ IOException -> 0x0071 }
            goto L_0x0071
        L_0x005a:
            r4 = move-exception
            goto L_0x005e
        L_0x005c:
            r4 = move-exception
            r2 = r10
        L_0x005e:
            if (r2 == 0) goto L_0x0063
            r2.close()     // Catch:{ IOException -> 0x0063 }
        L_0x0063:
            r3.close()     // Catch:{ IOException -> 0x0066 }
        L_0x0066:
            throw r4     // Catch:{ Exception -> 0x007f, all -> 0x007d }
        L_0x0067:
            r2 = r10
        L_0x0068:
            if (r2 == 0) goto L_0x006d
            r2.close()     // Catch:{ IOException -> 0x006d }
        L_0x006d:
            r3.close()     // Catch:{ IOException -> 0x0070 }
        L_0x0070:
            r4 = r10
        L_0x0071:
            if (r4 == 0) goto L_0x0076
            r0.add(r4)     // Catch:{ Exception -> 0x007f, all -> 0x007d }
        L_0x0076:
            boolean r2 = r12.moveToNext()     // Catch:{ Exception -> 0x007f, all -> 0x007d }
            if (r2 != 0) goto L_0x0039
            goto L_0x0082
        L_0x007d:
            r0 = move-exception
            goto L_0x00a2
        L_0x007f:
            r2 = move-exception
            r10 = r12
            goto L_0x0091
        L_0x0082:
            if (r12 == 0) goto L_0x0087
            r12.close()     // Catch:{ all -> 0x00ad }
        L_0x0087:
            if (r1 == 0) goto L_0x00a0
        L_0x0089:
            r1.close()     // Catch:{ all -> 0x00ad }
            goto L_0x00a0
        L_0x008d:
            r0 = move-exception
            r12 = r10
            goto L_0x00a2
        L_0x0090:
            r2 = move-exception
        L_0x0091:
            java.lang.String r12 = "openSDK_LOG.ReportDatabaseHelper"
            java.lang.String r3 = "getReportItemFromDB has exception."
            com.tencent.open.a.f.b(r12, r3, r2)     // Catch:{ all -> 0x008d }
            if (r10 == 0) goto L_0x009d
            r10.close()     // Catch:{ all -> 0x00ad }
        L_0x009d:
            if (r1 == 0) goto L_0x00a0
            goto L_0x0089
        L_0x00a0:
            monitor-exit(r11)
            return r0
        L_0x00a2:
            if (r12 == 0) goto L_0x00a7
            r12.close()     // Catch:{ all -> 0x00ad }
        L_0x00a7:
            if (r1 == 0) goto L_0x00ac
            r1.close()     // Catch:{ all -> 0x00ad }
        L_0x00ac:
            throw r0     // Catch:{ all -> 0x00ad }
        L_0x00ad:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.b.f.a(java.lang.String):java.util.List");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:36|37|(0)|42|43|44|45) */
    /* JADX WARNING: Can't wrap try/catch for region: R(9:26|27|(6:28|29|30|31|32|33)|34|35|52|54|55|86) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:34:0x004e */
    /* JADX WARNING: Missing exception handler attribute for start block: B:42:0x005b */
    /* JADX WARNING: Missing exception handler attribute for start block: B:44:0x005e */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0058 A[SYNTHETIC, Splitter:B:40:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0062 A[SYNTHETIC, Splitter:B:50:0x0062] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(java.lang.String r9, java.util.List<java.io.Serializable> r10) {
        /*
            r8 = this;
            monitor-enter(r8)
            int r0 = r10.size()     // Catch:{ all -> 0x00a5 }
            if (r0 != 0) goto L_0x0009
            monitor-exit(r8)
            return
        L_0x0009:
            r1 = 20
            if (r0 > r1) goto L_0x000e
            goto L_0x0010
        L_0x000e:
            r0 = 20
        L_0x0010:
            boolean r1 = android.text.TextUtils.isEmpty(r9)     // Catch:{ all -> 0x00a5 }
            if (r1 == 0) goto L_0x0018
            monitor-exit(r8)
            return
        L_0x0018:
            r8.b(r9)     // Catch:{ all -> 0x00a5 }
            android.database.sqlite.SQLiteDatabase r1 = r8.getWritableDatabase()     // Catch:{ all -> 0x00a5 }
            if (r1 != 0) goto L_0x0023
            monitor-exit(r8)
            return
        L_0x0023:
            r1.beginTransaction()     // Catch:{ all -> 0x00a5 }
            android.content.ContentValues r2 = new android.content.ContentValues     // Catch:{ Exception -> 0x0089 }
            r2.<init>()     // Catch:{ Exception -> 0x0089 }
            r3 = 0
        L_0x002c:
            if (r3 >= r0) goto L_0x007a
            java.lang.Object r4 = r10.get(r3)     // Catch:{ Exception -> 0x0089 }
            java.io.Serializable r4 = (java.io.Serializable) r4     // Catch:{ Exception -> 0x0089 }
            if (r4 == 0) goto L_0x0074
            java.lang.String r5 = "type"
            r2.put(r5, r9)     // Catch:{ Exception -> 0x0089 }
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0089 }
            r6 = 512(0x200, float:7.175E-43)
            r5.<init>(r6)     // Catch:{ Exception -> 0x0089 }
            r6 = 0
            java.io.ObjectOutputStream r7 = new java.io.ObjectOutputStream     // Catch:{ IOException -> 0x005f, all -> 0x0055 }
            r7.<init>(r5)     // Catch:{ IOException -> 0x005f, all -> 0x0055 }
            r7.writeObject(r4)     // Catch:{ IOException -> 0x0060, all -> 0x0052 }
            r7.close()     // Catch:{ IOException -> 0x004e }
        L_0x004e:
            r5.close()     // Catch:{ IOException -> 0x0066 }
            goto L_0x0066
        L_0x0052:
            r9 = move-exception
            r6 = r7
            goto L_0x0056
        L_0x0055:
            r9 = move-exception
        L_0x0056:
            if (r6 == 0) goto L_0x005b
            r6.close()     // Catch:{ IOException -> 0x005b }
        L_0x005b:
            r5.close()     // Catch:{ IOException -> 0x005e }
        L_0x005e:
            throw r9     // Catch:{ Exception -> 0x0089 }
        L_0x005f:
            r7 = r6
        L_0x0060:
            if (r7 == 0) goto L_0x004e
            r7.close()     // Catch:{ IOException -> 0x004e }
            goto L_0x004e
        L_0x0066:
            java.lang.String r4 = "blob"
            byte[] r5 = r5.toByteArray()     // Catch:{ Exception -> 0x0089 }
            r2.put(r4, r5)     // Catch:{ Exception -> 0x0089 }
            java.lang.String r4 = "via_cgi_report"
            r1.insert(r4, r6, r2)     // Catch:{ Exception -> 0x0089 }
        L_0x0074:
            r2.clear()     // Catch:{ Exception -> 0x0089 }
            int r3 = r3 + 1
            goto L_0x002c
        L_0x007a:
            r1.setTransactionSuccessful()     // Catch:{ Exception -> 0x0089 }
            r1.endTransaction()     // Catch:{ all -> 0x00a5 }
            if (r1 == 0) goto L_0x009a
            r1.close()     // Catch:{ all -> 0x00a5 }
            monitor-exit(r8)
            return
        L_0x0087:
            r9 = move-exception
            goto L_0x009c
        L_0x0089:
            java.lang.String r9 = "openSDK_LOG.ReportDatabaseHelper"
            java.lang.String r10 = "saveReportItemToDB has exception."
            com.tencent.open.a.f.e(r9, r10)     // Catch:{ all -> 0x0087 }
            r1.endTransaction()     // Catch:{ all -> 0x00a5 }
            if (r1 == 0) goto L_0x009a
            r1.close()     // Catch:{ all -> 0x00a5 }
            monitor-exit(r8)
            return
        L_0x009a:
            monitor-exit(r8)
            return
        L_0x009c:
            r1.endTransaction()     // Catch:{ all -> 0x00a5 }
            if (r1 == 0) goto L_0x00a4
            r1.close()     // Catch:{ all -> 0x00a5 }
        L_0x00a4:
            throw r9     // Catch:{ all -> 0x00a5 }
        L_0x00a5:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.b.f.a(java.lang.String, java.util.List):void");
    }

    public synchronized void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase != null) {
                try {
                    writableDatabase.delete("via_cgi_report", "type = ?", new String[]{str});
                    if (writableDatabase != null) {
                        writableDatabase.close();
                    }
                } catch (Exception e) {
                    try {
                        com.tencent.open.a.f.b("openSDK_LOG.ReportDatabaseHelper", "clearReportItem has exception.", e);
                        if (writableDatabase != null) {
                            writableDatabase.close();
                        }
                    } finally {
                        if (writableDatabase != null) {
                            writableDatabase.close();
                        }
                    }
                }
            }
        }
    }
}
