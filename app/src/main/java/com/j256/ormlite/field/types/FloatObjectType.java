package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.support.DatabaseResults;

public class FloatObjectType extends BaseDataType {
    private static final FloatObjectType singleTon = new FloatObjectType();

    public static FloatObjectType getSingleton() {
        return singleTon;
    }

    private FloatObjectType() {
        super(SqlType.FLOAT, new Class[]{Float.class});
    }

    protected FloatObjectType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return Float.valueOf(results.getFloat(columnPos));
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return Float.valueOf(Float.parseFloat(defaultStr));
    }

    public boolean isEscapedValue() {
        return false;
    }
}
