package com.j256.ormlite.support;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.StatementBuilder.StatementType;
import java.sql.Savepoint;

public class DatabaseConnectionProxy implements DatabaseConnection {
    private final DatabaseConnection proxy;

    public DatabaseConnectionProxy(DatabaseConnection proxy2) {
        this.proxy = proxy2;
    }

    public boolean isAutoCommitSupported() {
        if (this.proxy == null) {
            return false;
        }
        return this.proxy.isAutoCommitSupported();
    }

    public boolean isAutoCommit() {
        if (this.proxy == null) {
            return false;
        }
        return this.proxy.isAutoCommit();
    }

    public void setAutoCommit(boolean autoCommit) {
        if (this.proxy != null) {
            this.proxy.setAutoCommit(autoCommit);
        }
    }

    public Savepoint setSavePoint(String name) {
        if (this.proxy == null) {
            return null;
        }
        return this.proxy.setSavePoint(name);
    }

    public void commit(Savepoint savePoint) {
        if (this.proxy != null) {
            this.proxy.commit(savePoint);
        }
    }

    public void rollback(Savepoint savePoint) {
        if (this.proxy != null) {
            this.proxy.rollback(savePoint);
        }
    }

    public int executeStatement(String statementStr, int resultFlags) {
        if (this.proxy == null) {
            return 0;
        }
        return this.proxy.executeStatement(statementStr, resultFlags);
    }

    public CompiledStatement compileStatement(String statement, StatementType type, FieldType[] argFieldTypes, int resultFlags) {
        if (this.proxy == null) {
            return null;
        }
        return this.proxy.compileStatement(statement, type, argFieldTypes, resultFlags);
    }

    public int insert(String statement, Object[] args, FieldType[] argfieldTypes, GeneratedKeyHolder keyHolder) {
        if (this.proxy == null) {
            return 0;
        }
        return this.proxy.insert(statement, args, argfieldTypes, keyHolder);
    }

    public int update(String statement, Object[] args, FieldType[] argfieldTypes) {
        if (this.proxy == null) {
            return 0;
        }
        return this.proxy.update(statement, args, argfieldTypes);
    }

    public int delete(String statement, Object[] args, FieldType[] argfieldTypes) {
        if (this.proxy == null) {
            return 0;
        }
        return this.proxy.delete(statement, args, argfieldTypes);
    }

    public <T> Object queryForOne(String statement, Object[] args, FieldType[] argfieldTypes, GenericRowMapper<T> rowMapper, ObjectCache objectCache) {
        if (this.proxy == null) {
            return null;
        }
        return this.proxy.queryForOne(statement, args, argfieldTypes, rowMapper, objectCache);
    }

    public long queryForLong(String statement) {
        if (this.proxy == null) {
            return 0;
        }
        return this.proxy.queryForLong(statement);
    }

    public long queryForLong(String statement, Object[] args, FieldType[] argFieldTypes) {
        if (this.proxy == null) {
            return 0;
        }
        return this.proxy.queryForLong(statement, args, argFieldTypes);
    }

    public void close() {
        if (this.proxy != null) {
            this.proxy.close();
        }
    }

    public void closeQuietly() {
        if (this.proxy != null) {
            this.proxy.closeQuietly();
        }
    }

    public boolean isClosed() {
        if (this.proxy == null) {
            return true;
        }
        return this.proxy.isClosed();
    }

    public boolean isTableExists(String tableName) {
        if (this.proxy == null) {
            return false;
        }
        return this.proxy.isTableExists(tableName);
    }
}
