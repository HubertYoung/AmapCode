package com.huawei.android.pushselfshow.richpush.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.utils.a;

public class b {
    private static b a;

    private b() {
    }

    private a a(Context context) {
        if (a.e(context)) {
            c.a("PushSelfShowLog", "operate apk self database");
            return new e();
        } else if (!a.f(context)) {
            c.a("PushSelfShowLog", "operate sdk self database");
            return new e();
        } else if (a.g(context)) {
            c.a("PushSelfShowLog", "operate apk provider database");
            return new c();
        } else {
            c.a("PushSelfShowLog", "operate sdcard database");
            return new d(context);
        }
    }

    public static synchronized b a() {
        b bVar;
        synchronized (b.class) {
            try {
                if (a == null) {
                    a = new b();
                }
                bVar = a;
            }
        }
        return bVar;
    }

    public Cursor a(Context context, Uri uri, String str, String[] strArr) throws Exception {
        return a(context).a(context, uri, str, strArr);
    }

    public void a(Context context, Uri uri, String str, ContentValues contentValues) throws Exception {
        a(context).a(context, uri, str, contentValues);
    }

    public void a(Context context, Uri uri, String str, String str2, String[] strArr) throws Exception {
        a(context).a(context, uri, str, str2, strArr);
    }
}
