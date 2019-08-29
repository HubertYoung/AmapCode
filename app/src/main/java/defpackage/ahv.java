package defpackage;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* renamed from: ahv reason: default package */
/* compiled from: ReflectUtil */
public final class ahv {
    public static Object a(Object obj, String str) throws NoSuchFieldException, IllegalAccessException {
        return b(obj.getClass(), str).get(obj);
    }

    public static void a(Object obj, String str, Object obj2) throws NoSuchFieldException, IllegalAccessException {
        b(obj.getClass(), str).set(obj, obj2);
    }

    private static Field b(Class<? super T> cls, String str) throws NoSuchFieldException {
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

    public static Object a(Class<?> cls, Object obj, String str, Class<?>[] clsArr, Object[] objArr) throws Exception {
        Method a = a(cls, str, clsArr);
        if (objArr == null) {
            return a.invoke(obj, new Object[0]);
        }
        return a.invoke(obj, objArr);
    }

    public static Object a(Class<?> cls, String str) throws Exception {
        return a(cls, null, str, null, null);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r2v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.reflect.Method a(java.lang.Class r2, java.lang.String r3, java.lang.Class<?>[] r4) throws java.lang.NoSuchMethodException {
        /*
        L_0x0000:
            if (r2 == 0) goto L_0x0018
            java.lang.reflect.Method r0 = r2.getDeclaredMethod(r3, r4)     // Catch:{ Exception -> 0x0013, all -> 0x000e }
            r1 = 1
            r0.setAccessible(r1)     // Catch:{ Exception -> 0x0013, all -> 0x000e }
            r2.getSuperclass()
            return r0
        L_0x000e:
            r3 = move-exception
            r2.getSuperclass()
            throw r3
        L_0x0013:
            java.lang.Class r2 = r2.getSuperclass()
            goto L_0x0000
        L_0x0018:
            java.lang.NoSuchMethodException r2 = new java.lang.NoSuchMethodException
            r2.<init>()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ahv.a(java.lang.Class, java.lang.String, java.lang.Class[]):java.lang.reflect.Method");
    }
}
