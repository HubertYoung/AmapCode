package com.amap.location.b.c;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.HttpFileUpMMTask;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.amap.location.b.e.b;
import com.amap.location.common.f.g;
import java.util.Locale;

/* compiled from: DbManager */
public class f {
    private static final String[] a = {"id", "type", "data", "size"};
    private a b;
    private long c = a(true);
    private long d = a(false);

    /* compiled from: DbManager */
    static class a extends SQLiteOpenHelper {
        a(Context context, String str, int i) {
            super(context, str, null, i);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            try {
                sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS base (id INTEGER PRIMARY KEY AUTOINCREMENT , type SMALLINT, data BLOB, size INTEGER, time INTEGER);");
            } catch (Exception e) {
                com.amap.location.common.d.a.a((Throwable) e);
            }
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            try {
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS base");
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS byte_base");
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS extend");
                onCreate(sQLiteDatabase);
            } catch (Exception e) {
                com.amap.location.common.d.a.a((Throwable) e);
            }
        }

        public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            try {
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS base");
                onCreate(sQLiteDatabase);
            } catch (Exception e) {
                com.amap.location.common.d.a.a((Throwable) e);
            }
        }
    }

    public f(Context context) {
        this.b = new a(context, "aloccoll.db", 4);
    }

    public void a() {
        try {
            if (this.b != null) {
                this.b.close();
                this.b = null;
            }
        } catch (Exception e) {
            com.amap.location.common.d.a.a((Throwable) e);
        }
    }

    public b a(boolean z, int i, long j) {
        Cursor cursor;
        b bVar = new b();
        b bVar2 = null;
        try {
            cursor = this.b.getReadableDatabase().query(RpcConstant.BASE, a, z ? "type=0" : "type!=0", null, null, null, "id ASC", String.valueOf(i));
            int i2 = 0;
            while (cursor.moveToNext()) {
                try {
                    int i3 = cursor.getInt(3);
                    if (((long) bVar.c) >= j || ((long) (bVar.c + i3)) > j || i2 >= i) {
                        break;
                    }
                    bVar.a = cursor.getLong(0);
                    bVar.b.add(new d(cursor.getInt(1), cursor.getBlob(2)));
                    bVar.c += i3;
                    i2++;
                } catch (Exception e) {
                    e = e;
                    try {
                        com.amap.location.common.d.a.a((Throwable) e);
                        g.a(cursor);
                        return bVar2;
                    } catch (Throwable th) {
                        th = th;
                        g.a(cursor);
                        throw th;
                    }
                }
            }
            com.amap.location.common.d.a.b("DbManager", String.format(Locale.getDefault(), "get col content： %b, %d，%d，%d", new Object[]{Boolean.valueOf(z), Long.valueOf(bVar.a), Integer.valueOf(i2), Integer.valueOf(bVar.c)}));
            if (bVar.c != 0) {
                bVar2 = bVar;
            }
        } catch (Exception e2) {
            e = e2;
            cursor = null;
            com.amap.location.common.d.a.a((Throwable) e);
            g.a(cursor);
            return bVar2;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            g.a(cursor);
            throw th;
        }
        g.a(cursor);
        return bVar2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x00df A[SYNTHETIC, Splitter:B:37:0x00df] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00e4 A[SYNTHETIC, Splitter:B:41:0x00e4] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00ed A[SYNTHETIC, Splitter:B:48:0x00ed] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00f2 A[SYNTHETIC, Splitter:B:52:0x00f2] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(java.util.List<com.amap.location.b.c.d> r20) {
        /*
            r19 = this;
            r1 = r19
            r2 = 0
            com.amap.location.b.c.f$a r3 = r1.b     // Catch:{ Exception -> 0x00d6, all -> 0x00d1 }
            android.database.sqlite.SQLiteDatabase r3 = r3.getWritableDatabase()     // Catch:{ Exception -> 0x00d6, all -> 0x00d1 }
            r3.beginTransaction()     // Catch:{ Exception -> 0x00ce, all -> 0x00cb }
            java.lang.String r4 = "INSERT INTO base(type,data,size,time) VALUES(?,?,?,?)"
            android.database.sqlite.SQLiteStatement r4 = r3.compileStatement(r4)     // Catch:{ Exception -> 0x00ce, all -> 0x00cb }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00c9 }
            java.util.Iterator r2 = r20.iterator()     // Catch:{ Exception -> 0x00c9 }
            r8 = 0
            r10 = r8
            r12 = 0
        L_0x001e:
            boolean r13 = r2.hasNext()     // Catch:{ Exception -> 0x00c9 }
            r14 = 1
            if (r13 == 0) goto L_0x005c
            java.lang.Object r13 = r2.next()     // Catch:{ Exception -> 0x00c9 }
            com.amap.location.b.c.d r13 = (com.amap.location.b.c.d) r13     // Catch:{ Exception -> 0x00c9 }
            int r15 = r13.b()     // Catch:{ Exception -> 0x00c9 }
            r16 = r8
            long r7 = (long) r15     // Catch:{ Exception -> 0x00c9 }
            r4.bindLong(r14, r7)     // Catch:{ Exception -> 0x00c9 }
            byte[] r7 = r13.c()     // Catch:{ Exception -> 0x00c9 }
            r8 = 2
            r4.bindBlob(r8, r7)     // Catch:{ Exception -> 0x00c9 }
            long r7 = r13.a()     // Catch:{ Exception -> 0x00c9 }
            r9 = 3
            r4.bindLong(r9, r7)     // Catch:{ Exception -> 0x00c9 }
            r9 = 4
            r4.bindLong(r9, r5)     // Catch:{ Exception -> 0x00c9 }
            r4.executeInsert()     // Catch:{ Exception -> 0x00c9 }
            int r9 = r13.b()     // Catch:{ Exception -> 0x00c9 }
            if (r9 != 0) goto L_0x0057
            long r8 = r16 + r7
            int r12 = r12 + 1
            goto L_0x001e
        L_0x0057:
            r9 = 0
            long r10 = r10 + r7
            r8 = r16
            goto L_0x001e
        L_0x005c:
            r16 = r8
            r3.setTransactionSuccessful()     // Catch:{ Exception -> 0x00c9 }
            long r7 = r1.c     // Catch:{ Exception -> 0x00c9 }
            r2 = 0
            long r7 = r7 + r16
            r1.c = r7     // Catch:{ Exception -> 0x00c9 }
            long r7 = r1.d     // Catch:{ Exception -> 0x00c9 }
            r2 = 0
            long r7 = r7 + r10
            r1.d = r7     // Catch:{ Exception -> 0x00c9 }
            java.lang.String r2 = "DbManager"
            java.util.Locale r7 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x00c9 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c9 }
            java.lang.String r9 = "insert t:"
            r8.<init>(r9)     // Catch:{ Exception -> 0x00c9 }
            r8.append(r5)     // Catch:{ Exception -> 0x00c9 }
            java.lang.String r5 = "; net count:%d，%d; other: %d，%d"
            r8.append(r5)     // Catch:{ Exception -> 0x00c9 }
            java.lang.String r5 = r8.toString()     // Catch:{ Exception -> 0x00c9 }
            r6 = 5
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Exception -> 0x00c9 }
            int r8 = r20.size()     // Catch:{ Exception -> 0x00c9 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ Exception -> 0x00c9 }
            r9 = 0
            r6[r9] = r8     // Catch:{ Exception -> 0x00c9 }
            r8 = r16
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch:{ Exception -> 0x00c9 }
            r6[r14] = r8     // Catch:{ Exception -> 0x00c9 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r12)     // Catch:{ Exception -> 0x00c9 }
            r9 = 2
            r6[r9] = r8     // Catch:{ Exception -> 0x00c9 }
            java.lang.Long r8 = java.lang.Long.valueOf(r10)     // Catch:{ Exception -> 0x00c9 }
            r9 = 3
            r6[r9] = r8     // Catch:{ Exception -> 0x00c9 }
            int r8 = r20.size()     // Catch:{ Exception -> 0x00c9 }
            int r8 = r8 - r12
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ Exception -> 0x00c9 }
            r9 = 4
            r6[r9] = r8     // Catch:{ Exception -> 0x00c9 }
            java.lang.String r5 = java.lang.String.format(r7, r5, r6)     // Catch:{ Exception -> 0x00c9 }
            com.amap.location.common.d.a.b(r2, r5)     // Catch:{ Exception -> 0x00c9 }
            if (r4 == 0) goto L_0x00c3
            r4.close()     // Catch:{ Throwable -> 0x00c3 }
        L_0x00c3:
            if (r3 == 0) goto L_0x00e8
            r3.endTransaction()     // Catch:{ Exception -> 0x00c8 }
        L_0x00c8:
            return
        L_0x00c9:
            r0 = move-exception
            goto L_0x00d9
        L_0x00cb:
            r0 = move-exception
            r4 = r2
            goto L_0x00d4
        L_0x00ce:
            r0 = move-exception
            r4 = r2
            goto L_0x00d9
        L_0x00d1:
            r0 = move-exception
            r3 = r2
            r4 = r3
        L_0x00d4:
            r2 = r0
            goto L_0x00eb
        L_0x00d6:
            r0 = move-exception
            r3 = r2
            r4 = r3
        L_0x00d9:
            r2 = r0
            com.amap.location.common.d.a.a(r2)     // Catch:{ all -> 0x00e9 }
            if (r4 == 0) goto L_0x00e2
            r4.close()     // Catch:{ Throwable -> 0x00e2 }
        L_0x00e2:
            if (r3 == 0) goto L_0x00e8
            r3.endTransaction()     // Catch:{ Exception -> 0x00e7 }
        L_0x00e7:
            return
        L_0x00e8:
            return
        L_0x00e9:
            r0 = move-exception
            goto L_0x00d4
        L_0x00eb:
            if (r4 == 0) goto L_0x00f0
            r4.close()     // Catch:{ Throwable -> 0x00f0 }
        L_0x00f0:
            if (r3 == 0) goto L_0x00f5
            r3.endTransaction()     // Catch:{ Exception -> 0x00f5 }
        L_0x00f5:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.b.c.f.a(java.util.List):void");
    }

    public boolean a(b bVar) {
        boolean z = true;
        if (bVar == null || bVar.b.size() == 0) {
            return true;
        }
        try {
            boolean z2 = bVar.b.get(0).b() == 0;
            if (this.b.getWritableDatabase().delete(RpcConstant.BASE, z2 ? "type=0 AND id<=?" : "type!=0 AND id<=?", new String[]{String.valueOf(bVar.a)}) > 0) {
                if (z2) {
                    this.c -= (long) bVar.c;
                    if (this.c < 0) {
                        this.c = 0;
                    }
                } else {
                    this.d -= (long) bVar.c;
                    if (this.d < 0) {
                        this.d = 0;
                    }
                }
            }
        } catch (Exception e) {
            com.amap.location.common.d.a.a("DbManager", "delete failed", e);
            z = false;
        }
        return z;
    }

    public boolean a(long j) {
        if (j < 4611686018427387903L && this.c + this.d + j < HttpFileUpMMTask.BIG_FILE_SIZE_THRESHOLD) {
            return true;
        }
        long max = Math.max(204800, j);
        try {
            long a2 = a(false, max);
            if (a2 < max) {
                a(true, max - a2);
            }
            return true;
        } catch (Exception e) {
            com.amap.location.common.d.a.a((Throwable) e);
            return false;
        }
    }

    private long a(boolean z, long j) throws Exception {
        Cursor cursor;
        Throwable th;
        String str = z ? "type=0" : "type!=0";
        long j2 = -2147483648L;
        long j3 = 0;
        while (true) {
            if (j3 >= j) {
                break;
            }
            try {
                cursor = this.b.getReadableDatabase().query(RpcConstant.BASE, new String[]{"id", "type", "size"}, "id>? AND ".concat(String.valueOf(str)), new String[]{String.valueOf(j2)}, null, null, "id ASC", "100");
                try {
                    boolean moveToNext = cursor.moveToNext();
                    if (!moveToNext) {
                        g.a(cursor);
                        break;
                    }
                    while (moveToNext) {
                        j2 = cursor.getLong(0);
                        j3 += (long) cursor.getInt(2);
                        if (j3 >= j) {
                            break;
                        }
                        moveToNext = cursor.moveToNext();
                    }
                    g.a(cursor);
                } catch (Throwable th2) {
                    th = th2;
                    g.a(cursor);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                cursor = null;
                g.a(cursor);
                throw th;
            }
        }
        if (j3 > 0) {
            if (this.b.getWritableDatabase().delete(RpcConstant.BASE, "id<=? AND ".concat(String.valueOf(str)), new String[]{String.valueOf(j2)}) > 0) {
                if (z) {
                    this.c -= j3;
                    if (this.c < 0) {
                        this.c = 0;
                    }
                } else {
                    this.d -= j3;
                    if (this.d < 0) {
                        this.d = 0;
                    }
                }
            }
        }
        return j3;
    }

    public int b() {
        return (int) this.c;
    }

    public int c() {
        return (int) this.d;
    }

    private long a(boolean z) {
        SystemClock.elapsedRealtime();
        long j = 0;
        Cursor cursor = null;
        try {
            Cursor query = this.b.getReadableDatabase().query(RpcConstant.BASE, new String[]{"SUM(size)"}, z ? "type=?" : "type!=?", new String[]{"0"}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    j = query.getLong(0);
                }
                g.a(query);
            } catch (Exception e) {
                Throwable th = e;
                cursor = query;
                e = th;
                try {
                    com.amap.location.common.d.a.a(e);
                    g.a(cursor);
                    return j;
                } catch (Throwable th2) {
                    th = th2;
                    g.a(cursor);
                    throw th;
                }
            } catch (Throwable th3) {
                cursor = query;
                th = th3;
                g.a(cursor);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            com.amap.location.common.d.a.a(e);
            g.a(cursor);
            return j;
        }
        return j;
    }
}
