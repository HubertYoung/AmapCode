package com.alipay.mobile.common.rpc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

public interface RpcInterceptor {
    boolean exceptionHandle(Object obj, ThreadLocal<Object> threadLocal, byte[] bArr, Class<?> cls, Method method, Object[] objArr, RpcException rpcException, Annotation annotation);

    boolean postHandle(Object obj, ThreadLocal<Object> threadLocal, byte[] bArr, Class<?> cls, Method method, Object[] objArr, Annotation annotation);

    boolean preHandle(Object obj, ThreadLocal<Object> threadLocal, byte[] bArr, Class<?> cls, Method method, Object[] objArr, Annotation annotation, ThreadLocal<Map<String, Object>> threadLocal2);
}
