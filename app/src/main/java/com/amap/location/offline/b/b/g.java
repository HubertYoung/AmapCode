package com.amap.location.offline.b.b;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* compiled from: StatisticsDbOpenHelper */
public class g extends SQLiteOpenHelper {
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    g(Context context) {
        super(context, "OffStatistics.db", null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        e.a(sQLiteDatabase);
        f.a(sQLiteDatabase);
    }
}
