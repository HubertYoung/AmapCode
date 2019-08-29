package com.alibaba.sqlcrypto;

import android.util.Log;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration;
import java.io.File;

public final class DefaultDatabaseErrorHandler implements DatabaseErrorHandler {
    private static final String TAG = "DefaultDatabaseErrorHandler";

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0050, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0051, code lost:
        r4 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0052, code lost:
        if (r0 != null) goto L_0x0054;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0054, code lost:
        r1 = r0.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005c, code lost:
        if (r1.hasNext() != false) goto L_0x005e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x005e, code lost:
        deleteDatabaseFile((java.lang.String) r1.next().second);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006c, code lost:
        deleteDatabaseFile(r7.getPath());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0073, code lost:
        throw r4;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0050 A[ExcHandler: all (r3v3 'th' java.lang.Throwable A[CUSTOM_DECLARE]), PHI: r0 
      PHI: (r0v2 'attachedDbs' java.util.List) = (r0v0 'attachedDbs' java.util.List), (r0v3 'attachedDbs' java.util.List), (r0v3 'attachedDbs' java.util.List) binds: [B:4:0x0027, B:6:0x002b, B:7:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:4:0x0027] */
    @android.annotation.TargetApi(5)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onCorruption(com.alibaba.sqlcrypto.sqlite.SQLiteDatabase r7) {
        /*
            r6 = this;
            java.lang.String r3 = "DefaultDatabaseErrorHandler"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "Corruption reported by sqlite on database: "
            r4.<init>(r5)
            java.lang.String r5 = r7.getPath()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            android.util.Log.e(r3, r4)
            boolean r3 = r7.isOpen()
            if (r3 != 0) goto L_0x0026
            java.lang.String r3 = r7.getPath()
            r6.deleteDatabaseFile(r3)
        L_0x0025:
            return
        L_0x0026:
            r0 = 0
            java.util.List r0 = r7.getAttachedDbs()     // Catch:{ SQLiteException -> 0x0074, all -> 0x0050 }
        L_0x002b:
            r7.close()     // Catch:{ SQLiteException -> 0x0076, all -> 0x0050 }
        L_0x002e:
            if (r0 == 0) goto L_0x0048
            java.util.Iterator r1 = r0.iterator()
        L_0x0034:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x0025
            java.lang.Object r2 = r1.next()
            android.util.Pair r2 = (android.util.Pair) r2
            java.lang.Object r3 = r2.second
            java.lang.String r3 = (java.lang.String) r3
            r6.deleteDatabaseFile(r3)
            goto L_0x0034
        L_0x0048:
            java.lang.String r3 = r7.getPath()
            r6.deleteDatabaseFile(r3)
            goto L_0x0025
        L_0x0050:
            r3 = move-exception
            r4 = r3
            if (r0 == 0) goto L_0x006c
            java.util.Iterator r1 = r0.iterator()
        L_0x0058:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x0073
            java.lang.Object r2 = r1.next()
            android.util.Pair r2 = (android.util.Pair) r2
            java.lang.Object r3 = r2.second
            java.lang.String r3 = (java.lang.String) r3
            r6.deleteDatabaseFile(r3)
            goto L_0x0058
        L_0x006c:
            java.lang.String r3 = r7.getPath()
            r6.deleteDatabaseFile(r3)
        L_0x0073:
            throw r4
        L_0x0074:
            r3 = move-exception
            goto L_0x002b
        L_0x0076:
            r3 = move-exception
            goto L_0x002e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sqlcrypto.DefaultDatabaseErrorHandler.onCorruption(com.alibaba.sqlcrypto.sqlite.SQLiteDatabase):void");
    }

    private void deleteDatabaseFile(String fileName) {
        if (!fileName.equalsIgnoreCase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH) && fileName.trim().length() != 0) {
            Log.e(TAG, "deleting the database file: " + fileName);
            try {
                SQLiteDatabase.deleteDatabase(new File(fileName));
            } catch (Exception e) {
                Log.w(TAG, "delete failed: " + e.getMessage());
            }
        }
    }
}
