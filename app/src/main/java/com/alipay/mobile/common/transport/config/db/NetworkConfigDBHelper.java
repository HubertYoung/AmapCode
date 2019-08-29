package com.alipay.mobile.common.transport.config.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.alipay.mobile.common.transport.utils.LogCatUtil;

public class NetworkConfigDBHelper extends SQLiteOpenHelper {
    public static final String TB_NAME = "nw_conf_mng_table";
    private static NetworkConfigDBHelper a;

    public static synchronized NetworkConfigDBHelper getInstance(Context ctx) {
        NetworkConfigDBHelper networkConfigDBHelper;
        synchronized (NetworkConfigDBHelper.class) {
            if (a != null) {
                networkConfigDBHelper = a;
            } else {
                synchronized (NetworkConfigDBHelper.class) {
                    if (a == null) {
                        networkConfigDBHelper = new NetworkConfigDBHelper(ctx, "nw_conf_mng.db");
                        a = networkConfigDBHelper;
                    } else {
                        networkConfigDBHelper = a;
                    }
                }
            }
        }
        return networkConfigDBHelper;
    }

    private NetworkConfigDBHelper(Context context, String dbName) {
        super(context, dbName, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        LogCatUtil.debug("NetworkConfigDBHelper", "onCreate.");
        a(db);
    }

    private static void a(SQLiteDatabase db) {
        db.execSQL(SqlConstants.DROP_TABLE);
        db.execSQL(SqlConstants.CREATE_TABLE_SQL);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            a(db);
            LogCatUtil.debug("NetworkConfigDBHelper", "onUpgrade from " + oldVersion + " to " + newVersion);
        }
    }
}
