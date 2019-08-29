package com.j256.ormlite.android.apptools;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.sqlcrypto.DatabaseErrorHandler;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase.CursorFactory;
import com.alibaba.sqlcrypto.sqlite.SQLiteOpenHelper;
import com.alipay.android.phone.mobilesdk.storage.database.TaobaoOrmLiteEncryptionAgent;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.field.encrypt.OrmLiteEncryptionProcessor;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.DatabaseTableConfigLoader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

public abstract class OrmLiteSqliteOpenHelper extends SQLiteOpenHelper {
    private static final String LIB = "lib";
    private static final String LIB_NAME = "libdatabase_sqlcrypto.so";
    private static final String PLUGINS_LIB = "plugins_lib";
    protected static Logger logger = LoggerFactory.getLogger(OrmLiteSqliteOpenHelper.class);
    private static boolean sHasErrorReported = false;
    protected boolean cancelQueriesEnabled;
    protected AndroidConnectionSource connectionSource;
    private volatile boolean isOpen;

    public abstract void onCreate(SQLiteDatabase sQLiteDatabase, ConnectionSource connectionSource2);

    public abstract void onUpgrade(SQLiteDatabase sQLiteDatabase, ConnectionSource connectionSource2, int i, int i2);

    public OrmLiteSqliteOpenHelper(Context context, String databaseName, CursorFactory factory, int databaseVersion, DatabaseErrorHandler errorHandler) {
        super(context, databaseName, factory, databaseVersion, errorHandler);
        this.connectionSource = new AndroidConnectionSource((SQLiteOpenHelper) this);
        this.isOpen = true;
        afterConstructor(context);
    }

    public OrmLiteSqliteOpenHelper(Context context, String databaseName, CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
        this.connectionSource = new AndroidConnectionSource((SQLiteOpenHelper) this);
        this.isOpen = true;
        afterConstructor(context);
    }

    public OrmLiteSqliteOpenHelper(Context context, String databaseName, CursorFactory factory, int databaseVersion, int configFileId) {
        this(context, databaseName, factory, databaseVersion, openFileId(context, configFileId));
    }

    public OrmLiteSqliteOpenHelper(Context context, String databaseName, CursorFactory factory, int databaseVersion, File configFile) {
        this(context, databaseName, factory, databaseVersion, openFile(configFile));
    }

    public OrmLiteSqliteOpenHelper(Context context, String databaseName, CursorFactory factory, int databaseVersion, InputStream stream) {
        super(context, databaseName, factory, databaseVersion);
        this.connectionSource = new AndroidConnectionSource((SQLiteOpenHelper) this);
        this.isOpen = true;
        OrmLiteEncryptionProcessor.setOrmLiteEncryptionAgent(TaobaoOrmLiteEncryptionAgent.getInstance(context));
        if (stream != null) {
            try {
                DaoManager.addCachedDatabaseConfigs(DatabaseTableConfigLoader.loadDatabaseConfigFromReader(new BufferedReader(new InputStreamReader(stream), 4096)));
                try {
                    stream.close();
                } catch (IOException e) {
                }
            } catch (SQLException e2) {
                throw new IllegalStateException("Could not load object config file", e2);
            } catch (Throwable th) {
                try {
                    stream.close();
                } catch (IOException e3) {
                }
                throw th;
            }
        }
    }

    public ConnectionSource getConnectionSource() {
        if (!this.isOpen) {
            logger.warn((Throwable) new IllegalStateException(), (String) "Getting connectionSource was called after closed");
        }
        return this.connectionSource;
    }

    public final void onCreate(SQLiteDatabase db) {
        ConnectionSource cs = getConnectionSource();
        DatabaseConnection conn = cs.getSpecialConnection();
        boolean clearSpecial = false;
        if (conn == null) {
            conn = new AndroidDatabaseConnection(db, true, this.cancelQueriesEnabled);
            try {
                cs.saveSpecialConnection(conn);
                clearSpecial = true;
            } catch (SQLException e) {
                throw new IllegalStateException("Could not save special connection", e);
            }
        }
        try {
            onCreate(db, cs);
        } finally {
            if (clearSpecial) {
                cs.clearSpecialConnection(conn);
            }
        }
    }

    public final void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ConnectionSource cs = getConnectionSource();
        DatabaseConnection conn = cs.getSpecialConnection();
        boolean clearSpecial = false;
        if (conn == null) {
            conn = new AndroidDatabaseConnection(db, true, this.cancelQueriesEnabled);
            try {
                cs.saveSpecialConnection(conn);
                clearSpecial = true;
            } catch (SQLException e) {
                throw new IllegalStateException("Could not save special connection", e);
            }
        }
        try {
            onUpgrade(db, cs, oldVersion, newVersion);
        } finally {
            if (clearSpecial) {
                cs.clearSpecialConnection(conn);
            }
        }
    }

    public void close() {
        super.close();
        this.connectionSource.close();
        this.isOpen = false;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) {
        return DaoManager.createDao(getConnectionSource(), clazz);
    }

    public <D extends RuntimeExceptionDao<T, ?>, T> D getRuntimeExceptionDao(Class<T> clazz) {
        try {
            return new RuntimeExceptionDao(getDao(clazz));
        } catch (SQLException e) {
            throw new RuntimeException("Could not create RuntimeExcepitionDao for class " + clazz, e);
        }
    }

    public String toString() {
        return getClass().getSimpleName() + AUScreenAdaptTool.PREFIX_ID + Integer.toHexString(super.hashCode());
    }

    private static InputStream openFileId(Context context, int fileId) {
        InputStream stream = context.getResources().openRawResource(fileId);
        if (stream != null) {
            return stream;
        }
        throw new IllegalStateException("Could not find object config file with id " + fileId);
    }

    private static InputStream openFile(File configFile) {
        if (configFile == null) {
            return null;
        }
        try {
            return new FileInputStream(configFile);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Could not open config file " + configFile, e);
        }
    }

    private void afterConstructor(Context context) {
        OrmLiteEncryptionProcessor.setOrmLiteEncryptionAgent(TaobaoOrmLiteEncryptionAgent.getInstance(context));
        logger.trace((String) "{}: constructed connectionSource {}", (Object) this, (Object) this.connectionSource);
        if (!SQLiteDatabase.isDatabaseEnabled() && !sHasErrorReported) {
            try {
                String soLibPath = context.getApplicationInfo().dataDir + File.separator + "lib" + File.separator + LIB_NAME;
                String soPluginPath = context.getDir(PLUGINS_LIB, 0).getAbsolutePath() + File.separator + LIB_NAME;
                File soFileLib = new File(soLibPath);
                File soFilePlugin = new File(soPluginPath);
                String loadPath = null;
                long length = 0;
                int loadTarget = 0;
                if (soFileLib.exists()) {
                    length = soFileLib.length();
                    loadTarget = 1;
                    loadPath = soLibPath;
                } else if (soFilePlugin.exists()) {
                    length = soFilePlugin.length();
                    loadTarget = 2;
                    loadPath = soPluginPath;
                }
                errorReport(loadTarget + MergeUtil.SEPARATOR_KV + length);
                if (length > 0 && !TextUtils.isEmpty(loadPath)) {
                    System.load(loadPath);
                }
            } catch (Throwable throwable) {
                errorReport(throwable.toString());
            }
            sHasErrorReported = true;
        }
    }

    private void errorReport(String errorMessage) {
        try {
            com.alipay.mobile.common.logging.api.LoggerFactory.getMonitorLogger().mtBizReport("MTBIZ_DB_SO", errorMessage, null, null);
        } catch (Throwable th) {
        }
    }
}
