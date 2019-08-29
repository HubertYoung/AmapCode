package com.alibaba.sqlcrypto.sqlite;

import android.annotation.TargetApi;
import android.os.ParcelFileDescriptor;
import com.alibaba.sqlcrypto.CursorWindow;
import com.alibaba.sqlcrypto.DatabaseUtils;

public final class SQLiteSession {
    static final /* synthetic */ boolean $assertionsDisabled = (!SQLiteSession.class.desiredAssertionStatus());
    public static final int TRANSACTION_MODE_DEFERRED = 0;
    public static final int TRANSACTION_MODE_EXCLUSIVE = 2;
    public static final int TRANSACTION_MODE_IMMEDIATE = 1;
    private SQLiteConnection mConnection;
    private int mConnectionFlags;
    private final SQLiteConnectionPool mConnectionPool;
    private int mConnectionUseCount;
    private Transaction mTransactionPool;
    private Transaction mTransactionStack;

    final class Transaction {
        public boolean mChildFailed;
        public SQLiteTransactionListener mListener;
        public boolean mMarkedSuccessful;
        public int mMode;
        public Transaction mParent;

        private Transaction() {
        }
    }

    public SQLiteSession(SQLiteConnectionPool connectionPool) {
        if (connectionPool == null) {
            throw new IllegalArgumentException("connectionPool must not be null");
        }
        this.mConnectionPool = connectionPool;
    }

    public final boolean hasTransaction() {
        return this.mTransactionStack != null;
    }

    public final boolean hasNestedTransaction() {
        return (this.mTransactionStack == null || this.mTransactionStack.mParent == null) ? false : true;
    }

    public final boolean hasConnection() {
        return this.mConnection != null;
    }

    public final void beginTransaction(int transactionMode, SQLiteTransactionListener transactionListener, int connectionFlags, Object cancellationSignal) {
        throwIfTransactionMarkedSuccessful();
        beginTransactionUnchecked(transactionMode, transactionListener, connectionFlags, cancellationSignal);
    }

