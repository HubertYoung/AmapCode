package com.autonavi.minimap.bundle.apm.util;

import android.os.Build.VERSION;
import android.support.annotation.Keep;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Keep
public class AccessibleEnlarger {
    private static native void nativeEnlargeClassAccessible(Method method, int i, boolean z);

    private static native void nativeEnlargeFieldAccessible(Field field, int i, boolean z);

    private static native void nativeEnlargeMethodAccessible(Object obj, int i, boolean z);

    public static void enlargeMethod(Object obj) {
        nativeEnlargeMethodAccessible(obj, VERSION.SDK_INT, isArt());
    }

    public static void enlargeField(Field field) {
        nativeEnlargeFieldAccessible(field, VERSION.SDK_INT, isArt());
    }

    public static void enlargeClass(Class cls) {
        nativeEnlargeClassAccessible(cls.getDeclaredMethods()[0], VERSION.SDK_INT, isArt());
    }

    private static boolean isArt() {
        String property = System.getProperty("java.vm.version");
        return property != null && property.startsWith("2");
    }
}
