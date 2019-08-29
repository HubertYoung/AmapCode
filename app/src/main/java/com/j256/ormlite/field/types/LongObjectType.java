package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.support.DatabaseResults;

public class LongObjectType extends BaseDataType {
    private static final LongObjectType singleTon = new LongObjectType();

    public static LongObjectType getSingleton() {
        return singleTon;
    }

    private LongObjectType() {
        super(SqlType.LONG, new Class[]{Long.class});
    }

    protected LongObjectType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return Long.valueOf(Long.parseLong(defaultStr));
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return Long.valueOf(results.getLong(columnPos));
    }

    public Object convertIdNumber(Number number) {
        return Long.valueOf(number.longValue());
    }

    public boolean isEscapedValue() {
        return false;
    }

    public boolean isValidGeneratedType() {
        return true;
    }

    public boolean isValidForVersion() {
        return true;
    }

    public Object moveToNextValue(Object currentValue) {
        if (currentValue == null) {
            return Long.valueOf(1);
        }
        return Long.valueOf(((Long) currentValue).longValue() + 1);
    }
}
