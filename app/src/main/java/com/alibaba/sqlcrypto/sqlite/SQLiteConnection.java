package com.alibaba.sqlcrypto.sqlite;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.util.Printer;
import com.alibaba.sqlcrypto.CursorWindow;
import com.alibaba.sqlcrypto.DatabaseUtils;
import com.alibaba.sqlcrypto.LruCache;
import com.alibaba.sqlcrypto.sqlite.SQLiteDebug.DbStats;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.sdk.util.e;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public final class SQLiteConnection {
    private static final boolean DEBUG = false;
    /* access modifiers changed from: private */
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final String TAG = "SQLiteConnection";
    private static final Pattern TRIM_SQL_PATTERN = Pattern.compile("[\\s]*\\n+[\\s]*");
    private static final ConcurrentHashMap<String, Integer> mNativeHandles = new ConcurrentHashMap<>();
    private final SQLiteDatabaseConfiguration mConfiguration;
    private final int mConnectionId;
    private int mConnectionPtr;
    private final boolean mIsPrimaryConnection;
    private final boolean mIsReadOnlyConnection;
    private boolean mOnlyAllowReadOnlyOperations;
    private final SQLiteConnectionPool mPool;
    private final PreparedStatementCache mPreparedStatementCache;
    private PreparedStatement mPreparedStatementPool;
    private final OperationLog mRecentOperations = new OperationLog();

    final class Operation {
        private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        public ArrayList<Object> mBindArgs;
        public int mCookie;
        public long mEndTime;
        public Exception mException;
        public boolean mFinished;
        public String mKind;
        public String mSql;
        public long mStartTime;

        private Operation() {
        }

        public final void describe(StringBuilder msg, boolean verbose) {
            msg.append(this.mKind);
            if (this.mFinished) {
                msg.append(" took ").append(this.mEndTime - this.mStartTime).append(RPCDataParser.TIME_MS);
            } else {
                msg.append(" started ").append(System.currentTimeMillis() - this.mStartTime).append("ms ago");
            }
            msg.append(" - ").append(getStatus());
            if (this.mSql != null) {
                msg.append(", sql=\"").append(SQLiteConnection.trimSqlForDisplay(this.mSql)).append("\"");
            }
            if (!(!verbose || this.mBindArgs == null || this.mBindArgs.size() == 0)) {
                msg.append(", bindArgs=[");
                int count = this.mBindArgs.size();
                for (int i = 0; i < count; i++) {
                    Object arg = this.mBindArgs.get(i);
                    if (i != 0) {
                        msg.append(", ");
                    }
                    if (arg == null) {
                        msg.append("null");
                    } else if (arg instanceof byte[]) {
                        msg.append("<byte[]>");
                    } else if (arg instanceof String) {
                        msg.append("\"").append((String) arg).append("\"");
                    } else {
                        msg.append(arg);
                    }
                }
                msg.append("]");
            }
            if (this.mException != null) {
                msg.append(", exception=\"").append(this.mException.getMessage()).append("\"");
            }
        }

        private String getStatus() {
            if (!this.mFinished) {
                return MiscUtils.KEY_RUNNING;
            }
            return this.mException != null ? e.b : "succeeded";
        }

        /* access modifiers changed from: private */
        public String getFormattedStartTime() {
            return sDateFormat.format(new Date(this.mStartTime));
        }
    }

    final class OperationLog {
        private static final int COOKIE_GENERATION_SHIFT = 8;
        private static final int COOKIE_INDEX_MASK = 255;
        private static final int MAX_RECENT_OPERATIONS = 20;
        private int mGeneration;
        private int mIndex;
        private final Operation[] mOperations;

        private OperationLog() {
            this.mOperations = new Operation[20];
        }

        public final int beginOperation(String kind, String sql, Object[] bindArgs) {
            int i;
            synchronized (this.mOperations) {
                int index = (this.mIndex + 1) % 20;
                Operation operation2 = this.mOperations[index];
                if (operation2 == null) {
                    operation2 = new Operation();
                    this.mOperations[index] = operation2;
                } else {
                    operation2.mFinished = false;
                    operation2.mException = null;
                    if (operation2.mBindArgs != null) {
                        operation2.mBindArgs.clear();
                    }
                }
                operation2.mStartTime = System.currentTimeMillis();
                operation2.mKind = kind;
                operation2.mSql = sql;
                if (bindArgs != null) {
                    if (operation2.mBindArgs == null) {
                        operation2.mBindArgs = new ArrayList<>();
                    } else {
                        operation2.mBindArgs.clear();
                    }
                    for (Object arg : bindArgs) {
                        if (arg == null || !(arg instanceof byte[])) {
                            operation2.mBindArgs.add(arg);
                        } else {
                            operation2.mBindArgs.add(SQLiteConnection.EMPTY_BYTE_ARRAY);
                        }
                    }
                }
                operation2.mCookie = newOperationCookieLocked(index);
                this.mIndex = index;
                i = operation2.mCookie;
            }
            return i;
        }

        public final void failOperation(int cookie, Exception ex) {
            synchronized (this.mOperations) {
                Operation operation2 = getOperationLocked(cookie);
                if (operation2 != null) {
                    operation2.mException = ex;
                }
            }
        }

        public final void endOperation(int cookie) {
            synchronized (this.mOperations) {
                if (endOperationDeferLogLocked(cookie)) {
                    logOperationLocked(cookie, null);
                }
            }
        }

        public final boolean endOperationDeferLog(int cookie) {
            boolean endOperationDeferLogLocked;
            synchronized (this.mOperations) {
                endOperationDeferLogLocked = endOperationDeferLogLocked(cookie);
            }
            return endOperationDeferLogLocked;
        }

        public final void logOperation(int cookie, String detail) {
            synchronized (this.mOperations) {
                logOperationLocked(cookie, detail);
            }
        }

        private boolean endOperationDeferLogLocked(int cookie) {
            Operation operation2 = getOperationLocked(cookie);
            if (operation2 != null) {
                operation2.mEndTime = System.currentTimeMillis();
                operation2.mFinished = true;
            }
            return false;
        }

        private void logOperationLocked(int cookie, String detail) {
            Operation operation2 = getOperationLocked(cookie);
            StringBuilder msg = new StringBuilder();
            operation2.describe(msg, false);
            if (detail != null) {
                msg.append(", ").append(detail);
            }
            Log.d(SQLiteConnection.TAG, msg.toString());
        }

        private int newOperationCookieLocked(int index) {
            int i = this.mGeneration;
            this.mGeneration = i + 1;
            return (i << 8) | index;
        }

        private Operation getOperationLocked(int cookie) {
            Operation operation2 = this.mOperations[cookie & 255];
            if (operation2.mCookie == cookie) {
                return operation2;
            }
            return null;
        }

        public final String describeCurrentOperation() {
            String str;
            synchronized (this.mOperations) {
                Operation operation2 = this.mOperations[this.mIndex];
                if (operation2 == null || operation2.mFinished) {
                    str = null;
                } else {
                    StringBuilder msg = new StringBuilder();
                    operation2.describe(msg, false);
                    str = msg.toString();
                }
            }
            return str;
        }

        public final void dump(Printer printer, boolean verbose) {
            synchronized (this.mOperations) {
                printer.println("  Most recently executed operations:");
                int index = this.mIndex;
                Operation operation2 = this.mOperations[index];
                if (operation2 != null) {
                    int n = 0;
                    do {
                        StringBuilder msg = new StringBuilder();
                        msg.append("    ").append(n).append(": [");
                        msg.append(operation2.getFormattedStartTime());
                        msg.append("] ");
                        operation2.describe(msg, verbose);
                        printer.println(msg.toString());
                        if (index > 0) {
                            index--;
                        } else {
                            index = 19;
                        }
                        n++;
                        operation2 = this.mOperations[index];
                        if (operation2 == null) {
                            break;
                        }
                    } while (n < 20);
                } else {
                    printer.println("    <none>");
                }
            }
        }
    }

    final class PreparedStatement {
        public boolean mInCache;
        public boolean mInUse;
        public int mNumParameters;
        public PreparedStatement mPoolNext;
        public boolean mReadOnly;
        public String mSql;
        public int mStatementPtr;
        public int mType;

        private PreparedStatement() {
        }
    }

    @TargetApi(12)
    final class PreparedStatementCache extends LruCache<String, PreparedStatement> {
        public PreparedStatementCache(int size) {
            super(size);
        }

        /* access modifiers changed from: protected */
        public final void entryRemoved(boolean evicted, String key, PreparedStatement oldValue, PreparedStatement newValue) {
            oldValue.mInCache = false;
            if (!oldValue.mInUse) {
                SQLiteConnection.this.finalizePreparedStatement(oldValue);
            }
        }

        public final void dump(Printer printer) {
            printer.println("  Prepared statement cache:");
            Map cache = snapshot();
            if (!cache.isEmpty()) {
                int i = 0;
                for (Entry entry : cache.entrySet()) {
                    PreparedStatement statement = (PreparedStatement) entry.getValue();
                    if (statement.mInCache) {
                        printer.println("    " + i + ": statementPtr=0x" + Integer.toHexString(statement.mStatementPtr) + ", numParameters=" + statement.mNumParameters + ", type=" + statement.mType + ", readOnly=" + statement.mReadOnly + ", sql=\"" + SQLiteConnection.trimSqlForDisplay((String) entry.getKey()) + "\"");
                    }
                    i++;
                }
                return;
            }
            printer.println("    <none>");
        }
    }

    public static native String buildKey(Context context, String str);

    private static native void nativeBindBlob(int i, int i2, int i3, byte[] bArr);

    private static native void nativeBindDouble(int i, int i2, int i3, double d);

    private static native void nativeBindLong(int i, int i2, int i3, long j);

    private static native void nativeBindNull(int i, int i2, int i3);

    private static native void nativeBindString(int i, int i2, int i3, String str);

    private static native void nativeCancel(int i);

    static native boolean nativeCheckLoad();

    private static native void nativeClose(int i);

    private static native void nativeExecute(int i, int i2);

    private static native int nativeExecuteForBlobFileDescriptor(int i, int i2);

    private static native int nativeExecuteForChangedRowCount(int i, int i2);

    private static native long nativeExecuteForCursorWindow(int i, int i2, int i3, int i4, int i5, boolean z);

    private static native long nativeExecuteForLastInsertedRowId(int i, int i2);

    private static native long nativeExecuteForLong(int i, int i2);

    private static native String nativeExecuteForString(int i, int i2);

    private static native void nativeFinalizeStatement(int i, int i2);

    private static native int nativeGetColumnCount(int i, int i2);

    private static native String nativeGetColumnName(int i, int i2, int i3);

    private static native int nativeGetDbLookaside(int i);

    private static native int nativeGetParameterCount(int i, int i2);

    private static native boolean nativeIsReadOnly(int i, int i2);

    private static native int nativeOpen(String str, int i, String str2, boolean z, boolean z2);

    private static native int nativePrepareStatement(int i, String str);

    private static native void nativeRegisterCustomFunction(int i, SQLiteCustomFunction sQLiteCustomFunction);

    private static native void nativeRegisterLocalizedCollators(int i, String str);

    private static native void nativeResetCancel(int i, boolean z);

    private static native void nativeResetStatementAndClearBindings(int i, int i2);

    private static native int nativeSqliteHandler(int i);

    private static int getSqliteHandler(int connectionPtr) {
        return nativeSqliteHandler(connectionPtr);
    }

    private SQLiteConnection(SQLiteConnectionPool pool, SQLiteDatabaseConfiguration configuration, int connectionId, boolean primaryConnection) {
        this.mPool = pool;
        this.mConfiguration = new SQLiteDatabaseConfiguration(configuration);
        this.mConnectionId = connectionId;
        this.mIsPrimaryConnection = primaryConnection;
        this.mIsReadOnlyConnection = (configuration.openFlags & 1) != 0;
        this.mPreparedStatementCache = new PreparedStatementCache(this.mConfiguration.maxSqlCacheSize);
    }

    static int getNativeHandle(String path) {
        if (path == null) {
            return 0;
        }
        Integer handler = mNativeHandles.get(path);
        if (handler != null) {
            return handler.intValue();
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public final void finalize() {
        try {
            if (!(this.mPool == null || this.mConnectionPtr == 0)) {
                this.mPool.onConnectionLeaked();
            }
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    static SQLiteConnection open(SQLiteConnectionPool pool, SQLiteDatabaseConfiguration configuration, int connectionId, boolean primaryConnection) {
        SQLiteConnection connection = new SQLiteConnection(pool, configuration, connectionId, primaryConnection);
        try {
            connection.open();
            if (primaryConnection) {
                mNativeHandles.put(connection.mConfiguration.path, Integer.valueOf(getSqliteHandler(connection.mConnectionPtr)));
            }
            return connection;
        } catch (SQLiteException ex) {
            connection.dispose(false);
            throw ex;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void close() {
        dispose(false);
    }

    private void open() {
        this.mConnectionPtr = nativeOpen(this.mConfiguration.path, this.mConfiguration.openFlags, this.mConfiguration.label, false, false);
        setEncryptKey();
        setForeignKeyModeFromConfiguration();
        setWalModeFromConfiguration();
        setJournalSizeLimit();
        setAutoCheckpointInterval();
        setLocaleFromConfiguration();
        int functionCount = this.mConfiguration.customFunctions.size();
        for (int i = 0; i < functionCount; i++) {
            nativeRegisterCustomFunction(this.mConnectionPtr, this.mConfiguration.customFunctions.get(i));
        }
    }

    private void setEncryptKey() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            String password = this.mConfiguration.password;
            if (password != null) {
                File encryptFile = new File(this.mConfiguration.path + "-encrypt");
                if (encryptFile.exists()) {
                    execute("PRAGMA key='" + password + "';", null, null);
                    return;
                }
                execute("PRAGMA rekey='" + password + "';", null, null);
                try {
                    encryptFile.createNewFile();
                } catch (IOException e) {
                    Log.e(TAG, "Can't touch " + encryptFile.getName() + ", can't rekey the database");
                }
            }
        }
    }

    @TargetApi(12)
    private void dispose(boolean finalized) {
        if (this.mConnectionPtr != 0) {
            int cookie = this.mRecentOperations.beginOperation(DataflowMonitorModel.METHOD_NAME_CLOSE, null, null);
            try {
                this.mPreparedStatementCache.evictAll();
                nativeClose(this.mConnectionPtr);
                this.mConnectionPtr = 0;
            } finally {
                this.mRecentOperations.endOperation(cookie);
            }
        }
    }

    private void setPageSize() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            long newValue = SQLiteGlobal.getDefaultPageSize();
            if (executeForLong("PRAGMA page_size", null, null) != newValue) {
                execute("PRAGMA page_size=" + newValue, null, null);
            }
        }
    }

    private void setAutoCheckpointInterval() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            long newValue = (long) SQLiteGlobal.getWALAutoCheckpoint();
            if (executeForLong("PRAGMA wal_autocheckpoint", null, null) != newValue) {
                executeForLong("PRAGMA wal_autocheckpoint=" + newValue, null, null);
            }
        }
    }

    private void setJournalSizeLimit() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            long newValue = (long) SQLiteGlobal.getJournalSizeLimit();
            if (executeForLong("PRAGMA journal_size_limit", null, null) != newValue) {
                executeForLong("PRAGMA journal_size_limit=" + newValue, null, null);
            }
        }
    }

    private void setForeignKeyModeFromConfiguration() {
        if (!this.mIsReadOnlyConnection) {
            long newValue = this.mConfiguration.foreignKeyConstraintsEnabled ? 1 : 0;
            if (executeForLong("PRAGMA foreign_keys", null, null) != newValue) {
                execute("PRAGMA foreign_keys=" + newValue, null, null);
            }
        }
    }

    private void setWalModeFromConfiguration() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            if ((this.mConfiguration.openFlags & 536870912) != 0) {
                setJournalMode("WAL");
                setSyncMode(SQLiteGlobal.getWALSyncMode());
                return;
            }
            setJournalMode(SQLiteGlobal.getDefaultJournalMode());
            setSyncMode(SQLiteGlobal.getDefaultSyncMode());
        }
    }

    private void setSyncMode(String newValue) {
        if (!canonicalizeSyncMode(executeForString("PRAGMA synchronous", null, null)).equalsIgnoreCase(canonicalizeSyncMode(newValue))) {
            execute("PRAGMA synchronous=" + newValue, null, null);
        }
    }

    private static String canonicalizeSyncMode(String value) {
        if (value.equals("0")) {
            return "OFF";
        }
        if (value.equals("1")) {
            return H5ThreadType.NORMAL;
        }
        if (value.equals("2")) {
            return "FULL";
        }
        return value;
    }

    @TargetApi(11)
    private void setJournalMode(String newValue) {
        String value = executeForString("PRAGMA journal_mode", null, null);
        if (!value.equalsIgnoreCase(newValue)) {
            try {
                if (executeForString("PRAGMA journal_mode=" + newValue, null, null).equalsIgnoreCase(newValue)) {
                    return;
                }
            } catch (SQLiteDatabaseLockedException e) {
            }
            Log.w(TAG, "Could not change the database journal mode of '" + this.mConfiguration.label + "' from '" + value + "' to '" + newValue + "' because the database is locked.  This usually means that there are other open connections to the database which prevents the database from enabling or disabling write-ahead logging mode.  Proceeding without changing the journal mode.");
        }
    }

    private void setLocaleFromConfiguration() {
        if ((this.mConfiguration.openFlags & 16) == 0) {
            String newLocale = this.mConfiguration.locale.toString();
            nativeRegisterLocalizedCollators(this.mConnectionPtr, newLocale);
            if (!this.mIsReadOnlyConnection) {
                try {
                    execute("CREATE TABLE IF NOT EXISTS android_metadata (locale TEXT)", null, null);
                } catch (RuntimeException e) {
                    throw new SQLiteException("Failed to change locale for db '" + this.mConfiguration.label + "' to '" + newLocale + "'.");
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void reconfigure(SQLiteDatabaseConfiguration configuration) {
        boolean foreignKeyModeChanged;
        boolean walModeChanged;
        boolean localeChanged;
        this.mOnlyAllowReadOnlyOperations = false;
        int functionCount = configuration.customFunctions.size();
        for (int i = 0; i < functionCount; i++) {
            SQLiteCustomFunction function = configuration.customFunctions.get(i);
            if (!this.mConfiguration.customFunctions.contains(function)) {
                nativeRegisterCustomFunction(this.mConnectionPtr, function);
            }
        }
        if (configuration.foreignKeyConstraintsEnabled != this.mConfiguration.foreignKeyConstraintsEnabled) {
            foreignKeyModeChanged = true;
        } else {
            foreignKeyModeChanged = false;
        }
        if (((configuration.openFlags ^ this.mConfiguration.openFlags) & 536870912) != 0) {
            walModeChanged = true;
        } else {
            walModeChanged = false;
        }
        if (!configuration.locale.equals(this.mConfiguration.locale)) {
            localeChanged = true;
        } else {
            localeChanged = false;
        }
        this.mConfiguration.updateParametersFrom(configuration);
        this.mPreparedStatementCache.resize(configuration.maxSqlCacheSize);
        if (foreignKeyModeChanged) {
            setForeignKeyModeFromConfiguration();
        }
        if (walModeChanged) {
            setWalModeFromConfiguration();
        }
        if (localeChanged) {
            setLocaleFromConfiguration();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void setOnlyAllowReadOnlyOperations(boolean readOnly) {
        this.mOnlyAllowReadOnlyOperations = readOnly;
    }

    /* access modifiers changed from: 0000 */
    @TargetApi(12)
    public final boolean isPreparedStatementInCache(String sql) {
        return this.mPreparedStatementCache.get(sql) != null;
    }

    public final int getConnectionId() {
        return this.mConnectionId;
    }

    public final boolean isPrimaryConnection() {
        return this.mIsPrimaryConnection;
    }

    public final void prepare(String sql, SQLiteStatementInfo outStatementInfo) {
        PreparedStatement statement;
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int cookie = this.mRecentOperations.beginOperation("prepare", sql, null);
        try {
            statement = acquirePreparedStatement(sql);
            if (outStatementInfo != null) {
                outStatementInfo.numParameters = statement.mNumParameters;
                outStatementInfo.readOnly = statement.mReadOnly;
                int columnCount = nativeGetColumnCount(this.mConnectionPtr, statement.mStatementPtr);
                if (columnCount == 0) {
                    outStatementInfo.columnNames = EMPTY_STRING_ARRAY;
                } else {
                    outStatementInfo.columnNames = new String[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        outStatementInfo.columnNames[i] = nativeGetColumnName(this.mConnectionPtr, statement.mStatementPtr, i);
                    }
                }
            }
            releasePreparedStatement(statement);
            this.mRecentOperations.endOperation(cookie);
        } catch (RuntimeException ex) {
            try {
                this.mRecentOperations.failOperation(cookie, ex);
                throw ex;
            } catch (Throwable th) {
                this.mRecentOperations.endOperation(cookie);
                throw th;
            }
        } catch (Throwable th2) {
            releasePreparedStatement(statement);
            throw th2;
        }
    }

    public final void execute(String sql, Object[] bindArgs, Object cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int cookie = this.mRecentOperations.beginOperation("execute", sql, bindArgs);
        try {
            PreparedStatement statement = acquirePreparedStatement(sql);
            try {
                throwIfStatementForbidden(statement);
                bindArguments(statement, bindArgs);
                applyBlockGuardPolicy(statement);
                nativeExecute(this.mConnectionPtr, statement.mStatementPtr);
                this.mRecentOperations.endOperation(cookie);
            } finally {
                releasePreparedStatement(statement);
            }
        } catch (RuntimeException ex) {
            try {
                this.mRecentOperations.failOperation(cookie, ex);
                throw ex;
            } catch (Throwable th) {
                this.mRecentOperations.endOperation(cookie);
                throw th;
            }
        }
    }

    public final long executeForLong(String sql, Object[] bindArgs, Object cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int cookie = this.mRecentOperations.beginOperation("executeForLong", sql, bindArgs);
        try {
            PreparedStatement statement = acquirePreparedStatement(sql);
            try {
                throwIfStatementForbidden(statement);
                bindArguments(statement, bindArgs);
                applyBlockGuardPolicy(statement);
                long nativeExecuteForLong = nativeExecuteForLong(this.mConnectionPtr, statement.mStatementPtr);
                this.mRecentOperations.endOperation(cookie);
                return nativeExecuteForLong;
            } finally {
                releasePreparedStatement(statement);
            }
        } catch (RuntimeException ex) {
            try {
                this.mRecentOperations.failOperation(cookie, ex);
                throw ex;
            } catch (Throwable th) {
                this.mRecentOperations.endOperation(cookie);
                throw th;
            }
        }
    }

    public final String executeForString(String sql, Object[] bindArgs, Object cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int cookie = this.mRecentOperations.beginOperation("executeForString", sql, bindArgs);
        try {
            PreparedStatement statement = acquirePreparedStatement(sql);
            try {
                throwIfStatementForbidden(statement);
                bindArguments(statement, bindArgs);
                applyBlockGuardPolicy(statement);
                String nativeExecuteForString = nativeExecuteForString(this.mConnectionPtr, statement.mStatementPtr);
                this.mRecentOperations.endOperation(cookie);
                return nativeExecuteForString;
            } finally {
                releasePreparedStatement(statement);
            }
        } catch (RuntimeException ex) {
            try {
                this.mRecentOperations.failOperation(cookie, ex);
                throw ex;
            } catch (Throwable th) {
                this.mRecentOperations.endOperation(cookie);
                throw th;
            }
        }
    }

    @TargetApi(13)
    public final ParcelFileDescriptor executeForBlobFileDescriptor(String sql, Object[] bindArgs, Object cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int cookie = this.mRecentOperations.beginOperation("executeForBlobFileDescriptor", sql, bindArgs);
        try {
            PreparedStatement statement = acquirePreparedStatement(sql);
            try {
                throwIfStatementForbidden(statement);
                bindArguments(statement, bindArgs);
                applyBlockGuardPolicy(statement);
                int fd = nativeExecuteForBlobFileDescriptor(this.mConnectionPtr, statement.mStatementPtr);
                ParcelFileDescriptor parcelFileDescriptor = fd >= 0 ? ParcelFileDescriptor.adoptFd(fd) : null;
                this.mRecentOperations.endOperation(cookie);
                return parcelFileDescriptor;
            } finally {
                releasePreparedStatement(statement);
            }
        } catch (RuntimeException ex) {
            try {
                this.mRecentOperations.failOperation(cookie, ex);
                throw ex;
            } catch (Throwable th) {
                this.mRecentOperations.endOperation(cookie);
                throw th;
            }
        }
    }

    public final int executeForChangedRowCount(String sql, Object[] bindArgs, Object cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int changedRows = 0;
        int cookie = this.mRecentOperations.beginOperation("executeForChangedRowCount", sql, bindArgs);
        try {
            PreparedStatement statement = acquirePreparedStatement(sql);
            try {
                throwIfStatementForbidden(statement);
                bindArguments(statement, bindArgs);
                applyBlockGuardPolicy(statement);
                int changedRows2 = nativeExecuteForChangedRowCount(this.mConnectionPtr, statement.mStatementPtr);
                if (this.mRecentOperations.endOperationDeferLog(cookie)) {
                    this.mRecentOperations.logOperation(cookie, "changedRows=" + changedRows2);
                }
                return changedRows2;
            } finally {
                releasePreparedStatement(statement);
            }
        } catch (RuntimeException ex) {
            try {
                this.mRecentOperations.failOperation(cookie, ex);
                throw ex;
            } catch (Throwable th) {
                if (this.mRecentOperations.endOperationDeferLog(cookie)) {
                    this.mRecentOperations.logOperation(cookie, "changedRows=" + changedRows);
                }
                throw th;
            }
        }
    }

    public final long executeForLastInsertedRowId(String sql, Object[] bindArgs, Object cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        int cookie = this.mRecentOperations.beginOperation("executeForLastInsertedRowId", sql, bindArgs);
        try {
            PreparedStatement statement = acquirePreparedStatement(sql);
            try {
                throwIfStatementForbidden(statement);
                bindArguments(statement, bindArgs);
                applyBlockGuardPolicy(statement);
                long nativeExecuteForLastInsertedRowId = nativeExecuteForLastInsertedRowId(this.mConnectionPtr, statement.mStatementPtr);
                this.mRecentOperations.endOperation(cookie);
                return nativeExecuteForLastInsertedRowId;
            } finally {
                releasePreparedStatement(statement);
            }
        } catch (RuntimeException ex) {
            try {
                this.mRecentOperations.failOperation(cookie, ex);
                throw ex;
            } catch (Throwable th) {
                this.mRecentOperations.endOperation(cookie);
                throw th;
            }
        }
    }

    public final int executeForCursorWindow(String sql, Object[] bindArgs, CursorWindow window, int startPos, int requiredPos, boolean countAllRows, Object cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        } else if (window == null) {
            throw new IllegalArgumentException("window must not be null.");
        } else {
            window.acquireReference();
            int actualPos = -1;
            int countedRows = -1;
            int filledRows = -1;
            try {
                int cookie = this.mRecentOperations.beginOperation("executeForCursorWindow", sql, bindArgs);
                try {
                    PreparedStatement statement = acquirePreparedStatement(sql);
                    try {
                        throwIfStatementForbidden(statement);
                        bindArguments(statement, bindArgs);
                        applyBlockGuardPolicy(statement);
                        long result = nativeExecuteForCursorWindow(this.mConnectionPtr, statement.mStatementPtr, window.mWindowPtr, startPos, requiredPos, countAllRows);
                        actualPos = (int) (result >> 32);
                        countedRows = (int) result;
                        filledRows = window.getNumRows();
                        window.setStartPosition(actualPos);
                        releasePreparedStatement(statement);
                        if (this.mRecentOperations.endOperationDeferLog(cookie)) {
                            this.mRecentOperations.logOperation(cookie, "window='" + window + "', startPos=" + startPos + ", actualPos=" + actualPos + ", filledRows=" + filledRows + ", countedRows=" + countedRows);
                        }
                        return countedRows;
                    } finally {
                        releasePreparedStatement(statement);
                    }
                } catch (RuntimeException ex) {
                    try {
                        this.mRecentOperations.failOperation(cookie, ex);
                        throw ex;
                    } catch (Throwable th) {
                        if (this.mRecentOperations.endOperationDeferLog(cookie)) {
                            this.mRecentOperations.logOperation(cookie, "window='" + window + "', startPos=" + startPos + ", actualPos=" + actualPos + ", filledRows=" + filledRows + ", countedRows=" + countedRows);
                        }
                        throw th;
                    }
                }
            } finally {
                window.releaseReference();
            }
        }
    }

    @TargetApi(12)
    private PreparedStatement acquirePreparedStatement(String sql) {
        PreparedStatement statement = (PreparedStatement) this.mPreparedStatementCache.get(sql);
        boolean skipCache = false;
        if (statement != null) {
            if (!statement.mInUse) {
                return statement;
            }
            skipCache = true;
        }
        int statementPtr = nativePrepareStatement(this.mConnectionPtr, sql);
        try {
            int numParameters = nativeGetParameterCount(this.mConnectionPtr, statementPtr);
            int type = DatabaseUtils.getSqlStatementType(sql);
            PreparedStatement statement2 = obtainPreparedStatement(sql, statementPtr, numParameters, type, nativeIsReadOnly(this.mConnectionPtr, statementPtr));
            if (!skipCache && isCacheable(type)) {
                this.mPreparedStatementCache.put(sql, statement2);
                statement2.mInCache = true;
            }
            statement2.mInUse = true;
            return statement2;
        } catch (RuntimeException ex) {
            if (statement == null || !statement.mInCache) {
                nativeFinalizeStatement(this.mConnectionPtr, statementPtr);
            }
            throw ex;
        }
    }

    @TargetApi(12)
    private void releasePreparedStatement(PreparedStatement statement) {
        statement.mInUse = false;
        if (statement.mInCache) {
            try {
                nativeResetStatementAndClearBindings(this.mConnectionPtr, statement.mStatementPtr);
            } catch (SQLiteException e) {
                this.mPreparedStatementCache.remove(statement.mSql);
            }
        } else {
            finalizePreparedStatement(statement);
        }
    }

    /* access modifiers changed from: private */
    public void finalizePreparedStatement(PreparedStatement statement) {
        nativeFinalizeStatement(this.mConnectionPtr, statement.mStatementPtr);
        recyclePreparedStatement(statement);
    }

    @TargetApi(11)
    private void bindArguments(PreparedStatement statement, Object[] bindArgs) {
        long j;
        int count = bindArgs != null ? bindArgs.length : 0;
        if (count != statement.mNumParameters) {
            throw new SQLiteBindOrColumnIndexOutOfRangeException("Expected " + statement.mNumParameters + " bind arguments but " + count + " were provided.");
        } else if (count != 0) {
            int statementPtr = statement.mStatementPtr;
            for (int i = 0; i < count; i++) {
                Object arg = bindArgs[i];
                switch (DatabaseUtils.getTypeOfObject(arg)) {
                    case 0:
                        nativeBindNull(this.mConnectionPtr, statementPtr, i + 1);
                        break;
                    case 1:
                        nativeBindLong(this.mConnectionPtr, statementPtr, i + 1, ((Number) arg).longValue());
                        break;
                    case 2:
                        nativeBindDouble(this.mConnectionPtr, statementPtr, i + 1, ((Number) arg).doubleValue());
                        break;
                    case 4:
                        nativeBindBlob(this.mConnectionPtr, statementPtr, i + 1, (byte[]) arg);
                        break;
                    default:
                        if (!(arg instanceof Boolean)) {
                            nativeBindString(this.mConnectionPtr, statementPtr, i + 1, arg.toString());
                            break;
                        } else {
                            int i2 = this.mConnectionPtr;
                            int i3 = i + 1;
                            if (((Boolean) arg).booleanValue()) {
                                j = 1;
                            } else {
                                j = 0;
                            }
                            nativeBindLong(i2, statementPtr, i3, j);
                            break;
                        }
                }
            }
        }
    }

    private void throwIfStatementForbidden(PreparedStatement statement) {
        if (this.mOnlyAllowReadOnlyOperations && !statement.mReadOnly) {
            throw new SQLiteException("Cannot execute this statement because it might modify the database but the connection is read-only.");
        }
    }

    private static boolean isCacheable(int statementType) {
        if (statementType == 2 || statementType == 1) {
            return true;
        }
        return false;
    }

    private void applyBlockGuardPolicy(PreparedStatement statement) {
    }

    public final void dump(Printer printer, boolean verbose) {
        dumpUnsafe(printer, verbose);
    }

    /* access modifiers changed from: 0000 */
    public final void dumpUnsafe(Printer printer, boolean verbose) {
        printer.println("Connection #" + this.mConnectionId + ":");
        if (verbose) {
            printer.println("  connectionPtr: 0x" + Integer.toHexString(this.mConnectionPtr));
        }
        printer.println("  isPrimaryConnection: " + this.mIsPrimaryConnection);
        printer.println("  onlyAllowReadOnlyOperations: " + this.mOnlyAllowReadOnlyOperations);
        this.mRecentOperations.dump(printer, verbose);
        if (verbose) {
            this.mPreparedStatementCache.dump(printer);
        }
    }

    /* access modifiers changed from: 0000 */
    public final String describeCurrentOperationUnsafe() {
        return this.mRecentOperations.describeCurrentOperation();
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00e9, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00ea, code lost:
        r10.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00ed, code lost:
        throw r1;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00e9 A[ExcHandler:  FINALLY, Splitter:B:4:0x003b] */
    @android.annotation.TargetApi(9)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void collectDbStats(java.util.ArrayList<com.alibaba.sqlcrypto.sqlite.SQLiteDebug.DbStats> r25) {
        /*
            r24 = this;
            r0 = r24
            int r1 = r0.mConnectionPtr
            int r2 = nativeGetDbLookaside(r1)
            r3 = 0
            r5 = 0
            java.lang.String r1 = "PRAGMA page_count;"
            r7 = 0
            r8 = 0
            r0 = r24
            long r3 = r0.executeForLong(r1, r7, r8)     // Catch:{ SQLiteException -> 0x00f0 }
            java.lang.String r1 = "PRAGMA page_size;"
            r7 = 0
            r8 = 0
            r0 = r24
            long r5 = r0.executeForLong(r1, r7, r8)     // Catch:{ SQLiteException -> 0x00f0 }
        L_0x0020:
            r1 = r24
            com.alibaba.sqlcrypto.sqlite.SQLiteDebug$DbStats r1 = r1.getMainDbStatsUnsafe(r2, r3, r5)
            r0 = r25
            r0.add(r1)
            com.alibaba.sqlcrypto.CursorWindow r10 = new com.alibaba.sqlcrypto.CursorWindow
            java.lang.String r1 = "collectDbStats"
            r10.<init>(r1)
            java.lang.String r8 = "PRAGMA database_list;"
            r9 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r7 = r24
            r7.executeForCursorWindow(r8, r9, r10, r11, r12, r13, r14)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e9 }
            r21 = 1
        L_0x0040:
            int r1 = r10.getNumRows()     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e9 }
            r0 = r21
            if (r0 >= r1) goto L_0x00e0
            r1 = 1
            r0 = r21
            java.lang.String r22 = r10.getString(r0, r1)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e9 }
            r1 = 2
            r0 = r21
            java.lang.String r23 = r10.getString(r0, r1)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e9 }
            r3 = 0
            r5 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x00ee, all -> 0x00e9 }
            java.lang.String r7 = "PRAGMA "
            r1.<init>(r7)     // Catch:{ SQLiteException -> 0x00ee, all -> 0x00e9 }
            r0 = r22
            java.lang.StringBuilder r1 = r1.append(r0)     // Catch:{ SQLiteException -> 0x00ee, all -> 0x00e9 }
            java.lang.String r7 = ".page_count;"
            java.lang.StringBuilder r1 = r1.append(r7)     // Catch:{ SQLiteException -> 0x00ee, all -> 0x00e9 }
            java.lang.String r1 = r1.toString()     // Catch:{ SQLiteException -> 0x00ee, all -> 0x00e9 }
            r7 = 0
            r8 = 0
            r0 = r24
            long r3 = r0.executeForLong(r1, r7, r8)     // Catch:{ SQLiteException -> 0x00ee, all -> 0x00e9 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x00ee, all -> 0x00e9 }
            java.lang.String r7 = "PRAGMA "
            r1.<init>(r7)     // Catch:{ SQLiteException -> 0x00ee, all -> 0x00e9 }
            r0 = r22
            java.lang.StringBuilder r1 = r1.append(r0)     // Catch:{ SQLiteException -> 0x00ee, all -> 0x00e9 }
            java.lang.String r7 = ".page_size;"
            java.lang.StringBuilder r1 = r1.append(r7)     // Catch:{ SQLiteException -> 0x00ee, all -> 0x00e9 }
            java.lang.String r1 = r1.toString()     // Catch:{ SQLiteException -> 0x00ee, all -> 0x00e9 }
            r7 = 0
            r8 = 0
            r0 = r24
            long r5 = r0.executeForLong(r1, r7, r8)     // Catch:{ SQLiteException -> 0x00ee, all -> 0x00e9 }
        L_0x0098:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e9 }
            java.lang.String r7 = "  (attached) "
            r1.<init>(r7)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e9 }
            r0 = r22
            java.lang.StringBuilder r1 = r1.append(r0)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e9 }
            java.lang.String r12 = r1.toString()     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e9 }
            boolean r1 = r23.isEmpty()     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e9 }
            if (r1 != 0) goto L_0x00c8
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e9 }
            r1.<init>()     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e9 }
            java.lang.StringBuilder r1 = r1.append(r12)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e9 }
            java.lang.String r7 = ": "
            java.lang.StringBuilder r1 = r1.append(r7)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e9 }
            r0 = r23
            java.lang.StringBuilder r1 = r1.append(r0)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e9 }
            java.lang.String r12 = r1.toString()     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e9 }
        L_0x00c8:
            com.alibaba.sqlcrypto.sqlite.SQLiteDebug$DbStats r11 = new com.alibaba.sqlcrypto.sqlite.SQLiteDebug$DbStats     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e9 }
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            r13 = r3
            r15 = r5
            r11.<init>(r12, r13, r15, r17, r18, r19, r20)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e9 }
            r0 = r25
            r0.add(r11)     // Catch:{ SQLiteException -> 0x00e4, all -> 0x00e9 }
            int r21 = r21 + 1
            goto L_0x0040
        L_0x00e0:
            r10.close()
        L_0x00e3:
            return
        L_0x00e4:
            r1 = move-exception
            r10.close()
            goto L_0x00e3
        L_0x00e9:
            r1 = move-exception
            r10.close()
            throw r1
        L_0x00ee:
            r1 = move-exception
            goto L_0x0098
        L_0x00f0:
            r1 = move-exception
            goto L_0x0020
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sqlcrypto.sqlite.SQLiteConnection.collectDbStats(java.util.ArrayList):void");
    }

    /* access modifiers changed from: 0000 */
    public final void collectDbStatsUnsafe(ArrayList<DbStats> dbStatsList) {
        dbStatsList.add(getMainDbStatsUnsafe(0, 0, 0));
    }

    @TargetApi(12)
    private DbStats getMainDbStatsUnsafe(int lookaside, long pageCount, long pageSize) {
        String label = this.mConfiguration.path;
        if (!this.mIsPrimaryConnection) {
            label = label + " (" + this.mConnectionId + ")";
        }
        return new DbStats(label, pageCount, pageSize, lookaside, this.mPreparedStatementCache.hitCount(), this.mPreparedStatementCache.missCount(), this.mPreparedStatementCache.size());
    }

    public final String toString() {
        return "SQLiteConnection: " + this.mConfiguration.path + " (" + this.mConnectionId + ")";
    }

    private PreparedStatement obtainPreparedStatement(String sql, int statementPtr, int numParameters, int type, boolean readOnly) {
        PreparedStatement statement = this.mPreparedStatementPool;
        if (statement != null) {
            this.mPreparedStatementPool = statement.mPoolNext;
            statement.mPoolNext = null;
            statement.mInCache = false;
        } else {
            statement = new PreparedStatement();
        }
        statement.mSql = sql;
        statement.mStatementPtr = statementPtr;
        statement.mNumParameters = numParameters;
        statement.mType = type;
        statement.mReadOnly = readOnly;
        return statement;
    }

    private void recyclePreparedStatement(PreparedStatement statement) {
        statement.mSql = null;
        statement.mPoolNext = this.mPreparedStatementPool;
        this.mPreparedStatementPool = statement;
    }

    /* access modifiers changed from: private */
    public static String trimSqlForDisplay(String sql) {
        return TRIM_SQL_PATTERN.matcher(sql).replaceAll(Token.SEPARATOR);
    }
}
