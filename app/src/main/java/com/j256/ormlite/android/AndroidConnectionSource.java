package com.j256.ormlite.android;

import android.database.SQLException;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase;
import com.alibaba.sqlcrypto.sqlite.SQLiteOpenHelper;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.db.SqliteAndroidDatabaseType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.BaseConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseConnectionProxyFactory;

public class AndroidConnectionSource extends BaseConnectionSource implements ConnectionSource {
    private static DatabaseConnectionProxyFactory connectionProxyFactory;
    private static final Logger logger = LoggerFactory.getLogger(AndroidConnectionSource.class);
    private boolean cancelQueriesEnabled;
    private DatabaseConnection connection;
    private final DatabaseType databaseType;
    private final SQLiteOpenHelper helper;
    private volatile boolean isOpen;
    private final SQLiteDatabase sqliteDatabase;

    public AndroidConnectionSource(SQLiteOpenHelper helper2) {
        this.connection = null;
        this.isOpen = true;
        this.databaseType = new SqliteAndroidDatabaseType();
        this.cancelQueriesEnabled = false;
        this.helper = helper2;
        this.sqliteDatabase = null;
    }

    public AndroidConnectionSource(SQLiteDatabase sqliteDatabase2) {
        this.connection = null;
        this.isOpen = true;
        this.databaseType = new SqliteAndroidDatabaseType();
        this.cancelQueriesEnabled = false;
        this.helper = null;
        this.sqliteDatabase = sqliteDatabase2;
    }

    public DatabaseConnection getReadOnlyConnection() {
        return getReadWriteConnection();
    }

    public DatabaseConnection getReadWriteConnection() {
        SQLiteDatabase db;
        DatabaseConnection conn = getSavedConnection();
        if (conn != null) {
            return conn;
        }
        if (this.connection == null) {
            if (this.sqliteDatabase == null) {
                try {
                    db = this.helper.getWritableDatabase();
                } catch (SQLException e) {
                    throw SqlExceptionUtil.create("Getting a writable database from helper " + this.helper + " failed", e);
                }
            } else {
                db = this.sqliteDatabase;
            }
            this.connection = new AndroidDatabaseConnection(db, true, this.cancelQueriesEnabled);
            if (connectionProxyFactory != null) {
                this.connection = connectionProxyFactory.createProxy(this.connection);
            }
            logger.trace((String) "created connection {} for db {}, helper {}", (Object) this.connection, (Object) db, (Object) this.helper);
        } else {
            logger.trace((String) "{}: returning read-write connection {}, helper {}", (Object) this, (Object) this.connection, (Object) this.helper);
        }
        return this.connection;
    }

    public void releaseConnection(DatabaseConnection connection2) {
    }

    public boolean saveSpecialConnection(DatabaseConnection connection2) {
        return saveSpecial(connection2);
    }

    public void clearSpecialConnection(DatabaseConnection connection2) {
        clearSpecial(connection2, logger);
    }

    public void close() {
        this.isOpen = false;
    }

    public void closeQuietly() {
        close();
    }

    public DatabaseType getDatabaseType() {
        return this.databaseType;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public static void setDatabaseConnectionProxyFactory(DatabaseConnectionProxyFactory connectionProxyFactory2) {
        connectionProxyFactory = connectionProxyFactory2;
    }

    public boolean isCancelQueriesEnabled() {
        return this.cancelQueriesEnabled;
    }

    public void setCancelQueriesEnabled(boolean cancelQueriesEnabled2) {
        this.cancelQueriesEnabled = cancelQueriesEnabled2;
    }

    public String toString() {
        return getClass().getSimpleName() + AUScreenAdaptTool.PREFIX_ID + Integer.toHexString(super.hashCode());
    }
}
