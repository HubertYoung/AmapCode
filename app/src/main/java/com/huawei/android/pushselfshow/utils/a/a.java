package com.huawei.android.pushselfshow.utils.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.huawei.android.pushagent.a.a.c;
import java.io.File;
import java.util.ArrayList;

public class a {
    public static ArrayList a(Context context, String str) {
        Cursor a;
        String str2;
        String str3;
        ArrayList arrayList = new ArrayList();
        try {
            String c = c(context, "hwpushApp.db");
            if (TextUtils.isEmpty(c)) {
                c.a("PushSelfShowLog", "database is null,can't queryAppinfo");
                return arrayList;
            }
            c.a("PushSelfShowLog", "dbName path is ".concat(String.valueOf(c)));
            if (d.a().a(c, "openmarket")) {
                a = d.a().a(c, (String) "select * from openmarket where package = ?;", new String[]{str});
                if (a == null) {
                    c.a("PushSelfShowLog", "cursor is null.");
                    return arrayList;
                }
                try {
                    if (a.getCount() > 0) {
                        do {
                            String string = a.getString(a.getColumnIndex("msgid"));
                            arrayList.add(string);
                            StringBuilder sb = new StringBuilder("msgid and packageName is  ");
                            sb.append(string);
                            sb.append(",");
                            sb.append(str);
                            c.a(RPCDataItems.SWITCH_TAG_LOG, sb.toString());
                        } while (a.moveToNext());
                    }
                    try {
                        a.close();
                        return arrayList;
                    } catch (Exception e) {
                        e = e;
                        str2 = "PushSelfShowLog";
                        str3 = "cursor.close() ";
                        c.d(str2, str3, e);
                        return arrayList;
                    }
                } catch (Exception e2) {
                    StringBuilder sb2 = new StringBuilder("queryAppinfo error ");
                    sb2.append(e2.toString());
                    c.c(RPCDataItems.SWITCH_TAG_LOG, sb2.toString(), e2);
                    try {
                        a.close();
                        return arrayList;
                    } catch (Exception e3) {
                        e = e3;
                        str2 = "PushSelfShowLog";
                        str3 = "cursor.close() ";
                        c.d(str2, str3, e);
                        return arrayList;
                    }
                }
            }
            return arrayList;
        } catch (Exception e4) {
            c.d("PushSelfShowLog", "queryAppinfo error", e4);
        } catch (Throwable th) {
            try {
                a.close();
            } catch (Exception e5) {
                c.d("PushSelfShowLog", "cursor.close() ", e5);
            }
            throw th;
        }
    }

    public static void a(Context context, String str, String str2) {
        try {
            if (!context.getDatabasePath("hwpushApp.db").exists()) {
                context.openOrCreateDatabase("hwpushApp.db", 0, null);
            }
            String c = c(context, "hwpushApp.db");
            if (TextUtils.isEmpty(c)) {
                c.d("PushSelfShowLog", "database is null,can't insert appinfo into db");
                return;
            }
            c.a("PushSelfShowLog", "dbName path is ".concat(String.valueOf(c)));
            if (!d.a().a(c, "openmarket")) {
                d.a().a(context, c, (String) "create table openmarket(    _id INTEGER PRIMARY KEY AUTOINCREMENT,     msgid  TEXT,    package TEXT);");
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("msgid", str);
            contentValues.put("package", str2);
            d.a().a(context, c, (String) "openmarket", contentValues);
        } catch (Exception e) {
            c.d("PushSelfShowLog", "insertAppinfo error", e);
        }
    }

    public static void b(Context context, String str) {
        try {
            String c = c(context, "hwpushApp.db");
            if (TextUtils.isEmpty(c)) {
                c.d("PushSelfShowLog", "database is null,can't delete appinfo");
                return;
            }
            c.a("PushSelfShowLog", "dbName path is ".concat(String.valueOf(c)));
            if (d.a().a(c, "openmarket")) {
                d.a().a(c, (String) "openmarket", (String) "package = ?", new String[]{str});
            }
        } catch (Exception e) {
            c.d("PushSelfShowLog", "Delete Appinfo error", e);
        }
    }

    private static String c(Context context, String str) {
        String str2 = "";
        if (context == null) {
            return str2;
        }
        File databasePath = context.getDatabasePath("hwpushApp.db");
        if (databasePath.exists()) {
            str2 = databasePath.getAbsolutePath();
        }
        return str2;
    }
}
