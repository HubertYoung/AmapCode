package com.j256.ormlite.android;

import android.database.Cursor;
import android.database.SQLException;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase;
import com.alibaba.sqlcrypto.sqlite.SQLiteStatement;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.common.transport.utils.SwitchMonitorLogUtil;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.misc.VersionUtils;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.StatementBuilder.StatementType;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.GeneratedKeyHolder;
import java.sql.Savepoint;

public class AndroidDatabaseConnection implements DatabaseConnection {
    private static final String ANDROID_VERSION = "VERSION__4.48__";
    private static final String[] NO_STRING_ARGS = new String[0];
    private static Logger logger = LoggerFactory.getLogger(AndroidDatabaseConnection.class);
    private final boolean cancelQueriesEnabled;
    private final SQLiteDatabase db;
    private final boolean readWrite;

    class OurSavePoint implements Savepoint {
        private String name;

        public OurSavePoint(String name2) {
            this.name = name2;
        }

        public int getSavepointId() {
            return 0;
        }

        public String getSavepointName() {
            return this.name;
        }
    }

    static {
        VersionUtils.checkCoreVersusAndroidVersions(ANDROID_VERSION);
    }

    public AndroidDatabaseConnection(SQLiteDatabase db2, boolean readWrite2) {
        this(db2, readWrite2, false);
    }

    public AndroidDatabaseConnection(SQLiteDatabase db2, boolean readWrite2, boolean cancelQueriesEnabled2) {
        this.db = db2;
        this.readWrite = readWrite2;
        this.cancelQueriesEnabled = cancelQueriesEnabled2;
        logger.trace((String) "{}: db {} opened, read-write = {}", (Object) this, (Object) db2, (Object) Boolean.valueOf(readWrite2));
    }

    public boolean isAutoCommitSupported() {
        return true;
    }

    public boolean isAutoCommit() {
        try {
            boolean inTransaction = this.db.inTransaction();
            logger.trace((String) "{}: in transaction is {}", (Object) this, (Object) Boolean.valueOf(inTransaction));
            return !inTransaction;
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("problems getting auto-commit from database", e);
        }
    }

    public void setAutoCommit(boolean autoCommit) {
        if (autoCommit) {
            if (this.db.inTransaction()) {
                this.db.setTransactionSuccessful();
                this.db.endTransaction();
            }
        } else if (!this.db.inTransaction()) {
            this.db.beginTransaction();
        }
    }

    public Savepoint setSavePoint(String name) {
        try {
            this.db.beginTransaction();
            logger.trace((String) "{}: save-point set with name {}", (Object) this, (Object) name);
            return new OurSavePoint(name);
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("problems beginning transaction " + name, e);
        }
    }

    public boolean isReadWrite() {
        return this.readWrite;
    }

    public void commit(Savepoint savepoint) {
        try {
            this.db.setTransactionSuccessful();
            this.db.endTransaction();
            if (savepoint == null) {
                logger.trace((String) "{}: transaction is successfuly ended", (Object) this);
            } else {
                logger.trace((String) "{}: transaction {} is successfuly ended", (Object) this, (Object) savepoint.getSavepointName());
            }
        } catch (SQLException e) {
            if (savepoint == null) {
                throw SqlExceptionUtil.create("problems commiting transaction", e);
            }
            throw SqlExceptionUtil.create("problems commiting transaction " + savepoint.getSavepointName(), e);
        }
    }

    public void rollback(Savepoint savepoint) {
        try {
            this.db.endTransaction();
            if (savepoint == null) {
                logger.trace((String) "{}: transaction is ended, unsuccessfuly", (Object) this);
            } else {
                logger.trace((String) "{}: transaction {} is ended, unsuccessfuly", (Object) this, (Object) savepoint.getSavepointName());
            }
        } catch (SQLException e) {
            if (savepoint == null) {
                throw SqlExceptionUtil.create("problems rolling back transaction", e);
            }
            throw SqlExceptionUtil.create("problems rolling back transaction " + savepoint.getSavepointName(), e);
        }
    }

    public int executeStatement(String statementStr, int resultFlags) {
        return AndroidCompiledStatement.execSql(this.db, statementStr, statementStr, NO_STRING_ARGS);
    }

    public CompiledStatement compileStatement(String statement, StatementType type, FieldType[] argFieldTypes, int resultFlags) {
        CompiledStatement stmt = new AndroidCompiledStatement(statement, this.db, type, this.cancelQueriesEnabled);
        logger.trace((String) "{}: compiled statement got {}: {}", (Object) this, (Object) stmt, (Object) statement);
        return stmt;
    }

    public int insert(String statement, Object[] args, FieldType[] argFieldTypes, GeneratedKeyHolder keyHolder) {
        SQLiteStatement stmt = null;
        try {
            SQLiteStatement stmt2 = this.db.compileStatement(statement);
            bindArgs(stmt2, args, argFieldTypes);
            long rowId = stmt2.executeInsert();
            if (keyHolder != null) {
                keyHolder.addKey(Long.valueOf(rowId));
            }
            logger.trace((String) "{}: insert statement is compiled and executed, changed {}: {}", (Object) this, (Object) Integer.valueOf(1), (Object) statement);
            if (stmt2 != null) {
                stmt2.close();
            }
            return 1;
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("inserting to database failed: " + statement, e);
        } catch (Throwable th) {
            if (stmt != null) {
                stmt.close();
            }
            throw th;
        }
    }

    public int update(String statement, Object[] args, FieldType[] argFieldTypes) {
        return update(statement, args, argFieldTypes, SwitchMonitorLogUtil.SUB_TYPE_UPDATED);
    }

    public int delete(String statement, Object[] args, FieldType[] argFieldTypes) {
        return update(statement, args, argFieldTypes, "deleted");
    }

