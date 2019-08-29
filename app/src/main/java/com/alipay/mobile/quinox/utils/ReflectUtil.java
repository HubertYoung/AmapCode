package com.alipay.mobile.quinox.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectUtil {
    private static final Map<String, Field> sFields = new ConcurrentHashMap();
    private static final Map<String, Method> sMethods = new ConcurrentHashMap();

    public static Method getMethod(Class cls, String str) throws NoSuchMethodException {
        return doGetMethod(cls.getName(), cls, str, null);
    }

    public static Method getMethod(Class cls, String str, Class[] clsArr) throws NoSuchMethodException {
        return doGetMethod(cls.getName(), cls, str, clsArr);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r7v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.reflect.Method doGetMethod(java.lang.String r6, java.lang.Class r7, java.lang.String r8, java.lang.Class[] r9) throws java.lang.NoSuchMethodException {
        /*
        L_0x0000:
            java.lang.String r0 = r7.getName()
            java.lang.String r1 = "."
            java.lang.String r0 = r0.concat(r1)
            java.lang.String r0 = r0.concat(r8)
            r1 = 1
            r2 = 0
            if (r9 == 0) goto L_0x0042
            int r3 = r9.length
            if (r3 <= 0) goto L_0x0042
            java.lang.String r3 = "("
            java.lang.String r0 = r0.concat(r3)
            int r3 = r9.length
            r4 = r0
            r0 = 0
        L_0x001e:
            if (r0 >= r3) goto L_0x0033
            r5 = r9[r0]
            java.lang.String r5 = r5.getName()
            java.lang.String r4 = r4.concat(r5)
            java.lang.String r5 = ","
            java.lang.String r4 = r4.concat(r5)
            int r0 = r0 + 1
            goto L_0x001e
        L_0x0033:
            int r0 = r4.length()
            int r0 = r0 - r1
            java.lang.String r0 = r4.substring(r2, r0)
            java.lang.String r3 = ")"
            java.lang.String r0 = r0.concat(r3)
        L_0x0042:
            r3 = 0
            java.util.Map<java.lang.String, java.lang.reflect.Method> r4 = sMethods     // Catch:{ Throwable -> 0x0054 }
            boolean r4 = r4.containsKey(r0)     // Catch:{ Throwable -> 0x0054 }
            if (r4 == 0) goto L_0x0054
            java.util.Map<java.lang.String, java.lang.reflect.Method> r4 = sMethods     // Catch:{ Throwable -> 0x0054 }
            java.lang.Object r4 = r4.get(r0)     // Catch:{ Throwable -> 0x0054 }
            java.lang.reflect.Method r4 = (java.lang.reflect.Method) r4     // Catch:{ Throwable -> 0x0054 }
            r3 = r4
        L_0x0054:
            if (r3 != 0) goto L_0x008d
            if (r9 != 0) goto L_0x005f
            java.lang.Class[] r2 = new java.lang.Class[r2]     // Catch:{ NoSuchMethodException -> 0x006d }
            java.lang.reflect.Method r2 = r7.getDeclaredMethod(r8, r2)     // Catch:{ NoSuchMethodException -> 0x006d }
            goto L_0x0063
        L_0x005f:
            java.lang.reflect.Method r2 = r7.getDeclaredMethod(r8, r9)     // Catch:{ NoSuchMethodException -> 0x006d }
        L_0x0063:
            r3 = r2
            r3.setAccessible(r1)
            java.util.Map<java.lang.String, java.lang.reflect.Method> r6 = sMethods
            r6.put(r0, r3)
            goto L_0x008d
        L_0x006d:
            java.lang.Class r7 = r7.getSuperclass()
            if (r7 != 0) goto L_0x0000
            java.lang.NoSuchMethodException r7 = new java.lang.NoSuchMethodException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r6)
            java.lang.String r6 = "."
            r9.append(r6)
            r9.append(r8)
            java.lang.String r6 = r9.toString()
            r7.<init>(r6)
            throw r7
        L_0x008d:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.quinox.utils.ReflectUtil.doGetMethod(java.lang.String, java.lang.Class, java.lang.String, java.lang.Class[]):java.lang.reflect.Method");
    }

    public static Object invokeMethod(Object obj, String str) throws Exception {
        return invokeMethod(obj.getClass(), obj, str, null, null);
    }

    public static Object invokeMethod(Object obj, String str, Class cls, Object obj2) throws Exception {
        return invokeMethod(obj.getClass(), obj, str, new Class[]{cls}, new Object[]{obj2});
    }

    public static Object invokeMethod(Object obj, String str, Class[] clsArr, Object[] objArr) throws Exception {
        return invokeMethod(obj.getClass(), obj, str, clsArr, objArr);
    }

    public static Object invokeMethod(Class cls, String str) throws Exception {
        return invokeMethod(cls, null, str, null, null);
    }

    public static Object invokeMethod(Class cls, String str, Class[] clsArr, Object[] objArr) throws Exception {
        return invokeMethod(cls, null, str, clsArr, objArr);
    }

    public static Object invokeMethod(Class cls, Object obj, String str, Class[] clsArr, Object[] objArr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return doInvokeMethod(cls.getName(), cls, obj, str, clsArr, objArr);
    }

    @Deprecated
    public static Object invokeMethod(String str, String str2) throws Exception {
        return invokeMethod(Class.forName(str), null, str2, null, null);
    }

    public static Object invokeStaticMethod(String str, String str2) throws Exception {
        return invokeMethod(Class.forName(str), null, str2, null, null);
    }

    public static Object invokeStaticMethod(String str, String str2, Class cls, Object obj) throws Exception {
        return invokeMethod(Class.forName(str), null, str2, new Class[]{cls}, new Object[]{obj});
    }

    public static Object invokeStaticMethod(String str, String str2, Class[] clsArr, Object[] objArr) throws Exception {
        return invokeMethod(Class.forName(str), null, str2, clsArr, objArr);
    }

    private static Object doInvokeMethod(String str, Class cls, Object obj, String str2, Class[] clsArr, Object[] objArr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method doGetMethod = doGetMethod(str, cls, str2, clsArr);
        if (objArr == null) {
            return doGetMethod.invoke(obj, new Object[0]);
        }
        return doGetMethod.invoke(obj, objArr);
    }

    public static void setFieldValue(Object obj, String str, Object obj2) throws NoSuchFieldException, IllegalAccessException {
        setFieldValue(obj.getClass(), obj, str, obj2);
    }

    public static void setFieldValue(Class<?> cls, String str, Object obj) throws NoSuchFieldException, IllegalAccessException {
        setFieldValue(cls, null, str, obj);
    }

    public static void setFieldValue(Class<?> cls, Object obj, String str, Object obj2) throws NoSuchFieldException, IllegalAccessException {
        doGetField(cls.getName(), cls, str).set(obj, obj2);
    }

    public static void setStaticFieldValue(String str, String str2, Object obj) throws Exception {
        doGetField(str, Class.forName(str), str2).set(null, obj);
    }

    public static Object getFieldValue(Object obj, String str) throws NoSuchFieldException, IllegalAccessException {
        return getFieldValue(obj.getClass(), obj, str);
    }

    public static Object getFieldValue(Class<?> cls, String str) throws NoSuchFieldException, IllegalAccessException {
        return getFieldValue(cls, null, str);
    }

    public static Object getFieldValue(Class<?> cls, Object obj, String str) throws NoSuchFieldException, IllegalAccessException {
        return doGetField(cls.getName(), cls, str).get(obj);
    }

    public static Object getStaticFieldValue(String str, String str2) throws Exception {
        return doGetField(str, Class.forName(str), str2).get(null);
    }

    public static Field getFiled(Object obj, String str) throws NoSuchFieldException {
        return getFiled(obj.getClass(), str);
    }

    public static Field getFiled(Class<?> cls, String str) throws NoSuchFieldException {
        return doGetField(cls.getName(), cls, str);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r4v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.reflect.Field doGetField(java.lang.String r3, java.lang.Class r4, java.lang.String r5) throws java.lang.NoSuchFieldException {
        /*
        L_0x0000:
            java.lang.String r0 = r4.getName()
            java.lang.String r1 = "."
            java.lang.String r0 = r0.concat(r1)
            java.lang.String r0 = r0.concat(r5)
            r1 = 0
            java.util.Map<java.lang.String, java.lang.reflect.Field> r2 = sFields     // Catch:{ Throwable -> 0x0020 }
            boolean r2 = r2.containsKey(r0)     // Catch:{ Throwable -> 0x0020 }
            if (r2 == 0) goto L_0x0020
            java.util.Map<java.lang.String, java.lang.reflect.Field> r2 = sFields     // Catch:{ Throwable -> 0x0020 }
            java.lang.Object r2 = r2.get(r0)     // Catch:{ Throwable -> 0x0020 }
            java.lang.reflect.Field r2 = (java.lang.reflect.Field) r2     // Catch:{ Throwable -> 0x0020 }
            r1 = r2
        L_0x0020:
            if (r1 != 0) goto L_0x0050
            java.lang.reflect.Field r1 = r4.getDeclaredField(r5)     // Catch:{ NoSuchFieldException -> 0x0030 }
            r3 = 1
            r1.setAccessible(r3)
            java.util.Map<java.lang.String, java.lang.reflect.Field> r3 = sFields
            r3.put(r0, r1)
            goto L_0x0050
        L_0x0030:
            java.lang.Class r4 = r4.getSuperclass()
            if (r4 != 0) goto L_0x0000
            java.lang.NoSuchFieldException r4 = new java.lang.NoSuchFieldException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r3)
            java.lang.String r3 = "."
            r0.append(r3)
            r0.append(r5)
            java.lang.String r3 = r0.toString()
            r4.<init>(r3)
            throw r4
        L_0x0050:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.quinox.utils.ReflectUtil.doGetField(java.lang.String, java.lang.Class, java.lang.String):java.lang.reflect.Field");
    }
}
