package com.alibaba.sqlcrypto.sqlite;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import android.os.Looper;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.util.Pair;
import android.util.Printer;
import com.alibaba.sqlcrypto.DatabaseErrorHandler;
import com.alibaba.sqlcrypto.DatabaseUtils;
import com.alibaba.sqlcrypto.DefaultDatabaseErrorHandler;
import com.alibaba.sqlcrypto.sqlite.SQLiteDebug.DbStats;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Value;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

public final class SQLiteDatabase extends SQLiteClosable {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final int CONFLICT_ABORT = 2;
    public static final int CONFLICT_FAIL = 3;
    public static final int CONFLICT_IGNORE = 4;
    public static final int CONFLICT_NONE = 0;
    public static final int CONFLICT_REPLACE = 5;
    public static final int CONFLICT_ROLLBACK = 1;
    private static final String[] CONFLICT_VALUES = {"", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE "};
    public static final int CREATE_IF_NECESSARY = 268435456;
    public static final int ENABLE_WRITE_AHEAD_LOGGING = 536870912;
    private static final int EVENT_DB_CORRUPT = 75004;
    public static final int MAX_SQL_CACHE_SIZE = 100;
    public static final int NO_LOCALIZED_COLLATORS = 16;
    public static final int OPEN_READONLY = 1;
    public static final int OPEN_READWRITE = 0;
    private static final int OPEN_READ_MASK = 1;
    public static final int SQLITE_MAX_LIKE_PATTERN_LENGTH = 50000;
    private static final String TAG = "SQLiteDatabase";
    private static boolean mCryptoEnabled;
    private static WeakHashMap<SQLiteDatabase, Object> sActiveDatabases = new WeakHashMap<>();
    private final SQLiteDatabaseConfiguration mConfigurationLocked;
    private SQLiteConnectionPool mConnectionPoolLocked;
    private final CursorFactory mCursorFactory;
    private final DatabaseErrorHandler mErrorHandler;
    private boolean mHasAttachedDbsLocked;
    private final Object mLock = new Object();
    private final ThreadLocal<SQLiteSession> mThreadSession = new ThreadLocal<SQLiteSession>() {
        /* access modifiers changed from: protected */
        public SQLiteSession initialValue() {
            return SQLiteDatabase.this.createSession();
        }
    };

    public interface CursorFactory {
        Cursor newCursor(SQLiteDatabase sQLiteDatabase, SQLiteCursorDriver sQLiteCursorDriver, String str, SQLiteQuery sQLiteQuery);
    }

    public interface CustomFunction {
        void callback(String[] strArr);
    }

    static {
        boolean z;
        if (!SQLiteDatabase.class.desiredAssertionStatus()) {
            z = true;
        } else {
            z = false;
        }
        $assertionsDisabled = z;
        mCryptoEnabled = false;
        try {
            System.loadLibrary("database_sqlcrypto");
        } catch (Throwable error) {
            Log.e(TAG, "retry LibraryLoadUtils.loadLibrary() error", error);
        }
        try {
            if (SQLiteConnection.nativeCheckLoad()) {
                mCryptoEnabled = true;
                Log.v(TAG, "load database_sqlcrypto success.");
            }
        } catch (Throwable e) {
            Log.e(TAG, "SQLiteConnection.nativeCheckLoad() error", e);
        }
    }

    private SQLiteDatabase(String path, int openFlags, CursorFactory cursorFactory, DatabaseErrorHandler errorHandler) {
        this.mCursorFactory = cursorFactory;
        this.mErrorHandler = errorHandler == null ? new DefaultDatabaseErrorHandler() : errorHandler;
        this.mConfigurationLocked = new SQLiteDatabaseConfiguration(path, openFlags);
    }

    private SQLiteDatabase(String path, int openFlags, CursorFactory cursorFactory, DatabaseErrorHandler errorHandler, String key) {
        this.mCursorFactory = cursorFactory;
        this.mErrorHandler = errorHandler == null ? new DefaultDatabaseErrorHandler() : errorHandler;
        this.mConfigurationLocked = new SQLiteDatabaseConfiguration(path, openFlags, key);
    }

    /* access modifiers changed from: protected */
    public final void finalize() {
        try {
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    /* access modifiers changed from: protected */
    public final void onAllReferencesReleased() {
        dispose(false);
    }

    private void dispose(boolean finalized) {
        SQLiteConnectionPool pool;
        synchronized (this.mLock) {
            pool = this.mConnectionPoolLocked;
            this.mConnectionPoolLocked = null;
        }
        if (!finalized) {
            synchronized (sActiveDatabases) {
                sActiveDatabases.remove(this);
            }
            if (pool != null) {
                pool.close();
            }
        }
    }

    public static int releaseMemory() {
        return SQLiteGlobal.releaseMemory();
    }

    @Deprecated
    public final void setLockingEnabled(boolean lockingEnabled) {
    }

    /* access modifiers changed from: 0000 */
    public final String getLabel() {
        String str;
        synchronized (this.mLock) {
            str = this.mConfigurationLocked.label;
        }
        return str;
    }

    /* access modifiers changed from: 0000 */
    public final void onCorruption() {
        EventLog.writeEvent(EVENT_DB_CORRUPT, getLabel());
        this.mErrorHandler.onCorruption(this);
    }

    /* access modifiers changed from: 0000 */
    public final SQLiteSession getThreadSession() {
        return this.mThreadSession.get();
    }

    /* access modifiers changed from: 0000 */
    public final SQLiteSession createSession() {
        SQLiteConnectionPool pool;
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            pool = this.mConnectionPoolLocked;
        }
        return new SQLiteSession(pool);
    }

    /* access modifiers changed from: 0000 */
    public final int getThreadDefaultConnectionFlags(boolean readOnly) {
        int flags = readOnly ? 1 : 2;
        if (isMainThread()) {
            return flags | 4;
        }
        return flags;
    }

    private static boolean isMainThread() {
        Looper looper = Looper.myLooper();
        return looper != null && looper == Looper.getMainLooper();
    }

    public final void beginTransaction() {
        beginTransaction(null, true);
    }

    public final void beginTransactionNonExclusive() {
        beginTransaction(null, false);
    }

    public final void beginTransactionWithListener(SQLiteTransactionListener transactionListener) {
        beginTransaction(transactionListener, true);
    }

    public final void beginTransactionWithListenerNonExclusive(SQLiteTransactionListener transactionListener) {
        beginTransaction(transactionListener, false);
    }

    private void beginTransaction(SQLiteTransactionListener transactionListener, boolean exclusive) {
        acquireReference();
        try {
            getThreadSession().beginTransaction(exclusive ? 2 : 1, transactionListener, getThreadDefaultConnectionFlags(false), null);
        } finally {
            releaseReference();
        }
    }

    public final void endTransaction() {
        acquireReference();
        try {
            getThreadSession().endTransaction(null);
        } finally {
            releaseReference();
        }
    }

    public final void setTransactionSuccessful() {
        acquireReference();
        try {
            getThreadSession().setTransactionSuccessful();
        } finally {
            releaseReference();
        }
    }

    public final boolean inTransaction() {
        acquireReference();
        try {
            return getThreadSession().hasTransaction();
        } finally {
            releaseReference();
        }
    }

    public final boolean isDbLockedByCurrentThread() {
        acquireReference();
        try {
            return getThreadSession().hasConnection();
        } finally {
            releaseReference();
        }
    }

    @Deprecated
    public final boolean isDbLockedByOtherThreads() {
        return false;
    }

    @Deprecated
    public final boolean yieldIfContended() {
        return yieldIfContendedHelper(false, -1);
    }

    public final boolean yieldIfContendedSafely() {
        return yieldIfContendedHelper(true, -1);
    }

    public final boolean yieldIfContendedSafely(long sleepAfterYieldDelay) {
        return yieldIfContendedHelper(true, sleepAfterYieldDelay);
    }

    private boolean yieldIfContendedHelper(boolean throwIfUnsafe, long sleepAfterYieldDelay) {
        acquireReference();
        try {
            return getThreadSession().yieldTransaction(sleepAfterYieldDelay, throwIfUnsafe, null);
        } finally {
            releaseReference();
        }
    }

    @Deprecated
    public final Map<String, String> getSyncedTables() {
        return new HashMap(0);
    }

    public static SQLiteDatabase openDatabase(String path, CursorFactory factory, int flags) {
        return openDatabase(path, factory, flags, null);
    }

    public static SQLiteDatabase openDatabase(String path, CursorFactory factory, int flags, DatabaseErrorHandler errorHandler) {
        SQLiteDatabase db = new SQLiteDatabase(path, flags, factory, errorHandler);
        db.open();
        return db;
    }

    public static SQLiteDatabase openDatabase(String path, CursorFactory factory, int flags, DatabaseErrorHandler errorHandler, String key) {
        SQLiteDatabase db = new SQLiteDatabase(path, flags, factory, errorHandler, key);
        db.open();
        return db;
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, CursorFactory factory) {
        return openOrCreateDatabase(file.getPath(), factory);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, CursorFactory factory) {
        return openDatabase(path, factory, 268435456, null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return openDatabase(path, factory, 268435456, errorHandler);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, CursorFactory factory, DatabaseErrorHandler errorHandler, boolean enableWAL, String key) {
        if (enableWAL) {
            return openDatabase(path, factory, 805306368, errorHandler, key);
        }
        return openDatabase(path, factory, 268435456, errorHandler, key);
    }

    public static boolean deleteDatabase(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file must not be null");
        }
        boolean deleted = file.delete() | false | new File(file.getPath() + FilePathHelper.SUFFIX_JOURNAL).delete() | new File(file.getPath() + "-shm").delete() | new File(file.getPath() + "-wal").delete() | new File(file.getPath() + "-encrypt").delete();
        File dir = file.getParentFile();
        if (dir != null) {
            final String prefix = file.getName() + "-mj";
            for (File masterJournal : dir.listFiles(new FileFilter() {
                public final boolean accept(File candidate) {
                    return candidate.getName().startsWith(prefix);
                }
            })) {
                deleted |= masterJournal.delete();
            }
        }
        return deleted;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void reopenReadWrite() {
        /*
            r5 = this;
            java.lang.Object r3 = r5.mLock
            monitor-enter(r3)
            r5.throwIfNotOpenLocked()     // Catch:{ all -> 0x0027 }
            boolean r2 = r5.isReadOnlyLocked()     // Catch:{ all -> 0x0027 }
            if (r2 != 0) goto L_0x000e
            monitor-exit(r3)     // Catch:{ all -> 0x0027 }
        L_0x000d:
            return
        L_0x000e:
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r2 = r5.mConfigurationLocked     // Catch:{ all -> 0x0027 }
            int r1 = r2.openFlags     // Catch:{ all -> 0x0027 }
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r2 = r5.mConfigurationLocked     // Catch:{ all -> 0x0027 }
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r4 = r5.mConfigurationLocked     // Catch:{ all -> 0x0027 }
            int r4 = r4.openFlags     // Catch:{ all -> 0x0027 }
            r4 = r4 & -2
            r4 = r4 | 0
            r2.openFlags = r4     // Catch:{ all -> 0x0027 }
            com.alibaba.sqlcrypto.sqlite.SQLiteConnectionPool r2 = r5.mConnectionPoolLocked     // Catch:{ RuntimeException -> 0x002a }
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r4 = r5.mConfigurationLocked     // Catch:{ RuntimeException -> 0x002a }
            r2.reconfigure(r4)     // Catch:{ RuntimeException -> 0x002a }
            monitor-exit(r3)     // Catch:{ all -> 0x0027 }
            goto L_0x000d
        L_0x0027:
            r2 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0027 }
            throw r2
        L_0x002a:
            r0 = move-exception
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r2 = r5.mConfigurationLocked     // Catch:{ all -> 0x0027 }
            r2.openFlags = r1     // Catch:{ all -> 0x0027 }
            throw r0     // Catch:{ all -> 0x0027 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sqlcrypto.sqlite.SQLiteDatabase.reopenReadWrite():void");
    }

    private void open() {
        try {
            openInner();
        } catch (SQLiteDatabaseCorruptException e) {
            try {
                onCorruption();
                openInner();
            } catch (SQLiteException ex) {
                Log.e(TAG, "Failed to open database '" + getLabel() + "'.", ex);
                close();
                throw ex;
            }
        }
    }

    private void openInner() {
        synchronized (this.mLock) {
            if ($assertionsDisabled || this.mConnectionPoolLocked == null) {
                this.mConnectionPoolLocked = SQLiteConnectionPool.open(this.mConfigurationLocked);
            } else {
                throw new AssertionError();
            }
        }
        synchronized (sActiveDatabases) {
            sActiveDatabases.put(this, null);
        }
    }

    public static SQLiteDatabase create(CursorFactory factory) {
        return openDatabase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH, factory, 268435456);
    }

    public final void addCustomFunction(String name, int numArgs, CustomFunction function) {
        SQLiteCustomFunction wrapper = new SQLiteCustomFunction(name, numArgs, function);
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            this.mConfigurationLocked.customFunctions.add(wrapper);
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException ex) {
                this.mConfigurationLocked.customFunctions.remove(wrapper);
                throw ex;
            }
        }
    }

    public final int getVersion() {
        return Long.valueOf(DatabaseUtils.longForQuery(this, "PRAGMA user_version;", null)).intValue();
    }

    public final void setVersion(int version) {
        execSQL("PRAGMA user_version = " + version);
    }

    public final long getMaximumSize() {
        return DatabaseUtils.longForQuery(this, "PRAGMA max_page_count;", null) * getPageSize();
    }

    public final long setMaximumSize(long numBytes) {
        long pageSize = getPageSize();
        long numPages = numBytes / pageSize;
        if (numBytes % pageSize != 0) {
            numPages++;
        }
        return DatabaseUtils.longForQuery(this, "PRAGMA max_page_count = " + numPages, null) * pageSize;
    }

    public final long getPageSize() {
        return DatabaseUtils.longForQuery(this, "PRAGMA page_size;", null);
    }

    public final void setPageSize(long numBytes) {
        execSQL("PRAGMA page_size = " + numBytes);
    }

    @Deprecated
    public final void markTableSyncable(String table, String deletedTable) {
    }

    @Deprecated
    public final void markTableSyncable(String table, String foreignKey, String updateTable) {
    }

    public static String findEditTable(String tables) {
        if (!TextUtils.isEmpty(tables)) {
            int spacepos = tables.indexOf(32);
            int commapos = tables.indexOf(44);
            if (spacepos > 0 && (spacepos < commapos || commapos < 0)) {
                return tables.substring(0, spacepos);
            }
            if (commapos <= 0) {
                return tables;
            }
            if (commapos < spacepos || spacepos < 0) {
                return tables.substring(0, commapos);
            }
            return tables;
        }
        throw new IllegalStateException("Invalid tables");
    }

    public final SQLiteStatement compileStatement(String sql) {
        acquireReference();
        try {
            return new SQLiteStatement(this, sql, null);
        } finally {
            releaseReference();
        }
    }

    public final Cursor query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return queryWithFactory(null, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, null);
    }

    public final Cursor query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, Object cancellationSignal) {
        return queryWithFactory(null, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal);
    }

    public final Cursor queryWithFactory(CursorFactory cursorFactory, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return queryWithFactory(cursorFactory, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, null);
    }

    public final Cursor queryWithFactory(CursorFactory cursorFactory, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, Object cancellationSignal) {
        acquireReference();
        try {
            return rawQueryWithFactory(cursorFactory, SQLiteQueryBuilder.buildQueryString(distinct, table, columns, selection, groupBy, having, orderBy, limit), selectionArgs, findEditTable(table), cancellationSignal);
        } finally {
            releaseReference();
        }
    }

    public final Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return query(false, table, columns, selection, selectionArgs, groupBy, having, orderBy, null);
    }

    public final Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return query(false, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public final Cursor rawQuery(String sql, String[] selectionArgs) {
        return rawQueryWithFactory(null, sql, selectionArgs, null, null);
    }

    public final Cursor rawQuery(String sql, String[] selectionArgs, Object cancellationSignal) {
        return rawQueryWithFactory(null, sql, selectionArgs, null, cancellationSignal);
    }

    public final Cursor rawQueryWithFactory(CursorFactory cursorFactory, String sql, String[] selectionArgs, String editTable) {
        return rawQueryWithFactory(cursorFactory, sql, selectionArgs, editTable, null);
    }

    public final Cursor rawQueryWithFactory(CursorFactory cursorFactory, String sql, String[] selectionArgs, String editTable, Object cancellationSignal) {
        acquireReference();
        try {
            SQLiteDirectCursorDriver sQLiteDirectCursorDriver = new SQLiteDirectCursorDriver(this, sql, editTable, cancellationSignal);
            if (cursorFactory == null) {
                cursorFactory = this.mCursorFactory;
            }
            return sQLiteDirectCursorDriver.query(cursorFactory, selectionArgs);
        } finally {
            releaseReference();
        }
    }

    public final long insert(String table, String nullColumnHack, ContentValues values) {
        try {
            return insertWithOnConflict(table, nullColumnHack, values, 0);
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting " + values, e);
            return -1;
        }
    }

    public final long insertOrThrow(String table, String nullColumnHack, ContentValues values) {
        return insertWithOnConflict(table, nullColumnHack, values, 0);
    }

    public final long replace(String table, String nullColumnHack, ContentValues initialValues) {
        try {
            return insertWithOnConflict(table, nullColumnHack, initialValues, 5);
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting " + initialValues, e);
            return -1;
        }
    }

    public final long replaceOrThrow(String table, String nullColumnHack, ContentValues initialValues) {
        return insertWithOnConflict(table, nullColumnHack, initialValues, 5);
    }

    @TargetApi(11)
    public final long insertWithOnConflict(String table, String nullColumnHack, ContentValues initialValues, int conflictAlgorithm) {
        SQLiteStatement statement;
        acquireReference();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT");
            sql.append(CONFLICT_VALUES[conflictAlgorithm]);
            sql.append(" INTO ");
            sql.append(table);
            sql.append('(');
            Object[] bindArgs = null;
            int size = (initialValues == null || initialValues.size() <= 0) ? 0 : initialValues.size();
            if (size > 0) {
                bindArgs = new Object[size];
                int i = 0;
                for (String colName : initialValues.keySet()) {
                    sql.append(i > 0 ? "," : "");
                    sql.append(colName);
                    bindArgs[i] = initialValues.get(colName);
                    i++;
                }
                sql.append(')');
                sql.append(" VALUES (");
                int i2 = 0;
                while (i2 < size) {
                    sql.append(i2 > 0 ? ",?" : "?");
                    i2++;
                }
            } else {
                sql.append(nullColumnHack + ") VALUES (NULL");
            }
            sql.append(')');
            statement = new SQLiteStatement(this, sql.toString(), bindArgs);
            long executeInsert = statement.executeInsert();
            statement.close();
            releaseReference();
            return executeInsert;
        } catch (Throwable th) {
            releaseReference();
            throw th;
        }
    }

    public final int delete(String table, String whereClause, String[] whereArgs) {
        SQLiteStatement statement;
        acquireReference();
        try {
            statement = new SQLiteStatement(this, "DELETE FROM " + table + (!TextUtils.isEmpty(whereClause) ? " WHERE " + whereClause : ""), whereArgs);
            int executeUpdateDelete = statement.executeUpdateDelete();
            statement.close();
            releaseReference();
            return executeUpdateDelete;
        } catch (Throwable th) {
            releaseReference();
            throw th;
        }
    }

    public final int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return updateWithOnConflict(table, values, whereClause, whereArgs, 0);
    }

    @TargetApi(11)
    public final int updateWithOnConflict(String table, ContentValues values, String whereClause, String[] whereArgs, int conflictAlgorithm) {
        SQLiteStatement statement;
        if (values == null || values.size() == 0) {
            throw new IllegalArgumentException("Empty values");
        }
        acquireReference();
        try {
            StringBuilder sql = new StringBuilder(MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_);
            sql.append("UPDATE ");
            sql.append(CONFLICT_VALUES[conflictAlgorithm]);
            sql.append(table);
            sql.append(" SET ");
            int setValuesSize = values.size();
            int bindArgsSize = whereArgs == null ? setValuesSize : setValuesSize + whereArgs.length;
            Object[] bindArgs = new Object[bindArgsSize];
            int i = 0;
            for (String colName : values.keySet()) {
                sql.append(i > 0 ? "," : "");
                sql.append(colName);
                bindArgs[i] = values.get(colName);
                sql.append("=?");
                i++;
            }
            if (whereArgs != null) {
                for (int i2 = setValuesSize; i2 < bindArgsSize; i2++) {
                    bindArgs[i2] = whereArgs[i2 - setValuesSize];
                }
            }
            if (!TextUtils.isEmpty(whereClause)) {
                sql.append(" WHERE ");
                sql.append(whereClause);
            }
            statement = new SQLiteStatement(this, sql.toString(), bindArgs);
            int executeUpdateDelete = statement.executeUpdateDelete();
            statement.close();
            releaseReference();
            return executeUpdateDelete;
        } catch (Throwable th) {
            releaseReference();
            throw th;
        }
    }

    public final void execSQL(String sql) {
        executeSql(sql, null);
    }

    public final void execSQL(String sql, Object[] bindArgs) {
        if (bindArgs == null) {
            throw new IllegalArgumentException("Empty bindArgs");
        }
        executeSql(sql, bindArgs);
    }

    private int executeSql(String sql, Object[] bindArgs) {
        SQLiteStatement statement;
        acquireReference();
        try {
            if (DatabaseUtils.getSqlStatementType(sql) == 3) {
                boolean disableWal = false;
                synchronized (this.mLock) {
                    if (!this.mHasAttachedDbsLocked) {
                        this.mHasAttachedDbsLocked = true;
                        disableWal = true;
                    }
                }
                if (disableWal) {
                    disableWriteAheadLogging();
                }
            }
            statement = new SQLiteStatement(this, sql, bindArgs);
            int executeUpdateDelete = statement.executeUpdateDelete();
            statement.close();
            releaseReference();
            return executeUpdateDelete;
        } catch (Throwable th) {
            releaseReference();
            throw th;
        }
    }

    public final boolean isReadOnly() {
        boolean isReadOnlyLocked;
        synchronized (this.mLock) {
            isReadOnlyLocked = isReadOnlyLocked();
        }
        return isReadOnlyLocked;
    }

    private boolean isReadOnlyLocked() {
        return (this.mConfigurationLocked.openFlags & 1) == 1;
    }

    public final boolean isInMemoryDatabase() {
        boolean isInMemoryDb;
        synchronized (this.mLock) {
            isInMemoryDb = this.mConfigurationLocked.isInMemoryDb();
        }
        return isInMemoryDb;
    }

    public final boolean isOpen() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mConnectionPoolLocked != null;
        }
        return z;
    }

    public final boolean needUpgrade(int newVersion) {
        return newVersion > getVersion();
    }

    public final String getPath() {
        String str;
        synchronized (this.mLock) {
            str = this.mConfigurationLocked.path;
        }
        return str;
    }

    public final void setLocale(Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException("locale must not be null.");
        }
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            Locale oldLocale = this.mConfigurationLocked.locale;
            this.mConfigurationLocked.locale = locale;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException ex) {
                this.mConfigurationLocked.locale = oldLocale;
                throw ex;
            }
        }
    }

    public final void setMaxSqlCacheSize(int cacheSize) {
        if (cacheSize > 100 || cacheSize < 0) {
            throw new IllegalStateException("expected value between 0 and 100");
        }
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            int oldMaxSqlCacheSize = this.mConfigurationLocked.maxSqlCacheSize;
            this.mConfigurationLocked.maxSqlCacheSize = cacheSize;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException ex) {
                this.mConfigurationLocked.maxSqlCacheSize = oldMaxSqlCacheSize;
                throw ex;
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setForeignKeyConstraintsEnabled(boolean r5) {
        /*
            r4 = this;
            java.lang.Object r2 = r4.mLock
            monitor-enter(r2)
            r4.throwIfNotOpenLocked()     // Catch:{ all -> 0x001b }
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r1 = r4.mConfigurationLocked     // Catch:{ all -> 0x001b }
            boolean r1 = r1.foreignKeyConstraintsEnabled     // Catch:{ all -> 0x001b }
            if (r1 != r5) goto L_0x000e
            monitor-exit(r2)     // Catch:{ all -> 0x001b }
        L_0x000d:
            return
        L_0x000e:
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r1 = r4.mConfigurationLocked     // Catch:{ all -> 0x001b }
            r1.foreignKeyConstraintsEnabled = r5     // Catch:{ all -> 0x001b }
            com.alibaba.sqlcrypto.sqlite.SQLiteConnectionPool r1 = r4.mConnectionPoolLocked     // Catch:{ RuntimeException -> 0x001e }
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r3 = r4.mConfigurationLocked     // Catch:{ RuntimeException -> 0x001e }
            r1.reconfigure(r3)     // Catch:{ RuntimeException -> 0x001e }
            monitor-exit(r2)     // Catch:{ all -> 0x001b }
            goto L_0x000d
        L_0x001b:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x001b }
            throw r1
        L_0x001e:
            r0 = move-exception
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r3 = r4.mConfigurationLocked     // Catch:{ all -> 0x001b }
            if (r5 != 0) goto L_0x0027
            r1 = 1
        L_0x0024:
            r3.foreignKeyConstraintsEnabled = r1     // Catch:{ all -> 0x001b }
            throw r0     // Catch:{ all -> 0x001b }
        L_0x0027:
            r1 = 0
            goto L_0x0024
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sqlcrypto.sqlite.SQLiteDatabase.setForeignKeyConstraintsEnabled(boolean):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        return false;
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean enableWriteAheadLogging() {
        /*
            r6 = this;
            r5 = 536870912(0x20000000, float:1.0842022E-19)
            r1 = 1
            r2 = 0
            java.lang.Object r3 = r6.mLock
            monitor-enter(r3)
            r6.throwIfNotOpenLocked()     // Catch:{ all -> 0x006c }
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r4 = r6.mConfigurationLocked     // Catch:{ all -> 0x006c }
            int r4 = r4.openFlags     // Catch:{ all -> 0x006c }
            r4 = r4 & r5
            if (r4 == 0) goto L_0x0013
            monitor-exit(r3)     // Catch:{ all -> 0x006c }
        L_0x0012:
            return r1
        L_0x0013:
            boolean r4 = r6.isReadOnlyLocked()     // Catch:{ all -> 0x006c }
            if (r4 == 0) goto L_0x001c
            monitor-exit(r3)     // Catch:{ all -> 0x006c }
            r1 = r2
            goto L_0x0012
        L_0x001c:
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r4 = r6.mConfigurationLocked     // Catch:{ all -> 0x006c }
            boolean r4 = r4.isInMemoryDb()     // Catch:{ all -> 0x006c }
            if (r4 == 0) goto L_0x002e
            java.lang.String r1 = "SQLiteDatabase"
            java.lang.String r4 = "can't enable WAL for memory databases."
            android.util.Log.i(r1, r4)     // Catch:{ all -> 0x006c }
            monitor-exit(r3)     // Catch:{ all -> 0x006c }
            r1 = r2
            goto L_0x0012
        L_0x002e:
            boolean r4 = r6.mHasAttachedDbsLocked     // Catch:{ all -> 0x006c }
            if (r4 == 0) goto L_0x005c
            java.lang.String r1 = "SQLiteDatabase"
            r4 = 3
            boolean r1 = android.util.Log.isLoggable(r1, r4)     // Catch:{ all -> 0x006c }
            if (r1 == 0) goto L_0x0059
            java.lang.String r1 = "SQLiteDatabase"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x006c }
            java.lang.String r5 = "this database: "
            r4.<init>(r5)     // Catch:{ all -> 0x006c }
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r5 = r6.mConfigurationLocked     // Catch:{ all -> 0x006c }
            java.lang.String r5 = r5.label     // Catch:{ all -> 0x006c }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x006c }
            java.lang.String r5 = " has attached databases. can't  enable WAL."
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x006c }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x006c }
            android.util.Log.d(r1, r4)     // Catch:{ all -> 0x006c }
        L_0x0059:
            monitor-exit(r3)     // Catch:{ all -> 0x006c }
            r1 = r2
            goto L_0x0012
        L_0x005c:
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r2 = r6.mConfigurationLocked     // Catch:{ all -> 0x006c }
            int r4 = r2.openFlags     // Catch:{ all -> 0x006c }
            r4 = r4 | r5
            r2.openFlags = r4     // Catch:{ all -> 0x006c }
            com.alibaba.sqlcrypto.sqlite.SQLiteConnectionPool r2 = r6.mConnectionPoolLocked     // Catch:{ RuntimeException -> 0x006f }
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r4 = r6.mConfigurationLocked     // Catch:{ RuntimeException -> 0x006f }
            r2.reconfigure(r4)     // Catch:{ RuntimeException -> 0x006f }
            monitor-exit(r3)     // Catch:{ all -> 0x006c }
            goto L_0x0012
        L_0x006c:
            r1 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x006c }
            throw r1
        L_0x006f:
            r0 = move-exception
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r1 = r6.mConfigurationLocked     // Catch:{ all -> 0x006c }
            int r2 = r1.openFlags     // Catch:{ all -> 0x006c }
            r4 = -536870913(0xffffffffdfffffff, float:-3.6893486E19)
            r2 = r2 & r4
            r1.openFlags = r2     // Catch:{ all -> 0x006c }
            throw r0     // Catch:{ all -> 0x006c }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sqlcrypto.sqlite.SQLiteDatabase.enableWriteAheadLogging():boolean");
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void disableWriteAheadLogging() {
        /*
            r6 = this;
            r5 = 536870912(0x20000000, float:1.0842022E-19)
            java.lang.Object r2 = r6.mLock
            monitor-enter(r2)
            r6.throwIfNotOpenLocked()     // Catch:{ all -> 0x0024 }
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r1 = r6.mConfigurationLocked     // Catch:{ all -> 0x0024 }
            int r1 = r1.openFlags     // Catch:{ all -> 0x0024 }
            r1 = r1 & r5
            if (r1 != 0) goto L_0x0011
            monitor-exit(r2)     // Catch:{ all -> 0x0024 }
        L_0x0010:
            return
        L_0x0011:
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r1 = r6.mConfigurationLocked     // Catch:{ all -> 0x0024 }
            int r3 = r1.openFlags     // Catch:{ all -> 0x0024 }
            r4 = -536870913(0xffffffffdfffffff, float:-3.6893486E19)
            r3 = r3 & r4
            r1.openFlags = r3     // Catch:{ all -> 0x0024 }
            com.alibaba.sqlcrypto.sqlite.SQLiteConnectionPool r1 = r6.mConnectionPoolLocked     // Catch:{ RuntimeException -> 0x0027 }
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r3 = r6.mConfigurationLocked     // Catch:{ RuntimeException -> 0x0027 }
            r1.reconfigure(r3)     // Catch:{ RuntimeException -> 0x0027 }
            monitor-exit(r2)     // Catch:{ all -> 0x0024 }
            goto L_0x0010
        L_0x0024:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0024 }
            throw r1
        L_0x0027:
            r0 = move-exception
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r1 = r6.mConfigurationLocked     // Catch:{ all -> 0x0024 }
            int r3 = r1.openFlags     // Catch:{ all -> 0x0024 }
            r3 = r3 | r5
            r1.openFlags = r3     // Catch:{ all -> 0x0024 }
            throw r0     // Catch:{ all -> 0x0024 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sqlcrypto.sqlite.SQLiteDatabase.disableWriteAheadLogging():void");
    }

    public final boolean isWriteAheadLoggingEnabled() {
        boolean z;
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            z = (this.mConfigurationLocked.openFlags & 536870912) != 0;
        }
        return z;
    }

    static ArrayList<DbStats> getDbStats() {
        ArrayList dbStatsList = new ArrayList();
        Iterator<SQLiteDatabase> it = getActiveDatabases().iterator();
        while (it.hasNext()) {
            it.next().collectDbStats(dbStatsList);
        }
        return dbStatsList;
    }

    private void collectDbStats(ArrayList<DbStats> dbStatsList) {
        synchronized (this.mLock) {
            if (this.mConnectionPoolLocked != null) {
                this.mConnectionPoolLocked.collectDbStats(dbStatsList);
            }
        }
    }

    private static ArrayList<SQLiteDatabase> getActiveDatabases() {
        ArrayList databases = new ArrayList();
        synchronized (sActiveDatabases) {
            databases.addAll(sActiveDatabases.keySet());
        }
        return databases;
    }

    static void dumpAll(Printer printer, boolean verbose) {
        Iterator<SQLiteDatabase> it = getActiveDatabases().iterator();
        while (it.hasNext()) {
            it.next().dump(printer, verbose);
        }
    }

    private void dump(Printer printer, boolean verbose) {
        synchronized (this.mLock) {
            if (this.mConnectionPoolLocked != null) {
                printer.println("");
                this.mConnectionPoolLocked.dump(printer, verbose);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002b, code lost:
        r1 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r1 = rawQuery("pragma database_list;", null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0037, code lost:
        if (r1.moveToNext() == false) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0039, code lost:
        r0.add(new android.util.Pair(r1.getString(1), r1.getString(2)));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004c, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004d, code lost:
        if (r1 != null) goto L_0x004f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0052, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0053, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0054, code lost:
        releaseReference();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0057, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0058, code lost:
        if (r1 == null) goto L_0x005d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x005d, code lost:
        releaseReference();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<android.util.Pair<java.lang.String, java.lang.String>> getAttachedDbs() {
        /*
            r6 = this;
            r2 = 0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.Object r3 = r6.mLock
            monitor-enter(r3)
            com.alibaba.sqlcrypto.sqlite.SQLiteConnectionPool r4 = r6.mConnectionPoolLocked     // Catch:{ all -> 0x0024 }
            if (r4 != 0) goto L_0x0010
            monitor-exit(r3)     // Catch:{ all -> 0x0024 }
            r0 = r2
        L_0x000f:
            return r0
        L_0x0010:
            boolean r2 = r6.mHasAttachedDbsLocked     // Catch:{ all -> 0x0024 }
            if (r2 != 0) goto L_0x0027
            android.util.Pair r2 = new android.util.Pair     // Catch:{ all -> 0x0024 }
            java.lang.String r4 = "main"
            com.alibaba.sqlcrypto.sqlite.SQLiteDatabaseConfiguration r5 = r6.mConfigurationLocked     // Catch:{ all -> 0x0024 }
            java.lang.String r5 = r5.path     // Catch:{ all -> 0x0024 }
            r2.<init>(r4, r5)     // Catch:{ all -> 0x0024 }
            r0.add(r2)     // Catch:{ all -> 0x0024 }
            monitor-exit(r3)     // Catch:{ all -> 0x0024 }
            goto L_0x000f
        L_0x0024:
            r2 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0024 }
            throw r2
        L_0x0027:
            r6.acquireReference()     // Catch:{ all -> 0x0024 }
            monitor-exit(r3)     // Catch:{ all -> 0x0024 }
            r1 = 0
            java.lang.String r2 = "pragma database_list;"
            r3 = 0
            android.database.Cursor r1 = r6.rawQuery(r2, r3)     // Catch:{ all -> 0x004c }
        L_0x0033:
            boolean r2 = r1.moveToNext()     // Catch:{ all -> 0x004c }
            if (r2 == 0) goto L_0x0058
            android.util.Pair r2 = new android.util.Pair     // Catch:{ all -> 0x004c }
            r3 = 1
            java.lang.String r3 = r1.getString(r3)     // Catch:{ all -> 0x004c }
            r4 = 2
            java.lang.String r4 = r1.getString(r4)     // Catch:{ all -> 0x004c }
            r2.<init>(r3, r4)     // Catch:{ all -> 0x004c }
            r0.add(r2)     // Catch:{ all -> 0x004c }
            goto L_0x0033
        L_0x004c:
            r2 = move-exception
            if (r1 == 0) goto L_0x0052
            r1.close()     // Catch:{ all -> 0x0053 }
        L_0x0052:
            throw r2     // Catch:{ all -> 0x0053 }
        L_0x0053:
            r2 = move-exception
            r6.releaseReference()
            throw r2
        L_0x0058:
            if (r1 == 0) goto L_0x005d
            r1.close()     // Catch:{ all -> 0x0053 }
        L_0x005d:
            r6.releaseReference()
            goto L_0x000f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sqlcrypto.sqlite.SQLiteDatabase.getAttachedDbs():java.util.List");
    }

    public final boolean isDatabaseIntegrityOk() {
        List attachedDbs;
        SQLiteStatement prog;
        acquireReference();
        try {
            attachedDbs = getAttachedDbs();
            if (attachedDbs == null) {
                throw new IllegalStateException("databaselist for: " + getPath() + " couldn't be retrieved. probably because the database is closed");
            }
        } catch (SQLiteException e) {
            attachedDbs = new ArrayList();
            attachedDbs.add(new Pair("main", getPath()));
        } catch (Throwable th) {
            releaseReference();
            throw th;
        }
        for (int i = 0; i < attachedDbs.size(); i++) {
            Pair p = (Pair) attachedDbs.get(i);
            prog = null;
            SQLiteStatement prog2 = compileStatement("PRAGMA " + ((String) p.first) + ".integrity_check(1);");
            String rslt = prog2.simpleQueryForString();
            if (!rslt.equalsIgnoreCase(Value.OK)) {
                Log.e(TAG, "PRAGMA integrity_check on " + ((String) p.second) + " returned: " + rslt);
                if (prog2 != null) {
                    prog2.close();
                }
                releaseReference();
                return false;
            }
            if (prog2 != null) {
                prog2.close();
            }
        }
        releaseReference();
        return true;
    }

    public final String toString() {
        return "SQLiteDatabase: " + getPath();
    }

    private void throwIfNotOpenLocked() {
        if (this.mConnectionPoolLocked == null) {
            throw new IllegalStateException("The database '" + this.mConfigurationLocked.label + "' is not open.");
        }
    }

    public final int getSqliteHandler() {
        return getSqliteHandler(getPath());
    }

    public static int getSqliteHandler(String path) {
        return SQLiteConnection.getNativeHandle(path);
    }

    public static String buildKey(Context context, String key) {
        return SQLiteConnection.buildKey(context, key);
    }

    public static boolean isDatabaseEnabled() {
        return mCryptoEnabled;
    }
}