    public <T> Object queryForOne(String statement, Object[] args, FieldType[] argFieldTypes, GenericRowMapper<T> rowMapper, ObjectCache objectCache) {
        Cursor cursor = null;
        try {
            cursor = this.db.rawQuery(statement, toStrings(args));
            AndroidDatabaseResults results = new AndroidDatabaseResults(cursor, objectCache);
            logger.trace((String) "{}: queried for one result: {}", (Object) this, (Object) statement);
            if (!results.first()) {
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            }
            Object mapRow = rowMapper.mapRow(results);
            if (results.next()) {
                Object first = MORE_THAN_ONE;
                if (cursor == null) {
                    return first;
                }
                cursor.close();
                return first;
            } else if (cursor == null) {
                return mapRow;
            } else {
                cursor.close();
                return mapRow;
            }
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("queryForOne from database failed: " + statement, e);
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public long queryForLong(String statement) {
        SQLiteStatement stmt = null;
        try {
            stmt = this.db.compileStatement(statement);
            long result = stmt.simpleQueryForLong();
            logger.trace((String) "{}: query for long simple query returned {}: {}", (Object) this, (Object) Long.valueOf(result), (Object) statement);
            if (stmt != null) {
                stmt.close();
            }
            return result;
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("queryForLong from database failed: " + statement, e);
        } catch (Throwable th) {
            if (stmt != null) {
                stmt.close();
            }
            throw th;
        }
    }

    public long queryForLong(String statement, Object[] args, FieldType[] argFieldTypes) {
        long result;
        Cursor cursor = null;
        try {
            Cursor cursor2 = this.db.rawQuery(statement, toStrings(args));
            AndroidDatabaseResults results = new AndroidDatabaseResults(cursor2, null);
            if (results.first()) {
                result = results.getLong(0);
            } else {
                result = 0;
            }
            logger.trace((String) "{}: query for long raw query returned {}: {}", (Object) this, (Object) Long.valueOf(result), (Object) statement);
            if (cursor2 != null) {
                cursor2.close();
            }
            return result;
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("queryForLong from database failed: " + statement, e);
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public void close() {
        try {
            this.db.close();
            logger.trace((String) "{}: db {} closed", (Object) this, (Object) this.db);
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("problems closing the database connection", e);
        }
    }

    public void closeQuietly() {
        try {
            close();
        } catch (java.sql.SQLException e) {
        }
    }

    public boolean isClosed() {
        try {
            boolean isOpen = this.db.isOpen();
            logger.trace((String) "{}: db {} isOpen returned {}", (Object) this, (Object) this.db, (Object) Boolean.valueOf(isOpen));
            return !isOpen;
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("problems detecting if the database is closed", e);
        }
    }

    public boolean isTableExists(String tableName) {
        boolean result;
        Cursor cursor = this.db.rawQuery("SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = ? ", new String[]{tableName});
        try {
            if (cursor.getCount() > 0) {
                result = true;
            } else {
                result = false;
            }
            logger.trace((String) "{}: isTableExists '{}' returned {}", (Object) this, (Object) tableName, (Object) Boolean.valueOf(result));
            return result;
        } finally {
            cursor.close();
        }
    }

    private int update(String statement, Object[] args, FieldType[] argFieldTypes, String label) {
        int result;
        SQLiteStatement stmt = null;
        try {
            stmt = this.db.compileStatement(statement);
            bindArgs(stmt, args, argFieldTypes);
            stmt.execute();
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            try {
                stmt = this.db.compileStatement("SELECT CHANGES()");
                result = (int) stmt.simpleQueryForLong();
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                result = 1;
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Throwable th) {
                if (stmt != null) {
                    stmt.close();
                }
                throw th;
            }
            logger.trace((String) "{} statement is compiled and executed, changed {}: {}", (Object) label, (Object) Integer.valueOf(result), (Object) statement);
            return result;
        } catch (SQLException e2) {
            throw SqlExceptionUtil.create("updating database failed: " + statement, e2);
        } catch (Throwable th2) {
            if (stmt != null) {
                stmt.close();
            }
            throw th2;
        }
    }

    private void bindArgs(SQLiteStatement stmt, Object[] args, FieldType[] argFieldTypes) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg == null) {
                    stmt.bindNull(i + 1);
                } else {
                    SqlType sqlType = argFieldTypes[i].getSqlType();
                    switch (sqlType) {
                        case STRING:
                        case LONG_STRING:
                        case CHAR:
                            stmt.bindString(i + 1, arg.toString());
                            break;
                        case BOOLEAN:
                        case BYTE:
                        case SHORT:
                        case INTEGER:
                        case LONG:
                            stmt.bindLong(i + 1, ((Number) arg).longValue());
                            break;
                        case FLOAT:
                        case DOUBLE:
                            stmt.bindDouble(i + 1, ((Number) arg).doubleValue());
                            break;
                        case BYTE_ARRAY:
                        case SERIALIZABLE:
                            stmt.bindBlob(i + 1, (byte[]) arg);
                            break;
                        case DATE:
                        case BLOB:
                        case BIG_DECIMAL:
                            throw new java.sql.SQLException("Invalid Android type: " + sqlType);
                        default:
                            throw new java.sql.SQLException("Unknown sql argument type: " + sqlType);
                    }
                }
            }
        }
    }

    private String[] toStrings(Object[] args) {
        if (args == null || args.length == 0) {
            return null;
        }
        String[] strings = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg == null) {
                strings[i] = null;
            } else {
                strings[i] = arg.toString();
            }
        }
        return strings;
    }

    public String toString() {
        return getClass().getSimpleName() + AUScreenAdaptTool.PREFIX_ID + Integer.toHexString(super.hashCode());
    }
}
