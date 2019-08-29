package com.j256.ormlite.stmt.query;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.ArgumentHolder;
import java.util.Arrays;
import java.util.List;

public class In extends BaseComparison {
    private final boolean in;
    private Iterable<?> objects;

    public /* bridge */ /* synthetic */ void appendSql(DatabaseType databaseType, String str, StringBuilder sb, List list) {
        super.appendSql(databaseType, str, sb, list);
    }

    public /* bridge */ /* synthetic */ String getColumnName() {
        return super.getColumnName();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public In(String columnName, FieldType fieldType, Iterable<?> objects2, boolean in2) {
        super(columnName, fieldType, null, true);
        this.objects = objects2;
        this.in = in2;
    }

    public In(String columnName, FieldType fieldType, Object[] objects2, boolean in2) {
        super(columnName, fieldType, null, true);
        this.objects = Arrays.asList(objects2);
        this.in = in2;
    }

    public void appendOperation(StringBuilder sb) {
        if (this.in) {
            sb.append("IN ");
        } else {
            sb.append("NOT IN ");
        }
    }

    public void appendValue(DatabaseType databaseType, StringBuilder sb, List<ArgumentHolder> columnArgList) {
        sb.append('(');
        boolean first = true;
        for (Object value : this.objects) {
            if (value == null) {
                throw new IllegalArgumentException("one of the IN values for '" + this.columnName + "' is null");
            }
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            super.appendArgOrValue(databaseType, this.fieldType, sb, columnArgList, value);
        }
        sb.append(") ");
    }
}
