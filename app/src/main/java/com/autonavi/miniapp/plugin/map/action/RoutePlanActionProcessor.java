package com.autonavi.miniapp.plugin.map.action;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import com.autonavi.miniapp.plugin.map.route.MiniAppRouteServiceManager;
import java.lang.ref.WeakReference;

public class RoutePlanActionProcessor extends BaseActionProcessor {
    private MiniAppRouteServiceManager mMiniAppRouteServiceManager = new MiniAppRouteServiceManager();

    public RoutePlanActionProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView) {
        super("routePlan", weakReference, weakReference2, adapterTextureMapView);
    }

    /* access modifiers changed from: protected */
    public void doProcess(JSONObject jSONObject, H5BridgeContext h5BridgeContext) {
        this.mMiniAppRouteServiceManager.abortCalcRoute();
        this.mMiniAppRouteServiceManager.calcRoute(jSONObject, h5BridgeContext);
    }

    /* access modifiers changed from: protected */
    public void doDestroy() {
        this.mMiniAppRouteServiceManager.abortCalcRoute();
    }
}
