package com.j256.ormlite.stmt.query;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.stmt.ArgumentHolder;
import java.util.List;

public class Raw implements Clause {
    private final ArgumentHolder[] args;
    private final String statement;

    public Raw(String statement2, ArgumentHolder[] args2) {
        this.statement = statement2;
        this.args = args2;
    }

    public void appendSql(DatabaseType databaseType, String tableName, StringBuilder sb, List<ArgumentHolder> argList) {
        sb.append(this.statement);
        sb.append(' ');
        for (ArgumentHolder arg : this.args) {
            argList.add(arg);
        }
    }
}
