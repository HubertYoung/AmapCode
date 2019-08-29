package com.j256.ormlite.field.types;

import com.j256.ormlite.field.SqlType;

public class BooleanType extends BooleanObjectType {
    private static final BooleanType singleTon = new BooleanType();

    public static BooleanType getSingleton() {
        return singleTon;
    }

    private BooleanType() {
        super(SqlType.BOOLEAN, new Class[]{Boolean.TYPE});
    }

    protected BooleanType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public boolean isPrimitive() {
        return true;
    }
}
