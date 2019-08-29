package com.autonavi.miniapp.plugin.map.action;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import java.lang.ref.WeakReference;

public class ShowTileOverlayActionProcessor extends BaseActionProcessor {
    /* access modifiers changed from: protected */
    public void doProcess(JSONObject jSONObject, H5BridgeContext h5BridgeContext) {
    }

    public ShowTileOverlayActionProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView) {
        super("showTileOverLay", weakReference, weakReference2, adapterTextureMapView);
    }
}
