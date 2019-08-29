package com.autonavi.minimap.ajx3.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtil {
    public static Object invokeMethod(String str, String str2, Class[] clsArr, Object[] objArr) throws Exception {
        return invokeMethod(Class.forName(str), null, str2, clsArr, objArr);
    }

    public static Object invokeMethod(Object obj, String str, Class[] clsArr, Object[] objArr) throws Exception {
        return invokeMethod(obj.getClass(), obj, str, clsArr, objArr);
    }

    public static Object invokeMethod(Object obj, String str) throws Exception {
        return invokeMethod(obj.getClass(), obj, str, null, null);
    }

    public static Object invokeMethod(Class cls, Object obj, String str, Class[] clsArr, Object[] objArr) throws Exception {
        Method method;
        if (clsArr == null) {
            method = cls.getMethod(str, new Class[0]);
        } else {
            method = cls.getMethod(str, clsArr);
        }
        method.setAccessible(true);
        if (objArr == null) {
            return method.invoke(obj, new Object[0]);
        }
        return method.invoke(obj, objArr);
    }

    public static Field fieldGetOrg(Object obj, String str) throws Exception {
        Field declaredField = obj.getClass().getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField;
    }

    public static Field fieldGetOrg(Object obj, Class<?> cls, String str) throws Exception {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField;
    }

    public static void fieldSet(Object obj, String str, Object obj2) throws Exception {
        Field declaredField = obj.getClass().getDeclaredField(str);
        declaredField.setAccessible(true);
        declaredField.set(obj, obj2);
    }

    public static void fieldSet(Object obj, Class<?> cls, String str, Object obj2) throws Exception {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        declaredField.set(obj, obj2);
    }

    public static Object fieldGet(Object obj, String str) throws Exception {
        Field declaredField = obj.getClass().getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField.get(obj);
    }

    public static Object fieldGet(Object obj, Class<?> cls, String str) throws Exception {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField.get(obj);
    }

    public static Object getField(Object obj, String str) throws NoSuchFieldException, IllegalAccessException {
        return prepareField(obj.getClass(), str).get(obj);
    }

    public static void setField(Object obj, String str, Object obj2) throws NoSuchFieldException, IllegalAccessException {
        prepareField(obj.getClass(), str).set(obj, obj2);
    }

    private static Field prepareField(Class<? super T> cls, String str) throws NoSuchFieldException {
        while (cls != null) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                declaredField.setAccessible(true);
                return declaredField;
            } catch (Exception unused) {
            } finally {
                cls.getSuperclass();
            }
        }
        throw new NoSuchFieldException();
    }
}
