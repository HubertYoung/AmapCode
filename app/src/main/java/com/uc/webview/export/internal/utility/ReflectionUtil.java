package com.uc.webview.export.internal.utility;

import com.uc.webview.export.annotations.Interface;
import com.uc.webview.export.internal.SDKFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Interface
/* compiled from: ProGuard */
public class ReflectionUtil {

    @Interface
    /* compiled from: ProGuard */
    public static final class BindingMethod<T> {
        private Class<?> a;
        private Method b;
        private T c;

        public BindingMethod(Class<?> cls, String str) {
            this(cls, str, null);
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(3:4|5|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:5:?, code lost:
            r1.b = r1.a.getMethod(r3, r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:6:0x001d, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:7:0x001e, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0028, code lost:
            throw new java.lang.NoSuchMethodError(r2.getMessage());
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:4:0x0015 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public BindingMethod(java.lang.Class<?> r2, java.lang.String r3, java.lang.Class<?>[] r4) {
            /*
                r1 = this;
                r1.<init>()
                r0 = 0
                r1.a = r0
                r1.b = r0
                r1.c = r0
                r1.a = r2
                java.lang.Class<?> r2 = r1.a     // Catch:{ Throwable -> 0x0015 }
                java.lang.reflect.Method r2 = r2.getDeclaredMethod(r3, r4)     // Catch:{ Throwable -> 0x0015 }
                r1.b = r2     // Catch:{ Throwable -> 0x0015 }
                return
            L_0x0015:
                java.lang.Class<?> r2 = r1.a     // Catch:{ NoSuchMethodException -> 0x001e }
                java.lang.reflect.Method r2 = r2.getMethod(r3, r4)     // Catch:{ NoSuchMethodException -> 0x001e }
                r1.b = r2     // Catch:{ NoSuchMethodException -> 0x001e }
                return
            L_0x001e:
                r2 = move-exception
                java.lang.NoSuchMethodError r3 = new java.lang.NoSuchMethodError
                java.lang.String r2 = r2.getMessage()
                r3.<init>(r2)
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.utility.ReflectionUtil.BindingMethod.<init>(java.lang.Class, java.lang.String, java.lang.Class[]):void");
        }

        public final T invoke() {
            return invoke(null, null);
        }

        public final T invoke(Object[] objArr) {
            return invoke(null, objArr);
        }

        public final T invoke(Object obj) {
            return invoke(obj, null);
        }

        public final T invoke(Object obj, Object[] objArr) {
            try {
                return this.b.invoke(obj, objArr);
            } catch (InvocationTargetException e) {
                Throwable targetException = e.getTargetException();
                if (targetException instanceof RuntimeException) {
                    throw ((RuntimeException) targetException);
                } else if (targetException instanceof Error) {
                    throw ((Error) targetException);
                } else {
                    throw new RuntimeException(targetException);
                }
            } catch (RuntimeException e2) {
                throw e2;
            } catch (Exception e3) {
                throw new RuntimeException(e3);
            }
        }

        public final T getInstance() {
            if (this.c == null) {
                this.c = a();
            }
            return this.c;
        }

        private synchronized T a() {
            if (this.c == null) {
                this.c = invoke();
            }
            return this.c;
        }
    }

    public static Method getMethod(Class<?> cls, String str) {
        return getMethod(cls, str, null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0010, code lost:
        r0 = r6.getDeclaredMethod(r7, r8);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x000c */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0047  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.reflect.Method getMethod(java.lang.Class<?> r6, java.lang.String r7, java.lang.Class<?>... r8) {
        /*
            r0 = 0
            if (r6 != 0) goto L_0x0004
            return r0
        L_0x0004:
            if (r8 == 0) goto L_0x0012
            java.lang.reflect.Method r1 = r6.getMethod(r7, r8)     // Catch:{ Throwable -> 0x000c }
        L_0x000a:
            r0 = r1
            goto L_0x0045
        L_0x000c:
            java.lang.reflect.Method r6 = r6.getDeclaredMethod(r7, r8)     // Catch:{ Throwable -> 0x0045 }
            r0 = r6
            goto L_0x0045
        L_0x0012:
            java.lang.reflect.Method[] r8 = r6.getMethods()
            int r1 = r8.length
            r2 = 0
            r3 = 0
        L_0x0019:
            if (r3 >= r1) goto L_0x002c
            r4 = r8[r3]
            java.lang.String r5 = r4.getName()
            boolean r5 = r5.equals(r7)
            if (r5 == 0) goto L_0x0029
            r0 = r4
            goto L_0x002c
        L_0x0029:
            int r3 = r3 + 1
            goto L_0x0019
        L_0x002c:
            if (r0 != 0) goto L_0x0045
            java.lang.reflect.Method[] r6 = r6.getDeclaredMethods()
            int r8 = r6.length
        L_0x0033:
            if (r2 >= r8) goto L_0x0045
            r1 = r6[r2]
            java.lang.String r3 = r1.getName()
            boolean r3 = r3.equals(r7)
            if (r3 == 0) goto L_0x0042
            goto L_0x000a
        L_0x0042:
            int r2 = r2 + 1
            goto L_0x0033
        L_0x0045:
            if (r0 == 0) goto L_0x004b
            r6 = 1
            r0.setAccessible(r6)
        L_0x004b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.utility.ReflectionUtil.getMethod(java.lang.Class, java.lang.String, java.lang.Class[]):java.lang.reflect.Method");
    }

    public static Object invokeNoThrow(String str, String str2) {
        try {
            return invoke(str, str2);
        } catch (Exception unused) {
            return null;
        }
    }

    public static Object invokeNoThrow(Class<?> cls, String str) {
        try {
            return invoke((Object) cls, str);
        } catch (Exception unused) {
            return null;
        }
    }

    public static Object invokeNoThrow(Object obj, String str) {
        try {
            return invoke(obj, str);
        } catch (Exception unused) {
            return null;
        }
    }

    public static Object invokeNoThrow(Object obj, String str, Class[] clsArr, Object[] objArr) {
        try {
            return invoke(obj, str, clsArr, objArr);
        } catch (Exception unused) {
            return null;
        }
    }

    public static Object invokeNoThrow(String str, String str2, Class[] clsArr, Object[] objArr) throws Exception {
        try {
            return invoke(Class.forName(str, true, SDKFactory.c), str2, clsArr, objArr);
        } catch (Exception unused) {
            return null;
        }
    }

    public static Object invoke(String str, String str2) throws Exception {
        return invoke(str, str2, (Class[]) null, (Object[]) null);
    }

    public static Object invoke(String str, String str2, Class[] clsArr) throws Exception {
        return invoke(str, str2, clsArr, (Object[]) null);
    }

    public static Object invoke(String str, String str2, Object[] objArr) throws Exception {
        Class[] clsArr;
        if (objArr != null) {
            clsArr = new Class[objArr.length];
            int length = objArr.length;
            for (int i = 0; i < length; i++) {
                clsArr[i] = objArr[i].getClass();
            }
        } else {
            clsArr = null;
        }
        return invoke(str, str2, clsArr, objArr);
    }

    public static Object invoke(String str, String str2, Class[] clsArr, Object[] objArr) throws Exception {
        return invoke(Class.forName(str, true, SDKFactory.c), str2, clsArr, objArr);
    }

    public static Object invoke(Object obj, String str) throws Exception {
        return invoke(obj, str, (Class[]) null, new Object[0]);
    }

    public static Object invoke(Object obj, String str, Class[] clsArr, Object[] objArr) throws Exception {
        if (obj == null) {
            return null;
        }
        return invoke(obj, obj.getClass(), str, clsArr, objArr);
    }

    public static Object invoke(Class<?> cls, String str, Class[] clsArr, Object[] objArr) throws Exception {
        return invoke(null, cls, str, clsArr, objArr);
    }

    public static Object invoke(Object obj, Class<?> cls, String str, Class[] clsArr, Object[] objArr) throws Exception {
        Method method;
        try {
            method = cls.getDeclaredMethod(str, clsArr);
        } catch (Throwable unused) {
            method = cls.getMethod(str, clsArr);
        }
        return invoke(obj, cls, method, objArr);
    }

    public static Object invoke(Object obj, Class<?> cls, Method method, Object[] objArr) throws Exception {
        method.setAccessible(true);
        try {
            return method.invoke(obj, objArr);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException instanceof Exception) {
                throw ((Exception) targetException);
            } else if (targetException instanceof Error) {
                throw ((Error) targetException);
            } else {
                throw new RuntimeException(targetException);
            }
        }
    }

    public static Object newInstanceNoThrow(String str) {
        try {
            return newInstance(str);
        } catch (Exception unused) {
            return null;
        }
    }

    public static Object newInstance(String str) throws Exception {
        return newInstance(str, null, null);
    }

    public static Object newInstance(String str, Class[] clsArr, Object[] objArr) throws Exception {
        try {
            Constructor<?> constructor = Class.forName(str, true, SDKFactory.c).getConstructor(clsArr);
            constructor.setAccessible(true);
            return constructor.newInstance(objArr);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException instanceof Exception) {
                throw ((Exception) targetException);
            } else if (targetException instanceof Error) {
                throw ((Error) targetException);
            } else {
                throw new RuntimeException(targetException);
            }
        }
    }

    public static ClassLoader getCoreClassLoader() {
        return SDKFactory.c;
    }

    public static Object getStaticField(String str, String str2) throws Exception {
        try {
            return Class.forName(str, true, SDKFactory.c).getField(str2).get(null);
        } catch (Exception unused) {
            return null;
        }
    }
}
