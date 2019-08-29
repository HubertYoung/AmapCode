package com.alipay.mobile.common.rpc;

import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.lang.annotation.Annotation;

public class ThirdpartyRpcFactory extends RpcFactory {
    public static final String TAG = "ThirdpartyRpcFactory";

    public ThirdpartyRpcFactory(Config config) {
        super(config);
    }

    public void addRpcInterceptor(Class<? extends Annotation> clazz, RpcInterceptor rpcInterceptor) {
        LogCatUtil.debug(TAG, "addRpcInterceptor,do nothing");
    }

    public RpcInterceptor findRpcInterceptor(Class<? extends Annotation> clazz) {
        return null;
    }
}
