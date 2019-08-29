package com.alibaba.sqlcrypto.sqlite;

import android.database.Cursor;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase.CursorFactory;

public final class SQLiteDirectCursorDriver implements SQLiteCursorDriver {
    private final SQLiteDatabase mDatabase;
    private final String mEditTable;
    private SQLiteQuery mQuery;
    private final String mSql;

    public SQLiteDirectCursorDriver(SQLiteDatabase db, String sql, String editTable, Object cancellationSignal) {
        this.mDatabase = db;
        this.mEditTable = editTable;
        this.mSql = sql;
    }

    public final Cursor query(CursorFactory factory, String[] selectionArgs) {
        Cursor cursor;
        SQLiteQuery query = new SQLiteQuery(this.mDatabase, this.mSql, null);
        try {
            query.bindAllArgsAsStrings(selectionArgs);
            if (factory == null) {
                cursor = new SQLiteCursor(this, this.mEditTable, query);
            } else {
                cursor = factory.newCursor(this.mDatabase, this, this.mEditTable, query);
            }
            this.mQuery = query;
            return cursor;
        } catch (RuntimeException ex) {
            query.close();
            throw ex;
        }
    }

    public final void cursorClosed() {
    }

    public final void setBindArguments(String[] bindArgs) {
        this.mQuery.bindAllArgsAsStrings(bindArgs);
    }

    public final void cursorDeactivated() {
    }

    public final void cursorRequeried(Cursor cursor) {
    }

    public final String toString() {
        return "SQLiteDirectCursorDriver: " + this.mSql;
    }
}
