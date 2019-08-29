package com.alipay.android.phone.mobilecommon.rpc;

import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.common.rpc.RpcInterceptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/* compiled from: BioRpcInterceptor */
public final class c implements RpcInterceptor {
    public final boolean preHandle(Object obj, ThreadLocal<Object> threadLocal, byte[] bArr, Class<?> cls, Method method, Object[] objArr, Annotation annotation, ThreadLocal<Map<String, Object>> threadLocal2) {
        return true;
    }

    public final boolean postHandle(Object obj, ThreadLocal<Object> threadLocal, byte[] bArr, Class<?> cls, Method method, Object[] objArr, Annotation annotation) {
        return true;
    }

    public final boolean exceptionHandle(Object obj, ThreadLocal<Object> threadLocal, byte[] bArr, Class<?> cls, Method method, Object[] objArr, RpcException rpcException, Annotation annotation) {
        throw new b(rpcException);
    }
}
