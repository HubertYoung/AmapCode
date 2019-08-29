package com.alipay.mobile.framework.service.common.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.alipay.mobile.common.rpc.RpcFactory;
import com.alipay.mobile.common.rpc.RpcHeaderListener;
import com.alipay.mobile.common.rpc.RpcInterceptor;
import com.alipay.mobile.common.rpc.RpcInvokeContext;
import com.alipay.mobile.common.transport.http.inner.CoreHttpManager;
import com.alipay.mobile.framework.service.common.MpaasRpcService;
import java.lang.annotation.Annotation;
import java.util.concurrent.FutureTask;

public class MpaasRpcServiceImpl extends MpaasRpcService {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    protected RpcFactory mRpcFactory;

    public MpaasRpcServiceImpl(Context context) {
        this.mRpcFactory = new RpcFactory(new MpaasDefaultConfig(context));
        this.mRpcFactory.setContext(context);
        CoreHttpManager.getInstance(context);
    }

    public MpaasRpcServiceImpl(MpaasDefaultConfig config, Context context) {
        this.mRpcFactory = new RpcFactory(config);
        this.mRpcFactory.setContext(context);
        CoreHttpManager.getInstance(context);
    }

    public <T> T getRpcProxy(Class<T> clazz) {
        return this.mRpcFactory.getRpcProxy(clazz);
    }

    public <T> T getBgRpcProxy(Class<T> clazz) {
        return this.mRpcFactory.getBgRpcProxy(clazz);
    }

    public <T> T getPBRpcProxy(Class<T> clazz) {
        return this.mRpcFactory.getPBRpcProxy(clazz);
    }

    public void batchBegin() {
        this.mRpcFactory.batchBegin();
    }

    public FutureTask<?> batchCommit() {
        return this.mRpcFactory.batchCommit();
    }

    public void addProtocolArgs(String key, String value) {
        this.mRpcFactory.addProtocolArgs(key, value);
    }

    public void setScene(long time, String scene) {
        this.mRpcFactory.setScene(scene);
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                MpaasRpcServiceImpl.this.mRpcFactory.setScene(null);
            }
        }, time);
    }

    public void cancelAllRpc() {
        this.mRpcFactory.cancelAllRpc();
    }

    public String getScene() {
        return this.mRpcFactory.getScene();
    }

    public void prepareResetCookie(Object object) {
        this.mRpcFactory.prepareResetCookie(object);
    }

    public void addRpcInterceptor(Class<? extends Annotation> clazz, RpcInterceptor rpcInterceptor) {
        this.mRpcFactory.addRpcInterceptor(clazz, rpcInterceptor);
    }

    public RpcInvokeContext getRpcInvokeContext(Object object) {
        return this.mRpcFactory.getRpcInvokeContext(object);
    }

    public void addRpcHeaderListener(RpcHeaderListener rpcHeaderListener) {
        this.mRpcFactory.addRpcHeaderListener(rpcHeaderListener);
    }

    public void setContext(Context context) {
        this.mRpcFactory.setContext(context);
    }
}
