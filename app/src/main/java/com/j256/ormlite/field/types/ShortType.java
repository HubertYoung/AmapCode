package com.j256.ormlite.field.types;

import com.j256.ormlite.field.SqlType;

public class ShortType extends ShortObjectType {
    private static final ShortType singleTon = new ShortType();

    public static ShortType getSingleton() {
        return singleTon;
    }

    private ShortType() {
        super(SqlType.SHORT, new Class[]{Short.TYPE});
    }

    protected ShortType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public boolean isPrimitive() {
        return true;
    }
}
