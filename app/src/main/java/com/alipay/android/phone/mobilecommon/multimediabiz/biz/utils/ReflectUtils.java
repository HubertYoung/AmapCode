package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class ReflectUtils {
    private static Field a;
    private static Field b;

    public static Class getClass(String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            return null;
        }
    }

    public static Method getMethod(Class clazz, String methodName, Class<?>... args) {
        Method method = null;
        if (clazz == null) {
            return method;
        }
        try {
            return clazz.getDeclaredMethod(methodName, args);
        } catch (Exception e) {
            return method;
        }
    }

    public static Method getMethod(String clazzName, String methodName, Class<?>... args) {
        if (!TextUtils.isEmpty(clazzName)) {
            Class clazz = getClass(clazzName);
            if (clazz != null) {
                return getMethod(clazz, methodName, args);
            }
        }
        return null;
    }

    public static <T> T invoke(Object obj, Method method, Object... args) {
        T t = null;
        if (method == null) {
            return t;
        }
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            return t;
        }
    }

    public static Field getField(Class clazz, String fieldName) {
        Field field = null;
        if (clazz == null) {
            return field;
        }
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (Exception e) {
            return field;
        }
    }

    public static boolean setField(Object obj, Field field, Object fieldValue) {
        if (obj == null || field == null) {
            return false;
        }
        try {
            field.setAccessible(true);
            field.set(obj, fieldValue);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Method findMethod(Class clazz, String methodName, Class<?>... args) {
        Method method = getMethod(clazz, methodName, args);
        if (method != null || clazz == null) {
            return method;
        }
        return findMethod((Class) clazz.getSuperclass(), methodName, args);
    }

    public static Method findMethod(String clazzName, String methodName, Class<?>... args) {
        return findMethod(getClass(clazzName), methodName, args);
    }

    public static <T> Class<T> getClassGenericType(Class clazz) {
        return getClassGenericType(clazz, 0);
    }

    public static Class getClassGenericType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    public static <T> T getFieldValue(Field field, Object obj) {
        T t = null;
        if (field == null) {
            return t;
        }
        try {
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            return t;
        }
    }

    public static <T> T getFieldValue(Class clazz, String fieldName, Object obj) {
        T t = null;
        if (TextUtils.isEmpty(fieldName)) {
            return t;
        }
        Field field = getField(clazz, fieldName);
        if (field == null) {
            return t;
        }
        try {
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            return t;
        }
    }

    public static void removeViewAttacheListeners(View view) {
        try {
            if (a == null || b == null) {
                Field declaredField = View.class.getDeclaredField("mListenerInfo");
                a = declaredField;
                declaredField.setAccessible(true);
                Logger.D("ReflectUtils", "found mListenerField", new Object[0]);
                Class[] innerClasses = View.class.getDeclaredClasses();
                int i = innerClasses.length - 1;
                while (true) {
                    if (i < 0) {
                        break;
                    } else if (innerClasses[i].getSimpleName().contains("ListenerInfo")) {
                        Field declaredField2 = innerClasses[i].getDeclaredField("mOnAttachStateChangeListeners");
                        b = declaredField2;
                        declaredField2.setAccessible(true);
                        Logger.D("ReflectUtils", "found mAttachField", new Object[0]);
                        break;
                    } else {
                        i--;
                    }
                }
            }
            Object mListenerInfo = a.get(view);
            if (mListenerInfo != null) {
                CopyOnWriteArrayList mListeners = (CopyOnWriteArrayList) b.get(mListenerInfo);
                if (mListeners != null && !mListeners.isEmpty()) {
                    Logger.P("ReflectUtils", "clear attach listener, former size:" + mListeners.size(), new Object[0]);
                    Iterator it = mListeners.iterator();
                    while (it.hasNext()) {
                        OnAttachStateChangeListener item = (OnAttachStateChangeListener) it.next();
                        if (item instanceof GifDrawableImpl) {
                            mListeners.remove(item);
                        }
                    }
                }
            }
        } catch (Throwable e) {
            Logger.E((String) "ReflectUtils", e, (String) "removeViewAttacheListeners error", new Object[0]);
        }
    }
}
