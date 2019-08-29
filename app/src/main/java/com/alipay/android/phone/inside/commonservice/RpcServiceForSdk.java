package com.alipay.android.phone.inside.commonservice;

import android.content.Context;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.inside.android.phone.mrpc.core.HttpManager;
import com.alipay.inside.android.phone.mrpc.core.RpcFactory;
import com.alipay.inside.android.phone.mrpc.core.RpcInterceptor;
import com.alipay.inside.android.phone.mrpc.core.RpcInvokeContext;
import java.lang.annotation.Annotation;
import java.util.Map;

class RpcServiceForSdk extends RpcService {
    protected RpcFactory mRpcFactory = new RpcFactory(new DefaultConfig());

    public RpcServiceForSdk() {
        this.mRpcFactory.setContext(LauncherApplication.a());
        HttpManager.getInstance(LauncherApplication.a());
    }

    public <T> T getRpcProxy(Class<T> cls) {
        return this.mRpcFactory.getRpcProxy(cls);
    }

    public <T> T getRpcProxy(Class<T> cls, Map<String, String> map) {
        return this.mRpcFactory.getRpcProxy(cls, map);
    }

    public void prepareResetCookie(Object obj) {
        this.mRpcFactory.prepareResetCookie(obj);
    }

    public void addRpcInterceptor(Class<? extends Annotation> cls, RpcInterceptor rpcInterceptor) {
        this.mRpcFactory.addRpcInterceptor(cls, rpcInterceptor);
    }

    public RpcInvokeContext getRpcInvokeContext(Object obj) {
        return this.mRpcFactory.getRpcInvokeContext(obj);
    }

    public void setContext(Context context) {
        this.mRpcFactory.setContext(context);
    }
}
