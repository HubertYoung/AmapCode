package com.alipay.mobile.framework.service.common;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.rpc.RpcFactory;
import com.alipay.mobile.common.rpc.RpcHeaderListener;
import com.alipay.mobile.common.rpc.RpcInterceptor;
import com.alipay.mobile.common.rpc.RpcInvokeContext;
import com.alipay.mobile.common.rpc.impl.RpcLifeManagerImpl;
import com.alipay.mobile.framework.service.CommonService;
import java.lang.annotation.Annotation;
import java.util.concurrent.FutureTask;

public abstract class RpcService extends CommonService {
    public abstract void addProtocolArgs(String str, String str2);

    public abstract void addRpcInterceptor(Class<? extends Annotation> cls, RpcInterceptor rpcInterceptor);

    public abstract void batchBegin();

    public abstract FutureTask<?> batchCommit();

    public abstract <T> T getBgRpcProxy(Class<T> cls);

    public abstract <T> T getPBRpcProxy(Class<T> cls);

    public abstract RpcFactory getRpcFactory();

    public abstract <T> T getRpcProxy(Class<T> cls);

    public abstract String getScene();

    public abstract void prepareResetCookie(Object obj);

    public abstract void setScene(long j, String str);

    public RpcService() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public RpcInvokeContext getRpcInvokeContext(Object object) {
        return null;
    }

    public void addRpcHeaderListener(RpcHeaderListener rpcHeaderListener) {
    }

    public void cancelAllRpc() {
        RpcLifeManagerImpl.getInstance().cancelAllRpc();
    }
}
