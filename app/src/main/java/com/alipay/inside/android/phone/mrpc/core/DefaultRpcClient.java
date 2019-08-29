package com.alipay.inside.android.phone.mrpc.core;

import android.content.Context;
import java.lang.annotation.Annotation;

public class DefaultRpcClient extends RpcClient {
    /* access modifiers changed from: private */
    public Context mContext;
    protected RpcFactory mRpcFactory;

    public DefaultRpcClient(Context context) {
        this.mContext = context;
    }

    public DefaultRpcClient(Context context, RpcParams rpcParams) {
        this.mContext = context;
        this.mRpcFactory = new RpcFactory(createConfig(rpcParams));
    }

    public <T> T getRpcProxy(Class<T> cls, RpcParams rpcParams) {
        return new RpcFactory(createConfig(rpcParams)).getRpcProxy(cls);
    }

    public void addRpcInterceptor(Class<? extends Annotation> cls, RpcInterceptor rpcInterceptor) {
        this.mRpcFactory.addRpcInterceptor(cls, rpcInterceptor);
    }

    public void prepareResetCookie(Object obj) {
        this.mRpcFactory.prepareResetCookie(obj);
    }

    public RpcInvokeContext getRpcInvokeContext(Object obj) {
        return this.mRpcFactory.getRpcInvokeContext(obj);
    }

    private Config createConfig(final RpcParams rpcParams) {
        return new Config() {
            public String getUrl() {
                return rpcParams.getGwUrl();
            }

            public Transport getTransport() {
                return HttpManager.getInstance(getApplicationContext());
            }

            public RpcParams getRpcParams() {
                return rpcParams;
            }

            public Context getApplicationContext() {
                return DefaultRpcClient.this.mContext.getApplicationContext();
            }

            public boolean isGzip() {
                return rpcParams.isGzip();
            }

            public String getAppid() {
                return rpcParams.getAppid();
            }

            public boolean isResetCookie() {
                return rpcParams.isResetCookie();
            }
        };
    }
}
