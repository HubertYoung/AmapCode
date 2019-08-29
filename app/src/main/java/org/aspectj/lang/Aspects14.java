package org.aspectj.lang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Aspects14 {
    private static final Class[] a = new Class[0];
    private static final Class[] b;
    private static final Class[] c;
    static Class class$java$lang$Class;
    static Class class$java$lang$Object;
    private static final Object[] d = new Object[0];

    static {
        Class cls;
        Class cls2;
        Class[] clsArr = new Class[1];
        if (class$java$lang$Object == null) {
            cls = class$("java.lang.Object");
            class$java$lang$Object = cls;
        } else {
            cls = class$java$lang$Object;
        }
        clsArr[0] = cls;
        b = clsArr;
        Class[] clsArr2 = new Class[1];
        if (class$java$lang$Class == null) {
            cls2 = class$("java.lang.Class");
            class$java$lang$Class = cls2;
        } else {
            cls2 = class$java$lang$Class;
        }
        clsArr2[0] = cls2;
        c = clsArr2;
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public static Object aspectOf(Class aspectClass) {
        try {
            return a(aspectClass).invoke(null, d);
        } catch (InvocationTargetException e) {
            throw new NoAspectBoundException(aspectClass.getName(), e);
        } catch (Exception e2) {
            throw new NoAspectBoundException(aspectClass.getName(), e2);
        }
    }

    public static Object aspectOf(Class aspectClass, Object perObject) {
        try {
            return b(aspectClass).invoke(null, new Object[]{perObject});
        } catch (InvocationTargetException e) {
            throw new NoAspectBoundException(aspectClass.getName(), e);
        } catch (Exception e2) {
            throw new NoAspectBoundException(aspectClass.getName(), e2);
        }
    }

    public static Object aspectOf(Class aspectClass, Class perTypeWithin) {
        try {
            return c(aspectClass).invoke(null, new Object[]{perTypeWithin});
        } catch (InvocationTargetException e) {
            throw new NoAspectBoundException(aspectClass.getName(), e);
        } catch (Exception e2) {
            throw new NoAspectBoundException(aspectClass.getName(), e2);
        }
    }

    public static boolean hasAspect(Class aspectClass) {
        try {
            return ((Boolean) d(aspectClass).invoke(null, d)).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasAspect(Class aspectClass, Object perObject) {
        try {
            return ((Boolean) e(aspectClass).invoke(null, new Object[]{perObject})).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasAspect(Class aspectClass, Class perTypeWithin) {
        try {
            return ((Boolean) f(aspectClass).invoke(null, new Object[]{perTypeWithin})).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    private static Method a(Class aspectClass) {
        return a(aspectClass.getDeclaredMethod("aspectOf", a), aspectClass);
    }

    private static Method b(Class aspectClass) {
        return a(aspectClass.getDeclaredMethod("aspectOf", b), aspectClass);
    }

    private static Method c(Class aspectClass) {
        return a(aspectClass.getDeclaredMethod("aspectOf", c), aspectClass);
    }

    private static Method a(Method method, Class aspectClass) {
        method.setAccessible(true);
        if (method.isAccessible() && Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())) {
            return method;
        }
        throw new NoSuchMethodException(new StringBuffer().append(aspectClass.getName()).append(".aspectOf(..) is not accessible public static").toString());
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
        throw new NoSuchMethodException(new StringBuffer().append(aspectClass.getName()).append(".hasAspect(..) is not accessible public static").toString());
    }
}
