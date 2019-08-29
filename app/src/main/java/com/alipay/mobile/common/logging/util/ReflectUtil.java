package com.alipay.mobile.common.logging.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtil {
    public static Object invokeMethod(Object classOrName, String methodName, Class<?>[] argsClasses, Object targetObject, Object[] argsObjects) {
        Class clazz;
        if (classOrName instanceof Class) {
            clazz = (Class) classOrName;
        } else {
            clazz = Class.forName(String.valueOf(classOrName));
        }
        Method method = clazz.getDeclaredMethod(methodName, argsClasses);
        method.setAccessible(true);
        return method.invoke(targetObject, argsObjects);
    }

    public static Object getField(Object classOrName, String fieldName, Object targetObject) {
        Class clazz;
        if (classOrName instanceof Class) {
            clazz = (Class) classOrName;
        } else {
            clazz = Class.forName(String.valueOf(classOrName));
        }
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(targetObject);
    }

    public static void setField(Object classOrName, String fieldName, Object targetObject, Object fieldValue) {
        Class clazz;
        if (classOrName instanceof Class) {
            clazz = (Class) classOrName;
        } else {
            clazz = Class.forName(String.valueOf(classOrName));
        }
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, fieldValue);
    }
}
