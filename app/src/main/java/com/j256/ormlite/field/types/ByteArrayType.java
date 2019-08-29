package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.encrypt.OrmLiteEncryptionProcessor;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;
import java.util.Arrays;

public class ByteArrayType extends BaseDataType {
    private static final ByteArrayType singleTon = new ByteArrayType();

    public static ByteArrayType getSingleton() {
        return singleTon;
    }

    private ByteArrayType() {
        super(SqlType.BYTE_ARRAY, new Class[0]);
    }

    protected ByteArrayType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        throw new SQLException("byte[] type cannot have default values");
    }

    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        byte[] value = (byte[]) javaObject;
        if (fieldType == null || !fieldType.isEncryption()) {
            return super.javaToSqlArg(fieldType, javaObject);
        }
        return super.javaToSqlArg(fieldType, OrmLiteEncryptionProcessor.encrypt(value));
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        byte[] value = (byte[]) sqlArg;
        if (fieldType != null && fieldType.isEncryption()) {
            value = OrmLiteEncryptionProcessor.decrypt(value);
        }
        return super.sqlArgToJava(fieldType, value, columnPos);
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return results.getBytes(columnPos);
    }

    public boolean isAppropriateId() {
        return false;
    }

    public boolean isArgumentHolderRequired() {
        return true;
    }

    public boolean dataIsEqual(Object fieldObj1, Object fieldObj2) {
        if (fieldObj1 == null) {
            if (fieldObj2 == null) {
                return true;
            }
            return false;
        } else if (fieldObj2 != null) {
            return Arrays.equals((byte[]) fieldObj1, (byte[]) fieldObj2);
        } else {
            return false;
        }
    }

    public Object resultStringToJava(FieldType fieldType, String stringValue, int columnPos) {
        throw new SQLException("byte[] type cannot be converted from string to Java");
    }

    public Class<?> getPrimaryClass() {
        return byte[].class;
    }
}
