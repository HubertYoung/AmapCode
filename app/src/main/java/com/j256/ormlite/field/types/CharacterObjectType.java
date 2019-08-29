package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

public class CharacterObjectType extends BaseDataType {
    private static final CharacterObjectType singleTon = new CharacterObjectType();

    public static CharacterObjectType getSingleton() {
        return singleTon;
    }

    private CharacterObjectType() {
        super(SqlType.CHAR, new Class[]{Character.class});
    }

    protected CharacterObjectType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        if (defaultStr.length() == 1) {
            return Character.valueOf(defaultStr.charAt(0));
        }
        throw new SQLException("Problems with field " + fieldType + ", default string to long for Character: '" + defaultStr + "'");
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return Character.valueOf(results.getChar(columnPos));
    }
}
