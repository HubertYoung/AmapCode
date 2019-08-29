package com.alipay.mobile.common.reflect;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

public class ReflectUtil {
    public ReflectUtil() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static final Object getInstance(ClassLoader classLoader, String className) {
        return classLoader.loadClass(className).newInstance();
    }

    public static final Object getInstance(Class<?> clazz) {
        return clazz.newInstance();
    }
}
