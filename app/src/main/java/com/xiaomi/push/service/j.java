package com.xiaomi.push.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.misc.n;
import com.xiaomi.push.service.module.a;
import java.util.ArrayList;
import java.util.Iterator;

public class j {
    private static volatile j a;
    private Context b;

    private j(Context context) {
        this.b = context;
    }

    private synchronized Cursor a(SQLiteDatabase sQLiteDatabase) {
        n.a(false);
        try {
        } catch (Exception e) {
            b.d(e.toString());
            return null;
        }
        return sQLiteDatabase.query("geoMessage", null, null, null, null, null, null);
    }

    public static j a(Context context) {
        if (a == null) {
            synchronized (j.class) {
                try {
                    if (a == null) {
                        a = new j(context);
                    }
                }
            }
        }
        return a;
    }

    public synchronized int a(String str) {
        n.a(false);
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            int delete = i.a(this.b).a().delete("geoMessage", "message_id = ?", new String[]{str});
            i.a(this.b).b();
            return delete;
        } catch (Exception e) {
            b.d(e.toString());
            return 0;
        }
    }

    public synchronized ArrayList<a> a() {
        ArrayList<a> arrayList;
        n.a(false);
        try {
            Cursor a2 = a(i.a(this.b).a());
            arrayList = new ArrayList<>();
            if (a2 != null) {
                while (a2.moveToNext()) {
                    a aVar = new a();
                    aVar.a(a2.getString(a2.getColumnIndex("message_id")));
                    aVar.b(a2.getString(a2.getColumnIndex("geo_id")));
                    aVar.a(a2.getBlob(a2.getColumnIndex("content")));
                    aVar.a(a2.getInt(a2.getColumnIndex("action")));
                    aVar.a(a2.getLong(a2.getColumnIndex("deadline")));
                    arrayList.add(aVar);
                }
                a2.close();
            }
            i.a(this.b).b();
        } catch (Exception e) {
            b.d(e.toString());
            return null;
        }
        return arrayList;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0058, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean a(java.util.ArrayList<android.content.ContentValues> r8) {
        /*
            r7 = this;
            monitor-enter(r7)
            r0 = 0
            com.xiaomi.channel.commonutils.misc.n.a(r0)     // Catch:{ all -> 0x0059 }
            if (r8 == 0) goto L_0x0057
            int r1 = r8.size()     // Catch:{ all -> 0x0059 }
            if (r1 > 0) goto L_0x000e
            goto L_0x0057
        L_0x000e:
            r1 = 1
            android.content.Context r2 = r7.b     // Catch:{ Exception -> 0x004d }
            com.xiaomi.push.service.i r2 = com.xiaomi.push.service.i.a(r2)     // Catch:{ Exception -> 0x004d }
            android.database.sqlite.SQLiteDatabase r2 = r2.a()     // Catch:{ Exception -> 0x004d }
            r2.beginTransaction()     // Catch:{ Exception -> 0x004d }
            java.util.Iterator r8 = r8.iterator()     // Catch:{ Exception -> 0x004d }
        L_0x0020:
            boolean r3 = r8.hasNext()     // Catch:{ Exception -> 0x004d }
            if (r3 == 0) goto L_0x003a
            java.lang.Object r3 = r8.next()     // Catch:{ Exception -> 0x004d }
            android.content.ContentValues r3 = (android.content.ContentValues) r3     // Catch:{ Exception -> 0x004d }
            java.lang.String r4 = "geoMessage"
            r5 = 0
            long r3 = r2.insert(r4, r5, r3)     // Catch:{ Exception -> 0x004d }
            r5 = -1
            int r3 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r3 != 0) goto L_0x0020
            r1 = 0
        L_0x003a:
            if (r1 == 0) goto L_0x003f
            r2.setTransactionSuccessful()     // Catch:{ Exception -> 0x004d }
        L_0x003f:
            r2.endTransaction()     // Catch:{ Exception -> 0x004d }
            android.content.Context r8 = r7.b     // Catch:{ Exception -> 0x004d }
            com.xiaomi.push.service.i r8 = com.xiaomi.push.service.i.a(r8)     // Catch:{ Exception -> 0x004d }
            r8.b()     // Catch:{ Exception -> 0x004d }
            monitor-exit(r7)
            return r1
        L_0x004d:
            r8 = move-exception
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0059 }
            com.xiaomi.channel.commonutils.logger.b.d(r8)     // Catch:{ all -> 0x0059 }
            monitor-exit(r7)
            return r0
        L_0x0057:
            monitor-exit(r7)
            return r0
        L_0x0059:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.j.a(java.util.ArrayList):boolean");
    }

    public synchronized int b(String str) {
        n.a(false);
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            int delete = i.a(this.b).a().delete("geoMessage", "geo_id = ?", new String[]{str});
            i.a(this.b).b();
            return delete;
        } catch (Exception e) {
            b.d(e.toString());
            return 0;
        }
    }

    public synchronized ArrayList<a> c(String str) {
        n.a(false);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            ArrayList<a> a2 = a();
            ArrayList<a> arrayList = new ArrayList<>();
            Iterator<a> it = a2.iterator();
            while (it.hasNext()) {
                a next = it.next();
                if (TextUtils.equals(next.c(), str)) {
                    arrayList.add(next);
                }
            }
            return arrayList;
        } catch (Exception e) {
            b.d(e.toString());
            return null;
        }
    }
}
