package com.j256.ormlite.misc;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import java.sql.Savepoint;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionManager {
    private static final String SAVE_POINT_PREFIX = "ORMLITE";
    private static final Logger logger = LoggerFactory.getLogger(TransactionManager.class);
    private static AtomicInteger savePointCounter = new AtomicInteger();
    private ConnectionSource connectionSource;

    public TransactionManager() {
    }

    public TransactionManager(ConnectionSource connectionSource2) {
        this.connectionSource = connectionSource2;
        initialize();
    }

    public void initialize() {
        if (this.connectionSource == null) {
            throw new IllegalStateException("dataSource was not set on " + getClass().getSimpleName());
        }
    }

    public <T> T callInTransaction(Callable<T> callable) {
        return callInTransaction(this.connectionSource, callable);
    }

    public static <T> T callInTransaction(ConnectionSource connectionSource2, Callable<T> callable) {
        DatabaseConnection connection = connectionSource2.getReadWriteConnection();
        try {
            return callInTransaction(connection, connectionSource2.saveSpecialConnection(connection), connectionSource2.getDatabaseType(), callable);
        } finally {
            connectionSource2.clearSpecialConnection(connection);
            connectionSource2.releaseConnection(connection);
        }
    }

    public static <T> T callInTransaction(DatabaseConnection connection, DatabaseType databaseType, Callable<T> callable) {
        return callInTransaction(connection, false, databaseType, callable);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        if (r11.isNestedSavePointsSupported() != false) goto L_0x000c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> T callInTransaction(com.j256.ormlite.support.DatabaseConnection r9, boolean r10, com.j256.ormlite.db.DatabaseType r11, java.util.concurrent.Callable<T> r12) {
        /*
            r8 = 1
            r0 = 0
            r2 = 0
            r4 = 0
            if (r10 != 0) goto L_0x000c
            boolean r5 = r11.isNestedSavePointsSupported()     // Catch:{ all -> 0x0068 }
            if (r5 == 0) goto L_0x0046
        L_0x000c:
            boolean r5 = r9.isAutoCommitSupported()     // Catch:{ all -> 0x0068 }
            if (r5 == 0) goto L_0x0023
            boolean r0 = r9.isAutoCommit()     // Catch:{ all -> 0x0068 }
            if (r0 == 0) goto L_0x0023
            r5 = 0
            r9.setAutoCommit(r5)     // Catch:{ all -> 0x0068 }
            com.j256.ormlite.logger.Logger r5 = logger     // Catch:{ all -> 0x0068 }
            java.lang.String r6 = "had to set auto-commit to false"
            r5.debug(r6)     // Catch:{ all -> 0x0068 }
        L_0x0023:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0068 }
            java.lang.String r6 = "ORMLITE"
            r5.<init>(r6)     // Catch:{ all -> 0x0068 }
            java.util.concurrent.atomic.AtomicInteger r6 = savePointCounter     // Catch:{ all -> 0x0068 }
            int r6 = r6.incrementAndGet()     // Catch:{ all -> 0x0068 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0068 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0068 }
            java.sql.Savepoint r4 = r9.setSavePoint(r5)     // Catch:{ all -> 0x0068 }
            if (r4 != 0) goto L_0x005c
            com.j256.ormlite.logger.Logger r5 = logger     // Catch:{ all -> 0x0068 }
            java.lang.String r6 = "started savePoint transaction"
            r5.debug(r6)     // Catch:{ all -> 0x0068 }
        L_0x0045:
            r2 = 1
        L_0x0046:
            java.lang.Object r3 = r12.call()     // Catch:{ SQLException -> 0x0076, Exception -> 0x0086 }
            if (r2 == 0) goto L_0x004f
            commit(r9, r4)     // Catch:{ SQLException -> 0x0076, Exception -> 0x0086 }
        L_0x004f:
            if (r0 == 0) goto L_0x005b
            r9.setAutoCommit(r8)
            com.j256.ormlite.logger.Logger r5 = logger
            java.lang.String r6 = "restored auto-commit to true"
            r5.debug(r6)
        L_0x005b:
            return r3
        L_0x005c:
            com.j256.ormlite.logger.Logger r5 = logger     // Catch:{ all -> 0x0068 }
            java.lang.String r6 = "started savePoint transaction {}"
            java.lang.String r7 = r4.getSavepointName()     // Catch:{ all -> 0x0068 }
            r5.debug(r6, r7)     // Catch:{ all -> 0x0068 }
            goto L_0x0045
        L_0x0068:
            r5 = move-exception
            if (r0 == 0) goto L_0x0075
            r9.setAutoCommit(r8)
            com.j256.ormlite.logger.Logger r6 = logger
            java.lang.String r7 = "restored auto-commit to true"
            r6.debug(r7)
        L_0x0075:
            throw r5
        L_0x0076:
            r1 = move-exception
            if (r2 == 0) goto L_0x007c
            rollBack(r9, r4)     // Catch:{ SQLException -> 0x007d }
        L_0x007c:
            throw r1     // Catch:{ all -> 0x0068 }
        L_0x007d:
            r5 = move-exception
            com.j256.ormlite.logger.Logger r5 = logger     // Catch:{ all -> 0x0068 }
            java.lang.String r6 = "after commit exception, rolling back to save-point also threw exception"
            r5.error(r1, r6)     // Catch:{ all -> 0x0068 }
            goto L_0x007c
        L_0x0086:
            r1 = move-exception
            if (r2 == 0) goto L_0x008c
            rollBack(r9, r4)     // Catch:{ SQLException -> 0x0093 }
        L_0x008c:
            java.lang.String r5 = "Transaction callable threw non-SQL exception"
            java.sql.SQLException r5 = com.j256.ormlite.misc.SqlExceptionUtil.create(r5, r1)     // Catch:{ all -> 0x0068 }
            throw r5     // Catch:{ all -> 0x0068 }
        L_0x0093:
            r5 = move-exception
            com.j256.ormlite.logger.Logger r5 = logger     // Catch:{ all -> 0x0068 }
            java.lang.String r6 = "after commit exception, rolling back to save-point also threw exception"
            r5.error(r1, r6)     // Catch:{ all -> 0x0068 }
            goto L_0x008c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.j256.ormlite.misc.TransactionManager.callInTransaction(com.j256.ormlite.support.DatabaseConnection, boolean, com.j256.ormlite.db.DatabaseType, java.util.concurrent.Callable):java.lang.Object");
    }

    public void setConnectionSource(ConnectionSource connectionSource2) {
        this.connectionSource = connectionSource2;
    }

    private static void commit(DatabaseConnection connection, Savepoint savePoint) {
        String name = savePoint == null ? null : savePoint.getSavepointName();
        connection.commit(savePoint);
        if (name == null) {
            logger.debug("committed savePoint transaction");
        } else {
            logger.debug((String) "committed savePoint transaction {}", (Object) name);
        }
    }

    private static void rollBack(DatabaseConnection connection, Savepoint savePoint) {
        String name = savePoint == null ? null : savePoint.getSavepointName();
        connection.rollback(savePoint);
        if (name == null) {
            logger.debug("rolled back savePoint transaction");
        } else {
            logger.debug((String) "rolled back savePoint transaction {}", (Object) name);
        }
    }
}
