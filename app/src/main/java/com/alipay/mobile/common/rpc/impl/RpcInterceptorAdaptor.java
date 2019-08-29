package com.alipay.mobile.common.rpc.impl;

import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.common.rpc.RpcInterceptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

public class RpcInterceptorAdaptor implements RpcInterceptor {
    public boolean preHandle(Object proxy, ThreadLocal<Object> retValue, byte[] retRawValue, Class<?> clazz, Method method, Object[] args, Annotation annotation, ThreadLocal<Map<String, Object>> extParams) {
        return true;
    }

    public boolean postHandle(Object proxy, ThreadLocal<Object> retValue, byte[] retRawValue, Class<?> clazz, Method method, Object[] args, Annotation annotation) {
        return true;
    }

    public boolean exceptionHandle(Object proxy, ThreadLocal<Object> retValue, byte[] retRawValue, Class<?> clazz, Method method, Object[] args, RpcException exception, Annotation annotation) {
        return false;
    }
}
