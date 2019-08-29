package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDateType.DateStringFormatConfig;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

public class DateType extends BaseDateType {
    private static final DateType singleTon = new DateType();

    public static DateType getSingleton() {
        return singleTon;
    }

    private DateType() {
        super(SqlType.DATE, new Class[]{Date.class});
    }

    protected DateType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        DateStringFormatConfig dateFormatConfig = convertDateStringConfig(fieldType, getDefaultDateFormatConfig());
        try {
            return new Timestamp(parseDateString(dateFormatConfig, defaultStr).getTime());
        } catch (ParseException e) {
            throw SqlExceptionUtil.create("Problems parsing default date string '" + defaultStr + "' using '" + dateFormatConfig + '\'', e);
        }
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return results.getTimestamp(columnPos);
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return new Date(((Timestamp) sqlArg).getTime());
    }

    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        return new Timestamp(((Date) javaObject).getTime());
    }

    public boolean isArgumentHolderRequired() {
        return true;
    }

    /* access modifiers changed from: protected */
    public DateStringFormatConfig getDefaultDateFormatConfig() {
        return defaultDateFormatConfig;
    }
}
