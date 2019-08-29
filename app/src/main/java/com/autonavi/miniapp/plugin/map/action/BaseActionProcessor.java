package com.autonavi.miniapp.plugin.map.action;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import java.lang.ref.WeakReference;

public abstract class BaseActionProcessor {
    protected String mAction;
    protected WeakReference<Context> mContext;
    protected WeakReference<H5Page> mPage;
    protected AdapterTextureMapView mRealView;

    /* access modifiers changed from: protected */
    public void doDestroy() {
    }

    /* access modifiers changed from: protected */
    public abstract void doProcess(JSONObject jSONObject, H5BridgeContext h5BridgeContext);

    public boolean shouldForceRefresh() {
        return true;
    }

    public BaseActionProcessor(String str, WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView) {
        this.mAction = str;
        this.mContext = weakReference;
        this.mPage = weakReference2;
        this.mRealView = adapterTextureMapView;
    }

    public final void process(JSONObject jSONObject, H5BridgeContext h5BridgeContext) {
        if (jSONObject != null && h5BridgeContext != null) {
            doProcess(jSONObject, h5BridgeContext);
        }
    }

    public final void destroy() {
        doDestroy();
    }

    public final String getAction() {
        return this.mAction;
    }
}
