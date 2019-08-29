package com.j256.ormlite.field.types;

import com.j256.ormlite.field.SqlType;

public class IntType extends IntegerObjectType {
    private static final IntType singleTon = new IntType();

    public static IntType getSingleton() {
        return singleTon;
    }

    private IntType() {
        super(SqlType.INTEGER, new Class[]{Integer.TYPE});
    }

    protected IntType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public boolean isPrimitive() {
        return true;
    }
}
