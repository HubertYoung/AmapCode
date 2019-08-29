package com.alipay.mobile.monitor.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {
    private static final String TAG = "MonitorReflectUtils";

    public static void error(String str) {
        error(true, TAG, str);
    }

    public static void error(boolean z, String str, String str2) {
        if (z) {
            str2 = appendThreadName(str2);
        }
        try {
            invokeMethod(AndroidLogger.ANDROID_UTIL_LOG, "e", new Class[]{String.class, String.class}, null, new Object[]{str, str2});
        } catch (Throwable unused) {
        }
    }

    public static void error(boolean z, String str, String str2, Throwable th) {
        if (z) {
            str2 = appendThreadName(str2);
        }
        try {
            invokeMethod(AndroidLogger.ANDROID_UTIL_LOG, "e", new Class[]{String.class, String.class, Throwable.class}, null, new Object[]{str, str2, th});
        } catch (Throwable unused) {
        }
    }

    private static String appendThreadName(String str) {
        String name = Thread.currentThread().getName();
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(name);
        sb.append(']');
        sb.append(str);
        return sb.toString();
    }

    public static Object invokeMethod(Object obj, String str, Class<?>[] clsArr, Object obj2, Object[] objArr) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> cls;
        if (obj instanceof Class) {
            cls = (Class) obj;
        } else {
            cls = Class.forName(String.valueOf(obj));
        }
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        declaredMethod.setAccessible(true);
        return declaredMethod.invoke(obj2, objArr);
    }

    public static Object getField(Object obj, String str, Object obj2) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> cls;
        if (obj instanceof Class) {
            cls = (Class) obj;
        } else {
            cls = Class.forName(String.valueOf(obj));
        }
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField.get(obj2);
    }

    public static void setField(Object obj, String str, Object obj2, Object obj3) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> cls;
        if (obj instanceof Class) {
            cls = (Class) obj;
        } else {
            cls = Class.forName(String.valueOf(obj));
        }
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        declaredField.set(obj2, obj3);
    }
}
