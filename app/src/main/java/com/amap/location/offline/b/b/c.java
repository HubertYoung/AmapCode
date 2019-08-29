package com.amap.location.offline.b.b;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* compiled from: LocationDbOpenHelper */
class c extends SQLiteOpenHelper {
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    c(Context context) {
        super(context, "OffLocation.db", null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        a.a(sQLiteDatabase);
        b.a(sQLiteDatabase);
    }
}
