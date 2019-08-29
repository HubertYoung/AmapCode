package com.alipay.mobile.common.rpc;

import android.content.Context;
import com.alipay.mobile.common.rpc.impl.RpcLifeManagerImpl;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

public class RpcFactory {
    private static final Map<Class<? extends Annotation>, RpcInterceptor> GLOBLE_INTERCEPTORS = new HashMap(5);
    private static final List<RpcHeaderListener> GLOBLE_RPC_HEADER_LISTENER_LIST = new ArrayList(2);
    private static final String TAG = "RpcFactory";
    protected Config mConfig;
    protected Context mContext;
    private Map<Class<? extends Annotation>, RpcInterceptor> mInterceptors;
    protected RpcInvoker mRpcInvoker;
    private List<RpcHeaderListener> rpcHeaderListenerList = new ArrayList(2);

    public RpcFactory(Config config) {
        this.mConfig = config;
        this.mRpcInvoker = new RpcInvoker(this);
        this.mInterceptors = new HashMap(5);
    }

    public <T> T getRpcProxy(Class<T> clazz) {
        LogCatUtil.info(TAG, "clazz=[" + clazz.getName() + "]");
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new RpcInvocationHandler(this.mConfig, clazz, this.mRpcInvoker));
    }

    public <T> T getBgRpcProxy(Class<T> clazz) {
        LogCatUtil.info(TAG, "clazz=[" + clazz.getName() + "]");
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new RpcInvocationHandler(this.mConfig, clazz, this.mRpcInvoker, true));
    }

    public <T> T getPBRpcProxy(Class<T> clazz) {
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new RpcInvocationHandler(this.mConfig, clazz, this.mRpcInvoker));
    }

    public void prepareResetCookie(Object object) {
        if (Proxy.isProxyClass(object.getClass())) {
            ((RpcInvocationHandler) Proxy.getInvocationHandler(object)).setResetCoolie(true);
        }
    }

    public RpcInvokeContext getRpcInvokeContext(Object object) {
        if (Proxy.isProxyClass(object.getClass())) {
            return ((RpcInvocationHandler) Proxy.getInvocationHandler(object)).getRpcInvokeContext();
        }
        throw new IllegalArgumentException(object.getClass().getName() + " class is not a proxy class ");
    }

    public void setScene(String scene) {
        this.mRpcInvoker.setScene(scene);
    }

    public String getScene() {
        return this.mRpcInvoker.getScene();
    }

    public void batchBegin() {
        this.mRpcInvoker.batchBegin();
    }

    public FutureTask<?> batchCommit() {
        return this.mRpcInvoker.batchCommit();
    }

    public void addProtocolArgs(String key, String value) {
        RpcInvoker.addProtocolArgs(key, value);
    }

    public void addRpcInterceptor(Class<? extends Annotation> clazz, RpcInterceptor rpcInterceptor) {
        this.mInterceptors.put(clazz, rpcInterceptor);
        addGlobelRpcInterceptor(clazz, rpcInterceptor);
    }

    public RpcInterceptor findRpcInterceptor(Class<? extends Annotation> clazz) {
        RpcInterceptor rpcInterceptor = this.mInterceptors.get(clazz);
        return rpcInterceptor != null ? rpcInterceptor : GLOBLE_INTERCEPTORS.get(clazz);
    }

    public Context getContext() {
        try {
            if (this.mContext == null) {
                LogCatUtil.info(TAG, "");
                Class launcherAgentClass = Class.forName("com.alipay.mobile.framework.LauncherApplicationAgent");
                Object launcherAgentObj = launcherAgentClass.getDeclaredMethod("getInstance", new Class[0]).invoke(launcherAgentClass, new Object[0]);
                this.mContext = (Context) launcherAgentObj.getClass().getDeclaredMethod("getApplicationContext", new Class[0]).invoke(launcherAgentObj, new Object[0]);
            }
        } catch (Throwable e) {
            LogCatUtil.error(TAG, "getContext fail", e);
        }
        return this.mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public Config getConfig() {
        return this.mConfig;
    }

    public void setConfig(Config config) {
        this.mConfig = config;
    }

    public void addRpcHeaderListener(RpcHeaderListener rpcHeaderListener) {
        this.rpcHeaderListenerList.add(rpcHeaderListener);
        GLOBLE_RPC_HEADER_LISTENER_LIST.add(rpcHeaderListener);
    }

    public void notifyRpcHeaderUpdateEvent(RpcHeader rpcHeader) {
        List lRpcHeaderListenerList = this.rpcHeaderListenerList;
        if (lRpcHeaderListenerList.isEmpty()) {
            lRpcHeaderListenerList = GLOBLE_RPC_HEADER_LISTENER_LIST;
        }
        for (int i = 0; i < lRpcHeaderListenerList.size(); i++) {
            try {
                RpcHeaderListener rpcHeaderListener = lRpcHeaderListenerList.get(i);
                if (rpcHeaderListener != null) {
                    rpcHeaderListener.onRpcHeaderUpdateEvent(rpcHeader);
                    LogCatUtil.info(TAG, "onRpcHeaderUpdateEvent. class=[" + rpcHeaderListener.getClass().getSimpleName() + "]");
                }
            } catch (Throwable e) {
                LogCatUtil.warn((String) TAG, "notifyRpcHeaderUpdateEvent exception. " + e.toString());
            }
        }
    }

    public static final void addGlobelRpcInterceptor(Class<? extends Annotation> clazz, RpcInterceptor rpcInterceptor) {
        GLOBLE_INTERCEPTORS.put(clazz, rpcInterceptor);
    }

    public void cancelAllRpc() {
        RpcLifeManagerImpl.getInstance().cancelAllRpc();
    }
}
