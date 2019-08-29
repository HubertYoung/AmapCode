package com.alibaba.sqlcrypto.sqlite;

import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import com.alibaba.sqlcrypto.CursorWindow;

public final class SQLiteQuery extends SQLiteProgram {
    private static final String TAG = "SQLiteQuery";

    SQLiteQuery(SQLiteDatabase db, String query, Object cancellationSignal) {
        super(db, query, null, cancellationSignal);
    }

    /* access modifiers changed from: 0000 */
    public final int fillWindow(CursorWindow window, int startPos, int requiredPos, boolean countAllRows) {
        acquireReference();
        try {
            window.acquireReference();
            try {
                int numRows = getSession().executeForCursorWindow(getSql(), getBindArgs(), window, startPos, requiredPos, countAllRows, getConnectionFlags(), null);
                window.releaseReference();
                return numRows;
            } catch (SQLiteDatabaseCorruptException ex) {
                onCorruption();
                throw ex;
            } catch (SQLiteException ex2) {
                Log.e(TAG, "exception: " + ex2.getMessage() + "; query: " + getSql());
                throw ex2;
            } catch (Throwable th) {
                window.releaseReference();
                throw th;
            }
        } finally {
            releaseReference();
        }
    }

    public final String toString() {
        return "SQLiteQuery: " + getSql();
    }
}
