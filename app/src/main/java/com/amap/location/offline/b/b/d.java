package com.amap.location.offline.b.b;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.location.offline.b.a.a;
import com.amap.location.offline.b.c.b.c;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: OfflineDatabase */
public class d {
    private static final String[] a = {"id", "lat", CameraControllerManager.MY_POILOCATION_LNG, "acc", "conf", "timestamp"};
    private static final String[] b = {"id", "originid", "frequency"};
    private static volatile d c;
    private c d;
    private g e;
    private ReadWriteLock f = new ReentrantReadWriteLock();

    public static d a(@NonNull Context context) {
        if (c == null) {
            synchronized (d.class) {
                try {
                    if (c == null) {
                        c = new d(context);
                    }
                }
            }
        }
        return c;
    }

    private d(Context context) {
        this.d = new c(context);
        this.e = new g(context);
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x009b A[SYNTHETIC, Splitter:B:36:0x009b] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00ba A[SYNTHETIC, Splitter:B:45:0x00ba] */
    /* JADX WARNING: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.amap.location.offline.b.a.a a(java.lang.String r26, long r27) {
        /*
            r25 = this;
            r1 = 0
            r2 = r25
            com.amap.location.offline.b.b.c r3 = r2.d     // Catch:{ Throwable -> 0x0092, all -> 0x008e }
            android.database.sqlite.SQLiteDatabase r3 = r3.getReadableDatabase()     // Catch:{ Throwable -> 0x0092, all -> 0x008e }
            java.lang.String r5 = "CL"
            java.lang.String[] r6 = a     // Catch:{ Throwable -> 0x0092, all -> 0x008e }
            java.lang.String r7 = "id=?"
            r12 = 1
            java.lang.String[] r8 = new java.lang.String[r12]     // Catch:{ Throwable -> 0x0092, all -> 0x008e }
            java.lang.String r4 = java.lang.String.valueOf(r27)     // Catch:{ Throwable -> 0x0092, all -> 0x008e }
            r13 = 0
            r8[r13] = r4     // Catch:{ Throwable -> 0x0092, all -> 0x008e }
            r9 = 0
            r10 = 0
            r11 = 0
            r4 = r3
            android.database.Cursor r4 = r4.query(r5, r6, r7, r8, r9, r10, r11)     // Catch:{ Throwable -> 0x0092, all -> 0x008e }
            if (r4 == 0) goto L_0x0083
            boolean r5 = r4.moveToFirst()     // Catch:{ Throwable -> 0x0080 }
            if (r5 == 0) goto L_0x0083
            r5 = 5
            long r5 = r4.getLong(r5)     // Catch:{ Throwable -> 0x0080 }
            r7 = 15552000(0xed4e00, double:7.683709E-317)
            long r7 = r7 + r5
            long r9 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0080 }
            r14 = 1000(0x3e8, double:4.94E-321)
            long r9 = r9 / r14
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r7 <= 0) goto L_0x0070
            int r21 = r4.getInt(r12)     // Catch:{ Throwable -> 0x0080 }
            r3 = 2
            int r22 = r4.getInt(r3)     // Catch:{ Throwable -> 0x0080 }
            r3 = 3
            int r23 = r4.getInt(r3)     // Catch:{ Throwable -> 0x0080 }
            r3 = 4
            int r24 = r4.getInt(r3)     // Catch:{ Throwable -> 0x0080 }
            com.amap.location.offline.b.a.a r3 = new com.amap.location.offline.b.a.a     // Catch:{ Throwable -> 0x0080 }
            r17 = 1
            r16 = r3
            r18 = r26
            r19 = r27
            r16.<init>(r17, r18, r19, r21, r22, r23, r24)     // Catch:{ Throwable -> 0x0080 }
            r7 = 604800(0x93a80, double:2.98811E-318)
            long r5 = r5 + r7
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x006e }
            long r7 = r7 / r14
            int r1 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r1 >= 0) goto L_0x006c
            r3.h = r12     // Catch:{ Throwable -> 0x006e }
        L_0x006c:
            r1 = r3
            goto L_0x0083
        L_0x006e:
            r0 = move-exception
            goto L_0x0095
        L_0x0070:
            java.lang.String r5 = "CL"
            java.lang.String r6 = "id=?"
            java.lang.String[] r7 = new java.lang.String[r12]     // Catch:{ Throwable -> 0x0080 }
            java.lang.String r8 = java.lang.String.valueOf(r27)     // Catch:{ Throwable -> 0x0080 }
            r7[r13] = r8     // Catch:{ Throwable -> 0x0080 }
            r3.delete(r5, r6, r7)     // Catch:{ Throwable -> 0x0080 }
            goto L_0x0083
        L_0x0080:
            r0 = move-exception
            r3 = r1
            goto L_0x0095
        L_0x0083:
            if (r4 == 0) goto L_0x00a4
            r4.close()     // Catch:{ Throwable -> 0x0089 }
            goto L_0x00a4
        L_0x0089:
            r0 = move-exception
            com.amap.location.common.d.a.a(r0)
            goto L_0x00a4
        L_0x008e:
            r0 = move-exception
            r4 = r1
        L_0x0090:
            r1 = r0
            goto L_0x00b8
        L_0x0092:
            r0 = move-exception
            r3 = r1
            r4 = r3
        L_0x0095:
            r1 = r0
            com.amap.location.common.d.a.a(r1)     // Catch:{ all -> 0x00b6 }
            if (r4 == 0) goto L_0x00a3
            r4.close()     // Catch:{ Throwable -> 0x009f }
            goto L_0x00a3
        L_0x009f:
            r0 = move-exception
            com.amap.location.common.d.a.a(r0)
        L_0x00a3:
            r1 = r3
        L_0x00a4:
            if (r1 != 0) goto L_0x00b5
            com.amap.location.offline.b.a.a r1 = new com.amap.location.offline.b.a.a
            r4 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = -1
            r3 = r1
            r5 = r26
            r6 = r27
            r3.<init>(r4, r5, r6, r8, r9, r10, r11)
        L_0x00b5:
            return r1
        L_0x00b6:
            r0 = move-exception
            goto L_0x0090
        L_0x00b8:
            if (r4 == 0) goto L_0x00c2
            r4.close()     // Catch:{ Throwable -> 0x00be }
            goto L_0x00c2
        L_0x00be:
            r0 = move-exception
            com.amap.location.common.d.a.a(r0)
        L_0x00c2:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.offline.b.b.d.a(java.lang.String, long):com.amap.location.offline.b.a.a");
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:0x0110 A[SYNTHETIC, Splitter:B:44:0x0110] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x011c A[SYNTHETIC, Splitter:B:52:0x011c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(java.lang.String r14, com.amap.location.offline.b.a.c r15) {
        /*
            r13 = this;
            r0 = 0
            com.amap.location.offline.b.b.c r1 = r13.d     // Catch:{ Throwable -> 0x010a }
            android.database.sqlite.SQLiteDatabase r1 = r1.getReadableDatabase()     // Catch:{ Throwable -> 0x010a }
            java.lang.String r3 = "AP"
            java.lang.String[] r4 = a     // Catch:{ Throwable -> 0x010a }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x010a }
            java.lang.String r5 = "id IN ("
            r2.<init>(r5)     // Catch:{ Throwable -> 0x010a }
            r2.append(r14)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r14 = ")"
            r2.append(r14)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r5 = r2.toString()     // Catch:{ Throwable -> 0x010a }
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r2 = r1
            android.database.Cursor r14 = r2.query(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ Throwable -> 0x010a }
            if (r14 == 0) goto L_0x00fc
            boolean r0 = r14.moveToFirst()     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            if (r0 == 0) goto L_0x00fc
        L_0x002f:
            boolean r0 = r14.isAfterLast()     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r2 = 1
            if (r0 != 0) goto L_0x00e2
            r0 = 0
            long r3 = r14.getLong(r0)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r5 = 5
            long r5 = r14.getLong(r5)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r7 = 7776000(0x76a700, double:3.8418545E-317)
            long r7 = r7 + r5
            long r9 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r11 = 1000(0x3e8, double:4.94E-321)
            long r9 = r9 / r11
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r7 >= 0) goto L_0x0062
            java.lang.String r5 = "AP"
            java.lang.String r6 = "id=?"
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r2[r0] = r3     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r1.delete(r5, r6, r2)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r14.moveToNext()     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            goto L_0x002f
        L_0x0062:
            int r0 = r14.getInt(r2)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r7 = 2
            int r7 = r14.getInt(r7)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r8 = 3
            int r8 = r14.getInt(r8)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r9 = 4
            int r9 = r14.getInt(r9)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            java.util.HashMap<java.lang.Long, com.amap.location.offline.b.a.b> r10 = r15.b     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            java.lang.Object r3 = r10.get(r3)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            com.amap.location.offline.b.a.b r3 = (com.amap.location.offline.b.a.b) r3     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            if (r3 != 0) goto L_0x0087
            r14.moveToNext()     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            goto L_0x002f
        L_0x0087:
            r3.d = r2     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r3.g = r9     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r3.e = r0     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r3.f = r7     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r4 = 60
            if (r9 <= r4) goto L_0x00ce
            if (r8 <= 0) goto L_0x00ce
            r4 = 2000(0x7d0, float:2.803E-42)
            if (r8 >= r4) goto L_0x00ce
            int r4 = r15.c     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            int r4 = r4 + r2
            r15.c = r4     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            java.lang.StringBuilder r4 = r15.d     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            long r9 = r3.b     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r4.append(r9)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            java.lang.String r9 = ";"
            r4.append(r9)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            java.lang.StringBuilder r4 = r15.e     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r4.append(r7)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            java.lang.String r7 = ","
            r4.append(r7)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r4.append(r0)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            java.lang.String r0 = ","
            r4.append(r0)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r4.append(r8)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            java.lang.String r0 = ","
            r4.append(r0)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            int r0 = r3.c     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            r4.append(r0)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            java.lang.String r0 = ";"
            r4.append(r0)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
        L_0x00ce:
            r7 = 604800(0x93a80, double:2.98811E-318)
            long r5 = r5 + r7
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            long r7 = r7 / r11
            int r0 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r0 >= 0) goto L_0x00dd
            r3.h = r2     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
        L_0x00dd:
            r14.moveToNext()     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            goto L_0x002f
        L_0x00e2:
            java.lang.StringBuilder r0 = r15.e     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            int r0 = r0.length()     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            if (r0 <= 0) goto L_0x00fc
            java.lang.StringBuilder r0 = r15.e     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            java.lang.StringBuilder r15 = r15.e     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            int r15 = r15.length()     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            int r15 = r15 - r2
            r0.deleteCharAt(r15)     // Catch:{ Throwable -> 0x00f9, all -> 0x00f7 }
            goto L_0x00fc
        L_0x00f7:
            r15 = move-exception
            goto L_0x011a
        L_0x00f9:
            r15 = move-exception
            r0 = r14
            goto L_0x010b
        L_0x00fc:
            if (r14 == 0) goto L_0x0119
            r14.close()     // Catch:{ Throwable -> 0x0102 }
            return
        L_0x0102:
            r14 = move-exception
            com.amap.location.common.d.a.a(r14)
            return
        L_0x0107:
            r15 = move-exception
            r14 = r0
            goto L_0x011a
        L_0x010a:
            r15 = move-exception
        L_0x010b:
            com.amap.location.common.d.a.a(r15)     // Catch:{ all -> 0x0107 }
            if (r0 == 0) goto L_0x0119
            r0.close()     // Catch:{ Throwable -> 0x0114 }
            return
        L_0x0114:
            r14 = move-exception
            com.amap.location.common.d.a.a(r14)
            return
        L_0x0119:
            return
        L_0x011a:
            if (r14 == 0) goto L_0x0124
            r14.close()     // Catch:{ Throwable -> 0x0120 }
            goto L_0x0124
        L_0x0120:
            r14 = move-exception
            com.amap.location.common.d.a.a(r14)
        L_0x0124:
            throw r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.offline.b.b.d.a(java.lang.String, com.amap.location.offline.b.a.c):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x006c A[SYNTHETIC, Splitter:B:22:0x006c] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0077 A[SYNTHETIC, Splitter:B:28:0x0077] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<java.lang.String> a(int r13, int r14) {
        /*
            r12 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            com.amap.location.offline.b.b.g r2 = r12.e     // Catch:{ Throwable -> 0x0066 }
            android.database.sqlite.SQLiteDatabase r3 = r2.getReadableDatabase()     // Catch:{ Throwable -> 0x0066 }
            java.lang.String r4 = "CL"
            java.lang.String[] r5 = b     // Catch:{ Throwable -> 0x0066 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0066 }
            java.lang.String r6 = "frequency>="
            r2.<init>(r6)     // Catch:{ Throwable -> 0x0066 }
            r2.append(r13)     // Catch:{ Throwable -> 0x0066 }
            java.lang.String r13 = " AND time<"
            r2.append(r13)     // Catch:{ Throwable -> 0x0066 }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0066 }
            r8 = 1000(0x3e8, double:4.94E-321)
            long r6 = r6 / r8
            r8 = 86400(0x15180, double:4.26873E-319)
            long r6 = r6 - r8
            r2.append(r6)     // Catch:{ Throwable -> 0x0066 }
            java.lang.String r6 = r2.toString()     // Catch:{ Throwable -> 0x0066 }
            r7 = 0
            r8 = 0
            r9 = 0
            java.lang.String r10 = "frequency DESC"
            java.lang.String r11 = java.lang.String.valueOf(r14)     // Catch:{ Throwable -> 0x0066 }
            android.database.Cursor r13 = r3.query(r4, r5, r6, r7, r8, r9, r10, r11)     // Catch:{ Throwable -> 0x0066 }
            if (r13 == 0) goto L_0x005e
            boolean r14 = r13.moveToFirst()     // Catch:{ Throwable -> 0x005b, all -> 0x0058 }
            if (r14 == 0) goto L_0x005e
        L_0x0046:
            boolean r14 = r13.isAfterLast()     // Catch:{ Throwable -> 0x005b, all -> 0x0058 }
            if (r14 != 0) goto L_0x005e
            r14 = 1
            java.lang.String r14 = r13.getString(r14)     // Catch:{ Throwable -> 0x005b, all -> 0x0058 }
            r0.add(r14)     // Catch:{ Throwable -> 0x005b, all -> 0x0058 }
            r13.moveToNext()     // Catch:{ Throwable -> 0x005b, all -> 0x0058 }
            goto L_0x0046
        L_0x0058:
            r14 = move-exception
            r1 = r13
            goto L_0x0075
        L_0x005b:
            r14 = move-exception
            r1 = r13
            goto L_0x0067
        L_0x005e:
            if (r13 == 0) goto L_0x0074
            r13.close()     // Catch:{ Throwable -> 0x0070 }
            goto L_0x0074
        L_0x0064:
            r14 = move-exception
            goto L_0x0075
        L_0x0066:
            r14 = move-exception
        L_0x0067:
            com.amap.location.common.d.a.a(r14)     // Catch:{ all -> 0x0064 }
            if (r1 == 0) goto L_0x0074
            r1.close()     // Catch:{ Throwable -> 0x0070 }
            goto L_0x0074
        L_0x0070:
            r13 = move-exception
            com.amap.location.common.d.a.a(r13)
        L_0x0074:
            return r0
        L_0x0075:
            if (r1 == 0) goto L_0x007f
            r1.close()     // Catch:{ Throwable -> 0x007b }
            goto L_0x007f
        L_0x007b:
            r13 = move-exception
            com.amap.location.common.d.a.a(r13)
        L_0x007f:
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.offline.b.b.d.a(int, int):java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0070 A[SYNTHETIC, Splitter:B:22:0x0070] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x007b A[SYNTHETIC, Splitter:B:28:0x007b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<java.lang.Long> b(int r13, int r14) {
        /*
            r12 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            com.amap.location.offline.b.b.g r2 = r12.e     // Catch:{ Throwable -> 0x006a }
            android.database.sqlite.SQLiteDatabase r3 = r2.getReadableDatabase()     // Catch:{ Throwable -> 0x006a }
            java.lang.String r4 = "AP"
            java.lang.String[] r5 = b     // Catch:{ Throwable -> 0x006a }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x006a }
            java.lang.String r6 = "frequency>="
            r2.<init>(r6)     // Catch:{ Throwable -> 0x006a }
            r2.append(r13)     // Catch:{ Throwable -> 0x006a }
            java.lang.String r13 = " AND time<"
            r2.append(r13)     // Catch:{ Throwable -> 0x006a }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x006a }
            r8 = 1000(0x3e8, double:4.94E-321)
            long r6 = r6 / r8
            r8 = 86400(0x15180, double:4.26873E-319)
            long r6 = r6 - r8
            r2.append(r6)     // Catch:{ Throwable -> 0x006a }
            java.lang.String r6 = r2.toString()     // Catch:{ Throwable -> 0x006a }
            r7 = 0
            r8 = 0
            r9 = 0
            java.lang.String r10 = "frequency DESC"
            java.lang.String r11 = java.lang.String.valueOf(r14)     // Catch:{ Throwable -> 0x006a }
            android.database.Cursor r13 = r3.query(r4, r5, r6, r7, r8, r9, r10, r11)     // Catch:{ Throwable -> 0x006a }
            if (r13 == 0) goto L_0x0062
            boolean r14 = r13.moveToFirst()     // Catch:{ Throwable -> 0x005f, all -> 0x005c }
            if (r14 == 0) goto L_0x0062
        L_0x0046:
            boolean r14 = r13.isAfterLast()     // Catch:{ Throwable -> 0x005f, all -> 0x005c }
            if (r14 != 0) goto L_0x0062
            r14 = 1
            long r1 = r13.getLong(r14)     // Catch:{ Throwable -> 0x005f, all -> 0x005c }
            java.lang.Long r14 = java.lang.Long.valueOf(r1)     // Catch:{ Throwable -> 0x005f, all -> 0x005c }
            r0.add(r14)     // Catch:{ Throwable -> 0x005f, all -> 0x005c }
            r13.moveToNext()     // Catch:{ Throwable -> 0x005f, all -> 0x005c }
            goto L_0x0046
        L_0x005c:
            r14 = move-exception
            r1 = r13
            goto L_0x0079
        L_0x005f:
            r14 = move-exception
            r1 = r13
            goto L_0x006b
        L_0x0062:
            if (r13 == 0) goto L_0x0078
            r13.close()     // Catch:{ Throwable -> 0x0074 }
            goto L_0x0078
        L_0x0068:
            r14 = move-exception
            goto L_0x0079
        L_0x006a:
            r14 = move-exception
        L_0x006b:
            com.amap.location.common.d.a.a(r14)     // Catch:{ all -> 0x0068 }
            if (r1 == 0) goto L_0x0078
            r1.close()     // Catch:{ Throwable -> 0x0074 }
            goto L_0x0078
        L_0x0074:
            r13 = move-exception
            com.amap.location.common.d.a.a(r13)
        L_0x0078:
            return r0
        L_0x0079:
            if (r1 == 0) goto L_0x0083
            r1.close()     // Catch:{ Throwable -> 0x007f }
            goto L_0x0083
        L_0x007f:
            r13 = move-exception
            com.amap.location.common.d.a.a(r13)
        L_0x0083:
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.offline.b.b.d.b(int, int):java.util.List");
    }

    public void a(a aVar) {
        a aVar2 = aVar;
        long j = aVar2.g;
        String str = aVar2.f;
        if (!TextUtils.isEmpty(str)) {
            ContentValues contentValues = new ContentValues();
            this.f.readLock().lock();
            try {
                SQLiteDatabase writableDatabase = this.d.getWritableDatabase();
                SQLiteDatabase writableDatabase2 = this.e.getWritableDatabase();
                if (aVar2.a) {
                    if (aVar2.e > 60) {
                        a(writableDatabase, (String) "CL", j, contentValues);
                    }
                    if (aVar2.h) {
                        a(contentValues, j, str, 0, 100000);
                        a(writableDatabase2, (String) "CL", contentValues, true);
                    }
                } else {
                    a(contentValues, j, str, 0, 0);
                    a(writableDatabase2, (String) "CL", contentValues, true);
                    a(writableDatabase2, (String) "CL", j, contentValues);
                }
            } catch (Throwable th) {
                Throwable th2 = th;
                this.f.readLock().unlock();
                throw th2;
            }
            this.f.readLock().unlock();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0024 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(com.amap.location.offline.b.a.c r14) {
        /*
            r13 = this;
            java.util.HashMap<java.lang.Long, com.amap.location.offline.b.a.b> r0 = r14.b
            if (r0 == 0) goto L_0x00f1
            java.util.HashMap<java.lang.Long, com.amap.location.offline.b.a.b> r0 = r14.b
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x00f1
            r0 = 0
            com.amap.location.offline.b.b.c r1 = r13.d     // Catch:{ Throwable -> 0x001d }
            android.database.sqlite.SQLiteDatabase r1 = r1.getWritableDatabase()     // Catch:{ Throwable -> 0x001d }
            com.amap.location.offline.b.b.g r2 = r13.e     // Catch:{ Throwable -> 0x001b }
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch:{ Throwable -> 0x001b }
            r0 = r2
            goto L_0x0022
        L_0x001b:
            r2 = move-exception
            goto L_0x001f
        L_0x001d:
            r2 = move-exception
            r1 = r0
        L_0x001f:
            com.amap.location.common.d.a.a(r2)
        L_0x0022:
            if (r1 == 0) goto L_0x00f0
            if (r0 != 0) goto L_0x0028
            goto L_0x00f0
        L_0x0028:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.util.HashMap<java.lang.Long, com.amap.location.offline.b.a.b> r14 = r14.b
            java.util.Set r14 = r14.entrySet()
            java.util.Iterator r14 = r14.iterator()
            android.content.ContentValues r10 = new android.content.ContentValues
            r10.<init>()
            java.util.concurrent.locks.ReadWriteLock r2 = r13.f
            java.util.concurrent.locks.Lock r2 = r2.readLock()
            r2.lock()
            r1.beginTransaction()     // Catch:{ Throwable -> 0x00ce }
            r0.beginTransaction()     // Catch:{ Throwable -> 0x00ce }
        L_0x0055:
            boolean r2 = r14.hasNext()     // Catch:{ Throwable -> 0x00ce }
            if (r2 == 0) goto L_0x00b1
            java.lang.Object r2 = r14.next()     // Catch:{ Throwable -> 0x00ce }
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2     // Catch:{ Throwable -> 0x00ce }
            java.lang.Object r2 = r2.getValue()     // Catch:{ Throwable -> 0x00ce }
            r11 = r2
            com.amap.location.offline.b.a.b r11 = (com.amap.location.offline.b.a.b) r11     // Catch:{ Throwable -> 0x00ce }
            if (r11 == 0) goto L_0x0055
            boolean r2 = r11.d     // Catch:{ Throwable -> 0x00ce }
            r12 = 1
            if (r2 == 0) goto L_0x0096
            int r2 = r11.g     // Catch:{ Throwable -> 0x00ce }
            r3 = 60
            if (r2 <= r3) goto L_0x007f
            java.lang.String r5 = "AP"
            long r6 = r11.a     // Catch:{ Throwable -> 0x00ce }
            r3 = r13
            r4 = r1
            r8 = r10
            r3.a(r4, r5, r6, r8)     // Catch:{ Throwable -> 0x00ce }
        L_0x007f:
            boolean r2 = r11.h     // Catch:{ Throwable -> 0x00ce }
            if (r2 == 0) goto L_0x0055
            long r4 = r11.a     // Catch:{ Throwable -> 0x00ce }
            r6 = 0
            long r7 = r11.b     // Catch:{ Throwable -> 0x00ce }
            r9 = 100000(0x186a0, float:1.4013E-40)
            r2 = r13
            r3 = r10
            r2.a(r3, r4, r6, r7, r9)     // Catch:{ Throwable -> 0x00ce }
            java.lang.String r2 = "AP"
            r13.a(r0, r2, r10, r12)     // Catch:{ Throwable -> 0x00ce }
            goto L_0x0055
        L_0x0096:
            long r4 = r11.a     // Catch:{ Throwable -> 0x00ce }
            r6 = 0
            long r7 = r11.b     // Catch:{ Throwable -> 0x00ce }
            r9 = 0
            r2 = r13
            r3 = r10
            r2.a(r3, r4, r6, r7, r9)     // Catch:{ Throwable -> 0x00ce }
            java.lang.String r2 = "AP"
            r13.a(r0, r2, r10, r12)     // Catch:{ Throwable -> 0x00ce }
            java.lang.String r4 = "AP"
            long r5 = r11.a     // Catch:{ Throwable -> 0x00ce }
            r2 = r13
            r3 = r0
            r7 = r10
            r2.a(r3, r4, r5, r7)     // Catch:{ Throwable -> 0x00ce }
            goto L_0x0055
        L_0x00b1:
            r1.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00ce }
            r0.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00ce }
            r1.endTransaction()     // Catch:{ Throwable -> 0x00be }
            r0.endTransaction()     // Catch:{ Throwable -> 0x00be }
            goto L_0x00c2
        L_0x00be:
            r14 = move-exception
        L_0x00bf:
            com.amap.location.common.d.a.a(r14)
        L_0x00c2:
            java.util.concurrent.locks.ReadWriteLock r14 = r13.f
            java.util.concurrent.locks.Lock r14 = r14.readLock()
            r14.unlock()
            return
        L_0x00cc:
            r14 = move-exception
            goto L_0x00db
        L_0x00ce:
            r14 = move-exception
            com.amap.location.common.d.a.a(r14)     // Catch:{ all -> 0x00cc }
            r1.endTransaction()     // Catch:{ Throwable -> 0x00d9 }
            r0.endTransaction()     // Catch:{ Throwable -> 0x00d9 }
            goto L_0x00c2
        L_0x00d9:
            r14 = move-exception
            goto L_0x00bf
        L_0x00db:
            r1.endTransaction()     // Catch:{ Throwable -> 0x00e2 }
            r0.endTransaction()     // Catch:{ Throwable -> 0x00e2 }
            goto L_0x00e6
        L_0x00e2:
            r0 = move-exception
            com.amap.location.common.d.a.a(r0)
        L_0x00e6:
            java.util.concurrent.locks.ReadWriteLock r0 = r13.f
            java.util.concurrent.locks.Lock r0 = r0.readLock()
            r0.unlock()
            throw r14
        L_0x00f0:
            return
        L_0x00f1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.offline.b.b.d.a(com.amap.location.offline.b.a.c):void");
    }

    public void b(a aVar) {
        long j = aVar.g;
        String str = aVar.f;
        ContentValues contentValues = new ContentValues();
        try {
            a((String) "CL", contentValues, j);
            a(contentValues, j, str, 0, 100000);
            a(this.e.getWritableDatabase(), (String) "CL", contentValues, false);
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0020 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(com.amap.location.offline.b.a.c r21, com.amap.location.common.model.AmapLoc r22) {
        /*
            r20 = this;
            r9 = r20
            r1 = 0
            com.amap.location.offline.b.b.c r2 = r9.d     // Catch:{ Throwable -> 0x0016 }
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch:{ Throwable -> 0x0016 }
            com.amap.location.offline.b.b.g r3 = r9.e     // Catch:{ Throwable -> 0x0012 }
            android.database.sqlite.SQLiteDatabase r3 = r3.getWritableDatabase()     // Catch:{ Throwable -> 0x0012 }
            r10 = r2
            r11 = r3
            goto L_0x001e
        L_0x0012:
            r0 = move-exception
            r3 = r2
            r2 = r0
            goto L_0x0019
        L_0x0016:
            r0 = move-exception
            r2 = r0
            r3 = r1
        L_0x0019:
            com.amap.location.common.d.a.a(r2)
            r11 = r1
            r10 = r3
        L_0x001e:
            if (r10 == 0) goto L_0x00c4
            if (r11 != 0) goto L_0x0024
            goto L_0x00c4
        L_0x0024:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1 = r21
            java.util.HashMap<java.lang.Long, com.amap.location.offline.b.a.b> r1 = r1.b
            java.util.Set r1 = r1.entrySet()
            java.util.Iterator r12 = r1.iterator()
            android.content.ContentValues r13 = new android.content.ContentValues
            r13.<init>()
            r10.beginTransaction()     // Catch:{ Throwable -> 0x00a5 }
            r11.beginTransaction()     // Catch:{ Throwable -> 0x00a5 }
        L_0x0040:
            boolean r1 = r12.hasNext()     // Catch:{ Throwable -> 0x00a5 }
            if (r1 == 0) goto L_0x008f
            java.lang.Object r1 = r12.next()     // Catch:{ Throwable -> 0x00a5 }
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch:{ Throwable -> 0x00a5 }
            java.lang.Object r1 = r1.getValue()     // Catch:{ Throwable -> 0x00a5 }
            com.amap.location.offline.b.a.b r1 = (com.amap.location.offline.b.a.b) r1     // Catch:{ Throwable -> 0x00a5 }
            if (r1 == 0) goto L_0x0040
            boolean r2 = r1.d     // Catch:{ Throwable -> 0x00a5 }
            if (r2 == 0) goto L_0x0040
            int r2 = r1.g     // Catch:{ Throwable -> 0x00a5 }
            r3 = 60
            if (r2 <= r3) goto L_0x0040
            int r14 = r1.e     // Catch:{ Throwable -> 0x00a5 }
            int r15 = r1.f     // Catch:{ Throwable -> 0x00a5 }
            double r16 = r22.getLat()     // Catch:{ Throwable -> 0x00a5 }
            double r18 = r22.getLon()     // Catch:{ Throwable -> 0x00a5 }
            double r2 = com.amap.location.security.Core.gd(r14, r15, r16, r18)     // Catch:{ Throwable -> 0x00a5 }
            r4 = 4636737291354636288(0x4059000000000000, double:100.0)
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 <= 0) goto L_0x0040
            java.lang.String r2 = "AP"
            long r3 = r1.a     // Catch:{ Throwable -> 0x00a5 }
            r9.a(r2, r13, r3)     // Catch:{ Throwable -> 0x00a5 }
            long r3 = r1.a     // Catch:{ Throwable -> 0x00a5 }
            r5 = 0
            long r6 = r1.b     // Catch:{ Throwable -> 0x00a5 }
            r8 = 100000(0x186a0, float:1.4013E-40)
            r1 = r9
            r2 = r13
            r1.a(r2, r3, r5, r6, r8)     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r1 = "AP"
            r2 = 0
            r9.a(r11, r1, r13, r2)     // Catch:{ Throwable -> 0x00a5 }
            goto L_0x0040
        L_0x008f:
            r10.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00a5 }
            r11.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00a5 }
            r10.endTransaction()     // Catch:{ Throwable -> 0x009c }
            r11.endTransaction()     // Catch:{ Throwable -> 0x009c }
            return
        L_0x009c:
            r0 = move-exception
            r1 = r0
            com.amap.location.common.d.a.a(r1)
            return
        L_0x00a2:
            r0 = move-exception
            r1 = r0
            goto L_0x00b7
        L_0x00a5:
            r0 = move-exception
            r1 = r0
            com.amap.location.common.d.a.a(r1)     // Catch:{ all -> 0x00a2 }
            r10.endTransaction()     // Catch:{ Throwable -> 0x00b1 }
            r11.endTransaction()     // Catch:{ Throwable -> 0x00b1 }
            return
        L_0x00b1:
            r0 = move-exception
            r1 = r0
            com.amap.location.common.d.a.a(r1)
            return
        L_0x00b7:
            r10.endTransaction()     // Catch:{ Throwable -> 0x00be }
            r11.endTransaction()     // Catch:{ Throwable -> 0x00be }
            goto L_0x00c3
        L_0x00be:
            r0 = move-exception
            r2 = r0
            com.amap.location.common.d.a.a(r2)
        L_0x00c3:
            throw r1
        L_0x00c4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.offline.b.b.d.a(com.amap.location.offline.b.a.c, com.amap.location.common.model.AmapLoc):void");
    }

    private ContentValues a(ContentValues contentValues, long j, String str, long j2, int i) {
        contentValues.clear();
        contentValues.put("id", Long.valueOf(j));
        if (!TextUtils.isEmpty(str)) {
            contentValues.put("originid", str);
        } else {
            contentValues.put("originid", Long.valueOf(j2));
        }
        contentValues.put("frequency", Integer.valueOf(i));
        return contentValues;
    }

    private void a(SQLiteDatabase sQLiteDatabase, String str, ContentValues contentValues, boolean z) {
        if (z) {
            sQLiteDatabase.insertWithOnConflict(str, null, contentValues, 4);
        } else {
            sQLiteDatabase.insertWithOnConflict(str, null, contentValues, 5);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0047 A[SYNTHETIC, Splitter:B:26:0x0047] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0053 A[SYNTHETIC, Splitter:B:33:0x0053] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int a(android.database.sqlite.SQLiteDatabase r11, java.lang.String r12, long r13) {
        /*
            r10 = this;
            r0 = 0
            java.lang.String r1 = "frequency"
            java.lang.String[] r4 = new java.lang.String[]{r1}     // Catch:{ Throwable -> 0x0041 }
            java.lang.String r5 = "id=?"
            r1 = 1
            java.lang.String[] r6 = new java.lang.String[r1]     // Catch:{ Throwable -> 0x0041 }
            java.lang.String r13 = java.lang.String.valueOf(r13)     // Catch:{ Throwable -> 0x0041 }
            r14 = 0
            r6[r14] = r13     // Catch:{ Throwable -> 0x0041 }
            r7 = 0
            r8 = 0
            r9 = 0
            r2 = r11
            r3 = r12
            android.database.Cursor r11 = r2.query(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ Throwable -> 0x0041 }
            if (r11 == 0) goto L_0x0039
            boolean r12 = r11.moveToFirst()     // Catch:{ Throwable -> 0x0036, all -> 0x0033 }
            if (r12 == 0) goto L_0x0039
            int r12 = r11.getInt(r14)     // Catch:{ Throwable -> 0x0036, all -> 0x0033 }
            if (r11 == 0) goto L_0x0032
            r11.close()     // Catch:{ Throwable -> 0x002e }
            goto L_0x0032
        L_0x002e:
            r11 = move-exception
            com.amap.location.common.d.a.a(r11)
        L_0x0032:
            return r12
        L_0x0033:
            r12 = move-exception
            r0 = r11
            goto L_0x0051
        L_0x0036:
            r12 = move-exception
            r0 = r11
            goto L_0x0042
        L_0x0039:
            if (r11 == 0) goto L_0x004f
            r11.close()     // Catch:{ Throwable -> 0x004b }
            goto L_0x004f
        L_0x003f:
            r12 = move-exception
            goto L_0x0051
        L_0x0041:
            r12 = move-exception
        L_0x0042:
            com.amap.location.common.d.a.a(r12)     // Catch:{ all -> 0x003f }
            if (r0 == 0) goto L_0x004f
            r0.close()     // Catch:{ Throwable -> 0x004b }
            goto L_0x004f
        L_0x004b:
            r11 = move-exception
            com.amap.location.common.d.a.a(r11)
        L_0x004f:
            r11 = -1
            return r11
        L_0x0051:
            if (r0 == 0) goto L_0x005b
            r0.close()     // Catch:{ Throwable -> 0x0057 }
            goto L_0x005b
        L_0x0057:
            r11 = move-exception
            com.amap.location.common.d.a.a(r11)
        L_0x005b:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.offline.b.b.d.a(android.database.sqlite.SQLiteDatabase, java.lang.String, long):int");
    }

    private boolean a(SQLiteDatabase sQLiteDatabase, String str, long j, ContentValues contentValues) {
        int a2 = a(sQLiteDatabase, str, j);
        if (a2 < 0) {
            return false;
        }
        contentValues.clear();
        contentValues.put("frequency", Integer.valueOf(a2 + 1));
        sQLiteDatabase.update(str, contentValues, "id=?", new String[]{String.valueOf(j)});
        return true;
    }

    private void a(String str, ContentValues contentValues, long j) {
        contentValues.clear();
        contentValues.put("conf", Integer.valueOf(0));
        this.d.getWritableDatabase().update(str, contentValues, "id=?", new String[]{String.valueOf(j)});
    }

    public void a(c cVar) {
        SQLiteDatabase sQLiteDatabase;
        try {
            sQLiteDatabase = this.d.getWritableDatabase();
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
            sQLiteDatabase = null;
        }
        if (sQLiteDatabase != null) {
            ContentValues contentValues = new ContentValues();
            try {
                sQLiteDatabase.beginTransaction();
                for (int i = 0; i < cVar.b(); i++) {
                    com.amap.location.offline.b.c.b.a b2 = cVar.b(i);
                    if (b2 != null) {
                        a(contentValues, b2);
                        a(sQLiteDatabase, (String) "AP", contentValues);
                    }
                }
                for (int i2 = 0; i2 < cVar.a(); i2++) {
                    com.amap.location.offline.b.c.b.a a2 = cVar.a(i2);
                    if (a2 != null) {
                        a(contentValues, a2);
                        a(sQLiteDatabase, (String) "CL", contentValues);
                    }
                }
                sQLiteDatabase.setTransactionSuccessful();
                try {
                    sQLiteDatabase.endTransaction();
                    return;
                } catch (Throwable th2) {
                    com.amap.location.common.d.a.a(th2);
                    return;
                }
            } catch (Throwable th3) {
                com.amap.location.common.d.a.a(th3);
                return;
            }
        } else {
            return;
        }
        throw th;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0022 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(com.amap.location.offline.b.c.b.c r18, java.util.List<java.lang.Long> r19, java.util.List<java.lang.String> r20, android.content.Context r21) {
        /*
            r17 = this;
            r7 = r17
            r8 = r18
            r1 = 0
            com.amap.location.offline.b.b.c r2 = r7.d     // Catch:{ Throwable -> 0x0018 }
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch:{ Throwable -> 0x0018 }
            com.amap.location.offline.b.b.g r3 = r7.e     // Catch:{ Throwable -> 0x0014 }
            android.database.sqlite.SQLiteDatabase r3 = r3.getWritableDatabase()     // Catch:{ Throwable -> 0x0014 }
            r9 = r2
            r10 = r3
            goto L_0x0020
        L_0x0014:
            r0 = move-exception
            r3 = r2
            r2 = r0
            goto L_0x001b
        L_0x0018:
            r0 = move-exception
            r2 = r0
            r3 = r1
        L_0x001b:
            com.amap.location.common.d.a.a(r2)
            r10 = r1
            r9 = r3
        L_0x0020:
            if (r9 == 0) goto L_0x0131
            if (r10 != 0) goto L_0x0026
            goto L_0x0131
        L_0x0026:
            android.content.ContentValues r11 = new android.content.ContentValues
            r11.<init>()
            java.util.concurrent.locks.ReadWriteLock r1 = r7.f
            java.util.concurrent.locks.Lock r1 = r1.writeLock()
            r1.lock()
            r9.beginTransaction()     // Catch:{ Throwable -> 0x010c }
            r10.beginTransaction()     // Catch:{ Throwable -> 0x010c }
            r1 = r19
            java.util.HashSet r12 = r7.a(r1)     // Catch:{ Throwable -> 0x010c }
            java.lang.Object r1 = r12.clone()     // Catch:{ Throwable -> 0x010c }
            r13 = r1
            java.util.HashSet r13 = (java.util.HashSet) r13     // Catch:{ Throwable -> 0x010c }
            r14 = 0
            r15 = 0
        L_0x0049:
            int r1 = r18.b()     // Catch:{ Throwable -> 0x010c }
            r6 = 60
            if (r15 >= r1) goto L_0x008a
            com.amap.location.offline.b.c.b.a r1 = r8.b(r15)     // Catch:{ Throwable -> 0x010c }
            if (r1 == 0) goto L_0x0087
            long r2 = r1.a()     // Catch:{ Throwable -> 0x010c }
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ Throwable -> 0x010c }
            r12.remove(r2)     // Catch:{ Throwable -> 0x010c }
            r7.a(r11, r1)     // Catch:{ Throwable -> 0x010c }
            java.lang.String r2 = "AP"
            r7.a(r9, r2, r11)     // Catch:{ Throwable -> 0x010c }
            byte r2 = r1.e()     // Catch:{ Throwable -> 0x010c }
            if (r2 > r6) goto L_0x0087
            long r2 = r1.a()     // Catch:{ Throwable -> 0x010c }
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ Throwable -> 0x010c }
            r13.remove(r2)     // Catch:{ Throwable -> 0x010c }
            java.lang.String r3 = "AP"
            long r4 = r1.a()     // Catch:{ Throwable -> 0x010c }
            r1 = r7
            r2 = r10
            r6 = r11
            r1.b(r2, r3, r4, r6)     // Catch:{ Throwable -> 0x010c }
        L_0x0087:
            int r15 = r15 + 1
            goto L_0x0049
        L_0x008a:
            java.lang.String r1 = "AP"
            r7.a(r9, r1, r12, r11)     // Catch:{ Throwable -> 0x010c }
            r1 = r20
            java.util.HashSet r12 = r7.b(r1)     // Catch:{ Throwable -> 0x010c }
            java.lang.Object r1 = r12.clone()     // Catch:{ Throwable -> 0x010c }
            r15 = r1
            java.util.HashSet r15 = (java.util.HashSet) r15     // Catch:{ Throwable -> 0x010c }
        L_0x009c:
            int r1 = r18.a()     // Catch:{ Throwable -> 0x010c }
            if (r14 >= r1) goto L_0x00e2
            com.amap.location.offline.b.c.b.a r1 = r8.a(r14)     // Catch:{ Throwable -> 0x010c }
            if (r1 == 0) goto L_0x00db
            long r2 = r1.a()     // Catch:{ Throwable -> 0x010c }
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ Throwable -> 0x010c }
            r12.remove(r2)     // Catch:{ Throwable -> 0x010c }
            r7.a(r11, r1)     // Catch:{ Throwable -> 0x010c }
            java.lang.String r2 = "CL"
            r7.a(r9, r2, r11)     // Catch:{ Throwable -> 0x010c }
            byte r2 = r1.e()     // Catch:{ Throwable -> 0x010c }
            if (r2 > r6) goto L_0x00db
            long r2 = r1.a()     // Catch:{ Throwable -> 0x010c }
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ Throwable -> 0x010c }
            r15.remove(r2)     // Catch:{ Throwable -> 0x010c }
            java.lang.String r3 = "CL"
            long r4 = r1.a()     // Catch:{ Throwable -> 0x010c }
            r1 = r7
            r2 = r10
            r16 = 60
            r6 = r11
            r1.b(r2, r3, r4, r6)     // Catch:{ Throwable -> 0x010c }
            goto L_0x00dd
        L_0x00db:
            r16 = 60
        L_0x00dd:
            int r14 = r14 + 1
            r6 = 60
            goto L_0x009c
        L_0x00e2:
            java.lang.String r1 = "CL"
            r7.a(r9, r1, r12, r11)     // Catch:{ Throwable -> 0x010c }
            r7.a(r10, r15, r13)     // Catch:{ Throwable -> 0x010c }
            r17.a()     // Catch:{ Throwable -> 0x010c }
            r9.setTransactionSuccessful()     // Catch:{ Throwable -> 0x010c }
            r10.setTransactionSuccessful()     // Catch:{ Throwable -> 0x010c }
            r9.endTransaction()     // Catch:{ Throwable -> 0x00fa }
            r10.endTransaction()     // Catch:{ Throwable -> 0x00fa }
            goto L_0x00ff
        L_0x00fa:
            r0 = move-exception
            r1 = r0
        L_0x00fc:
            com.amap.location.common.d.a.a(r1)
        L_0x00ff:
            java.util.concurrent.locks.ReadWriteLock r1 = r7.f
            java.util.concurrent.locks.Lock r1 = r1.writeLock()
            r1.unlock()
            return
        L_0x0109:
            r0 = move-exception
            r1 = r0
            goto L_0x011b
        L_0x010c:
            r0 = move-exception
            r1 = r0
            com.amap.location.common.d.a.a(r1)     // Catch:{ all -> 0x0109 }
            r9.endTransaction()     // Catch:{ Throwable -> 0x0118 }
            r10.endTransaction()     // Catch:{ Throwable -> 0x0118 }
            goto L_0x00ff
        L_0x0118:
            r0 = move-exception
            r1 = r0
            goto L_0x00fc
        L_0x011b:
            r9.endTransaction()     // Catch:{ Throwable -> 0x0122 }
            r10.endTransaction()     // Catch:{ Throwable -> 0x0122 }
            goto L_0x0127
        L_0x0122:
            r0 = move-exception
            r2 = r0
            com.amap.location.common.d.a.a(r2)
        L_0x0127:
            java.util.concurrent.locks.ReadWriteLock r2 = r7.f
            java.util.concurrent.locks.Lock r2 = r2.writeLock()
            r2.unlock()
            throw r1
        L_0x0131:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.offline.b.b.d.a(com.amap.location.offline.b.c.b.c, java.util.List, java.util.List, android.content.Context):void");
    }

    private ContentValues a(ContentValues contentValues, com.amap.location.offline.b.c.b.a aVar) {
        contentValues.clear();
        contentValues.put("id", Long.valueOf(aVar.a()));
        contentValues.put("lat", Integer.valueOf(aVar.b()));
        contentValues.put(CameraControllerManager.MY_POILOCATION_LNG, Integer.valueOf(aVar.c()));
        contentValues.put("acc", Short.valueOf(aVar.d()));
        contentValues.put("conf", Byte.valueOf(aVar.e()));
        contentValues.put("timestamp", Long.valueOf(System.currentTimeMillis() / 1000));
        return contentValues;
    }

    private void a(SQLiteDatabase sQLiteDatabase, String str, ContentValues contentValues) {
        try {
            sQLiteDatabase.replace(str, null, contentValues);
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
        }
    }

    private void a(SQLiteDatabase sQLiteDatabase, String str, HashSet<Long> hashSet, ContentValues contentValues) {
        contentValues.clear();
        contentValues.put("lat", Integer.valueOf(0));
        contentValues.put(CameraControllerManager.MY_POILOCATION_LNG, Integer.valueOf(0));
        contentValues.put("acc", Integer.valueOf(0));
        contentValues.put("conf", Integer.valueOf(-1));
        contentValues.put("timestamp", Long.valueOf(System.currentTimeMillis() / 1000));
        Iterator<Long> it = hashSet.iterator();
        while (it.hasNext()) {
            contentValues.put("id", Long.valueOf(it.next().longValue()));
            a(sQLiteDatabase, str, contentValues);
        }
    }

    private void b(SQLiteDatabase sQLiteDatabase, String str, long j, ContentValues contentValues) {
        try {
            contentValues.clear();
            contentValues.put("time", Long.valueOf(System.currentTimeMillis() / 1000));
            sQLiteDatabase.update(str, contentValues, "id=?", new String[]{String.valueOf(j)});
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
        }
    }

    private HashSet<Long> a(List<Long> list) {
        HashSet<Long> hashSet = new HashSet<>();
        if (list != null) {
            for (Long longValue : list) {
                hashSet.add(Long.valueOf(com.amap.location.offline.e.a.a(longValue.longValue())));
            }
        }
        return hashSet;
    }

    private HashSet<Long> b(List<String> list) {
        HashSet<Long> hashSet = new HashSet<>();
        if (list != null) {
            for (String a2 : list) {
                long a3 = com.amap.location.offline.e.a.a(a2);
                if (a3 != -1) {
                    hashSet.add(Long.valueOf(a3));
                }
            }
        }
        return hashSet;
    }

    private void a(SQLiteDatabase sQLiteDatabase, HashSet<Long> hashSet, HashSet<Long> hashSet2) {
        if (hashSet != null) {
            Iterator<Long> it = hashSet.iterator();
            while (it.hasNext()) {
                sQLiteDatabase.delete("CL", "id=?", new String[]{String.valueOf(it.next().longValue())});
            }
        }
        if (hashSet2 != null) {
            Iterator<Long> it2 = hashSet2.iterator();
            while (it2.hasNext()) {
                sQLiteDatabase.delete("AP", "id=?", new String[]{String.valueOf(it2.next())});
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x00ec A[SYNTHETIC, Splitter:B:52:0x00ec] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00f4 A[Catch:{ Throwable -> 0x00f0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0104 A[SYNTHETIC, Splitter:B:65:0x0104] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x010c A[Catch:{ Throwable -> 0x0108 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a() {
        /*
            r14 = this;
            r0 = 0
            com.amap.location.offline.b.b.c r1 = r14.d     // Catch:{ Throwable -> 0x0008 }
            android.database.sqlite.SQLiteDatabase r1 = r1.getWritableDatabase()     // Catch:{ Throwable -> 0x0008 }
            goto L_0x000d
        L_0x0008:
            r1 = move-exception
            com.amap.location.common.d.a.a(r1)
            r1 = r0
        L_0x000d:
            if (r1 != 0) goto L_0x0010
            return
        L_0x0010:
            r1.beginTransaction()     // Catch:{ Throwable -> 0x00e5, all -> 0x00e2 }
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00e5, all -> 0x00e2 }
            r4 = 1000(0x3e8, double:4.94E-321)
            long r2 = r2 / r4
            r6 = 7776000(0x76a700, double:3.8418545E-317)
            long r2 = r2 - r6
            java.lang.String r8 = "AP"
            java.lang.String r9 = "timestamp<?"
            r10 = 1
            java.lang.String[] r11 = new java.lang.String[r10]     // Catch:{ Throwable -> 0x00e5, all -> 0x00e2 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x00e5, all -> 0x00e2 }
            r12 = 0
            r11[r12] = r2     // Catch:{ Throwable -> 0x00e5, all -> 0x00e2 }
            r1.delete(r8, r9, r11)     // Catch:{ Throwable -> 0x00e5, all -> 0x00e2 }
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00e5, all -> 0x00e2 }
            long r2 = r2 / r4
            r4 = 0
            long r2 = r2 - r6
            java.lang.String r4 = "CL"
            java.lang.String r5 = "timestamp<?"
            java.lang.String[] r6 = new java.lang.String[r10]     // Catch:{ Throwable -> 0x00e5, all -> 0x00e2 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x00e5, all -> 0x00e2 }
            r6[r12] = r2     // Catch:{ Throwable -> 0x00e5, all -> 0x00e2 }
            r1.delete(r4, r5, r6)     // Catch:{ Throwable -> 0x00e5, all -> 0x00e2 }
            java.lang.String r3 = "AP"
            java.lang.String r2 = "id"
            java.lang.String[] r4 = new java.lang.String[]{r2}     // Catch:{ Throwable -> 0x00e5, all -> 0x00e2 }
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            java.lang.String r9 = "timestamp DESC,frequency DESC LIMIT 200000,-1"
            r2 = r1
            android.database.Cursor r11 = r2.query(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ Throwable -> 0x00e5, all -> 0x00e2 }
            if (r11 == 0) goto L_0x0088
            boolean r2 = r11.moveToFirst()     // Catch:{ Throwable -> 0x0083, all -> 0x0080 }
            if (r2 == 0) goto L_0x0088
        L_0x0063:
            boolean r2 = r11.isAfterLast()     // Catch:{ Throwable -> 0x0083, all -> 0x0080 }
            if (r2 != 0) goto L_0x0088
            long r2 = r11.getLong(r12)     // Catch:{ Throwable -> 0x0083, all -> 0x0080 }
            java.lang.String r4 = "AP"
            java.lang.String r5 = "id=?"
            java.lang.String[] r6 = new java.lang.String[r10]     // Catch:{ Throwable -> 0x0083, all -> 0x0080 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x0083, all -> 0x0080 }
            r6[r12] = r2     // Catch:{ Throwable -> 0x0083, all -> 0x0080 }
            r1.delete(r4, r5, r6)     // Catch:{ Throwable -> 0x0083, all -> 0x0080 }
            r11.moveToNext()     // Catch:{ Throwable -> 0x0083, all -> 0x0080 }
            goto L_0x0063
        L_0x0080:
            r2 = move-exception
            goto L_0x0102
        L_0x0083:
            r2 = move-exception
            r3 = r0
        L_0x0085:
            r0 = r11
            goto L_0x00e7
        L_0x0088:
            java.lang.String r3 = "CL"
            java.lang.String r2 = "id"
            java.lang.String[] r4 = new java.lang.String[]{r2}     // Catch:{ Throwable -> 0x0083, all -> 0x0080 }
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            java.lang.String r9 = "timestamp DESC,frequency DESC LIMIT 200000,-1"
            r2 = r1
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ Throwable -> 0x0083, all -> 0x0080 }
            if (r2 == 0) goto L_0x00ca
            boolean r0 = r2.moveToFirst()     // Catch:{ Throwable -> 0x00c6, all -> 0x00c1 }
            if (r0 == 0) goto L_0x00ca
        L_0x00a4:
            boolean r0 = r2.isAfterLast()     // Catch:{ Throwable -> 0x00c6, all -> 0x00c1 }
            if (r0 != 0) goto L_0x00ca
            long r3 = r2.getLong(r12)     // Catch:{ Throwable -> 0x00c6, all -> 0x00c1 }
            java.lang.String r0 = "CL"
            java.lang.String r5 = "id=?"
            java.lang.String[] r6 = new java.lang.String[r10]     // Catch:{ Throwable -> 0x00c6, all -> 0x00c1 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x00c6, all -> 0x00c1 }
            r6[r12] = r3     // Catch:{ Throwable -> 0x00c6, all -> 0x00c1 }
            r1.delete(r0, r5, r6)     // Catch:{ Throwable -> 0x00c6, all -> 0x00c1 }
            r2.moveToNext()     // Catch:{ Throwable -> 0x00c6, all -> 0x00c1 }
            goto L_0x00a4
        L_0x00c1:
            r0 = move-exception
            r13 = r2
            r2 = r0
            r0 = r13
            goto L_0x0102
        L_0x00c6:
            r0 = move-exception
            r3 = r2
            r2 = r0
            goto L_0x0085
        L_0x00ca:
            r1.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00c6, all -> 0x00c1 }
            if (r11 == 0) goto L_0x00d5
            r11.close()     // Catch:{ Throwable -> 0x00d3 }
            goto L_0x00d5
        L_0x00d3:
            r0 = move-exception
            goto L_0x00de
        L_0x00d5:
            if (r2 == 0) goto L_0x00da
            r2.close()     // Catch:{ Throwable -> 0x00d3 }
        L_0x00da:
            r1.endTransaction()     // Catch:{ Throwable -> 0x00d3 }
            return
        L_0x00de:
            com.amap.location.common.d.a.a(r0)
            return
        L_0x00e2:
            r2 = move-exception
            r11 = r0
            goto L_0x0102
        L_0x00e5:
            r2 = move-exception
            r3 = r0
        L_0x00e7:
            com.amap.location.common.d.a.a(r2)     // Catch:{ all -> 0x00ff }
            if (r0 == 0) goto L_0x00f2
            r0.close()     // Catch:{ Throwable -> 0x00f0 }
            goto L_0x00f2
        L_0x00f0:
            r0 = move-exception
            goto L_0x00fb
        L_0x00f2:
            if (r3 == 0) goto L_0x00f7
            r3.close()     // Catch:{ Throwable -> 0x00f0 }
        L_0x00f7:
            r1.endTransaction()     // Catch:{ Throwable -> 0x00f0 }
            return
        L_0x00fb:
            com.amap.location.common.d.a.a(r0)
            return
        L_0x00ff:
            r2 = move-exception
            r11 = r0
            r0 = r3
        L_0x0102:
            if (r11 == 0) goto L_0x010a
            r11.close()     // Catch:{ Throwable -> 0x0108 }
            goto L_0x010a
        L_0x0108:
            r0 = move-exception
            goto L_0x0113
        L_0x010a:
            if (r0 == 0) goto L_0x010f
            r0.close()     // Catch:{ Throwable -> 0x0108 }
        L_0x010f:
            r1.endTransaction()     // Catch:{ Throwable -> 0x0108 }
            goto L_0x0116
        L_0x0113:
            com.amap.location.common.d.a.a(r0)
        L_0x0116:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.offline.b.b.d.a():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x00b8 A[SYNTHETIC, Splitter:B:53:0x00b8] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00c0 A[Catch:{ Throwable -> 0x00bc }] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00d0 A[SYNTHETIC, Splitter:B:66:0x00d0] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x00d8 A[Catch:{ Throwable -> 0x00d4 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b() {
        /*
            r14 = this;
            r0 = 0
            com.amap.location.offline.b.b.g r1 = r14.e     // Catch:{ Throwable -> 0x0008 }
            android.database.sqlite.SQLiteDatabase r1 = r1.getWritableDatabase()     // Catch:{ Throwable -> 0x0008 }
            goto L_0x000d
        L_0x0008:
            r1 = move-exception
            com.amap.location.common.d.a.a(r1)
            r1 = r0
        L_0x000d:
            if (r1 != 0) goto L_0x0010
            return
        L_0x0010:
            r1.beginTransaction()     // Catch:{ Throwable -> 0x00b1, all -> 0x00ae }
            java.lang.String r3 = "AP"
            java.lang.String r2 = "id"
            java.lang.String[] r4 = new java.lang.String[]{r2}     // Catch:{ Throwable -> 0x00b1, all -> 0x00ae }
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            java.lang.String r9 = "frequency DESC LIMIT 10000,-1"
            r2 = r1
            android.database.Cursor r10 = r2.query(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ Throwable -> 0x00b1, all -> 0x00ae }
            r11 = 1
            r12 = 0
            if (r10 == 0) goto L_0x0055
            boolean r2 = r10.moveToFirst()     // Catch:{ Throwable -> 0x0050, all -> 0x004d }
            if (r2 == 0) goto L_0x0055
        L_0x0030:
            boolean r2 = r10.isAfterLast()     // Catch:{ Throwable -> 0x0050, all -> 0x004d }
            if (r2 != 0) goto L_0x0055
            long r2 = r10.getLong(r12)     // Catch:{ Throwable -> 0x0050, all -> 0x004d }
            java.lang.String r4 = "AP"
            java.lang.String r5 = "id=?"
            java.lang.String[] r6 = new java.lang.String[r11]     // Catch:{ Throwable -> 0x0050, all -> 0x004d }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x0050, all -> 0x004d }
            r6[r12] = r2     // Catch:{ Throwable -> 0x0050, all -> 0x004d }
            r1.delete(r4, r5, r6)     // Catch:{ Throwable -> 0x0050, all -> 0x004d }
            r10.moveToNext()     // Catch:{ Throwable -> 0x0050, all -> 0x004d }
            goto L_0x0030
        L_0x004d:
            r2 = move-exception
            goto L_0x00ce
        L_0x0050:
            r2 = move-exception
            r3 = r0
        L_0x0052:
            r0 = r10
            goto L_0x00b3
        L_0x0055:
            java.lang.String r3 = "CL"
            java.lang.String r2 = "id"
            java.lang.String[] r4 = new java.lang.String[]{r2}     // Catch:{ Throwable -> 0x0050, all -> 0x004d }
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            java.lang.String r9 = "frequency DESC LIMIT 10000,-1"
            r2 = r1
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ Throwable -> 0x0050, all -> 0x004d }
            if (r2 == 0) goto L_0x0096
            boolean r0 = r2.moveToFirst()     // Catch:{ Throwable -> 0x0092, all -> 0x008d }
            if (r0 == 0) goto L_0x0096
        L_0x0070:
            boolean r0 = r2.isAfterLast()     // Catch:{ Throwable -> 0x0092, all -> 0x008d }
            if (r0 != 0) goto L_0x0096
            long r3 = r2.getLong(r12)     // Catch:{ Throwable -> 0x0092, all -> 0x008d }
            java.lang.String r0 = "CL"
            java.lang.String r5 = "id=?"
            java.lang.String[] r6 = new java.lang.String[r11]     // Catch:{ Throwable -> 0x0092, all -> 0x008d }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x0092, all -> 0x008d }
            r6[r12] = r3     // Catch:{ Throwable -> 0x0092, all -> 0x008d }
            r1.delete(r0, r5, r6)     // Catch:{ Throwable -> 0x0092, all -> 0x008d }
            r2.moveToNext()     // Catch:{ Throwable -> 0x0092, all -> 0x008d }
            goto L_0x0070
        L_0x008d:
            r0 = move-exception
            r13 = r2
            r2 = r0
            r0 = r13
            goto L_0x00ce
        L_0x0092:
            r0 = move-exception
            r3 = r2
            r2 = r0
            goto L_0x0052
        L_0x0096:
            r1.setTransactionSuccessful()     // Catch:{ Throwable -> 0x0092, all -> 0x008d }
            if (r10 == 0) goto L_0x00a1
            r10.close()     // Catch:{ Throwable -> 0x009f }
            goto L_0x00a1
        L_0x009f:
            r0 = move-exception
            goto L_0x00aa
        L_0x00a1:
            if (r2 == 0) goto L_0x00a6
            r2.close()     // Catch:{ Throwable -> 0x009f }
        L_0x00a6:
            r1.endTransaction()     // Catch:{ Throwable -> 0x009f }
            return
        L_0x00aa:
            com.amap.location.common.d.a.a(r0)
            return
        L_0x00ae:
            r2 = move-exception
            r10 = r0
            goto L_0x00ce
        L_0x00b1:
            r2 = move-exception
            r3 = r0
        L_0x00b3:
            com.amap.location.common.d.a.a(r2)     // Catch:{ all -> 0x00cb }
            if (r0 == 0) goto L_0x00be
            r0.close()     // Catch:{ Throwable -> 0x00bc }
            goto L_0x00be
        L_0x00bc:
            r0 = move-exception
            goto L_0x00c7
        L_0x00be:
            if (r3 == 0) goto L_0x00c3
            r3.close()     // Catch:{ Throwable -> 0x00bc }
        L_0x00c3:
            r1.endTransaction()     // Catch:{ Throwable -> 0x00bc }
            return
        L_0x00c7:
            com.amap.location.common.d.a.a(r0)
            return
        L_0x00cb:
            r2 = move-exception
            r10 = r0
            r0 = r3
        L_0x00ce:
            if (r10 == 0) goto L_0x00d6
            r10.close()     // Catch:{ Throwable -> 0x00d4 }
            goto L_0x00d6
        L_0x00d4:
            r0 = move-exception
            goto L_0x00df
        L_0x00d6:
            if (r0 == 0) goto L_0x00db
            r0.close()     // Catch:{ Throwable -> 0x00d4 }
        L_0x00db:
            r1.endTransaction()     // Catch:{ Throwable -> 0x00d4 }
            goto L_0x00e2
        L_0x00df:
            com.amap.location.common.d.a.a(r0)
        L_0x00e2:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.offline.b.b.d.b():void");
    }

    public void c() {
        try {
            SQLiteDatabase writableDatabase = this.d.getWritableDatabase();
            SQLiteDatabase writableDatabase2 = this.e.getWritableDatabase();
            writableDatabase.delete("CL", null, null);
            writableDatabase.delete("AP", null, null);
            writableDatabase2.delete("CL", null, null);
            writableDatabase2.delete("AP", null, null);
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
        }
    }
}
