package org.xidea.el.impl;

import java.util.Map;

public interface Convertor<T> {
    public static final Convertor<Object> a = new DefaultConvertor();
    public static final Map<Class<?>, Convertor<?>> b = DefaultConvertor.a();
}
