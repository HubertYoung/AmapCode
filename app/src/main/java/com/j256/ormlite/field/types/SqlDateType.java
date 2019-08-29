package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDateType.DateStringFormatConfig;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;

public class SqlDateType extends DateType {
    private static final SqlDateType singleTon = new SqlDateType();
    private static final DateStringFormatConfig sqlDateFormatConfig = new DateStringFormatConfig("yyyy-MM-dd");

    public static SqlDateType getSingleton() {
        return singleTon;
    }

    private SqlDateType() {
        super(SqlType.DATE, new Class[]{Date.class});
    }

    protected SqlDateType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return new Date(((Timestamp) sqlArg).getTime());
    }

    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        return new Timestamp(((Date) javaObject).getTime());
    }

    /* access modifiers changed from: protected */
    public DateStringFormatConfig getDefaultDateFormatConfig() {
        return sqlDateFormatConfig;
    }

    public Object resultStringToJava(FieldType fieldType, String stringValue, int columnPos) {
        return sqlArgToJava(fieldType, Timestamp.valueOf(stringValue), columnPos);
    }

    public boolean isValidForField(Field field) {
        return field.getType() == Date.class;
    }
}
