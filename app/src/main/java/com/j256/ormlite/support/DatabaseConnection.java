package com.j256.ormlite.support;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.StatementBuilder.StatementType;
import java.sql.Savepoint;

public interface DatabaseConnection {
    public static final int DEFAULT_RESULT_FLAGS = -1;
    public static final Object MORE_THAN_ONE = new Object();

    void close();

    void closeQuietly();

    void commit(Savepoint savepoint);

    CompiledStatement compileStatement(String str, StatementType statementType, FieldType[] fieldTypeArr, int i);

    int delete(String str, Object[] objArr, FieldType[] fieldTypeArr);

    int executeStatement(String str, int i);

    int insert(String str, Object[] objArr, FieldType[] fieldTypeArr, GeneratedKeyHolder generatedKeyHolder);

    boolean isAutoCommit();

    boolean isAutoCommitSupported();

    boolean isClosed();

    boolean isTableExists(String str);

    long queryForLong(String str);

    long queryForLong(String str, Object[] objArr, FieldType[] fieldTypeArr);

    <T> Object queryForOne(String str, Object[] objArr, FieldType[] fieldTypeArr, GenericRowMapper<T> genericRowMapper, ObjectCache objectCache);

    void rollback(Savepoint savepoint);

    void setAutoCommit(boolean z);

    Savepoint setSavePoint(String str);

    int update(String str, Object[] objArr, FieldType[] fieldTypeArr);
}