    @android.annotation.TargetApi(5)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void beginTransactionUnchecked(int r6, com.alibaba.sqlcrypto.sqlite.SQLiteTransactionListener r7, int r8, java.lang.Object r9) {
        /*
            r5 = this;
            r3 = 0
            com.alibaba.sqlcrypto.sqlite.SQLiteSession$Transaction r2 = r5.mTransactionStack
            if (r2 != 0) goto L_0x0008
            r5.acquireConnection(r3, r8, r9)
        L_0x0008:
            com.alibaba.sqlcrypto.sqlite.SQLiteSession$Transaction r2 = r5.mTransactionStack     // Catch:{ all -> 0x0037 }
            if (r2 != 0) goto L_0x0017
            switch(r6) {
                case 1: goto L_0x002e;
                case 2: goto L_0x0040;
                default: goto L_0x000f;
            }     // Catch:{ all -> 0x0037 }
        L_0x000f:
            com.alibaba.sqlcrypto.sqlite.SQLiteConnection r2 = r5.mConnection     // Catch:{ all -> 0x0037 }
            java.lang.String r3 = "BEGIN;"
            r4 = 0
            r2.execute(r3, r4, r9)     // Catch:{ all -> 0x0037 }
        L_0x0017:
            if (r7 == 0) goto L_0x001c
            r7.onBegin()     // Catch:{ RuntimeException -> 0x0049 }
        L_0x001c:
            com.alibaba.sqlcrypto.sqlite.SQLiteSession$Transaction r1 = r5.obtainTransaction(r6, r7)     // Catch:{ all -> 0x0037 }
            com.alibaba.sqlcrypto.sqlite.SQLiteSession$Transaction r2 = r5.mTransactionStack     // Catch:{ all -> 0x0037 }
            r1.mParent = r2     // Catch:{ all -> 0x0037 }
            r5.mTransactionStack = r1     // Catch:{ all -> 0x0037 }
            com.alibaba.sqlcrypto.sqlite.SQLiteSession$Transaction r2 = r5.mTransactionStack
            if (r2 != 0) goto L_0x002d
            r5.releaseConnection()
        L_0x002d:
            return
        L_0x002e:
            com.alibaba.sqlcrypto.sqlite.SQLiteConnection r2 = r5.mConnection     // Catch:{ all -> 0x0037 }
            java.lang.String r3 = "BEGIN IMMEDIATE;"
            r4 = 0
            r2.execute(r3, r4, r9)     // Catch:{ all -> 0x0037 }
            goto L_0x0017
        L_0x0037:
            r2 = move-exception
            com.alibaba.sqlcrypto.sqlite.SQLiteSession$Transaction r3 = r5.mTransactionStack
            if (r3 != 0) goto L_0x003f
            r5.releaseConnection()
        L_0x003f:
            throw r2
        L_0x0040:
            com.alibaba.sqlcrypto.sqlite.SQLiteConnection r2 = r5.mConnection     // Catch:{ all -> 0x0037 }
            java.lang.String r3 = "BEGIN EXCLUSIVE;"
            r4 = 0
            r2.execute(r3, r4, r9)     // Catch:{ all -> 0x0037 }
            goto L_0x0017
        L_0x0049:
            r0 = move-exception
            com.alibaba.sqlcrypto.sqlite.SQLiteSession$Transaction r2 = r5.mTransactionStack     // Catch:{ all -> 0x0037 }
            if (r2 != 0) goto L_0x0056
            com.alibaba.sqlcrypto.sqlite.SQLiteConnection r2 = r5.mConnection     // Catch:{ all -> 0x0037 }
            java.lang.String r3 = "ROLLBACK;"
            r4 = 0
            r2.execute(r3, r4, r9)     // Catch:{ all -> 0x0037 }
        L_0x0056:
            throw r0     // Catch:{ all -> 0x0037 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sqlcrypto.sqlite.SQLiteSession.beginTransactionUnchecked(int, com.alibaba.sqlcrypto.sqlite.SQLiteTransactionListener, int, java.lang.Object):void");
    }

    public final void setTransactionSuccessful() {
        throwIfNoTransaction();
        throwIfTransactionMarkedSuccessful();
        this.mTransactionStack.mMarkedSuccessful = true;
    }

    public final void endTransaction(Object cancellationSignal) {
        throwIfNoTransaction();
        if ($assertionsDisabled || this.mConnection != null) {
            endTransactionUnchecked(cancellationSignal, false);
            return;
        }
        throw new AssertionError();
    }

    @TargetApi(5)
    private void endTransactionUnchecked(Object cancellationSignal, boolean yielding) {
        Transaction top = this.mTransactionStack;
        boolean successful = (top.mMarkedSuccessful || yielding) && !top.mChildFailed;
        RuntimeException listenerException = null;
        SQLiteTransactionListener listener = top.mListener;
        if (listener != null) {
            if (successful) {
                try {
                    listener.onCommit();
                } catch (RuntimeException e) {
                    listenerException = e;
                    successful = false;
                }
            } else {
                listener.onRollback();
            }
        }
        this.mTransactionStack = top.mParent;
        recycleTransaction(top);
        if (this.mTransactionStack == null) {
            if (successful) {
                try {
                    this.mConnection.execute("COMMIT;", null, cancellationSignal);
                } catch (Throwable th) {
                    releaseConnection();
                    throw th;
                }
            } else {
                this.mConnection.execute("ROLLBACK;", null, cancellationSignal);
            }
            releaseConnection();
        } else if (!successful) {
            this.mTransactionStack.mChildFailed = true;
        }
        if (listenerException != null) {
            throw listenerException;
        }
    }

    public final boolean yieldTransaction(long sleepAfterYieldDelayMillis, boolean throwIfUnsafe, Object cancellationSignal) {
        if (throwIfUnsafe) {
            throwIfNoTransaction();
            throwIfTransactionMarkedSuccessful();
            throwIfNestedTransaction();
        } else if (this.mTransactionStack == null || this.mTransactionStack.mMarkedSuccessful || this.mTransactionStack.mParent != null) {
            return false;
        }
        if (!$assertionsDisabled && this.mConnection == null) {
            throw new AssertionError();
        } else if (!this.mTransactionStack.mChildFailed) {
            return yieldTransactionUnchecked(sleepAfterYieldDelayMillis, cancellationSignal);
        } else {
            return false;
        }
    }

    private boolean yieldTransactionUnchecked(long sleepAfterYieldDelayMillis, Object cancellationSignal) {
        if (!this.mConnectionPool.shouldYieldConnection(this.mConnection, this.mConnectionFlags)) {
            return false;
        }
        int transactionMode = this.mTransactionStack.mMode;
        SQLiteTransactionListener listener = this.mTransactionStack.mListener;
        int connectionFlags = this.mConnectionFlags;
        endTransactionUnchecked(cancellationSignal, true);
        if (sleepAfterYieldDelayMillis > 0) {
            try {
                Thread.sleep(sleepAfterYieldDelayMillis);
            } catch (InterruptedException e) {
            }
        }
        beginTransactionUnchecked(transactionMode, listener, connectionFlags, cancellationSignal);
        return true;
    }

    public final void prepare(String sql, int connectionFlags, Object cancellationSignal, SQLiteStatementInfo outStatementInfo) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        }
        acquireConnection(sql, connectionFlags, cancellationSignal);
        try {
            this.mConnection.prepare(sql, outStatementInfo);
        } finally {
            releaseConnection();
        }
    }

    public final void execute(String sql, Object[] bindArgs, int connectionFlags, Object cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        } else if (!executeSpecial(sql, bindArgs, connectionFlags, cancellationSignal)) {
            acquireConnection(sql, connectionFlags, cancellationSignal);
            try {
                this.mConnection.execute(sql, bindArgs, cancellationSignal);
            } finally {
                releaseConnection();
            }
        }
    }

    public final long executeForLong(String sql, Object[] bindArgs, int connectionFlags, Object cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        } else if (executeSpecial(sql, bindArgs, connectionFlags, cancellationSignal)) {
            return 0;
        } else {
            acquireConnection(sql, connectionFlags, cancellationSignal);
            try {
                return this.mConnection.executeForLong(sql, bindArgs, cancellationSignal);
            } finally {
                releaseConnection();
            }
        }
    }

    public final String executeForString(String sql, Object[] bindArgs, int connectionFlags, Object cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        } else if (executeSpecial(sql, bindArgs, connectionFlags, cancellationSignal)) {
            return null;
        } else {
            acquireConnection(sql, connectionFlags, cancellationSignal);
            try {
                return this.mConnection.executeForString(sql, bindArgs, cancellationSignal);
            } finally {
                releaseConnection();
            }
        }
    }

    public final ParcelFileDescriptor executeForBlobFileDescriptor(String sql, Object[] bindArgs, int connectionFlags, Object cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        } else if (executeSpecial(sql, bindArgs, connectionFlags, cancellationSignal)) {
            return null;
        } else {
            acquireConnection(sql, connectionFlags, cancellationSignal);
            try {
                return this.mConnection.executeForBlobFileDescriptor(sql, bindArgs, cancellationSignal);
            } finally {
                releaseConnection();
            }
        }
    }

    public final int executeForChangedRowCount(String sql, Object[] bindArgs, int connectionFlags, Object cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        } else if (executeSpecial(sql, bindArgs, connectionFlags, cancellationSignal)) {
            return 0;
        } else {
            acquireConnection(sql, connectionFlags, cancellationSignal);
            try {
                return this.mConnection.executeForChangedRowCount(sql, bindArgs, cancellationSignal);
            } finally {
                releaseConnection();
            }
        }
    }

    public final long executeForLastInsertedRowId(String sql, Object[] bindArgs, int connectionFlags, Object cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        } else if (executeSpecial(sql, bindArgs, connectionFlags, cancellationSignal)) {
            return 0;
        } else {
            acquireConnection(sql, connectionFlags, cancellationSignal);
            try {
                return this.mConnection.executeForLastInsertedRowId(sql, bindArgs, cancellationSignal);
            } finally {
                releaseConnection();
            }
        }
    }

    public final int executeForCursorWindow(String sql, Object[] bindArgs, CursorWindow window, int startPos, int requiredPos, boolean countAllRows, int connectionFlags, Object cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        } else if (window == null) {
            throw new IllegalArgumentException("window must not be null.");
        } else if (executeSpecial(sql, bindArgs, connectionFlags, cancellationSignal)) {
            window.clear();
            return 0;
        } else {
            acquireConnection(sql, connectionFlags, cancellationSignal);
            try {
                return this.mConnection.executeForCursorWindow(sql, bindArgs, window, startPos, requiredPos, countAllRows, cancellationSignal);
            } finally {
                releaseConnection();
            }
        }
    }

    private boolean executeSpecial(String sql, Object[] bindArgs, int connectionFlags, Object cancellationSignal) {
        switch (DatabaseUtils.getSqlStatementType(sql)) {
            case 4:
                beginTransaction(2, null, connectionFlags, cancellationSignal);
                return true;
            case 5:
                setTransactionSuccessful();
                endTransaction(cancellationSignal);
                return true;
            case 6:
                endTransaction(cancellationSignal);
                return true;
            default:
                return false;
        }
    }

    private void acquireConnection(String sql, int connectionFlags, Object cancellationSignal) {
        if (this.mConnection == null) {
            if ($assertionsDisabled || this.mConnectionUseCount == 0) {
                this.mConnection = this.mConnectionPool.acquireConnection(sql, connectionFlags, cancellationSignal);
                this.mConnectionFlags = connectionFlags;
            } else {
                throw new AssertionError();
            }
        }
        this.mConnectionUseCount++;
    }

    private void releaseConnection() {
        if (!$assertionsDisabled && this.mConnection == null) {
            throw new AssertionError();
        } else if ($assertionsDisabled || this.mConnectionUseCount > 0) {
            int i = this.mConnectionUseCount - 1;
            this.mConnectionUseCount = i;
            if (i == 0) {
                try {
                    this.mConnectionPool.releaseConnection(this.mConnection);
                } finally {
                    this.mConnection = null;
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    private void throwIfNoTransaction() {
        if (this.mTransactionStack == null) {
            throw new IllegalStateException("Cannot perform this operation because there is no current transaction.");
        }
    }

    private void throwIfTransactionMarkedSuccessful() {
        if (this.mTransactionStack != null && this.mTransactionStack.mMarkedSuccessful) {
            throw new IllegalStateException("Cannot perform this operation because the transaction has already been marked successful.  The only thing you can do now is call endTransaction().");
        }
    }

    private void throwIfNestedTransaction() {
        if (hasNestedTransaction()) {
            throw new IllegalStateException("Cannot perform this operation because a nested transaction is in progress.");
        }
    }

    private Transaction obtainTransaction(int mode, SQLiteTransactionListener listener) {
        Transaction transaction = this.mTransactionPool;
        if (transaction != null) {
            this.mTransactionPool = transaction.mParent;
            transaction.mParent = null;
            transaction.mMarkedSuccessful = false;
            transaction.mChildFailed = false;
        } else {
            transaction = new Transaction();
        }
        transaction.mMode = mode;
        transaction.mListener = listener;
        return transaction;
    }

    private void recycleTransaction(Transaction transaction) {
        transaction.mParent = this.mTransactionPool;
        transaction.mListener = null;
        this.mTransactionPool = transaction;
    }
}
