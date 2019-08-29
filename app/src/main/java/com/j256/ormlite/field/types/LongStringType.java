package com.j256.ormlite.field.types;

import com.j256.ormlite.field.SqlType;

public class LongStringType extends StringType {
    private static final LongStringType singleTon = new LongStringType();

    public static LongStringType getSingleton() {
        return singleTon;
    }

    private LongStringType() {
        super(SqlType.LONG_STRING, new Class[0]);
    }

    protected LongStringType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public boolean isAppropriateId() {
        return false;
    }

    public int getDefaultWidth() {
        return 0;
    }

    public Class<?> getPrimaryClass() {
        return String.class;
    }
}
