package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.encrypt.OrmLiteEncryptionProcessor;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EnumStringType extends BaseEnumType {
    public static int DEFAULT_WIDTH = 100;
    private static final EnumStringType singleTon = new EnumStringType();

    public static EnumStringType getSingleton() {
        return singleTon;
    }

    private EnumStringType() {
        super(SqlType.STRING, new Class[]{Enum.class});
    }

    protected EnumStringType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return results.getString(columnPos);
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        if (fieldType == null) {
            return sqlArg;
        }
        String value = (String) sqlArg;
        if (fieldType != null && fieldType.isEncryption()) {
            value = OrmLiteEncryptionProcessor.decrypt(value);
        }
        Map enumStringMap = (Map) fieldType.getDataTypeConfigObj();
        if (enumStringMap == null) {
            return enumVal(fieldType, value, null, fieldType.getUnknownEnumVal());
        }
        return enumVal(fieldType, value, (Enum) enumStringMap.get(value), fieldType.getUnknownEnumVal());
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        if (fieldType == null || !fieldType.isEncryption()) {
            return defaultStr;
        }
        return OrmLiteEncryptionProcessor.encrypt(defaultStr);
    }

    public Object javaToSqlArg(FieldType fieldType, Object obj) {
        Enum enumVal = (Enum) obj;
        if (fieldType == null || !fieldType.isEncryption()) {
            return enumVal.name();
        }
        return OrmLiteEncryptionProcessor.encrypt(enumVal.name());
    }

    public Object makeConfigObject(FieldType fieldType) {
        Map enumStringMap = new HashMap();
        Enum[] constants = (Enum[]) fieldType.getType().getEnumConstants();
        if (constants == null) {
            throw new SQLException("Field " + fieldType + " improperly configured as type " + this);
        }
        for (Enum enumVal : constants) {
            enumStringMap.put(enumVal.name(), enumVal);
        }
        return enumStringMap;
    }

    public int getDefaultWidth() {
        return DEFAULT_WIDTH;
    }

    public Object resultStringToJava(FieldType fieldType, String stringValue, int columnPos) {
        return sqlArgToJava(fieldType, stringValue, columnPos);
    }
}
