package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EnumIntegerType extends BaseEnumType {
    private static final EnumIntegerType singleTon = new EnumIntegerType();

    public static EnumIntegerType getSingleton() {
        return singleTon;
    }

    private EnumIntegerType() {
        super(SqlType.INTEGER, new Class[0]);
    }

    protected EnumIntegerType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return Integer.valueOf(Integer.parseInt(defaultStr));
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return Integer.valueOf(results.getInt(columnPos));
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        if (fieldType == null) {
            return sqlArg;
        }
        Integer valInteger = (Integer) sqlArg;
        Map enumIntMap = (Map) fieldType.getDataTypeConfigObj();
        if (enumIntMap == null) {
            return enumVal(fieldType, valInteger, null, fieldType.getUnknownEnumVal());
        }
        return enumVal(fieldType, valInteger, (Enum) enumIntMap.get(valInteger), fieldType.getUnknownEnumVal());
    }

    public Object javaToSqlArg(FieldType fieldType, Object obj) {
        return Integer.valueOf(((Enum) obj).ordinal());
    }

    public boolean isEscapedValue() {
        return false;
    }

    public Object makeConfigObject(FieldType fieldType) {
        Map enumIntMap = new HashMap();
        Enum[] constants = (Enum[]) fieldType.getType().getEnumConstants();
        if (constants == null) {
            throw new SQLException("Field " + fieldType + " improperly configured as type " + this);
        }
        for (Enum enumVal : constants) {
            enumIntMap.put(Integer.valueOf(enumVal.ordinal()), enumVal);
        }
        return enumIntMap;
    }

    public Object resultStringToJava(FieldType fieldType, String stringValue, int columnPos) {
        return sqlArgToJava(fieldType, Integer.valueOf(Integer.parseInt(stringValue)), columnPos);
    }

    public Class<?> getPrimaryClass() {
        return Integer.TYPE;
    }
}
