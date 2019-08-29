package com.alipay.mobile.framework.service.common;

import com.alipay.mobile.common.rpc.RpcHeaderListener;
import com.alipay.mobile.common.rpc.RpcInterceptor;
import com.alipay.mobile.common.rpc.RpcInvokeContext;
import java.lang.annotation.Annotation;
import java.util.concurrent.FutureTask;

public abstract class MpaasRpcService {
    public abstract void addProtocolArgs(String str, String str2);

    public abstract void addRpcInterceptor(Class<? extends Annotation> cls, RpcInterceptor rpcInterceptor);

    public abstract void batchBegin();

    public abstract FutureTask<?> batchCommit();

    public abstract void cancelAllRpc();

    public abstract <T> T getBgRpcProxy(Class<T> cls);

    public abstract <T> T getPBRpcProxy(Class<T> cls);

    public abstract <T> T getRpcProxy(Class<T> cls);

    public abstract String getScene();

    public abstract void prepareResetCookie(Object obj);

    public abstract void setScene(long j, String str);

    public RpcInvokeContext getRpcInvokeContext(Object object) {
        return null;
    }

    public void addRpcHeaderListener(RpcHeaderListener rpcHeaderListener) {
    }
}
