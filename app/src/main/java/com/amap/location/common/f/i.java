package com.amap.location.common.f;

import java.lang.reflect.Method;

/* compiled from: ReflectUtil */
public class i {
    public static Object a(String str, String str2, Object[] objArr, Class<?>[] clsArr) throws Exception {
        Method declaredMethod = Class.forName(str).getDeclaredMethod(str2, clsArr);
        if (!declaredMethod.isAccessible()) {
            declaredMethod.setAccessible(true);
        }
        return declaredMethod.invoke(null, objArr);
    }

    public static Object a(String str, String str2) throws Exception {
        Method declaredMethod = Class.forName(str).getDeclaredMethod(str2, new Class[0]);
        if (!declaredMethod.isAccessible()) {
            declaredMethod.setAccessible(true);
        }
        return declaredMethod.invoke(null, new Object[0]);
    }
}
