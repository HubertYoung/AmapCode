package com.amap.location.uptunnel.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.amap.location.common.b;
import com.autonavi.minimap.bundle.apm.internal.report.ReportManager;

/* compiled from: DBOpenHelper */
public class a extends SQLiteOpenHelper {
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public a(Context context) {
        // StringBuilder sb = new StringBuilder();
        // sb.append(b.e());
        // sb.append("_uptunnel.db");
        super(context, sb.toString(), null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("create table if not exists count (ID integer PRIMARY KEY AUTOINCREMENT NOT NULL, type integer, value integer, time long)");
        sQLiteDatabase.execSQL(com.amap.location.uptunnel.core.db.a.a.a("event"));
        sQLiteDatabase.execSQL(com.amap.location.uptunnel.core.db.a.a.a("key_log"));
        sQLiteDatabase.execSQL(com.amap.location.uptunnel.core.db.a.a.a(ReportManager.LOG_PATH));
        sQLiteDatabase.execSQL(com.amap.location.uptunnel.core.db.a.a.a("data_block"));
    }
}
