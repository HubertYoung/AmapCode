package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;

public class CharType extends CharacterObjectType {
    private static final CharType singleTon = new CharType();

    public static CharType getSingleton() {
        return singleTon;
    }

    private CharType() {
        super(SqlType.CHAR, new Class[]{Character.TYPE});
    }

    protected CharType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        Character character = (Character) javaObject;
        if (character == null || character.charValue() == 0) {
            return null;
        }
        return character;
    }

    public boolean isPrimitive() {
        return true;
    }
}
