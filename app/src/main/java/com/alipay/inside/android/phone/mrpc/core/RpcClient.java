package com.alipay.inside.android.phone.mrpc.core;

import java.lang.annotation.Annotation;

public abstract class RpcClient {
    public abstract void addRpcInterceptor(Class<? extends Annotation> cls, RpcInterceptor rpcInterceptor);

    public RpcInvokeContext getRpcInvokeContext(Object obj) {
        return null;
    }

    public abstract <T> T getRpcProxy(Class<T> cls, RpcParams rpcParams);

    public abstract void prepareResetCookie(Object obj);
}
