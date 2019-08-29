package com.j256.ormlite.android.compat;

import android.database.Cursor;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.compat.ApiCompatibility.CancellationHook;

public class BasicApiCompatibility implements ApiCompatibility {
    public Cursor rawQuery(SQLiteDatabase db, String sql, String[] selectionArgs, CancellationHook cancellationHook) {
        return db.rawQuery(sql, selectionArgs);
    }

    public CancellationHook createCancellationHook() {
        return null;
    }
}
