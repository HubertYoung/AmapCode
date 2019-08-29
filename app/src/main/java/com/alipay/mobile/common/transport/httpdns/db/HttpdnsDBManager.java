package com.alipay.mobile.common.transport.httpdns.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.alipay.mobile.common.transport.utils.LogCatUtil;

public class HttpdnsDBManager extends SQLiteOpenHelper {
    private static volatile HttpdnsDBManager a;

    public static synchronized HttpdnsDBManager getInstance(Context ctx) {
        HttpdnsDBManager httpdnsDBManager;
        synchronized (HttpdnsDBManager.class) {
            try {
                if (a == null) {
                    a = new HttpdnsDBManager(ctx, "httpdns.db");
                }
                httpdnsDBManager = a;
            }
        }
        return httpdnsDBManager;
    }

    private HttpdnsDBManager(Context context, String dbName) {
        super(context, dbName, null, 6);
    }

    public void onCreate(SQLiteDatabase db) {
        LogCatUtil.debug("HTTP_DNS_DBManager", "onCreate.");
        a(db);
    }

    private static void a(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS httpdns_original");
        db.execSQL(HttpdnsDBSql.CREATE_HTTPDNS_ORIGINAL);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogCatUtil.debug("HTTP_DNS_DBManager", "onUpgrade from " + oldVersion + " to " + newVersion);
        if (newVersion != oldVersion) {
            try {
                db.execSQL(HttpdnsDBSql.dropTable);
                onCreate(db);
            } catch (Exception e) {
                LogCatUtil.error((String) "HTTP_DNS_DBManager", (String) "onUpgrade exception");
            }
        }
    }
}
