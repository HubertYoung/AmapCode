package com.taobao.accs.statistics;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AdapterUtilityImpl;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DBHelper extends SQLiteOpenHelper {
    private static final int MAX_DB_COUNT = 4000;
    private static final int MAX_SQL_NUM = 5;
    private static final String TAG = "DBHelper";
    private static final Lock lock = new ReentrantLock();
    private static volatile DBHelper sInstance;
    LinkedList<SQLObject> cachedSql = new LinkedList<>();
    public int curLogsCount = 0;
    private Context mContext;

    class SQLObject {
        Object[] args;
        String sql;

        private SQLObject(String str, Object[] objArr) {
            this.sql = str;
            this.args = objArr;
        }
    }

    public SQLiteDatabase getWritableDatabase() {
        if (!AdapterUtilityImpl.checkIsWritable(super.getWritableDatabase().getPath(), 102400)) {
            return null;
        }
        return super.getWritableDatabase();
    }

    public static DBHelper getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DBHelper.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new DBHelper(context, Constants.DB_NAME, null, 3);
                    }
                }
            }
        }
        return sInstance;
    }

    private DBHelper(Context context, String str, CursorFactory cursorFactory, int i) {
        super(context, str, cursorFactory, i);
        this.mContext = context;
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            if (lock.tryLock()) {
                sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS traffic(_id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, host TEXT,serviceid TEXT, bid TEXT, isbackground TEXT, size TEXT)");
            }
        } finally {
            lock.unlock();
        }
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i < i2) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS service");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS network");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ping");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS msg");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ack");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS election");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS bindApp");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS bindUser");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS traffic");
            onCreate(sQLiteDatabase);
        }
    }

    public void onTraffics(String str, String str2, String str3, boolean z, long j, String str4) {
        if (!checkTrafficsExist(str, str2, str3, z, j, str4)) {
            execSQL("INSERT INTO traffic VALUES(null,?,?,?,?,?,?)", new Object[]{str4, str, str2, str3, String.valueOf(z), Long.valueOf(j)}, true);
            return;
        }
        execSQL("UPDATE traffic SET size=? WHERE date=? AND host=? AND bid=? AND isbackground=?", new Object[]{Long.valueOf(j), str4, str, str3, String.valueOf(z)}, true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004a, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004d, code lost:
        if (r3 != null) goto L_0x004f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0065, code lost:
        if (r3 != null) goto L_0x004f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0069, code lost:
        return false;
     */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x006e A[SYNTHETIC, Splitter:B:36:0x006e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean checkTrafficsExist(java.lang.String r14, java.lang.String r15, java.lang.String r16, boolean r17, long r18, java.lang.String r20) {
        /*
            r13 = this;
            monitor-enter(r13)
            r1 = 0
            r2 = 0
            android.database.sqlite.SQLiteDatabase r3 = r13.getWritableDatabase()     // Catch:{ Exception -> 0x0057, all -> 0x0053 }
            if (r3 != 0) goto L_0x000b
            monitor-exit(r13)
            return r2
        L_0x000b:
            java.lang.String r4 = "traffic"
            java.lang.String r5 = "_id"
            java.lang.String r6 = "date"
            java.lang.String r7 = "host"
            java.lang.String r8 = "serviceid"
            java.lang.String r9 = "bid"
            java.lang.String r10 = "isbackground"
            java.lang.String r11 = "size"
            java.lang.String[] r5 = new java.lang.String[]{r5, r6, r7, r8, r9, r10, r11}     // Catch:{ Exception -> 0x0057, all -> 0x0053 }
            java.lang.String r6 = "date=? AND host=? AND bid=? AND isbackground=?"
            r7 = 4
            java.lang.String[] r7 = new java.lang.String[r7]     // Catch:{ Exception -> 0x0057, all -> 0x0053 }
            r7[r2] = r20     // Catch:{ Exception -> 0x0057, all -> 0x0053 }
            r12 = 1
            r7[r12] = r14     // Catch:{ Exception -> 0x0057, all -> 0x0053 }
            r8 = 2
            r7[r8] = r16     // Catch:{ Exception -> 0x0057, all -> 0x0053 }
            r8 = 3
            java.lang.String r9 = java.lang.String.valueOf(r17)     // Catch:{ Exception -> 0x0057, all -> 0x0053 }
            r7[r8] = r9     // Catch:{ Exception -> 0x0057, all -> 0x0053 }
            r8 = 0
            r9 = 0
            r10 = 0
            java.lang.String r11 = "100"
            android.database.Cursor r3 = r3.query(r4, r5, r6, r7, r8, r9, r10, r11)     // Catch:{ Exception -> 0x0057, all -> 0x0053 }
            if (r3 == 0) goto L_0x004d
            int r1 = r3.getCount()     // Catch:{ Exception -> 0x004b }
            if (r1 <= 0) goto L_0x004d
            if (r3 == 0) goto L_0x0049
            r3.close()     // Catch:{ all -> 0x0072 }
        L_0x0049:
            monitor-exit(r13)
            return r12
        L_0x004b:
            r0 = move-exception
            goto L_0x0059
        L_0x004d:
            if (r3 == 0) goto L_0x0068
        L_0x004f:
            r3.close()     // Catch:{ all -> 0x0072 }
            goto L_0x0068
        L_0x0053:
            r0 = move-exception
            r3 = r1
        L_0x0055:
            r1 = r0
            goto L_0x006c
        L_0x0057:
            r0 = move-exception
            r3 = r1
        L_0x0059:
            r1 = r0
            java.lang.String r4 = "DBHelper"
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x006a }
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ all -> 0x006a }
            com.taobao.accs.utl.ALog.w(r4, r1, r5)     // Catch:{ all -> 0x006a }
            if (r3 == 0) goto L_0x0068
            goto L_0x004f
        L_0x0068:
            monitor-exit(r13)
            return r2
        L_0x006a:
            r0 = move-exception
            goto L_0x0055
        L_0x006c:
            if (r3 == 0) goto L_0x0075
            r3.close()     // Catch:{ all -> 0x0072 }
            goto L_0x0075
        L_0x0072:
            r0 = move-exception
            r1 = r0
            goto L_0x0076
        L_0x0075:
            throw r1     // Catch:{ all -> 0x0072 }
        L_0x0076:
            monitor-exit(r13)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.statistics.DBHelper.checkTrafficsExist(java.lang.String, java.lang.String, java.lang.String, boolean, long, java.lang.String):boolean");
    }

    public void clearTraffics() {
        execSQL("DELETE FROM traffic", null, true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0066, code lost:
        return null;
     */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00c8 A[SYNTHETIC, Splitter:B:45:0x00c8] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00cf A[Catch:{ all -> 0x00d3 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.taobao.accs.ut.monitor.TrafficsMonitor.TrafficInfo> getTraffics(boolean r15) {
        /*
            r14 = this;
            monitor-enter(r14)
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x00d3 }
            r0.<init>()     // Catch:{ all -> 0x00d3 }
            r1 = 0
            r2 = 0
            android.database.sqlite.SQLiteDatabase r3 = r14.getWritableDatabase()     // Catch:{ Exception -> 0x00ba }
            if (r3 != 0) goto L_0x0010
            monitor-exit(r14)     // Catch:{ all -> 0x00d3 }
            return r2
        L_0x0010:
            r12 = 1
            if (r15 == 0) goto L_0x003f
            java.lang.String r4 = "traffic"
            java.lang.String r5 = "_id"
            java.lang.String r6 = "date"
            java.lang.String r7 = "host"
            java.lang.String r8 = "serviceid"
            java.lang.String r9 = "bid"
            java.lang.String r10 = "isbackground"
            java.lang.String r11 = "size"
            java.lang.String[] r5 = new java.lang.String[]{r5, r6, r7, r8, r9, r10, r11}     // Catch:{ Exception -> 0x00ba }
            java.lang.String r6 = "date=?"
            java.lang.String[] r7 = new java.lang.String[r12]     // Catch:{ Exception -> 0x00ba }
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00ba }
            java.lang.String r15 = com.taobao.accs.utl.UtilityImpl.formatDay(r8)     // Catch:{ Exception -> 0x00ba }
            r7[r1] = r15     // Catch:{ Exception -> 0x00ba }
            r8 = 0
            r9 = 0
            r10 = 0
            java.lang.String r11 = "100"
            android.database.Cursor r15 = r3.query(r4, r5, r6, r7, r8, r9, r10, r11)     // Catch:{ Exception -> 0x00ba }
            goto L_0x005e
        L_0x003f:
            java.lang.String r4 = "traffic"
            java.lang.String r5 = "_id"
            java.lang.String r6 = "date"
            java.lang.String r7 = "host"
            java.lang.String r8 = "serviceid"
            java.lang.String r9 = "bid"
            java.lang.String r10 = "isbackground"
            java.lang.String r11 = "size"
            java.lang.String[] r5 = new java.lang.String[]{r5, r6, r7, r8, r9, r10, r11}     // Catch:{ Exception -> 0x00ba }
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            java.lang.String r11 = "100"
            android.database.Cursor r15 = r3.query(r4, r5, r6, r7, r8, r9, r10, r11)     // Catch:{ Exception -> 0x00ba }
        L_0x005e:
            if (r15 != 0) goto L_0x0067
            if (r15 == 0) goto L_0x0065
            r15.close()     // Catch:{ all -> 0x00d3 }
        L_0x0065:
            monitor-exit(r14)     // Catch:{ all -> 0x00d3 }
            return r2
        L_0x0067:
            boolean r2 = r15.moveToFirst()     // Catch:{ Exception -> 0x00b3, all -> 0x00af }
            if (r2 == 0) goto L_0x00a9
        L_0x006d:
            java.lang.String r4 = r15.getString(r12)     // Catch:{ Exception -> 0x00b3, all -> 0x00af }
            r2 = 2
            java.lang.String r8 = r15.getString(r2)     // Catch:{ Exception -> 0x00b3, all -> 0x00af }
            r2 = 3
            java.lang.String r6 = r15.getString(r2)     // Catch:{ Exception -> 0x00b3, all -> 0x00af }
            r2 = 4
            java.lang.String r5 = r15.getString(r2)     // Catch:{ Exception -> 0x00b3, all -> 0x00af }
            r2 = 5
            java.lang.String r2 = r15.getString(r2)     // Catch:{ Exception -> 0x00b3, all -> 0x00af }
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)     // Catch:{ Exception -> 0x00b3, all -> 0x00af }
            boolean r7 = r2.booleanValue()     // Catch:{ Exception -> 0x00b3, all -> 0x00af }
            r2 = 6
            long r9 = r15.getLong(r2)     // Catch:{ Exception -> 0x00b3, all -> 0x00af }
            if (r5 == 0) goto L_0x00a3
            r2 = 0
            int r2 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x00a3
            com.taobao.accs.ut.monitor.TrafficsMonitor$TrafficInfo r2 = new com.taobao.accs.ut.monitor.TrafficsMonitor$TrafficInfo     // Catch:{ Exception -> 0x00b3, all -> 0x00af }
            r3 = r2
            r3.<init>(r4, r5, r6, r7, r8, r9)     // Catch:{ Exception -> 0x00b3, all -> 0x00af }
            r0.add(r2)     // Catch:{ Exception -> 0x00b3, all -> 0x00af }
        L_0x00a3:
            boolean r2 = r15.moveToNext()     // Catch:{ Exception -> 0x00b3, all -> 0x00af }
            if (r2 != 0) goto L_0x006d
        L_0x00a9:
            if (r15 == 0) goto L_0x00cb
            r15.close()     // Catch:{ all -> 0x00d3 }
            goto L_0x00cb
        L_0x00af:
            r0 = move-exception
            r2 = r15
            r15 = r0
            goto L_0x00cd
        L_0x00b3:
            r2 = move-exception
            r13 = r2
            r2 = r15
            r15 = r13
            goto L_0x00bb
        L_0x00b8:
            r15 = move-exception
            goto L_0x00cd
        L_0x00ba:
            r15 = move-exception
        L_0x00bb:
            java.lang.String r3 = "DBHelper"
            java.lang.String r15 = r15.toString()     // Catch:{ all -> 0x00b8 }
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x00b8 }
            com.taobao.accs.utl.ALog.w(r3, r15, r1)     // Catch:{ all -> 0x00b8 }
            if (r2 == 0) goto L_0x00cb
            r2.close()     // Catch:{ all -> 0x00d3 }
        L_0x00cb:
            monitor-exit(r14)     // Catch:{ all -> 0x00d3 }
            return r0
        L_0x00cd:
            if (r2 == 0) goto L_0x00d2
            r2.close()     // Catch:{ all -> 0x00d3 }
        L_0x00d2:
            throw r15     // Catch:{ all -> 0x00d3 }
        L_0x00d3:
            r15 = move-exception
            monitor-exit(r14)     // Catch:{ all -> 0x00d3 }
            throw r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.statistics.DBHelper.getTraffics(boolean):java.util.List");
    }

    private synchronized void execSQL(String str, Object[] objArr, boolean z) {
        SQLiteDatabase writableDatabase;
        this.cachedSql.add(new SQLObject(str, objArr));
        if (this.cachedSql.size() > 5 || z) {
            try {
                writableDatabase = getWritableDatabase();
                if (writableDatabase != null) {
                    while (true) {
                        if (this.cachedSql.size() > 0) {
                            SQLObject removeFirst = this.cachedSql.removeFirst();
                            if (removeFirst.args != null) {
                                writableDatabase.execSQL(removeFirst.sql, removeFirst.args);
                            } else {
                                writableDatabase.execSQL(removeFirst.sql);
                            }
                            if (removeFirst.sql.contains("INSERT")) {
                                this.curLogsCount++;
                                if (this.curLogsCount > 4000) {
                                    ALog.d(TAG, "db is full!", new Object[0]);
                                    onUpgrade(writableDatabase, 0, 1);
                                    this.curLogsCount = 0;
                                    break;
                                }
                            }
                        }
                    }
                    writableDatabase.close();
                }
            } catch (Exception e) {
                ALog.d(TAG, e.toString(), new Object[0]);
            } catch (Throwable th) {
                writableDatabase.close();
                throw th;
            }
        }
    }
}
