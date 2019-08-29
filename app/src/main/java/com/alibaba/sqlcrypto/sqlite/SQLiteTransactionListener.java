package com.alibaba.sqlcrypto.sqlite;

public interface SQLiteTransactionListener {
    void onBegin();

    void onCommit();

    void onRollback();
}
