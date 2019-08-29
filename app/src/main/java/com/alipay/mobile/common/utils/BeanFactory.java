package com.alipay.mobile.common.utils;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class BeanFactory {
    private static final Map<String, Object> a = new HashMap();

    public BeanFactory() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static final <T> T getBean(Class<T> tClass) {
        T object = getObject(tClass);
        if (object != null) {
            return object;
        }
        synchronized (tClass) {
            Object o1 = getObject(tClass);
            if (o1 != null) {
                return o1;
            }
            try {
                Constructor constructor = tClass.getDeclaredConstructor(null);
                constructor.setAccessible(true);
                Object o12 = constructor.newInstance(null);
                a.put(tClass.getName(), o12);
                return o12;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static final void clearAllBean() {
        a.clear();
    }

    public static final <T> T getObject(Class<T> clazz) {
        try {
            Object o = a.get(clazz.getName());
            if (o != null) {
                return o;
            }
        } catch (Throwable e) {
            LogCatUtil.error((String) "BeanFactory", "1. BEAN_MAP.get exception. " + e.toString());
        }
        return null;
    }
}
