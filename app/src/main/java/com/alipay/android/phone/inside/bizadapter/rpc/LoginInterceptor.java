package com.alipay.android.phone.inside.bizadapter.rpc;

import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.inside.android.phone.mrpc.core.RpcInterceptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

public class LoginInterceptor implements RpcInterceptor {
    public boolean exceptionHandle(Object obj, ThreadLocal<Object> threadLocal, byte[] bArr, Class<?> cls, Method method, Object[] objArr, RpcException rpcException, Annotation annotation) throws RpcException {
        return true;
    }

    public boolean postHandle(Object obj, ThreadLocal<Object> threadLocal, byte[] bArr, Class<?> cls, Method method, Object[] objArr, Annotation annotation) throws RpcException {
        return true;
    }

    public boolean preHandle(Object obj, ThreadLocal<Object> threadLocal, byte[] bArr, Class<?> cls, Method method, Object[] objArr, Annotation annotation, ThreadLocal<Map<String, Object>> threadLocal2) throws RpcException {
        return true;
    }
}
