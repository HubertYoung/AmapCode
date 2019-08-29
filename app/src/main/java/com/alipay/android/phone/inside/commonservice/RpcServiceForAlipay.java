package com.alipay.android.phone.inside.commonservice;

import com.alipay.inside.android.phone.mrpc.core.RpcInterceptor;
import com.alipay.mobile.framework.service.common.impl.DefaultConfig;
import com.alipay.mobile.framework.service.common.impl.RpcServiceImpl;
import java.lang.annotation.Annotation;
import java.util.Map;

class RpcServiceForAlipay extends RpcService {
    public void addRpcInterceptor(Class<? extends Annotation> cls, RpcInterceptor rpcInterceptor) {
    }

    public void prepareResetCookie(Object obj) {
    }

    RpcServiceForAlipay() {
    }

    public <T> T getRpcProxy(Class<T> cls) {
        return new RpcServiceImpl(new DefaultConfig()).getRpcProxy(cls);
    }

    public <T> T getRpcProxy(Class<T> cls, Map<String, String> map) {
        return getRpcProxy(cls);
    }
}
