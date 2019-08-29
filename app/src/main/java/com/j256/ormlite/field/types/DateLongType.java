package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseResults;
import java.util.Date;

public class DateLongType extends BaseDateType {
    private static final DateLongType singleTon = new DateLongType();

    public static DateLongType getSingleton() {
        return singleTon;
    }

    private DateLongType() {
        super(SqlType.LONG, new Class[0]);
    }

    protected DateLongType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        try {
            return Long.valueOf(Long.parseLong(defaultStr));
        } catch (NumberFormatException e) {
            throw SqlExceptionUtil.create("Problems with field " + fieldType + " parsing default date-long value: " + defaultStr, e);
        }
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return Long.valueOf(results.getLong(columnPos));
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return new Date(((Long) sqlArg).longValue());
    }

    public Object javaToSqlArg(FieldType fieldType, Object obj) {
        return Long.valueOf(((Date) obj).getTime());
    }

    public boolean isEscapedValue() {
        return false;
    }

    public Object resultStringToJava(FieldType fieldType, String stringValue, int columnPos) {
        return sqlArgToJava(fieldType, Long.valueOf(Long.parseLong(stringValue)), columnPos);
    }

    public Class<?> getPrimaryClass() {
        return Date.class;
    }
}
