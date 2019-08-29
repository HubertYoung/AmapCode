package com.ali.auth.third.core.util;

import com.ali.auth.third.core.trace.SDKLogger;
import com.alipay.multimedia.js.video.H5VideoUploadPlugin.UploadVideoParams;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtils {
    private static final Map<String, Class<?>> a = new HashMap();
    private static final String b = "ReflectionUtils";

    static {
        a.put(UploadVideoParams.TYPE_SHORT, Short.TYPE);
        a.put("int", Integer.TYPE);
        a.put("long", Long.TYPE);
        a.put("double", Double.TYPE);
        a.put("float", Float.TYPE);
        a.put("char", Character.TYPE);
        a.put("boolean", Boolean.TYPE);
    }

    public static Object invoke(String str, String str2, String[] strArr, Object obj, Object[] objArr) {
        Class[] clsArr;
        try {
            Class<?> cls = Class.forName(str);
            if (strArr != null) {
                if (strArr.length != 0) {
                    clsArr = toClasses(strArr);
                    return cls.getMethod(str2, clsArr).invoke(obj, objArr);
                }
            }
            clsArr = new Class[0];
            return cls.getMethod(str2, clsArr).invoke(obj, objArr);
        } catch (Exception e) {
            String str3 = b;
            StringBuilder sb = new StringBuilder("Fail to invoke the ");
            sb.append(str);
            sb.append(".");
            sb.append(str2);
            sb.append(", the error is ");
            sb.append(e.getMessage());
            SDKLogger.e(str3, sb.toString());
            throw new RuntimeException(e);
        }
    }

    public static Class<?> loadClassQuietly(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static <T> T newInstance(Class<T> cls, Class<?>[] clsArr, Object[] objArr) {
        if (clsArr != null) {
            try {
                if (clsArr.length != 0) {
                    return cls.getConstructor(clsArr).newInstance(objArr);
                }
            } catch (Exception e) {
                String str = b;
                StringBuilder sb = new StringBuilder("Fail to create the instance of type ");
                sb.append(cls.getName());
                sb.append(", the error is ");
                sb.append(e.getMessage());
                SDKLogger.e(str, sb.toString());
                throw new RuntimeException(e);
            }
        }
        return cls.newInstance();
    }

    public static Object newInstance(String str, String[] strArr, Object[] objArr) {
        try {
            return newInstance(Class.forName(str), (Class<?>[]) toClasses(strArr), objArr);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            String str2 = b;
            StringBuilder sb = new StringBuilder("Fail to create the instance of type ");
            sb.append(str);
            sb.append(", the error is ");
            sb.append(e2.getMessage());
            SDKLogger.e(str2, sb.toString());
            throw new RuntimeException(e2);
        }
    }

    public static void set(Object obj, String str, Object obj2) {
        try {
            Field field = obj.getClass().getField(str);
            field.setAccessible(true);
            field.set(obj, obj2);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
    }

    public static Class<?>[] toClasses(String[] strArr) throws Exception {
        if (strArr == null) {
            return null;
        }
        Class<?>[] clsArr = new Class[strArr.length];
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            String str = strArr[i];
            if (str.length() < 8) {
                clsArr[i] = a.get(str);
            }
            if (clsArr[i] == null) {
                clsArr[i] = Class.forName(str);
            }
        }
        return clsArr;
    }
}
