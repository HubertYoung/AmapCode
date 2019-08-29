package com.alipay.sdk.tid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.alipay.sdk.util.a;

public final class b {
    private static b c;
    public String a;
    public String b;

    private b() {
    }

    private String b() {
        return this.a;
    }

    private void a(String str) {
        this.a = str;
    }

    private String c() {
        return this.b;
    }

    private void b(String str) {
        this.b = str;
    }

    private void a(Context context) {
        a aVar = new a(context);
        try {
            aVar.a(a.a(context).a(), a.a(context).b(), this.a, this.b);
        } catch (Exception unused) {
        } finally {
            aVar.close();
        }
    }

    private boolean d() {
        return TextUtils.isEmpty(this.a);
    }

    public static synchronized b a() {
        b bVar;
        synchronized (b.class) {
            try {
                if (c == null) {
                    c = new b();
                    Context context = com.alipay.sdk.sys.b.a().a;
                    a aVar = new a(context);
                    String a2 = a.a(context).a();
                    String b2 = a.a(context).b();
                    c.a = aVar.a(a2, b2);
                    c.b = aVar.b(a2, b2);
                    if (TextUtils.isEmpty(c.b)) {
                        b bVar2 = c;
                        String hexString = Long.toHexString(System.currentTimeMillis());
                        if (hexString.length() > 10) {
                            hexString = hexString.substring(hexString.length() - 10);
                        }
                        bVar2.b = hexString;
                    }
                    aVar.a(a2, b2, c.a, c.b);
                }
                bVar = c;
            }
        }
        return bVar;
    }

    private static void e() {
        SQLiteDatabase sQLiteDatabase;
        Throwable th;
        Context context = com.alipay.sdk.sys.b.a().a;
        String a2 = a.a(context).a();
        String b2 = a.a(context).b();
        a aVar = new a(context);
        SQLiteDatabase sQLiteDatabase2 = null;
        try {
            sQLiteDatabase = aVar.getWritableDatabase();
            try {
                aVar.a(sQLiteDatabase, a2, b2, "", "");
                a.a(sQLiteDatabase, a.c(a2, b2));
                if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                    sQLiteDatabase.close();
                }
            } catch (Exception unused) {
                sQLiteDatabase2 = sQLiteDatabase;
                if (sQLiteDatabase2 != null && sQLiteDatabase2.isOpen()) {
                    sQLiteDatabase2.close();
                }
                aVar.close();
            } catch (Throwable th2) {
                th = th2;
                if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                    sQLiteDatabase.close();
                }
                throw th;
            }
        } catch (Exception unused2) {
            sQLiteDatabase2.close();
            aVar.close();
        } catch (Throwable th3) {
            sQLiteDatabase = null;
            th = th3;
            sQLiteDatabase.close();
            throw th;
        }
        aVar.close();
    }

    private static String f() {
        String hexString = Long.toHexString(System.currentTimeMillis());
        return hexString.length() > 10 ? hexString.substring(hexString.length() - 10) : hexString;
    }
}
