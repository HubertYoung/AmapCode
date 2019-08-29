package com.autonavi.miniapp.plugin.map.action;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import java.lang.ref.WeakReference;

public class ShowCompassActionProcessor extends BaseActionProcessor {
    public ShowCompassActionProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView) {
        super("showsCompass", weakReference, weakReference2, adapterTextureMapView);
    }

    /* access modifiers changed from: protected */
    public void doProcess(JSONObject jSONObject, H5BridgeContext h5BridgeContext) {
        int intValue = jSONObject.getInteger("isShowsCompass").intValue();
        boolean z = true;
        if (intValue == 0 || intValue == 1) {
            AdapterTextureMapView adapterTextureMapView = this.mRealView;
            if (intValue != 1) {
                z = false;
            }
            adapterTextureMapView.setShowCompass(z);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(intValue);
        sb.append(" is invalid value of isShowsCompass");
        h5BridgeContext.sendError(2, sb.toString());
    }
}
