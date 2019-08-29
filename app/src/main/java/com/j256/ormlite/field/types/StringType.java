package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.encrypt.OrmLiteEncryptionProcessor;
import com.j256.ormlite.support.DatabaseResults;

public class StringType extends BaseDataType {
    public static int DEFAULT_WIDTH = 255;
    private static final StringType singleTon = new StringType();

    public static StringType getSingleton() {
        return singleTon;
    }

    private StringType() {
        super(SqlType.STRING, new Class[]{String.class});
    }

    protected StringType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        if (fieldType == null || !fieldType.isEncryption()) {
            return defaultStr;
        }
        return OrmLiteEncryptionProcessor.encrypt(defaultStr);
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        String value = (String) sqlArg;
        if (fieldType != null && fieldType.isEncryption()) {
            value = OrmLiteEncryptionProcessor.decrypt(value);
        }
        return super.sqlArgToJava(fieldType, value, columnPos);
    }

    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        String value = (String) javaObject;
        if (fieldType != null && fieldType.isEncryption()) {
            value = OrmLiteEncryptionProcessor.encrypt(value);
        }
        return super.javaToSqlArg(fieldType, value);
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return results.getString(columnPos);
    }

    public int getDefaultWidth() {
        return DEFAULT_WIDTH;
    }

    public Object resultStringToJava(FieldType fieldType, String stringValue, int columnPos) {
        return sqlArgToJava(fieldType, stringValue, columnPos);
    }
}
