package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.support.DatabaseResults;

public class ShortObjectType extends BaseDataType {
    private static final ShortObjectType singleTon = new ShortObjectType();

    public static ShortObjectType getSingleton() {
        return singleTon;
    }

    private ShortObjectType() {
        super(SqlType.SHORT, new Class[]{Short.class});
    }

    protected ShortObjectType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return Short.valueOf(Short.parseShort(defaultStr));
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return Short.valueOf(results.getShort(columnPos));
    }

    public boolean isEscapedValue() {
        return false;
    }

    public boolean isValidForVersion() {
        return true;
    }

    public Object moveToNextValue(Object currentValue) {
        if (currentValue == null) {
            return Short.valueOf(1);
        }
        return Short.valueOf((short) (((Short) currentValue).shortValue() + 1));
    }
}
