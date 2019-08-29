package com.j256.ormlite.stmt.query;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.stmt.ArgumentHolder;
import java.util.List;

public interface Clause {
    void appendSql(DatabaseType databaseType, String str, StringBuilder sb, List<ArgumentHolder> list);
}
