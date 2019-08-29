package com.j256.ormlite.field.types;

import com.j256.ormlite.field.SqlType;

public class DoubleType extends DoubleObjectType {
    private static final DoubleType singleTon = new DoubleType();

    public static DoubleType getSingleton() {
        return singleTon;
    }

    private DoubleType() {
        super(SqlType.DOUBLE, new Class[]{Double.TYPE});
    }

    protected DoubleType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public boolean isPrimitive() {
        return true;
    }
}
