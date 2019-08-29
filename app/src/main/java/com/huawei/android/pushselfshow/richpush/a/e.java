package com.huawei.android.pushselfshow.richpush.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.utils.a.b;

public class e implements a {
    private String a;

    public e() {
        this.a = null;
        this.a = null;
    }

    protected e(String str) {
        this.a = null;
        this.a = str;
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x007a  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0084  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(android.content.Context r8, android.database.sqlite.SQLiteDatabase r9, java.lang.String r10, android.content.ContentValues r11) throws java.lang.Exception {
        /*
            if (r8 != 0) goto L_0x000a
            java.lang.String r8 = "PushSelfShowLog"
            java.lang.String r9 = "context is null"
        L_0x0006:
            com.huawei.android.pushagent.a.a.c.d(r8, r9)
            return
        L_0x000a:
            if (r9 != 0) goto L_0x0011
            java.lang.String r8 = "PushSelfShowLog"
            java.lang.String r9 = "db is null"
            goto L_0x0006
        L_0x0011:
            boolean r8 = android.text.TextUtils.isEmpty(r10)
            if (r8 == 0) goto L_0x001c
            java.lang.String r8 = "PushSelfShowLog"
            java.lang.String r9 = "table is null"
            goto L_0x0006
        L_0x001c:
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r0 = r9
            r1 = r10
            android.database.Cursor r0 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x006c, all -> 0x0068 }
            if (r0 != 0) goto L_0x003d
            java.lang.String r8 = "PushSelfShowLog"
            java.lang.String r10 = "cursor is null"
            com.huawei.android.pushagent.a.a.c.d(r8, r10)     // Catch:{ Exception -> 0x003b }
            if (r0 == 0) goto L_0x0037
            r0.close()
        L_0x0037:
            r9.close()
            return
        L_0x003b:
            r8 = move-exception
            goto L_0x006f
        L_0x003d:
            int r1 = r0.getCount()     // Catch:{ Exception -> 0x003b }
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.String r3 = "queryAndInsert, exist rowNumber:"
            java.lang.String r4 = java.lang.String.valueOf(r1)     // Catch:{ Exception -> 0x003b }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ Exception -> 0x003b }
            com.huawei.android.pushagent.a.a.c.a(r2, r3)     // Catch:{ Exception -> 0x003b }
            r2 = 1000(0x3e8, float:1.401E-42)
            if (r1 >= r2) goto L_0x0058
            r9.insert(r10, r8, r11)     // Catch:{ Exception -> 0x003b }
            goto L_0x005f
        L_0x0058:
            java.lang.String r8 = "PushSelfShowLog"
            java.lang.String r10 = "queryAndInsert failed"
            com.huawei.android.pushagent.a.a.c.d(r8, r10)     // Catch:{ Exception -> 0x003b }
        L_0x005f:
            if (r0 == 0) goto L_0x0064
            r0.close()
        L_0x0064:
            r9.close()
            return
        L_0x0068:
            r10 = move-exception
            r0 = r8
            r8 = r10
            goto L_0x0082
        L_0x006c:
            r10 = move-exception
            r0 = r8
            r8 = r10
        L_0x006f:
            java.lang.String r10 = "PushSelfShowLog"
            java.lang.String r11 = r8.toString()     // Catch:{ all -> 0x0081 }
            com.huawei.android.pushagent.a.a.c.c(r10, r11, r8)     // Catch:{ all -> 0x0081 }
            if (r0 == 0) goto L_0x007d
            r0.close()
        L_0x007d:
            r9.close()
            return
        L_0x0081:
            r8 = move-exception
        L_0x0082:
            if (r0 == 0) goto L_0x0087
            r0.close()
        L_0x0087:
            r9.close()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.richpush.a.e.a(android.content.Context, android.database.sqlite.SQLiteDatabase, java.lang.String, android.content.ContentValues):void");
    }

    public Cursor a(Context context, Uri uri, String str, String[] strArr) throws Exception {
        SQLiteDatabase readableDatabase = a(context).getReadableDatabase();
        if (readableDatabase != null) {
            try {
                return readableDatabase.rawQuery(str, strArr);
            } catch (Exception e) {
                c.c("PushSelfShowLog", e.toString(), e);
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public b a(Context context) {
        return this.a == null ? b.a(context) : b.a(context, this.a);
    }

    public void a(Context context, Uri uri, String str, ContentValues contentValues) throws Exception {
        a(context, a(context).getWritableDatabase(), str, contentValues);
    }

    public void a(Context context, Uri uri, String str, String str2, String[] strArr) throws Exception {
        SQLiteDatabase writableDatabase = a(context).getWritableDatabase();
        if (writableDatabase != null) {
            try {
                writableDatabase.delete(str, str2, strArr);
            } catch (Exception e) {
                c.c("PushSelfShowLog", e.toString(), e);
            } finally {
                writableDatabase.close();
            }
        }
    }
}
