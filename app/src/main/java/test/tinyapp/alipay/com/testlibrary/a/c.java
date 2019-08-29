package test.tinyapp.alipay.com.testlibrary.a;

import java.lang.reflect.Method;

/* compiled from: ReflectUtil */
public final class c {
    public static Object a(String className, String methodName) {
        boolean z = false;
        try {
            Method method = Class.forName(className).getDeclaredMethod(methodName, null);
            method.setAccessible(true);
            return method.invoke(null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return z;
        }
    }

    public static Enum[] a(String enumClassName) {
        Enum[] enumConstants = null;
        try {
            return (Enum[]) Class.forName(enumClassName).getEnumConstants();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return enumConstants;
        } catch (Exception e2) {
            e2.printStackTrace();
            return enumConstants;
        }
    }
}
