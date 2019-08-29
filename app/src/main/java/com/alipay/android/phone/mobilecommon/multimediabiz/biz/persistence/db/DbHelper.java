package com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.db;

import android.content.Context;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase.CursorFactory;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import java.io.File;
import java.io.InputStream;

public class DbHelper extends OrmLiteSqliteOpenHelper {
    private OnDbCreateUpgradeHandler mHandler;

    public DbHelper(Context context, String databaseName, int databaseVersion) {
        this(context, databaseName, databaseVersion, (OnDbCreateUpgradeHandler) null);
    }

    public DbHelper(Context context, String databaseName, int databaseVersion, OnDbCreateUpgradeHandler handler) {
        this(context, databaseName, (CursorFactory) null, databaseVersion);
        this.mHandler = handler;
    }

    private DbHelper(Context context, String databaseName, CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    private DbHelper(Context context, String databaseName, CursorFactory factory, int databaseVersion, int configFileId) {
        super(context, databaseName, factory, databaseVersion, configFileId);
    }

    private DbHelper(Context context, String databaseName, CursorFactory factory, int databaseVersion, File configFile) {
        super(context, databaseName, factory, databaseVersion, configFile);
    }

    private DbHelper(Context context, String databaseName, CursorFactory factory, int databaseVersion, InputStream stream) {
        super(context, databaseName, factory, databaseVersion, stream);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        if (this.mHandler != null) {
            this.mHandler.onCreate(sqLiteDatabase, connectionSource);
        }
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        if (this.mHandler != null) {
            this.mHandler.onUpgrade(sqLiteDatabase, connectionSource, oldVer, newVer);
        }
    }

    public void onConfigure(SQLiteDatabase sqLiteDatabase) {
    }
}
