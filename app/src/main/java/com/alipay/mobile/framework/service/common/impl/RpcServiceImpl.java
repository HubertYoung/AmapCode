package com.alipay.mobile.framework.service.common.impl;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.rpc.Config;
import com.alipay.mobile.common.rpc.RpcFactory;
import com.alipay.mobile.common.rpc.RpcHeaderListener;
import com.alipay.mobile.common.rpc.RpcInterceptor;
import com.alipay.mobile.common.rpc.RpcInvokeContext;
import com.alipay.mobile.common.transport.http.inner.CoreHttpManager;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.RpcService;
import java.lang.annotation.Annotation;
import java.util.concurrent.FutureTask;

public class RpcServiceImpl extends RpcService {
    private Handler a;
    protected RpcFactory mRpcFactory;

    public RpcServiceImpl() {
        this.a = new Handler(Looper.getMainLooper());
        this.mRpcFactory = new RpcFactory(new DefaultConfig());
        this.mRpcFactory.setContext(LauncherApplicationAgent.getInstance().getApplicationContext());
        if (!LoggerFactory.getProcessInfo().isLiteProcess()) {
            CoreHttpManager.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext());
        }
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    @Deprecated
    public RpcServiceImpl(Config config) {
        this.a = new Handler(Looper.getMainLooper());
        this.mRpcFactory = new RpcFactory(config);
        this.mRpcFactory.setContext(LauncherApplicationAgent.getInstance().getApplicationContext());
        if (!LoggerFactory.getProcessInfo().isLiteProcess()) {
            CoreHttpManager.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext());
        }
    }

    public RpcServiceImpl(DefaultConfig config) {
        this.a = new Handler(Looper.getMainLooper());
        this.mRpcFactory = new RpcFactory(config);
        this.mRpcFactory.setContext(LauncherApplicationAgent.getInstance().getApplicationContext());
        if (!LoggerFactory.getProcessInfo().isLiteProcess()) {
            CoreHttpManager.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext());
        }
    }

    public RpcServiceImpl(DefaultConfig config, Context context) {
        this.a = new Handler(Looper.getMainLooper());
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
        this.a.postDelayed(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                RpcServiceImpl.this.mRpcFactory.setScene(null);
            }
        }, time);
    }

    public RpcFactory getRpcFactory() {
        return this.mRpcFactory;
    }

    public String getScene() {
        return this.mRpcFactory.getScene();
    }

    public void prepareResetCookie(Object object) {
        this.mRpcFactory.prepareResetCookie(object);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle params) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle params) {
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
