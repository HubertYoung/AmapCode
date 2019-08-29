package com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.db;

import android.content.Context;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APStorageCacheInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class BaseDb implements DbHelperCreator {
    protected static final String TAG = "MultimediaDb";
    private DbHelper mDbHelper;

    public DbHelper getDbHelper(Context context) {
        if (this.mDbHelper == null) {
            this.mDbHelper = new DbHelper(context, getDbName(), getDbVersion(), getOnDbCreateUpgradeHandler());
        }
        return this.mDbHelper;
    }

    public OnDbCreateUpgradeHandler getOnDbCreateUpgradeHandler() {
        return null;
    }

    public String getDbName() {
        return null;
    }

    public int getDbVersion() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public <T> int createTableIfNotExists(ConnectionSource connectionSource, Class<T> dataClass) {
        boolean z = false;
        try {
            return TableUtils.createTableIfNotExists(connectionSource, dataClass);
        } catch (Exception e) {
            Logger.E((String) TAG, (Throwable) e, (String) "createTableIfNotExists error", new Object[z]);
            return z;
        }
    }

    /* access modifiers changed from: protected */
    public void execSQL(SQLiteDatabase db, String sql) {
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            Logger.E((String) TAG, (Throwable) e, "execSQL error sql: " + sql, new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    public void addColumn(SQLiteDatabase db, String tableName, String columnName, String dataType) {
        execSQL(db, String.format("ALTER TABLE %s ADD %s %s", new Object[]{tableName, columnName, dataType}));
    }

    /* access modifiers changed from: protected */
    public void dropColumn(SQLiteDatabase db, String tableName, String columnName) {
        execSQL(db, String.format("ALTER TABLE %s DROP COLUMN %s", new Object[]{tableName, columnName}));
    }

    /* access modifiers changed from: protected */
    public void addCacheTableInfo(SQLiteDatabase db, String tableName) {
        addColumn(db, tableName, APStorageCacheInfo.F_CACHE_ID, "TEXT");
        addColumn(db, tableName, APStorageCacheInfo.F_CACHE_PATH, "TEXT");
        addColumn(db, tableName, APStorageCacheInfo.F_CACHE_FILE_SIZE, "BIGINT");
        addColumn(db, tableName, APStorageCacheInfo.F_CACHE_CREATE_TIME, "BIGINT");
        addColumn(db, tableName, APStorageCacheInfo.F_CACHE_LAST_MODIFIED_TIME, "BIGINT");
        addColumn(db, tableName, APStorageCacheInfo.F_CACHE_LOCK, "INT");
        addColumn(db, tableName, APStorageCacheInfo.F_CACHE_BUSINESS_ID, "TEXT");
        addColumn(db, tableName, APStorageCacheInfo.F_CACHE_TYPE, "TEXT");
        addColumn(db, tableName, APStorageCacheInfo.F_CACHE_EXTRA, "TEXT");
        addIndex(db, tableName, APStorageCacheInfo.F_CACHE_ID);
        addIndex(db, tableName, APStorageCacheInfo.F_CACHE_CREATE_TIME);
        addIndex(db, tableName, APStorageCacheInfo.F_CACHE_LAST_MODIFIED_TIME);
        addIndex(db, tableName, APStorageCacheInfo.F_CACHE_BUSINESS_ID);
    }

    /* access modifiers changed from: protected */
    public void addIndex(SQLiteDatabase db, String tableName, String columnName) {
        execSQL(db, String.format("CREATE INDEX %s_%s_idx ON %s (%s);", new Object[]{tableName, columnName, tableName, columnName}));
    }
}
