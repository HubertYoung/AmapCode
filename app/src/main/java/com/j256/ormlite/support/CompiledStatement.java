package com.j256.ormlite.support;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.SqlType;

public interface CompiledStatement {
    void cancel();

    void close();

    void closeQuietly();

    int getColumnCount();

    String getColumnName(int i);

    int runExecute();

    DatabaseResults runQuery(ObjectCache objectCache);

    int runUpdate();

    void setMaxRows(int i);

    void setObject(int i, Object obj, SqlType sqlType);

    void setQueryTimeout(long j);
}
