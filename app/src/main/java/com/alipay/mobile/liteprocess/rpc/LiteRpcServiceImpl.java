package com.alipay.mobile.liteprocess.rpc;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.alipay.mobile.common.rpc.Config;
import com.alipay.mobile.common.rpc.RpcFactory;
import com.alipay.mobile.common.rpc.RpcHeaderListener;
import com.alipay.mobile.common.rpc.RpcInterceptor;
import com.alipay.mobile.common.rpc.RpcInvokeContext;
import com.alipay.mobile.common.transport.http.inner.CoreHttpManager;
import com.alipay.mobile.framework.service.common.RpcService;
import com.alipay.mobile.liteprocess.Util;
import java.lang.annotation.Annotation;
import java.util.concurrent.FutureTask;

public class LiteRpcServiceImpl extends RpcService {
    protected LiteRpcFactory a;
    private Handler b;

    public LiteRpcServiceImpl() {
        this.b = new Handler(Looper.getMainLooper());
        this.a = new LiteRpcFactory(new LiteRpcDefaultConfig());
        this.a.setContext(Util.getContext());
        CoreHttpManager.getInstance(Util.getContext());
    }

    @Deprecated
    public LiteRpcServiceImpl(Config config) {
        this.b = new Handler(Looper.getMainLooper());
        this.a = new LiteRpcFactory(config);
        this.a.setContext(Util.getContext());
        CoreHttpManager.getInstance(Util.getContext());
    }

    public LiteRpcServiceImpl(LiteRpcDefaultConfig config) {
        this.b = new Handler(Looper.getMainLooper());
        this.a = new LiteRpcFactory(config);
        this.a.setContext(Util.getContext());
        CoreHttpManager.getInstance(Util.getContext());
    }

    public LiteRpcServiceImpl(LiteRpcDefaultConfig config, Context context) {
        this.b = new Handler(Looper.getMainLooper());
        this.a = new LiteRpcFactory(config);
        this.a.setContext(context);
        CoreHttpManager.getInstance(context);
    }

    public <T> T getRpcProxy(Class<T> clazz) {
        return this.a.getRpcProxy(clazz);
    }

    public <T> T getBgRpcProxy(Class<T> clazz) {
        return this.a.getBgRpcProxy(clazz);
    }

    public <T> T getPBRpcProxy(Class<T> clazz) {
        return this.a.getPBRpcProxy(clazz);
    }

    public void batchBegin() {
        this.a.batchBegin();
    }

    public FutureTask<?> batchCommit() {
        return this.a.batchCommit();
    }

    public void addProtocolArgs(String key, String value) {
        this.a.addProtocolArgs(key, value);
    }

    public void setScene(long time, String scene) {
        this.a.setScene(scene);
        this.b.postDelayed(new Runnable() {
            public void run() {
                LiteRpcServiceImpl.this.a.setScene(null);
            }
        }, time);
    }

    public RpcFactory getRpcFactory() {
        return this.a;
    }

    public String getScene() {
        return this.a.getScene();
    }

    public void prepareResetCookie(Object object) {
        this.a.prepareResetCookie(object);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle params) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle params) {
    }

    public void addRpcInterceptor(Class<? extends Annotation> clazz, RpcInterceptor rpcInterceptor) {
        this.a.addRpcInterceptor(clazz, rpcInterceptor);
    }

    public RpcInvokeContext getRpcInvokeContext(Object object) {
        return this.a.getRpcInvokeContext(object);
    }

    public void addRpcHeaderListener(RpcHeaderListener rpcHeaderListener) {
        this.a.addRpcHeaderListener(rpcHeaderListener);
    }

    public void setContext(Context context) {
        this.a.setContext(context);
    }
}
