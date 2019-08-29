package com.alipay.mobile.security.bio.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {
    public static Object getFieldValue(Object obj, String str) {
        Object obj2 = null;
        if (obj == null) {
            return obj2;
        }
        try {
            Field declaredField = obj.getClass().getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField.get(obj);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
            return obj2;
        }
    }

    public static Object invokeMethod(Object obj, String str) {
        Object obj2 = null;
        if (obj == null) {
            return obj2;
        }
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod(str, new Class[0]);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(obj, new Object[0]);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            return obj2;
        }
    }

    public static Object invokeMethod(Object obj, String str, Class[] clsArr, Object[] objArr) {
        Object obj2 = null;
        if (obj == null) {
            return obj2;
        }
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod(str, clsArr);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(obj, objArr);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            return obj2;
        }
    }

    public static Object invokeStaticMethod(String str, String str2) {
        boolean z = false;
        try {
            Method declaredMethod = Class.forName(str).getDeclaredMethod(str2, new Class[0]);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(null, new Object[0]);
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            return z;
        }
    }

    public static Object invokeStaticMethod(String str, String str2, Class[] clsArr, Object[] objArr) {
        boolean z = false;
        try {
            Method declaredMethod = Class.forName(str).getDeclaredMethod(str2, clsArr);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(null, objArr);
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            return z;
        }
    }

    public static boolean checkType(Object obj, String str) {
        return obj != null && obj.getClass().getName().equals(str);
    }
}
