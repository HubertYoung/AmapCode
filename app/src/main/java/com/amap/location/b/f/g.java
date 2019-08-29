package com.amap.location.b.f;

import java.lang.reflect.Method;

/* compiled from: Reflect */
public class g {
    public static Object a(Object obj, String str, Object... objArr) throws Exception {
        Class<?> cls = obj.getClass();
        Class<Integer>[] clsArr = new Class[objArr.length];
        int length = objArr.length;
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
            if (clsArr[i] == Integer.class) {
                clsArr[i] = Integer.TYPE;
            }
        }
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        if (!declaredMethod.isAccessible()) {
            declaredMethod.setAccessible(true);
        }
        return declaredMethod.invoke(obj, objArr);
    }
}
