package com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.db;

import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase;
import com.j256.ormlite.support.ConnectionSource;

public interface OnDbCreateUpgradeHandler {
    void onCreate(SQLiteDatabase sQLiteDatabase, ConnectionSource connectionSource);

    void onUpgrade(SQLiteDatabase sQLiteDatabase, ConnectionSource connectionSource, int i, int i2);
}
