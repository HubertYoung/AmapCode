package com.alibaba.sqlcrypto;

import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase;

public interface DatabaseErrorHandler {
    void onCorruption(SQLiteDatabase sQLiteDatabase);
}
