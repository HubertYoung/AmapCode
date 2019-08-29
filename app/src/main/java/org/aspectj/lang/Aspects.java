package org.aspectj.lang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Aspects {
    private static final Class[] a = new Class[0];
    private static final Class[] b = {Object.class};
    private static final Class[] c = {Class.class};
    private static final Object[] d = new Object[0];

    public static <T> T aspectOf(Class<T> aspectClass) {
        try {
            return a(aspectClass).invoke(null, d);
        } catch (InvocationTargetException e) {
            throw new NoAspectBoundException(aspectClass.getName(), e);
        } catch (Exception e2) {
            throw new NoAspectBoundException(aspectClass.getName(), e2);
        }
    }

    public static <T> T aspectOf(Class<T> aspectClass, Object perObject) {
        try {
            return b(aspectClass).invoke(null, new Object[]{perObject});
        } catch (InvocationTargetException e) {
            throw new NoAspectBoundException(aspectClass.getName(), e);
        } catch (Exception e2) {
            throw new NoAspectBoundException(aspectClass.getName(), e2);
        }
    }

    public static <T> T aspectOf(Class<T> aspectClass, Class<?> perTypeWithin) {
        try {
            return c(aspectClass).invoke(null, new Object[]{perTypeWithin});
        } catch (InvocationTargetException e) {
            throw new NoAspectBoundException(aspectClass.getName(), e);
        } catch (Exception e2) {
            throw new NoAspectBoundException(aspectClass.getName(), e2);
        }
    }

    public static boolean hasAspect(Class<?> aspectClass) {
        try {
            return ((Boolean) d(aspectClass).invoke(null, d)).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasAspect(Class<?> aspectClass, Object perObject) {
        try {
            return ((Boolean) e(aspectClass).invoke(null, new Object[]{perObject})).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasAspect(Class<?> aspectClass, Class<?> perTypeWithin) {
        try {
            return ((Boolean) f(aspectClass).invoke(null, new Object[]{perTypeWithin})).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    private static Method a(Class<?> aspectClass) {
        return a(aspectClass.getDeclaredMethod("aspectOf", a), aspectClass);
    }

    private static Method b(Class<?> aspectClass) {
        return a(aspectClass.getDeclaredMethod("aspectOf", b), aspectClass);
    }

    private static Method c(Class<?> aspectClass) {
        return a(aspectClass.getDeclaredMethod("aspectOf", c), aspectClass);
    }

    private static Method a(Method method, Class<?> aspectClass) {
        method.setAccessible(true);
        if (method.isAccessible() && Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())) {
            return method;
        }
        throw new NoSuchMethodException(aspectClass.getName() + ".aspectOf(..) is not accessible public static");
    }

    private static Method d(Class aspectClass) {
        return b(aspectClass.getDeclaredMethod("hasAspect", a), aspectClass);
    }

    private static Method e(Class aspectClass) {
        return b(aspectClass.getDeclaredMethod("hasAspect", b), aspectClass);
    }

    private static Method f(Class aspectClass) {
        return b(aspectClass.getDeclaredMethod("hasAspect", c), aspectClass);
    }

    private static Method b(Method method, Class aspectClass) {
        method.setAccessible(true);
        if (method.isAccessible() && Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())) {
            return method;
        }
        throw new NoSuchMethodException(aspectClass.getName() + ".hasAspect(..) is not accessible public static");
    }
}
