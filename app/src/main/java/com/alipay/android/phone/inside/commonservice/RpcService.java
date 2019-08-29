package com.alipay.android.phone.inside.commonservice;

import com.alipay.inside.android.phone.mrpc.core.RpcInterceptor;
import com.alipay.inside.android.phone.mrpc.core.RpcInvokeContext;
import java.lang.annotation.Annotation;
import java.util.Map;

public abstract class RpcService {
    public abstract void addRpcInterceptor(Class<? extends Annotation> cls, RpcInterceptor rpcInterceptor);

    public RpcInvokeContext getRpcInvokeContext(Object obj) {
        return null;
    }

    public abstract <T> T getRpcProxy(Class<T> cls);

    public abstract <T> T getRpcProxy(Class<T> cls, Map<String, String> map);

    public abstract void prepareResetCookie(Object obj);
}
