package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.encrypt.OrmLiteEncryptionProcessor;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseResults;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class StringBytesType extends BaseDataType {
    private static final String DEFAULT_STRING_BYTES_CHARSET_NAME = "Unicode";
    private static final StringBytesType singleTon = new StringBytesType();

    public static StringBytesType getSingleton() {
        return singleTon;
    }

    private StringBytesType() {
        super(SqlType.BYTE_ARRAY, new Class[0]);
    }

    protected StringBytesType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        throw new SQLException("String-bytes type cannot have default values");
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return results.getBytes(columnPos);
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        byte[] bytes = (byte[]) sqlArg;
        if (fieldType != null && fieldType.isEncryption()) {
            bytes = OrmLiteEncryptionProcessor.decrypt(bytes);
        }
        String charsetName = getCharsetName(fieldType);
        try {
            return new String(bytes, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw SqlExceptionUtil.create("Could not convert string with charset name: " + charsetName, e);
        }
    }

    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        String string = (String) javaObject;
        String charsetName = getCharsetName(fieldType);
        if (fieldType != null) {
            try {
                if (fieldType.isEncryption()) {
                    return OrmLiteEncryptionProcessor.encrypt(string.getBytes(charsetName));
                }
            } catch (UnsupportedEncodingException e) {
                throw SqlExceptionUtil.create("Could not convert string with charset name: " + charsetName, e);
            }
        }
        return string.getBytes(charsetName);
    }

    public boolean isAppropriateId() {
        return false;
    }

    public boolean isArgumentHolderRequired() {
        return true;
    }

    public Object resultStringToJava(FieldType fieldType, String stringValue, int columnPos) {
        throw new SQLException("String-bytes type cannot be converted from string to Java");
    }

    public Class<?> getPrimaryClass() {
        return String.class;
    }

    private String getCharsetName(FieldType fieldType) {
        if (fieldType == null || fieldType.getFormat() == null) {
            return DEFAULT_STRING_BYTES_CHARSET_NAME;
        }
        return fieldType.getFormat();
    }
}
