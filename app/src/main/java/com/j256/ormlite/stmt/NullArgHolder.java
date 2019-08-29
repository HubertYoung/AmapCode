package com.j256.ormlite.stmt;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;

public class NullArgHolder implements ArgumentHolder {
    public String getColumnName() {
        return "null-holder";
    }

    public void setValue(Object value) {
        throw new UnsupportedOperationException("Cannot set null on " + getClass());
    }

    public void setMetaInfo(String columnName) {
    }

    public void setMetaInfo(FieldType fieldType) {
    }

    public void setMetaInfo(String columnName, FieldType fieldType) {
    }

    public Object getSqlArgValue() {
        return null;
    }

    public SqlType getSqlType() {
        return SqlType.STRING;
    }

    public FieldType getFieldType() {
        return null;
    }
}
