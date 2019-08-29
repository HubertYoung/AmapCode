package com.alipay.mobile.framework.service.common.impl;

import android.content.Context;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.rpc.Config;
import com.alipay.mobile.common.rpc.RpcInterceptor;
import com.alipay.mobile.common.rpc.ThirdpartyRpcFactory;
import com.alipay.mobile.common.transport.http.inner.CoreHttpManager;
import com.alipay.mobile.common.utils.LogCatUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import java.lang.annotation.Annotation;

public class ThirdpartyRpcServiceImpl extends RpcServiceImpl {
    public static final String TAG = "ThirdpartyRpcServiceImpl";

    public ThirdpartyRpcServiceImpl() {
        this.mRpcFactory = new ThirdpartyRpcFactory(new DefaultConfig());
        this.mRpcFactory.setContext(LauncherApplicationAgent.getInstance().getApplicationContext());
        CoreHttpManager.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext());
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    @Deprecated
    public ThirdpartyRpcServiceImpl(Config config) {
        this.mRpcFactory = new ThirdpartyRpcFactory(config);
        this.mRpcFactory.setContext(LauncherApplicationAgent.getInstance().getApplicationContext());
        CoreHttpManager.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext());
    }

    public ThirdpartyRpcServiceImpl(DefaultConfig config) {
        this.mRpcFactory = new ThirdpartyRpcFactory(config);
        this.mRpcFactory.setContext(LauncherApplicationAgent.getInstance().getApplicationContext());
        CoreHttpManager.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext());
    }

    public ThirdpartyRpcServiceImpl(DefaultConfig config, Context context) {
        this.mRpcFactory = new ThirdpartyRpcFactory(config);
        this.mRpcFactory.setContext(context);
        CoreHttpManager.getInstance(context);
    }

    public void addRpcInterceptor(Class<? extends Annotation> clazz, RpcInterceptor rpcInterceptor) {
        LogCatUtil.debug(TAG, "addRpcInterceptor,do nothing");
    }
}
