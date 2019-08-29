package com.ali.user.mobile.util;

import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.AliUserLogUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {
    public static Object a(String str) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method method = Class.forName(str).getMethod("getInstance", new Class[0]);
        method.setAccessible(true);
        Object invoke = method.invoke(null, new Object[0]);
        AliUserLog.c("ReflectUtils", "static1 end className = ".concat(String.valueOf(str)));
        return invoke;
    }

    public static Object a(String str, Class<?>[] clsArr, Object[] objArr) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = Class.forName(str).getMethod("getInstance", clsArr);
        method.setAccessible(true);
        Object invoke = method.invoke(null, objArr);
        AliUserLog.c("ReflectUtils", "static2 end className = ".concat(String.valueOf(str)));
        return invoke;
    }

    public static Object a(Object obj, String str) {
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod(str, new Class[0]);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(obj, new Object[0]);
        } catch (Exception e) {
            AliUserLogUtil.a((String) "ReflectUtils", (Throwable) e);
            return null;
        }
    }

    public static Object a(Object obj, String str, Class[] clsArr, Object[] objArr) {
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod(str, clsArr);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(obj, objArr);
        } catch (Exception e) {
            AliUserLogUtil.a((String) "ReflectUtils", (Throwable) e);
            return null;
        }
    }

    public static Object a(String str, String str2) {
        try {
            Method declaredMethod = Class.forName(str).getDeclaredMethod(str2, new Class[0]);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(null, new Object[0]);
        } catch (Exception e) {
            AliUserLogUtil.a((String) "ReflectUtils", (Throwable) e);
            return null;
        }
    }
}
