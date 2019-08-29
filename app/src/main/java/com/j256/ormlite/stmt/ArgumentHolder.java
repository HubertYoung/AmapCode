package com.j256.ormlite.stmt;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;

public interface ArgumentHolder {
    String getColumnName();

    FieldType getFieldType();

    Object getSqlArgValue();

    SqlType getSqlType();

    void setMetaInfo(FieldType fieldType);

    void setMetaInfo(String str);

    void setMetaInfo(String str, FieldType fieldType);

    void setValue(Object obj);
}
