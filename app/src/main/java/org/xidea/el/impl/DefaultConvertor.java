package org.xidea.el.impl;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Convertor */
class DefaultConvertor implements Convertor<Object> {
    DefaultConvertor() {
    }

    static Map<Class<?>, Convertor<?>> a() {
        Class[] clsArr = {File.class, URL.class, URI.class, Long.TYPE, Long.class, Integer.TYPE, Integer.class, Double.TYPE, Double.class, Short.TYPE, Short.class, Byte.TYPE, Byte.class, Boolean.TYPE, Boolean.class, Character.TYPE, Character.class, String.class, Object.class};
        HashMap hashMap = new HashMap();
        for (int i = 0; i < 19; i++) {
            hashMap.put(clsArr[i], Convertor.a);
        }
        return Collections.unmodifiableMap(hashMap);
    }
}
