package com.autonavi.miniapp.plugin.map.action;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils;
import java.lang.ref.WeakReference;

public class GetCenterLocationActionProcessor extends BaseActionProcessor {
    public boolean shouldForceRefresh() {
        return false;
    }

    public GetCenterLocationActionProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView) {
        super("getCenterLocation", weakReference, weakReference2, adapterTextureMapView);
    }

    /* access modifiers changed from: protected */
    public void doProcess(JSONObject jSONObject, H5BridgeContext h5BridgeContext) {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put((String) "longitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(this.mRealView.getMap().o().getLongitude())));
        jSONObject2.put((String) "latitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(this.mRealView.getMap().o().getLatitude())));
        jSONObject2.put((String) WidgetType.SCALE, (Object) Double.valueOf(H5MapUtils.convertScale((double) this.mRealView.getMap().v())));
        h5BridgeContext.sendBridgeResult(jSONObject2);
        StringBuilder sb = new StringBuilder("getCenterLocation ");
        sb.append(jSONObject2.toJSONString());
        AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, sb.toString());
    }
}
