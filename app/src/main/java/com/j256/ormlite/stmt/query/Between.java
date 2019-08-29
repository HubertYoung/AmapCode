package com.j256.ormlite.stmt.query;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.ArgumentHolder;
import java.util.List;

public class Between extends BaseComparison {
    private Object high;
    private Object low;

    public /* bridge */ /* synthetic */ void appendSql(DatabaseType databaseType, String str, StringBuilder sb, List list) {
        super.appendSql(databaseType, str, sb, list);
    }

    public /* bridge */ /* synthetic */ String getColumnName() {
        return super.getColumnName();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public Between(String columnName, FieldType fieldType, Object low2, Object high2) {
        super(columnName, fieldType, null, true);
        this.low = low2;
        this.high = high2;
    }

    public void appendOperation(StringBuilder sb) {
        sb.append("BETWEEN ");
    }

    public void appendValue(DatabaseType databaseType, StringBuilder sb, List<ArgumentHolder> argList) {
        if (this.low == null) {
            throw new IllegalArgumentException("BETWEEN low value for '" + this.columnName + "' is null");
        } else if (this.high == null) {
            throw new IllegalArgumentException("BETWEEN high value for '" + this.columnName + "' is null");
        } else {
            appendArgOrValue(databaseType, this.fieldType, sb, argList, this.low);
            sb.append("AND ");
            appendArgOrValue(databaseType, this.fieldType, sb, argList, this.high);
        }
    }
}
