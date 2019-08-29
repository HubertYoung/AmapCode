package com.ta.audid.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.ta.audid.utils.UtdidLogger;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class SqliteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private DelayCloseDbTask mCloseDbTask = new DelayCloseDbTask();
    /* access modifiers changed from: private */
    public AtomicInteger mWritableCounter = new AtomicInteger();
    /* access modifiers changed from: private */
    public SQLiteDatabase mWritableDatabase;
    private Future<?> mcloseFuture;

    class DelayCloseDbTask implements Runnable {
        DelayCloseDbTask() {
        }

        public void run() {
            synchronized (SqliteHelper.this) {
                if (SqliteHelper.this.mWritableCounter.get() == 0 && SqliteHelper.this.mWritableDatabase != null) {
                    SqliteHelper.this.mWritableDatabase.close();
                    SqliteHelper.this.mWritableDatabase = null;
                }
            }
        }
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public SqliteHelper(Context context, String str) {
        super(context, str, null, 2);
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [java.lang.String[], android.database.Cursor] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v0, types: [java.lang.String[], android.database.Cursor]
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY]]
      uses: [android.database.Cursor, java.lang.String[]]
      mth insns count: 10
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onOpen(android.database.sqlite.SQLiteDatabase r3) {
        /*
            r2 = this;
            r0 = 0
            java.lang.String r1 = "PRAGMA journal_mode=DELETE"
            android.database.Cursor r1 = r3.rawQuery(r1, r0)     // Catch:{ Throwable -> 0x0010, all -> 0x000b }
            r2.closeCursor(r1)
            goto L_0x0013
        L_0x000b:
            r3 = move-exception
            r2.closeCursor(r0)
            throw r3
        L_0x0010:
            r2.closeCursor(r0)
        L_0x0013:
            super.onOpen(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.db.SqliteHelper.onOpen(android.database.sqlite.SQLiteDatabase):void");
    }

    public synchronized SQLiteDatabase getWritableDatabase() {
        try {
            if (this.mWritableDatabase == null) {
                this.mWritableDatabase = super.getWritableDatabase();
            }
            this.mWritableCounter.incrementAndGet();
        } catch (Throwable th) {
            UtdidLogger.w(RPCDataItems.SWITCH_TAG_LOG, "e", th);
        }
        return this.mWritableDatabase;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0027, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void closeWritableDatabase(android.database.sqlite.SQLiteDatabase r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r5 != 0) goto L_0x0005
            monitor-exit(r4)
            return
        L_0x0005:
            java.util.concurrent.atomic.AtomicInteger r5 = r4.mWritableCounter     // Catch:{ Throwable -> 0x002b, all -> 0x0028 }
            int r5 = r5.decrementAndGet()     // Catch:{ Throwable -> 0x002b, all -> 0x0028 }
            if (r5 != 0) goto L_0x0026
            java.util.concurrent.Future<?> r5 = r4.mcloseFuture     // Catch:{ Throwable -> 0x002b, all -> 0x0028 }
            if (r5 == 0) goto L_0x0017
            java.util.concurrent.Future<?> r5 = r4.mcloseFuture     // Catch:{ Throwable -> 0x002b, all -> 0x0028 }
            r0 = 0
            r5.cancel(r0)     // Catch:{ Throwable -> 0x002b, all -> 0x0028 }
        L_0x0017:
            com.ta.audid.utils.TaskExecutor r5 = com.ta.audid.utils.TaskExecutor.getInstance()     // Catch:{ Throwable -> 0x002b, all -> 0x0028 }
            r0 = 0
            com.ta.audid.db.SqliteHelper$DelayCloseDbTask r1 = r4.mCloseDbTask     // Catch:{ Throwable -> 0x002b, all -> 0x0028 }
            r2 = 30000(0x7530, double:1.4822E-319)
            java.util.concurrent.ScheduledFuture r5 = r5.schedule(r0, r1, r2)     // Catch:{ Throwable -> 0x002b, all -> 0x0028 }
            r4.mcloseFuture = r5     // Catch:{ Throwable -> 0x002b, all -> 0x0028 }
        L_0x0026:
            monitor-exit(r4)
            return
        L_0x0028:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        L_0x002b:
            monitor-exit(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.db.SqliteHelper.closeWritableDatabase(android.database.sqlite.SQLiteDatabase):void");
    }

    public void closeCursor(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Throwable unused) {
            }
        }
    }
}
