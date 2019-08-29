package com.j256.ormlite.field.types;

import com.j256.ormlite.field.SqlType;

public class FloatType extends FloatObjectType {
    private static final FloatType singleTon = new FloatType();

    public static FloatType getSingleton() {
        return singleTon;
    }

    private FloatType() {
        super(SqlType.FLOAT, new Class[]{Float.TYPE});
    }

    protected FloatType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public boolean isPrimitive() {
        return true;
    }
}
