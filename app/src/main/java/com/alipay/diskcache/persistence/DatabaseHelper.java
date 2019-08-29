package com.alipay.diskcache.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.alipay.diskcache.model.FileCacheModel;
import com.alipay.diskcache.utils.LogHelper;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_FILECACHE = "create table if not exists tbl_file_cache (path VARCHAR , alias_key VARCHAR ,multi_alias_key VARCHAR ,business_id VARCHAR ,key VARCHAR ,extra VARCHAR ,type INTEGER ,file_size BIGINT ,modify_time BIGINT ,access_time BIGINT ,expiredTime BIGINT ,tag INTEGER ,id INTEGER PRIMARY KEY AUTOINCREMENT)";
    private static final String DATABASE_NAME = ".info_v1";
    private static final int DATABASE_VERSION = 3;
    private static final String TAG = "DatabaseHelper";
    private SQLiteDatabase mDb = null;
    private AtomicInteger mOpenCounter = new AtomicInteger(0);

    public DatabaseHelper(Context context, String dir) {
        super(new DBContext(context, dir), DATABASE_NAME, null, 3);
    }

    public void onCreate(SQLiteDatabase db) {
        LogHelper.d(TAG, "DBHelper onCreate");
        createCacheTable(db);
        createCacheIndex(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogHelper.d(TAG, "onDowngrade onDowngrade database from version " + oldVersion + " to " + newVersion);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int currentVersion) {
        LogHelper.d(TAG, "onUpgrade Upgrading database from version " + oldVersion + " to " + currentVersion);
        if (oldVersion < 2 && currentVersion >= 2) {
            LogHelper.d(TAG, "onUpgrade update v1->v2");
            addColumn(db, FileCacheModel.TABLE_FILE_CACHE, FileCacheModel.F_MULTI_ALIAS_KEY, "VARCHAR");
            addIndex(db, FileCacheModel.TABLE_FILE_CACHE, FileCacheModel.F_MULTI_ALIAS_KEY);
        }
        if (oldVersion < 3 && currentVersion >= 3) {
            LogHelper.d(TAG, "onUpgrade update v2->v3");
            addColumn(db, FileCacheModel.TABLE_FILE_CACHE, FileCacheModel.F_CACHE_EXPIRED_TIME, "BIGINT");
        }
    }

    /* access modifiers changed from: protected */
    public void addColumn(SQLiteDatabase db, String tableName, String columnName, String dataType) {
        execSQL(db, String.format("ALTER TABLE %s ADD %s %s", new Object[]{tableName, columnName, dataType}));
    }

    /* access modifiers changed from: protected */
    public void addIndex(SQLiteDatabase db, String tableName, String columnName) {
        execSQL(db, String.format("CREATE INDEX %s_%s_idx ON %s (%s);", new Object[]{tableName, columnName, tableName, columnName}));
    }

    private void execSQL(SQLiteDatabase db, String sql) {
        if (db != null && !TextUtils.isEmpty(sql)) {
            try {
                db.execSQL(sql);
            } catch (Throwable t) {
                LogHelper.e(TAG, "execSQL error, sql: " + sql + ", db: " + db, t);
            }
        }
    }

    public synchronized SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase ret;
        ret = null;
        try {
            ret = super.getWritableDatabase();
        } catch (Exception e) {
            LogHelper.e(TAG, "getWritableDatabase", e);
        }
        return ret;
    }

    public synchronized SQLiteDatabase getReadableDatabase() {
        SQLiteDatabase ret;
        ret = null;
        try {
            ret = super.getReadableDatabase();
        } catch (Exception e) {
            LogHelper.e(TAG, "getReadableDatabase", e);
        }
        return ret;
    }

    private void createCacheIndex(SQLiteDatabase db) {
        if (db != null) {
            db.beginTransaction();
            try {
                db.execSQL("CREATE INDEX tbl_file_cache_key_idx ON tbl_file_cache ( key );");
                db.execSQL("CREATE INDEX tbl_file_cache_aliaskey_idx ON tbl_file_cache ( alias_key );");
                db.execSQL("CREATE INDEX tbl_file_cache_multi_aliaskey_idx ON tbl_file_cache ( multi_alias_key );");
                db.execSQL("CREATE INDEX tbl_file_cache_path_idx ON tbl_file_cache ( path );");
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }

    private void createCacheTable(SQLiteDatabase db) {
        if (db != null) {
            db.beginTransaction();
            try {
                db.execSQL(CREATE_TABLE_FILECACHE);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }

    public synchronized void closeDatabase() {
        if (this.mOpenCounter.decrementAndGet() == 0 && this.mDb != null) {
            this.mDb.close();
        }
    }

    public synchronized SQLiteDatabase openDatabase() {
        try {
            if (this.mOpenCounter.incrementAndGet() == 1) {
                this.mDb = getWritableDatabase();
                if (this.mDb == null) {
                    LogHelper.e(TAG, this + "\t getWritableDatabase return null");
                }
                LogHelper.d(TAG, this + "\t mDb:" + this.mDb);
            }
        }
        return this.mDb;
    }
}
