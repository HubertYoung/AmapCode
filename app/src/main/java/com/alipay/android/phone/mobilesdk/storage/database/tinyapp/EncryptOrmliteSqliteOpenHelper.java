package com.alipay.android.phone.mobilesdk.storage.database.tinyapp;

import android.content.Context;
import android.content.ContextWrapper;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase.CursorFactory;
import com.alipay.android.phone.mobilesdk.storage.encryption.TaobaoSecurityEncryptor;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.autonavi.common.SuperId;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;

public class EncryptOrmliteSqliteOpenHelper extends OrmLiteSqliteOpenHelper {
    public static final int MAX_DB_SIZE = 10485760;
    private static final String TAG = "TinyAppStoragePlugin";
    private static final int VERSION = 1;
    private Context mContext;
    private String mCurrentSizeKey;
    private Dao<TinyAppCacheModel, Integer> mDao;

    private EncryptOrmliteSqliteOpenHelper(Context context, String databaseName, CursorFactory factory, int version) {
        super(context, databaseName, factory, version);
    }

    private EncryptOrmliteSqliteOpenHelper(Context context, String databaseName) {
        this(context, databaseName + ".db", null, 1);
        try {
            this.mContext = context.getApplicationContext();
            String password = TaobaoSecurityEncryptor.encrypt(new ContextWrapper(this.mContext), databaseName);
            this.mCurrentSizeKey = password;
            setPassword(password);
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error("TinyAppStoragePlugin", "get encrypt password throws exception", e);
        }
        setWriteAheadLoggingEnabled(true);
    }

    public static EncryptOrmliteSqliteOpenHelper getInstance(Context context, String databaseName) {
        LoggerFactory.getTraceLogger().info("TinyAppStoragePlugin", "EncryptOrmliteSqliteOpenHelper databaseName = " + databaseName);
        return new EncryptOrmliteSqliteOpenHelper(context, databaseName);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, TinyAppCacheModel.class);
        } catch (SQLException e) {
            LoggerFactory.getTraceLogger().error("TinyAppStoragePlugin", "onCreate exception", e);
        }
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
    }

    public Dao<TinyAppCacheModel, Integer> getDao() {
        if (this.mDao == null) {
            this.mDao = getDao(TinyAppCacheModel.class);
        }
        return this.mDao;
    }

    private TinyAppCacheModel getSizeModel() {
        try {
            Dao dao = getDao();
            List query = dao.query(dao.queryBuilder().where().eq("key", this.mCurrentSizeKey).prepare());
            if (query.size() > 1) {
                throw new RuntimeException("current size key duplicated!");
            } else if (query.size() != 0) {
                return (TinyAppCacheModel) query.get(0);
            } else {
                TinyAppCacheModel sizeModel = new TinyAppCacheModel(this.mCurrentSizeKey, "0");
                dao.create(sizeModel);
                return sizeModel;
            }
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error("TinyAppStoragePlugin", "getSizeModel exception ", e);
            return null;
        }
    }

    public int getCurrentSize() {
        TinyAppCacheModel sizeModel = getSizeModel();
        if (sizeModel != null) {
            return Integer.valueOf(sizeModel.getValue()).intValue();
        }
        try {
            getDao().create(new TinyAppCacheModel(this.mCurrentSizeKey, "0"));
            return 0;
        } catch (SQLException e) {
            LoggerFactory.getTraceLogger().error("TinyAppStoragePlugin", "getCurrentSize exception ", e);
            return -1;
        }
    }

    public void updateCurrentSize(int size) {
        LoggerFactory.getTraceLogger().debug("TinyAppStoragePlugin", "update current size, delta size = " + size);
        TinyAppCacheModel sizeModel = getSizeModel();
        LoggerFactory.getTraceLogger().debug("TinyAppStoragePlugin", "old size = " + getCurrentSize() + "b, new size = " + size + SuperId.BIT_1_RQBXY);
        int newSize = getCurrentSize() + size;
        if (sizeModel != null) {
            try {
                Dao dao = getDao();
                dao.delete(sizeModel);
                dao.create(new TinyAppCacheModel(this.mCurrentSizeKey, String.valueOf(newSize)));
                LoggerFactory.getTraceLogger().info("TinyAppStoragePlugin", "update current size = " + newSize + SuperId.BIT_1_RQBXY);
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().error("TinyAppStoragePlugin", "update size fail ", e);
            }
        }
    }

    public String getCurrentSizeKey() {
        return this.mCurrentSizeKey;
    }

    public void close() {
        super.close();
        this.mDao = null;
    }
}
