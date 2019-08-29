package com.j256.ormlite.stmt.query;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.stmt.ArgumentHolder;
import java.util.List;

public class ManyClause implements Clause, NeedsFutureClause {
    public static final String AND_OPERATION = "AND";
    public static final String OR_OPERATION = "OR";
    private final Clause first;

    /* renamed from: operation reason: collision with root package name */
    private final String f16operation;
    private final Clause[] others;
    private Clause second;
    private final int startOthersAt;

    public ManyClause(Clause first2, String operation2) {
        this.first = first2;
        this.second = null;
        this.others = null;
        this.startOthersAt = 0;
        this.f16operation = operation2;
    }

    public ManyClause(Clause first2, Clause second2, Clause[] others2, String operation2) {
        this.first = first2;
        this.second = second2;
        this.others = others2;
        this.startOthersAt = 0;
        this.f16operation = operation2;
    }

    public ManyClause(Clause[] others2, String operation2) {
        this.first = others2[0];
        if (others2.length < 2) {
            this.second = null;
            this.startOthersAt = others2.length;
        } else {
            this.second = others2[1];
            this.startOthersAt = 2;
        }
        this.others = others2;
        this.f16operation = operation2;
    }

    public void appendSql(DatabaseType databaseType, String tableName, StringBuilder sb, List<ArgumentHolder> selectArgList) {
        sb.append("(");
        this.first.appendSql(databaseType, tableName, sb, selectArgList);
        if (this.second != null) {
            sb.append(this.f16operation);
            sb.append(' ');
            this.second.appendSql(databaseType, tableName, sb, selectArgList);
        }
        if (this.others != null) {
            for (int i = this.startOthersAt; i < this.others.length; i++) {
                sb.append(this.f16operation);
                sb.append(' ');
                this.others[i].appendSql(databaseType, tableName, sb, selectArgList);
            }
        }
        sb.append(") ");
    }

    public void setMissingClause(Clause right) {
        this.second = right;
    }
}
