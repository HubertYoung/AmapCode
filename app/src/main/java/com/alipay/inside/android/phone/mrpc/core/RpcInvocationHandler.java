package com.alipay.inside.android.phone.mrpc.core;

import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RpcInvocationHandler implements InvocationHandler {
    private InnerRpcInvokeContext innerRpcInvokeContext;
    protected Class<?> mClazz;
    protected Config mConfig;
    protected Map<String, String> mExtParams = new HashMap();
    private boolean mResetCoolie;
    protected RpcInvoker mRpcInvoker;

    public RpcInvocationHandler(Config config, Class<?> cls, RpcInvoker rpcInvoker) {
        this.mConfig = config;
        this.mClazz = cls;
        this.mRpcInvoker = rpcInvoker;
    }

    public RpcInvocationHandler(Config config, Class<?> cls, RpcInvoker rpcInvoker, Map<String, String> map) {
        this.mConfig = config;
        this.mClazz = cls;
        this.mRpcInvoker = rpcInvoker;
        this.mExtParams = map;
    }

    public String getExtParams(String str) {
        if (this.mExtParams == null) {
            return "";
        }
        return this.mExtParams.get(str);
    }

    public Object invoke(Object obj, Method method, Object[] objArr) throws RpcException {
        return this.mRpcInvoker.invoke(obj, this.mClazz, method, objArr, buildRpcInvokeContext(method));
    }

    private InnerRpcInvokeContext buildRpcInvokeContext(Method method) {
        LoggerFactory.f().b((String) "rpc", "RpcInvocationHandler::buildRpcInvokeContext > method:".concat(String.valueOf(method)));
        InnerRpcInvokeContext innerRpcInvokeContext2 = getInnerRpcInvokeContext();
        if (innerRpcInvokeContext2.resetCookie == null) {
            innerRpcInvokeContext2.resetCookie = Boolean.valueOf(isResetCoolie());
        }
        if (TextUtils.isEmpty(innerRpcInvokeContext2.appKey)) {
            innerRpcInvokeContext2.appKey = this.mConfig.getAppid();
        }
        if (TextUtils.isEmpty(innerRpcInvokeContext2.gwUrl)) {
            innerRpcInvokeContext2.gwUrl = this.mConfig.getUrl();
        }
        return innerRpcInvokeContext2;
    }

    public boolean isResetCoolie() {
        return this.mResetCoolie;
    }

    public void setResetCoolie(boolean z) {
        this.mResetCoolie = z;
    }

    public RpcInvokeContext getRpcInvokeContext() {
        return getInnerRpcInvokeContext();
    }

    private InnerRpcInvokeContext getInnerRpcInvokeContext() {
        if (this.innerRpcInvokeContext == null) {
            this.innerRpcInvokeContext = new InnerRpcInvokeContext();
        }
        return this.innerRpcInvokeContext;
    }
}
