package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseResults;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class DateTimeType extends BaseDataType {
    private static final String[] associatedClassNames = {"org.joda.time.DateTime"};
    private static Class<?> dateTimeClass = null;
    private static Method getMillisMethod = null;
    private static Constructor<?> millisConstructor = null;
    private static final DateTimeType singleTon = new DateTimeType();

    private DateTimeType() {
        super(SqlType.LONG, new Class[0]);
    }

    protected DateTimeType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public static DateTimeType getSingleton() {
        return singleTon;
    }

    public String[] getAssociatedClassNames() {
        return associatedClassNames;
    }

    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        try {
            Method method = getMillisMethod();
            if (javaObject == null) {
                return null;
            }
            return method.invoke(javaObject, new Object[0]);
        } catch (Exception e) {
            throw SqlExceptionUtil.create("Could not use reflection to get millis from Joda DateTime: " + javaObject, e);
        }
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return Long.valueOf(Long.parseLong(defaultStr));
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return Long.valueOf(results.getLong(columnPos));
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        try {
            return getConstructor().newInstance(new Object[]{(Long) sqlArg});
        } catch (Exception e) {
            throw SqlExceptionUtil.create("Could not use reflection to construct a Joda DateTime", e);
        }
    }

    public boolean isEscapedValue() {
        return false;
    }

    public boolean isAppropriateId() {
        return false;
    }

    public Class<?> getPrimaryClass() {
        try {
            return getDateTimeClass();
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private Method getMillisMethod() {
        if (getMillisMethod == null) {
            getMillisMethod = getDateTimeClass().getMethod("getMillis", new Class[0]);
        }
        return getMillisMethod;
    }

    private Constructor<?> getConstructor() {
        if (millisConstructor == null) {
            millisConstructor = getDateTimeClass().getConstructor(new Class[]{Long.TYPE});
        }
        return millisConstructor;
    }

    private Class<?> getDateTimeClass() {
        if (dateTimeClass == null) {
            dateTimeClass = Class.forName("org.joda.time.DateTime");
        }
        return dateTimeClass;
    }
}
