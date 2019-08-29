package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import java.lang.reflect.Field;
import java.sql.Timestamp;

public class TimeStampType extends DateType {
    private static final TimeStampType singleTon = new TimeStampType();

    public static TimeStampType getSingleton() {
        return singleTon;
    }

    private TimeStampType() {
        super(SqlType.DATE, new Class[]{Timestamp.class});
    }

    protected TimeStampType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return sqlArg;
    }

    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        return javaObject;
    }

    public boolean isValidForField(Field field) {
        return field.getType() == Timestamp.class;
    }

    public Object moveToNextValue(Object currentValue) {
        long newVal = System.currentTimeMillis();
        if (currentValue == null) {
            return new Timestamp(newVal);
        }
        if (newVal == ((Timestamp) currentValue).getTime()) {
            return new Timestamp(1 + newVal);
        }
        return new Timestamp(newVal);
    }
}
