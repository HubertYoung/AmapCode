package com.xiaomi.channel.commonutils.reflect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class a {
    private static final Map<Class<?>, Class<?>> a;

    /* renamed from: com.xiaomi.channel.commonutils.reflect.a$a reason: collision with other inner class name */
    public static class C0074a<T> {
        public final Class<? extends T> a;
        public final T b;
    }

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        hashMap.put(Boolean.class, Boolean.TYPE);
        a.put(Byte.class, Byte.TYPE);
        a.put(Character.class, Character.TYPE);
        a.put(Short.class, Short.TYPE);
        a.put(Integer.class, Integer.TYPE);
        a.put(Float.class, Float.TYPE);
        a.put(Long.class, Long.TYPE);
        a.put(Double.class, Double.TYPE);
        Map<Class<?>, Class<?>> map = a;
        Class cls = Boolean.TYPE;
        map.put(cls, cls);
        Map<Class<?>, Class<?>> map2 = a;
        Class cls2 = Byte.TYPE;
        map2.put(cls2, cls2);
        Map<Class<?>, Class<?>> map3 = a;
        Class cls3 = Character.TYPE;
        map3.put(cls3, cls3);
        Map<Class<?>, Class<?>> map4 = a;
        Class cls4 = Short.TYPE;
        map4.put(cls4, cls4);
        Map<Class<?>, Class<?>> map5 = a;
        Class cls5 = Integer.TYPE;
        map5.put(cls5, cls5);
        Map<Class<?>, Class<?>> map6 = a;
        Class cls6 = Float.TYPE;
        map6.put(cls6, cls6);
        Map<Class<?>, Class<?>> map7 = a;
        Class cls7 = Long.TYPE;
        map7.put(cls7, cls7);
        Map<Class<?>, Class<?>> map8 = a;
        Class cls8 = Double.TYPE;
        map8.put(cls8, cls8);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<? extends java.lang.Object>, code=java.lang.Class, for r3v0, types: [java.lang.Class<? extends java.lang.Object>, java.lang.Class] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0014 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> T a(java.lang.Class r3, java.lang.Object r4, java.lang.String r5) {
        /*
            r0 = 0
        L_0x0001:
            r1 = 1
            if (r0 != 0) goto L_0x001a
            java.lang.reflect.Field r2 = r3.getDeclaredField(r5)     // Catch:{ NoSuchFieldException -> 0x000e }
            r2.setAccessible(r1)     // Catch:{ NoSuchFieldException -> 0x000d }
            r0 = r2
            goto L_0x0012
        L_0x000d:
            r0 = r2
        L_0x000e:
            java.lang.Class r3 = r3.getSuperclass()
        L_0x0012:
            if (r3 != 0) goto L_0x0001
            java.lang.NoSuchFieldException r3 = new java.lang.NoSuchFieldException
            r3.<init>()
            throw r3
        L_0x001a:
            r0.setAccessible(r1)
            java.lang.Object r3 = r0.get(r4)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.channel.commonutils.reflect.a.a(java.lang.Class, java.lang.Object, java.lang.String):java.lang.Object");
    }

    public static <T> T a(Class<? extends Object> cls, String str) {
        try {
            return a(cls, (Object) null, str);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static <T> T a(Class<?> cls, String str, Object... objArr) {
        return a(cls, str, (Class<?>[]) a(objArr)).invoke(null, b(objArr));
    }

    public static <T> T a(Object obj, String str) {
        try {
            return a(obj.getClass(), obj, str);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static <T> T a(Object obj, String str, Object... objArr) {
        try {
            return b(obj, str, objArr);
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder("Meet exception when call Method '");
            sb.append(str);
            sb.append("' in ");
            sb.append(obj);
            return null;
        }
    }

    public static <T> T a(String str, String str2) {
        try {
            return a(Class.forName(str), (Object) null, str2);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return null;
        } catch (ClassNotFoundException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public static <T> T a(String str, String str2, Object... objArr) {
        try {
            return a(Class.forName(str), str2, objArr);
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder("Meet exception when call Method '");
            sb.append(str2);
            sb.append("' in ");
            sb.append(str);
            return null;
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r1v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.reflect.Method a(java.lang.Class r1, java.lang.String r2, java.lang.Class<?>... r3) {
        /*
        L_0x0000:
            java.lang.reflect.Method[] r0 = r1.getDeclaredMethods()
            java.lang.reflect.Method r0 = a(r0, r2, (java.lang.Class<?>[]) r3)
            if (r0 != 0) goto L_0x001b
            java.lang.Class r0 = r1.getSuperclass()
            if (r0 == 0) goto L_0x0015
            java.lang.Class r1 = r1.getSuperclass()
            goto L_0x0000
        L_0x0015:
            java.lang.NoSuchMethodException r1 = new java.lang.NoSuchMethodException
            r1.<init>()
            throw r1
        L_0x001b:
            r1 = 1
            r0.setAccessible(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.channel.commonutils.reflect.a.a(java.lang.Class, java.lang.String, java.lang.Class[]):java.lang.reflect.Method");
    }

    private static Method a(Method[] methodArr, String str, Class<?>[] clsArr) {
        if (str == null) {
            throw new NullPointerException("Method name must not be null.");
        }
        for (Method method : methodArr) {
            if (method.getName().equals(str) && a((Class<?>[]) method.getParameterTypes(), clsArr)) {
                return method;
            }
        }
        return null;
    }

    private static boolean a(Class<?>[] clsArr, Class<?>[] clsArr2) {
        if (clsArr == null) {
            return clsArr2 == null || clsArr2.length == 0;
        }
        if (clsArr2 == null) {
            return clsArr.length == 0;
        }
        if (clsArr.length != clsArr2.length) {
            return false;
        }
        for (int i = 0; i < clsArr.length; i++) {
            if (!clsArr[i].isAssignableFrom(clsArr2[i]) && (!a.containsKey(clsArr[i]) || !a.get(clsArr[i]).equals(a.get(clsArr2[i])))) {
                return false;
            }
        }
        return true;
    }

    private static Class<?>[] a(Object... objArr) {
        if (objArr == null || objArr.length <= 0) {
            return null;
        }
        Class[] clsArr = new Class[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            C0074a aVar = objArr[i];
            Class cls = (aVar == null || !(aVar instanceof C0074a)) ? aVar == null ? null : aVar.getClass() : aVar.a;
            clsArr[i] = cls;
        }
        return clsArr;
    }

    public static <T> T b(Object obj, String str, Object... objArr) {
        return a(obj.getClass(), str, (Class<?>[]) a(objArr)).invoke(obj, b(objArr));
    }

    private static Object[] b(Object... objArr) {
        if (objArr == null || objArr.length <= 0) {
            return null;
        }
        Object[] objArr2 = new Object[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            C0074a aVar = objArr[i];
            if (aVar == null || !(aVar instanceof C0074a)) {
                objArr2[i] = aVar;
            } else {
                objArr2[i] = aVar.b;
            }
        }
        return objArr2;
    }
}
