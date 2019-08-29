package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.support.DatabaseResults;

public class IntegerObjectType extends BaseDataType {
    private static final IntegerObjectType singleTon = new IntegerObjectType();

    public static IntegerObjectType getSingleton() {
        return singleTon;
    }

    private IntegerObjectType() {
        super(SqlType.INTEGER, new Class[]{Integer.class});
    }

    protected IntegerObjectType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return Integer.valueOf(Integer.parseInt(defaultStr));
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return Integer.valueOf(results.getInt(columnPos));
    }

    public Object convertIdNumber(Number number) {
        return Integer.valueOf(number.intValue());
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
            return Integer.valueOf(1);
        }
        return Integer.valueOf(((Integer) currentValue).intValue() + 1);
    }
}
