package com.alipay.mobile.nebulacore.android;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class DynamicProxy {
    DynamicProxy() {
    }

    public static <T> T dynamicProxy(Class<T> clazz, final Object delegate) {
        InvocationHandler invoke = new InvocationHandler() {
            public final Object invoke(Object proxy, Method method, Object[] args) {
                try {
                    return delegate.getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(delegate, args);
                } catch (InvocationTargetException e) {
                    throw e.getCause();
                }
            }
        };
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, invoke);
    }
}
