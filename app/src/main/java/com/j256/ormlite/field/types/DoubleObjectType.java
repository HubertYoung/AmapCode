package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.support.DatabaseResults;

public class DoubleObjectType extends BaseDataType {
    private static final DoubleObjectType singleTon = new DoubleObjectType();

    public static DoubleObjectType getSingleton() {
        return singleTon;
    }

    private DoubleObjectType() {
        super(SqlType.DOUBLE, new Class[]{Double.class});
    }

    protected DoubleObjectType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return Double.valueOf(Double.parseDouble(defaultStr));
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return Double.valueOf(results.getDouble(columnPos));
    }

    public boolean isEscapedValue() {
        return false;
    }
}
