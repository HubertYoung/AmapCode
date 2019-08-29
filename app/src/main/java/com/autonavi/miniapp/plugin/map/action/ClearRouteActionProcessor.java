package com.autonavi.miniapp.plugin.map.action;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteManager;
import java.lang.ref.WeakReference;

public class ClearRouteActionProcessor extends BaseActionProcessor {
    private MiniAppShowRouteManager mMiniAppShowRouteManager;

    public ClearRouteActionProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView, MiniAppShowRouteManager miniAppShowRouteManager) {
        super("clearRoute", weakReference, weakReference2, adapterTextureMapView);
        this.mMiniAppShowRouteManager = miniAppShowRouteManager;
    }

    /* access modifiers changed from: protected */
    public void doProcess(JSONObject jSONObject, H5BridgeContext h5BridgeContext) {
        this.mMiniAppShowRouteManager.clearRoute();
    }
}
