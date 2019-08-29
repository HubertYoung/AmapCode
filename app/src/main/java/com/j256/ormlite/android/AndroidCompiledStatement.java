package com.j256.ormlite.android;

import android.database.Cursor;
import android.database.SQLException;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase;
import com.alibaba.sqlcrypto.sqlite.SQLiteStatement;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.j256.ormlite.android.compat.ApiCompatibility;
import com.j256.ormlite.android.compat.ApiCompatibility.CancellationHook;
import com.j256.ormlite.android.compat.ApiCompatibilityUtils;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.stmt.StatementBuilder.StatementType;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.DatabaseResults;
import java.util.ArrayList;
import java.util.List;

public class AndroidCompiledStatement implements CompiledStatement {
    private static final String[] NO_STRING_ARGS = new String[0];
    private static final ApiCompatibility apiCompatibility = ApiCompatibilityUtils.getCompatibility();
    private static Logger logger = LoggerFactory.getLogger(AndroidCompiledStatement.class);
    private List<Object> args;
    private final boolean cancelQueriesEnabled;
    private CancellationHook cancellationHook;
    private Cursor cursor;
    private final SQLiteDatabase db;
    private Integer max;
    private final String sql;
    private final StatementType type;

    public AndroidCompiledStatement(String sql2, SQLiteDatabase db2, StatementType type2, boolean cancelQueriesEnabled2) {
        this.sql = sql2;
        this.db = db2;
        this.type = type2;
        this.cancelQueriesEnabled = cancelQueriesEnabled2;
    }

    public int getColumnCount() {
        return getCursor().getColumnCount();
    }

    public String getColumnName(int column) {
        return getCursor().getColumnName(column);
    }

    public DatabaseResults runQuery(ObjectCache objectCache) {
        if (this.type.isOkForQuery()) {
            return new AndroidDatabaseResults(getCursor(), objectCache);
        }
        throw new IllegalArgumentException("Cannot call query on a " + this.type + " statement");
    }

    public int runUpdate() {
        String finalSql;
        if (!this.type.isOkForUpdate()) {
            throw new IllegalArgumentException("Cannot call update on a " + this.type + " statement");
        }
        if (this.max == null) {
            finalSql = this.sql;
        } else {
            finalSql = this.sql + Token.SEPARATOR + this.max;
        }
        return execSql(this.db, "runUpdate", finalSql, getArgArray());
    }

    public int runExecute() {
        if (this.type.isOkForExecute()) {
            return execSql(this.db, "runExecute", this.sql, getArgArray());
        }
        throw new IllegalArgumentException("Cannot call execute on a " + this.type + " statement");
    }

    public void close() {
        if (this.cursor != null) {
            try {
                this.cursor.close();
            } catch (SQLException e) {
                throw SqlExceptionUtil.create("Problems closing Android cursor", e);
            }
        }
        this.cancellationHook = null;
    }

    public void closeQuietly() {
        try {
            close();
        } catch (java.sql.SQLException e) {
        }
    }

    public void cancel() {
        if (this.cancellationHook != null) {
            this.cancellationHook.cancel();
        }
    }

    public void setObject(int parameterIndex, Object obj, SqlType sqlType) {
        isInPrep();
        if (this.args == null) {
            this.args = new ArrayList();
        }
        if (obj == null) {
            this.args.add(parameterIndex, null);
            return;
        }
        switch (sqlType) {
            case STRING:
            case LONG_STRING:
            case DATE:
            case BOOLEAN:
            case CHAR:
            case BYTE:
            case SHORT:
            case INTEGER:
            case LONG:
            case FLOAT:
            case DOUBLE:
                this.args.add(parameterIndex, obj.toString());
                return;
            case BYTE_ARRAY:
            case SERIALIZABLE:
                this.args.add(parameterIndex, obj);
                return;
            case BLOB:
            case BIG_DECIMAL:
                throw new java.sql.SQLException("Invalid Android type: " + sqlType);
            default:
                throw new java.sql.SQLException("Unknown sql argument type: " + sqlType);
        }
    }

    public void setMaxRows(int max2) {
        isInPrep();
        this.max = Integer.valueOf(max2);
    }

    public void setQueryTimeout(long millis) {
    }

    public Cursor getCursor() {
        String finalSql;
        if (this.cursor == null) {
            String finalSql2 = null;
            try {
                if (this.max == null) {
                    finalSql = this.sql;
                } else {
                    finalSql = this.sql + Token.SEPARATOR + this.max;
                }
                if (this.cancelQueriesEnabled) {
                    this.cancellationHook = apiCompatibility.createCancellationHook();
                }
                this.cursor = apiCompatibility.rawQuery(this.db, finalSql, getStringArray(), this.cancellationHook);
                this.cursor.moveToFirst();
                logger.trace((String) "{}: started rawQuery cursor for: {}", (Object) this, (Object) finalSql);
            } catch (SQLException e) {
                throw SqlExceptionUtil.create("Problems executing Android query: " + finalSql2, e);
            }
        }
        return this.cursor;
    }

    public String toString() {
        return getClass().getSimpleName() + AUScreenAdaptTool.PREFIX_ID + Integer.toHexString(super.hashCode());
    }

    static int execSql(SQLiteDatabase db2, String label, String finalSql, Object[] argArray) {
        int result;
        try {
            db2.execSQL(finalSql, argArray);
            SQLiteStatement stmt = null;
            try {
                stmt = db2.compileStatement("SELECT CHANGES()");
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
            logger.trace((String) "executing statement {} changed {} rows: {}", (Object) label, (Object) Integer.valueOf(result), (Object) finalSql);
            return result;
        } catch (SQLException e2) {
            throw SqlExceptionUtil.create("Problems executing " + label + " Android statement: " + finalSql, e2);
        }
    }

    private void isInPrep() {
        if (this.cursor != null) {
            throw new java.sql.SQLException("Query already run. Cannot add argument values.");
        }
    }

    private Object[] getArgArray() {
        if (this.args == null) {
            return NO_STRING_ARGS;
        }
        return this.args.toArray(new Object[this.args.size()]);
    }

    private String[] getStringArray() {
        if (this.args == null) {
            return NO_STRING_ARGS;
        }
        return (String[]) this.args.toArray(new String[this.args.size()]);
    }
}
