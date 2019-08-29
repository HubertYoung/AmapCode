package com.alibaba.sqlcrypto.sqlite;

import android.database.Cursor;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase.CursorFactory;

public interface SQLiteCursorDriver {
    void cursorClosed();

    void cursorDeactivated();

    void cursorRequeried(Cursor cursor);

    Cursor query(CursorFactory cursorFactory, String[] strArr);

    void setBindArguments(String[] strArr);
}
