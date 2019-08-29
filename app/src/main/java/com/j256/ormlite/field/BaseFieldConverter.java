package com.j256.ormlite.field;

import com.j256.ormlite.support.DatabaseResults;

public abstract class BaseFieldConverter implements FieldConverter {
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        return javaObject;
    }

    public Object resultToJava(FieldType fieldType, DatabaseResults results, int columnPos) {
        Object value = resultToSqlArg(fieldType, results, columnPos);
        if (value == null) {
            return null;
        }
        return sqlArgToJava(fieldType, value, columnPos);
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return sqlArg;
    }

    public boolean isStreamType() {
        return false;
    }
}
