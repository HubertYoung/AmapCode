package com.alipay.mobile.common.transport.iprank.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.alipay.mobile.common.transport.utils.LogCatUtil;

public class IpRankDBHelper extends SQLiteOpenHelper {
    private static volatile IpRankDBHelper a;

    public static synchronized IpRankDBHelper getInstance(Context ctx) {
        IpRankDBHelper ipRankDBHelper;
        synchronized (IpRankDBHelper.class) {
            try {
                if (a == null) {
                    a = new IpRankDBHelper(ctx, "iprank.db");
                }
                ipRankDBHelper = a;
            }
        }
        return ipRankDBHelper;
    }

    private IpRankDBHelper(Context context, String dbName) {
        super(context, dbName, null, 4);
    }

    public void onCreate(SQLiteDatabase db) {
        LogCatUtil.debug("IPR_IPRDBHelper", "onCreate.");
        a(db);
        b(db);
    }

    private static void a(SQLiteDatabase db) {
        db.execSQL("drop table if exists lbs");
        db.execSQL(IpRankSql.CREATE_LBS_TABLE);
    }

    private static void b(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS ip_rank");
        db.execSQL(IpRankSql.CREATE_IP_RANK);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogCatUtil.debug("IPR_IPRDBHelper", "onUpgrade from " + oldVersion + " to " + newVersion);
    }
}
