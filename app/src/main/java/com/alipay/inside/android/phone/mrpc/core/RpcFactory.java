package com.alipay.inside.android.phone.mrpc.core;

import android.content.Context;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class RpcFactory {
    private static final Map<Class<? extends Annotation>, RpcInterceptor> GLOBLE_INTERCEPTORS = new HashMap(5);
    private Config mConfig;
    private Context mContext;
    private Map<Class<? extends Annotation>, RpcInterceptor> mInterceptors = new HashMap(5);
    private RpcInvoker mRpcInvoker = new RpcInvoker(this);

    public RpcFactory(Config config) {
        this.mConfig = config;
    }

    public <T> T getRpcProxy(Class<T> cls) {
        return Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new RpcInvocationHandler(this.mConfig, cls, this.mRpcInvoker));
    }

    public <T> T getRpcProxy(Class<T> cls, Map<String, String> map) {
        return Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new RpcInvocationHandler(this.mConfig, cls, this.mRpcInvoker, map));
    }

    public Config getConfig() {
        return this.mConfig;
    }

    public Context getContext() {
        if (this.mContext == null) {
            this.mContext = this.mConfig.getApplicationContext();
        }
        return this.mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void prepareResetCookie(Object obj) {
        if (Proxy.isProxyClass(obj.getClass())) {
            ((RpcInvocationHandler) Proxy.getInvocationHandler(obj)).setResetCoolie(true);
        }
    }

    public RpcInvokeContext getRpcInvokeContext(Object obj) {
        if (Proxy.isProxyClass(obj.getClass())) {
            return ((RpcInvocationHandler) Proxy.getInvocationHandler(obj)).getRpcInvokeContext();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(obj.getClass().getName());
        sb.append(" class is not a proxy class ");
        throw new IllegalArgumentException(sb.toString());
    }

    public void addRpcInterceptor(Class<? extends Annotation> cls, RpcInterceptor rpcInterceptor) {
        this.mInterceptors.put(cls, rpcInterceptor);
        addGlobelRpcInterceptor(cls, rpcInterceptor);
    }

    public RpcInterceptor findRpcInterceptor(Class<? extends Annotation> cls) {
        RpcInterceptor rpcInterceptor = this.mInterceptors.get(cls);
        if (rpcInterceptor != null) {
            return rpcInterceptor;
        }
        return GLOBLE_INTERCEPTORS.get(cls);
    }

    public static final void addGlobelRpcInterceptor(Class<? extends Annotation> cls, RpcInterceptor rpcInterceptor) {
        GLOBLE_INTERCEPTORS.put(cls, rpcInterceptor);
    }
}
