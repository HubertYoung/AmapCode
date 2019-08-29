package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.encrypt.OrmLiteEncryptionProcessor;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseResults;
import java.util.UUID;

public class UuidType extends BaseDataType {
    public static int DEFAULT_WIDTH = 48;
    private static final UuidType singleTon = new UuidType();

    public static UuidType getSingleton() {
        return singleTon;
    }

    private UuidType() {
        super(SqlType.STRING, new Class[]{UUID.class});
    }

    protected UuidType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        if (fieldType != null) {
            try {
                if (fieldType.isEncryption()) {
                    return OrmLiteEncryptionProcessor.encrypt(UUID.fromString(defaultStr).toString());
                }
            } catch (IllegalArgumentException e) {
                throw SqlExceptionUtil.create("Problems with field " + fieldType + " parsing default UUID-string '" + defaultStr + "'", e);
            }
        }
        return UUID.fromString(defaultStr);
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return results.getString(columnPos);
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        String uuidStr;
        if (fieldType == null || !fieldType.isEncryption()) {
            uuidStr = (String) sqlArg;
        } else {
            uuidStr = OrmLiteEncryptionProcessor.decrypt((String) sqlArg);
        }
        try {
            return UUID.fromString(uuidStr);
        } catch (IllegalArgumentException e) {
            throw SqlExceptionUtil.create("Problems with column " + columnPos + " parsing UUID-string '" + uuidStr + "'", e);
        }
    }

    public Object javaToSqlArg(FieldType fieldType, Object obj) {
        UUID uuid = (UUID) obj;
        if (fieldType == null || !fieldType.isEncryption()) {
            return uuid.toString();
        }
        return OrmLiteEncryptionProcessor.encrypt(uuid.toString());
    }

    public boolean isValidGeneratedType() {
        return true;
    }

    public boolean isSelfGeneratedId() {
        return true;
    }

    public Object generateId() {
        return UUID.randomUUID();
    }

    public int getDefaultWidth() {
        return DEFAULT_WIDTH;
    }

    public Object resultStringToJava(FieldType fieldType, String stringValue, int columnPos) {
        return sqlArgToJava(fieldType, stringValue, columnPos);
    }
}
