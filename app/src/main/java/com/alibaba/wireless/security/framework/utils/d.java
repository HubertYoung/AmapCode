package com.alibaba.wireless.security.framework.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class d {
    private final Object a;
    private final boolean b = false;

    static class a {
        private a() {
        }
    }

    private d(Object obj) {
        this.a = obj;
    }

    public static d a(Object obj) {
        return new d(obj);
    }

    private static d a(Method method, Object obj, Object... objArr) throws e {
        try {
            a((T) method);
            if (method.getReturnType() != Void.TYPE) {
                return a(method.invoke(obj, objArr));
            }
            method.invoke(obj, objArr);
            return a(obj);
        } catch (Exception e) {
            throw new e(e);
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r1v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Class<?> a(java.lang.Class r1) {
        /*
            if (r1 != 0) goto L_0x0004
            r1 = 0
            return r1
        L_0x0004:
            boolean r0 = r1.isPrimitive()
            if (r0 == 0) goto L_0x0048
            java.lang.Class r0 = java.lang.Boolean.TYPE
            if (r0 != r1) goto L_0x0011
            java.lang.Class<java.lang.Boolean> r1 = java.lang.Boolean.class
            return r1
        L_0x0011:
            java.lang.Class r0 = java.lang.Integer.TYPE
            if (r0 != r1) goto L_0x0018
            java.lang.Class<java.lang.Integer> r1 = java.lang.Integer.class
            return r1
        L_0x0018:
            java.lang.Class r0 = java.lang.Long.TYPE
            if (r0 != r1) goto L_0x001f
            java.lang.Class<java.lang.Long> r1 = java.lang.Long.class
            return r1
        L_0x001f:
            java.lang.Class r0 = java.lang.Short.TYPE
            if (r0 != r1) goto L_0x0026
            java.lang.Class<java.lang.Short> r1 = java.lang.Short.class
            return r1
        L_0x0026:
            java.lang.Class r0 = java.lang.Byte.TYPE
            if (r0 != r1) goto L_0x002d
            java.lang.Class<java.lang.Byte> r1 = java.lang.Byte.class
            return r1
        L_0x002d:
            java.lang.Class r0 = java.lang.Double.TYPE
            if (r0 != r1) goto L_0x0034
            java.lang.Class<java.lang.Double> r1 = java.lang.Double.class
            return r1
        L_0x0034:
            java.lang.Class r0 = java.lang.Float.TYPE
            if (r0 != r1) goto L_0x003b
            java.lang.Class<java.lang.Float> r1 = java.lang.Float.class
            return r1
        L_0x003b:
            java.lang.Class r0 = java.lang.Character.TYPE
            if (r0 != r1) goto L_0x0042
            java.lang.Class<java.lang.Character> r1 = java.lang.Character.class
            return r1
        L_0x0042:
            java.lang.Class r0 = java.lang.Void.TYPE
            if (r0 != r1) goto L_0x0048
            java.lang.Class<java.lang.Void> r1 = java.lang.Void.class
        L_0x0048:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.framework.utils.d.a(java.lang.Class):java.lang.Class");
    }

    public static <T extends AccessibleObject> T a(T t) {
        if (t == null) {
            return null;
        }
        if (t instanceof Member) {
            Member member = (Member) t;
            if (Modifier.isPublic(member.getModifiers()) && Modifier.isPublic(member.getDeclaringClass().getModifiers())) {
                return t;
            }
        }
        if (!t.isAccessible()) {
            t.setAccessible(true);
        }
        return t;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:4|5|6) */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0019, code lost:
        throw new java.lang.NoSuchMethodException();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000d, code lost:
        return r0.getDeclaredMethod(r3, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x000e, code lost:
        r0 = r0.getSuperclass();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
        if (r0 != null) goto L_0x0009;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:4:0x0009 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.reflect.Method a(java.lang.String r3, java.lang.Class<?>[] r4) throws java.lang.NoSuchMethodException {
        /*
            r2 = this;
            java.lang.Class r0 = r2.b()
            java.lang.reflect.Method r1 = r0.getMethod(r3, r4)     // Catch:{ NoSuchMethodException -> 0x0009 }
            return r1
        L_0x0009:
            java.lang.reflect.Method r1 = r0.getDeclaredMethod(r3, r4)     // Catch:{ NoSuchMethodException -> 0x000e }
            return r1
        L_0x000e:
            java.lang.Class r0 = r0.getSuperclass()
            if (r0 != 0) goto L_0x0009
            java.lang.NoSuchMethodException r3 = new java.lang.NoSuchMethodException
            r3.<init>()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.framework.utils.d.a(java.lang.String, java.lang.Class[]):java.lang.reflect.Method");
    }

    private boolean a(Method method, String str, Class<?>[] clsArr) {
        return method.getName().equals(str) && a((Class<?>[]) method.getParameterTypes(), clsArr);
    }

    private boolean a(Class<?>[] clsArr, Class<?>[] clsArr2) {
        if (clsArr.length != clsArr2.length) {
            return false;
        }
        for (int i = 0; i < clsArr2.length; i++) {
            if (clsArr2[i] != a.class && !a(clsArr[i]).isAssignableFrom(a(clsArr2[i]))) {
                return false;
            }
        }
        return true;
    }

    private static Class<?>[] a(Object... objArr) {
        if (objArr == null) {
            return new Class[0];
        }
        Class[] clsArr = new Class[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            Object obj = objArr[i];
            clsArr[i] = obj == null ? a.class : obj.getClass();
        }
        return clsArr;
    }

    private Method b(String str, Class<?>[] clsArr) throws NoSuchMethodException {
        Method[] methods;
        Method[] declaredMethods;
        Class b2 = b();
        for (Method method : b2.getMethods()) {
            if (a(method, str, clsArr)) {
                return method;
            }
        }
        do {
            for (Method method2 : b2.getDeclaredMethods()) {
                if (a(method2, str, clsArr)) {
                    return method2;
                }
            }
            b2 = b2.getSuperclass();
        } while (b2 != null);
        StringBuilder sb = new StringBuilder("No similar method ");
        sb.append(str);
        sb.append(" with params ");
        sb.append(Arrays.toString(clsArr));
        sb.append(" could be found on type ");
        sb.append(b());
        sb.append(".");
        throw new NoSuchMethodException(sb.toString());
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:4|5|6) */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0019, code lost:
        return a(b(r4, r0), r3.a, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001a, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0020, code lost:
        throw new com.alibaba.wireless.security.framework.utils.e(r4);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:4:0x000f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alibaba.wireless.security.framework.utils.d a(java.lang.String r4, java.lang.Object... r5) throws com.alibaba.wireless.security.framework.utils.e {
        /*
            r3 = this;
            java.lang.Class[] r0 = a(r5)
            java.lang.reflect.Method r1 = r3.a(r4, (java.lang.Class<?>[]) r0)     // Catch:{ NoSuchMethodException -> 0x000f }
            java.lang.Object r2 = r3.a     // Catch:{ NoSuchMethodException -> 0x000f }
            com.alibaba.wireless.security.framework.utils.d r1 = a(r1, r2, r5)     // Catch:{ NoSuchMethodException -> 0x000f }
            return r1
        L_0x000f:
            java.lang.reflect.Method r4 = r3.b(r4, r0)     // Catch:{ NoSuchMethodException -> 0x001a }
            java.lang.Object r0 = r3.a     // Catch:{ NoSuchMethodException -> 0x001a }
            com.alibaba.wireless.security.framework.utils.d r4 = a(r4, r0, r5)     // Catch:{ NoSuchMethodException -> 0x001a }
            return r4
        L_0x001a:
            r4 = move-exception
            com.alibaba.wireless.security.framework.utils.e r5 = new com.alibaba.wireless.security.framework.utils.e
            r5.<init>(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.framework.utils.d.a(java.lang.String, java.lang.Object[]):com.alibaba.wireless.security.framework.utils.d");
    }

    public <T> T a() {
        return this.a;
    }

    public Class<?> b() {
        return this.b ? (Class) this.a : this.a.getClass();
    }

    public boolean equals(Object obj) {
        if (obj instanceof d) {
            return this.a.equals(((d) obj).a());
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public String toString() {
        return this.a.toString();
    }
}
