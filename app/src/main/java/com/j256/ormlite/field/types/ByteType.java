package com.j256.ormlite.field.types;

import com.j256.ormlite.field.SqlType;

public class ByteType extends ByteObjectType {
    private static final ByteType singleTon = new ByteType();

    public static ByteType getSingleton() {
        return singleTon;
    }

    private ByteType() {
        super(SqlType.BYTE, new Class[]{Byte.TYPE});
    }

    protected ByteType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public boolean isPrimitive() {
        return true;
    }
}
