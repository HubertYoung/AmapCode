package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import java.lang.reflect.Field;
import java.sql.SQLException;

public abstract class BaseEnumType extends BaseDataType {
    protected BaseEnumType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    protected static Enum<?> enumVal(FieldType fieldType, Object val, Enum<?> enumVal, Enum<?> unknownEnumVal) {
        if (enumVal != null) {
            return enumVal;
        }
        if (unknownEnumVal != null) {
            return unknownEnumVal;
        }
        throw new SQLException("Cannot get enum value of '" + val + "' for field " + fieldType);
    }

    public boolean isValidForField(Field field) {
        return field.getType().isEnum();
    }
}
