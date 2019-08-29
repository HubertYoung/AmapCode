package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.encrypt.OrmLiteEncryptionProcessor;
import com.j256.ormlite.field.types.BaseDateType.DateStringFormatConfig;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseResults;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateStringType extends BaseDateType {
    public static int DEFAULT_WIDTH = 50;
    private static final DateStringType singleTon = new DateStringType();

    public static DateStringType getSingleton() {
        return singleTon;
    }

    private DateStringType() {
        super(SqlType.STRING, new Class[0]);
    }

    protected DateStringType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        DateStringFormatConfig formatConfig = convertDateStringConfig(fieldType, defaultDateFormatConfig);
        if (fieldType != null) {
            try {
                if (fieldType.isEncryption()) {
                    return OrmLiteEncryptionProcessor.encrypt(normalizeDateString(formatConfig, defaultStr));
                }
            } catch (ParseException e) {
                throw SqlExceptionUtil.create("Problems with field " + fieldType + " parsing default date-string '" + defaultStr + "' using '" + formatConfig + "'", e);
            }
        }
        return normalizeDateString(formatConfig, defaultStr);
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return results.getString(columnPos);
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        String value = (String) sqlArg;
        if (fieldType != null && fieldType.isEncryption()) {
            value = OrmLiteEncryptionProcessor.decrypt(value);
        }
        DateStringFormatConfig formatConfig = convertDateStringConfig(fieldType, defaultDateFormatConfig);
        try {
            return parseDateString(formatConfig, value);
        } catch (ParseException e) {
            throw SqlExceptionUtil.create("Problems with column " + columnPos + " parsing date-string '" + value + "' using '" + formatConfig + "'", e);
        }
    }

    public Object javaToSqlArg(FieldType fieldType, Object obj) {
        DateFormat dateFormat = convertDateStringConfig(fieldType, defaultDateFormatConfig).getDateFormat();
        if (fieldType == null || !fieldType.isEncryption()) {
            return dateFormat.format((Date) obj);
        }
        return OrmLiteEncryptionProcessor.encrypt(dateFormat.format((Date) obj));
    }

    public Object makeConfigObject(FieldType fieldType) {
        String format = fieldType.getFormat();
        if (format == null) {
            return defaultDateFormatConfig;
        }
        return new DateStringFormatConfig(format);
    }

    public int getDefaultWidth() {
        return DEFAULT_WIDTH;
    }

    public Object resultStringToJava(FieldType fieldType, String stringValue, int columnPos) {
        return sqlArgToJava(fieldType, stringValue, columnPos);
    }

    public Class<?> getPrimaryClass() {
        return byte[].class;
    }
}
