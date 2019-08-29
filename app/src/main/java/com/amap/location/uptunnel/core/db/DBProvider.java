package com.amap.location.uptunnel.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import com.amap.location.common.d.a;
import com.amap.location.common.database.AbstractContentProvider;
import com.autonavi.minimap.bundle.apm.internal.report.ReportManager;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;

public class DBProvider extends AbstractContentProvider {
    public static String d = "com.amap.android.uptunnel.dbPersistent";
    private static Object e = new Object();
    private static volatile DBProvider f;
    private static Context g;
    private a h;

    private DBProvider(Context context) {
        g = context;
        onCreate();
    }

    public static DBProvider a(Context context) {
        try {
            if (f == null) {
                synchronized (e) {
                    if (f == null) {
                        f = new DBProvider(context.getApplicationContext());
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return f;
    }

    public String a() {
        return d;
    }

    public void b() {
        a aVar = new a(g);
        this.h = aVar;
        a(Integer.valueOf(1), (String) NewHtcHomeBadger.COUNT, (SQLiteOpenHelper) aVar);
        a(Integer.valueOf(2), (String) "event", (SQLiteOpenHelper) aVar);
        a(Integer.valueOf(3), (String) "key_log", (SQLiteOpenHelper) aVar);
        a(Integer.valueOf(4), (String) ReportManager.LOG_PATH, (SQLiteOpenHelper) aVar);
        a(Integer.valueOf(5), (String) "data_block", (SQLiteOpenHelper) aVar);
    }

    public SQLiteDatabase c() {
        try {
            return this.h.getWritableDatabase();
        } catch (Exception e2) {
            a.a((Throwable) e2);
            return null;
        }
    }

    public static Uri a(String str) {
        StringBuilder sb = new StringBuilder("content://");
        sb.append(d);
        sb.append("/");
        sb.append(str);
        return Uri.parse(sb.toString());
    }
}
