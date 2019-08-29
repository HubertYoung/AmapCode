package com.alipay.mobile.common.rpc;

import android.text.TextUtils;
import com.alipay.mobile.common.rpc.transport.InnerRpcInvokeContext;
import com.alipay.mobile.common.rpc.util.RpcInvokerUtil;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RpcInvocationHandler implements InvocationHandler {
    private InnerRpcInvokeContext innerRpcInvokeContext;
    private boolean mBackgroundRpc;
    protected Class<?> mClazz;
    protected Config mConfig;
    private boolean mResetCoolie;
    protected RpcInvoker mRpcInvoker;

    public RpcInvocationHandler(Config config, Class<?> clazz, RpcInvoker rpcInvoker) {
        this(config, clazz, rpcInvoker, false);
    }

    public RpcInvocationHandler(Config config, Class<?> clazz, RpcInvoker rpcInvoker, boolean backgroundRpc) {
        this.mBackgroundRpc = false;
        this.mConfig = config;
        this.mClazz = clazz;
        this.mRpcInvoker = rpcInvoker;
        this.mBackgroundRpc = backgroundRpc;
    }

    public Object invoke(Object proxy, Method method, Object[] args) {
        return this.mRpcInvoker.invoke(proxy, this.mClazz, method, args, buildRpcInvokeContext(method));
    }

    private InnerRpcInvokeContext buildRpcInvokeContext(Method method) {
        InnerRpcInvokeContext localContext = getInnerRpcInvokeContext();
        if (localContext.resetCookie == null) {
            localContext.resetCookie = Boolean.valueOf(isResetCoolie());
        }
        if (localContext.bgRpc == null) {
            localContext.bgRpc = Boolean.valueOf(this.mBackgroundRpc);
        }
        if (TextUtils.isEmpty(localContext.appKey)) {
            localContext.appKey = this.mConfig.getAppKey();
        }
        if (TextUtils.isEmpty(localContext.gwUrl)) {
            localContext.gwUrl = this.mConfig.getUrl();
        }
        if (localContext.compress == null) {
            localContext.compress = Boolean.valueOf(this.mConfig.isCompress());
        }
        if (localContext.allowRetry == null) {
            if (RpcInvokerUtil.isRetryable(method)) {
                localContext.setAllowRetry(true);
            } else {
                localContext.setAllowRetry(false);
            }
        }
        return localContext;
    }

    public Config getConfig() {
        return this.mConfig;
    }

    public boolean isResetCoolie() {
        return this.mResetCoolie;
    }

    public void setResetCoolie(boolean resetCoolie) {
        this.mResetCoolie = resetCoolie;
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

    public void setInnerRpcInvokeContext(InnerRpcInvokeContext innerRpcInvokeContext2) {
        this.innerRpcInvokeContext = innerRpcInvokeContext2;
    }
}
