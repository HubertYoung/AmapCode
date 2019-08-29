package com.amap.location.offline.c;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.amap.location.offline.e.c;

/* compiled from: EventManager */
public class a {
    private static volatile a a;
    private C0035a b;
    private Object c = new Object();

    /* renamed from: com.amap.location.offline.c.a$a reason: collision with other inner class name */
    /* compiled from: EventManager */
    static class C0035a extends SQLiteOpenHelper {
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }

        C0035a(Context context) {
            super(context, "OffEvent.db", null, 1);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            b.a(sQLiteDatabase);
        }
    }

    public static a a() {
        if (a == null) {
            synchronized (a.class) {
                try {
                    if (a == null) {
                        a = new a();
                    }
                }
            }
        }
        return a;
    }

    private a() {
    }

    public void a(Context context, com.amap.location.offline.b.a.a aVar) {
        synchronized (this.c) {
            b(context);
        }
        if (!TextUtils.isEmpty(aVar.f)) {
            a(aVar.f.replace(":", "_"));
        }
    }

    public void a(Context context) {
        synchronized (this.c) {
            b(context);
        }
        if (TextUtils.isEmpty(com.amap.location.common.a.a())) {
            com.amap.location.common.d.a.c("offevnmgr", "adiu is null");
            return;
        }
        boolean a2 = c.a(context, 500);
        int b2 = c.b(context, 500);
        if (a2 && b2 > 0) {
            String a3 = a(b2);
            if (a3 != null && a3.length() > 0) {
                com.amap.location.offline.d.a.a(800002, a3.getBytes());
                int i = 0;
                String[] split = a3.split(MetaRecord.LOG_SEPARATOR);
                if (split != null && split.length > 0) {
                    i = split.length;
                }
                c.c(context, i);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0034 A[Catch:{ Throwable -> 0x002e, all -> 0x002c }] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004d A[Catch:{ Throwable -> 0x002e, all -> 0x002c }] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0069 A[SYNTHETIC, Splitter:B:17:0x0069] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x007b A[SYNTHETIC, Splitter:B:29:0x007b] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0087 A[SYNTHETIC, Splitter:B:37:0x0087] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.lang.String r13) {
        /*
            r12 = this;
            r0 = 0
            com.amap.location.offline.c.a$a r1 = r12.b     // Catch:{ Throwable -> 0x0075 }
            android.database.sqlite.SQLiteDatabase r1 = r1.getWritableDatabase()     // Catch:{ Throwable -> 0x0075 }
            java.lang.String r3 = "ACL"
            r10 = 1
            java.lang.String[] r4 = new java.lang.String[r10]     // Catch:{ Throwable -> 0x0075 }
            java.lang.String r2 = com.amap.location.offline.c.b.b     // Catch:{ Throwable -> 0x0075 }
            r11 = 0
            r4[r11] = r2     // Catch:{ Throwable -> 0x0075 }
            java.lang.String r5 = "id=?"
            java.lang.String[] r6 = new java.lang.String[r10]     // Catch:{ Throwable -> 0x0075 }
            r6[r11] = r13     // Catch:{ Throwable -> 0x0075 }
            r7 = 0
            r8 = 0
            r9 = 0
            r2 = r1
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ Throwable -> 0x0075 }
            if (r2 == 0) goto L_0x0031
            boolean r3 = r2.moveToFirst()     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
            if (r3 == 0) goto L_0x0031
            int r3 = r2.getInt(r11)     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
            goto L_0x0032
        L_0x002c:
            r13 = move-exception
            goto L_0x0085
        L_0x002e:
            r13 = move-exception
            r0 = r2
            goto L_0x0076
        L_0x0031:
            r3 = 0
        L_0x0032:
            if (r3 != 0) goto L_0x004d
            android.content.ContentValues r3 = new android.content.ContentValues     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
            r3.<init>()     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
            java.lang.String r4 = "id"
            r3.put(r4, r13)     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
            java.lang.String r13 = "frequency"
            java.lang.Integer r4 = java.lang.Integer.valueOf(r10)     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
            r3.put(r13, r4)     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
            java.lang.String r13 = "ACL"
            r1.insert(r13, r0, r3)     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
            goto L_0x0067
        L_0x004d:
            android.content.ContentValues r0 = new android.content.ContentValues     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
            r0.<init>()     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
            java.lang.String r4 = "frequency"
            int r3 = r3 + r10
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
            r0.put(r4, r3)     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
            java.lang.String r3 = "ACL"
            java.lang.String r4 = "id=?"
            java.lang.String[] r5 = new java.lang.String[r10]     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
            r5[r11] = r13     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
            r1.update(r3, r0, r4, r5)     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
        L_0x0067:
            if (r2 == 0) goto L_0x0084
            r2.close()     // Catch:{ Throwable -> 0x006d }
            return
        L_0x006d:
            r13 = move-exception
            com.amap.location.common.d.a.a(r13)
            return
        L_0x0072:
            r13 = move-exception
            r2 = r0
            goto L_0x0085
        L_0x0075:
            r13 = move-exception
        L_0x0076:
            com.amap.location.common.d.a.a(r13)     // Catch:{ all -> 0x0072 }
            if (r0 == 0) goto L_0x0084
            r0.close()     // Catch:{ Throwable -> 0x007f }
            return
        L_0x007f:
            r13 = move-exception
            com.amap.location.common.d.a.a(r13)
            return
        L_0x0084:
            return
        L_0x0085:
            if (r2 == 0) goto L_0x008f
            r2.close()     // Catch:{ Throwable -> 0x008b }
            goto L_0x008f
        L_0x008b:
            r0 = move-exception
            com.amap.location.common.d.a.a(r0)
        L_0x008f:
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.offline.c.a.a(java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x00da A[SYNTHETIC, Splitter:B:55:0x00da] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00f2 A[SYNTHETIC, Splitter:B:66:0x00f2] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String a(int r19) {
        /*
            r18 = this;
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r4 = r18
            com.amap.location.offline.c.a$a r5 = r4.b     // Catch:{ Exception -> 0x0013 }
            android.database.sqlite.SQLiteDatabase r5 = r5.getWritableDatabase()     // Catch:{ Exception -> 0x0013 }
            goto L_0x0019
        L_0x0013:
            r0 = move-exception
            r5 = r0
            com.amap.location.common.d.a.a(r5)
            r5 = 0
        L_0x0019:
            if (r5 != 0) goto L_0x0020
            java.lang.String r1 = r1.toString()
            return r1
        L_0x0020:
            r5.beginTransaction()     // Catch:{ Throwable -> 0x00d2, all -> 0x00ce }
            java.lang.String r7 = "ACL"
            r6 = 2
            java.lang.String[] r8 = new java.lang.String[r6]     // Catch:{ Throwable -> 0x00d2, all -> 0x00ce }
            java.lang.String r6 = com.amap.location.offline.c.b.a     // Catch:{ Throwable -> 0x00d2, all -> 0x00ce }
            r15 = 0
            r8[r15] = r6     // Catch:{ Throwable -> 0x00d2, all -> 0x00ce }
            java.lang.String r6 = com.amap.location.offline.c.b.b     // Catch:{ Throwable -> 0x00d2, all -> 0x00ce }
            r14 = 1
            r8[r14] = r6     // Catch:{ Throwable -> 0x00d2, all -> 0x00ce }
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            java.lang.String r13 = "frequency DESC"
            java.lang.String r16 = java.lang.String.valueOf(r19)     // Catch:{ Throwable -> 0x00d2, all -> 0x00ce }
            r6 = r5
            r3 = 1
            r14 = r16
            android.database.Cursor r6 = r6.query(r7, r8, r9, r10, r11, r12, r13, r14)     // Catch:{ Throwable -> 0x00d2, all -> 0x00ce }
            if (r6 == 0) goto L_0x00c2
            boolean r7 = r6.moveToFirst()     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            if (r7 == 0) goto L_0x00c2
        L_0x004c:
            boolean r7 = r6.isAfterLast()     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            if (r7 != 0) goto L_0x009f
            java.lang.String r7 = r6.getString(r15)     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            int r8 = r6.getInt(r3)     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            if (r7 == 0) goto L_0x009b
            java.lang.String r9 = "_"
            java.lang.String[] r9 = r7.split(r9)     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            if (r9 == 0) goto L_0x0097
            int r10 = r9.length     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            r11 = 3
            r12 = 4
            if (r10 == r11) goto L_0x006d
            int r10 = r9.length     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            if (r10 == r12) goto L_0x006d
            goto L_0x0097
        L_0x006d:
            int r9 = r9.length     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            if (r9 != r12) goto L_0x0072
            r9 = 0
            goto L_0x0073
        L_0x0072:
            r9 = 1
        L_0x0073:
            int r10 = r1.length()     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            if (r10 == 0) goto L_0x007e
            java.lang.String r10 = "#"
            r1.append(r10)     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
        L_0x007e:
            r1.append(r9)     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            java.lang.String r9 = "|"
            r1.append(r9)     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            r1.append(r7)     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            java.lang.String r9 = "|"
            r1.append(r9)     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            r1.append(r8)     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            r2.add(r7)     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            goto L_0x009b
        L_0x0097:
            r6.moveToNext()     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            goto L_0x004c
        L_0x009b:
            r6.moveToNext()     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            goto L_0x004c
        L_0x009f:
            java.util.Iterator r2 = r2.iterator()     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
        L_0x00a3:
            boolean r7 = r2.hasNext()     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            if (r7 == 0) goto L_0x00c2
            java.lang.Object r7 = r2.next()     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            java.lang.String r8 = "ACL"
            java.lang.String r9 = "id=?"
            java.lang.String[] r10 = new java.lang.String[r3]     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            r10[r15] = r7     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            r5.delete(r8, r9, r10)     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            goto L_0x00a3
        L_0x00bb:
            r0 = move-exception
            r1 = r0
            goto L_0x00f0
        L_0x00be:
            r0 = move-exception
            r2 = r0
            r3 = r6
            goto L_0x00d5
        L_0x00c2:
            r5.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            if (r6 == 0) goto L_0x00ca
            r6.close()     // Catch:{ Throwable -> 0x00de }
        L_0x00ca:
            r5.endTransaction()     // Catch:{ Throwable -> 0x00de }
            goto L_0x00e8
        L_0x00ce:
            r0 = move-exception
            r1 = r0
            r6 = 0
            goto L_0x00f0
        L_0x00d2:
            r0 = move-exception
            r2 = r0
            r3 = 0
        L_0x00d5:
            com.amap.location.common.d.a.a(r2)     // Catch:{ all -> 0x00ed }
            if (r3 == 0) goto L_0x00e1
            r3.close()     // Catch:{ Throwable -> 0x00de }
            goto L_0x00e1
        L_0x00de:
            r0 = move-exception
            r2 = r0
            goto L_0x00e5
        L_0x00e1:
            r5.endTransaction()     // Catch:{ Throwable -> 0x00de }
            goto L_0x00e8
        L_0x00e5:
            com.amap.location.common.d.a.a(r2)
        L_0x00e8:
            java.lang.String r1 = r1.toString()
            return r1
        L_0x00ed:
            r0 = move-exception
            r1 = r0
            r6 = r3
        L_0x00f0:
            if (r6 == 0) goto L_0x00f9
            r6.close()     // Catch:{ Throwable -> 0x00f6 }
            goto L_0x00f9
        L_0x00f6:
            r0 = move-exception
            r2 = r0
            goto L_0x00fd
        L_0x00f9:
            r5.endTransaction()     // Catch:{ Throwable -> 0x00f6 }
            goto L_0x0100
        L_0x00fd:
            com.amap.location.common.d.a.a(r2)
        L_0x0100:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.offline.c.a.a(int):java.lang.String");
    }

    private void b(Context context) {
        if (this.b == null) {
            this.b = new C0035a(context);
        }
    }
}
