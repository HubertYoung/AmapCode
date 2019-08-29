package com.autonavi.miniapp.plugin.map.action;

import android.content.Context;
import android.graphics.Rect;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils;
import com.autonavi.minimap.map.DPoint;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;

public class GetRegionActionProcessor extends BaseActionProcessor {
    public GetRegionActionProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView) {
        super("getRegion", weakReference, weakReference2, adapterTextureMapView);
    }

    /* access modifiers changed from: protected */
    public void doProcess(JSONObject jSONObject, H5BridgeContext h5BridgeContext) {
        if (h5BridgeContext == null) {
            AMapLog.error("infoservice.miniapp", GetRegionActionProcessor.class.getSimpleName(), "doProcess bridgeContext is null!");
            return;
        }
        Rect H = this.mRealView.getMap().H();
        DPoint a = cfg.a((long) H.left, (long) H.bottom);
        DPoint a2 = cfg.a((long) H.right, (long) H.top);
        DPoint formatLongitudeAndLatitude = formatLongitudeAndLatitude(a);
        DPoint formatLongitudeAndLatitude2 = formatLongitudeAndLatitude(a2);
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put((String) "longitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(formatLongitudeAndLatitude.x)));
        jSONObject2.put((String) "latitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(formatLongitudeAndLatitude.y)));
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put((String) "longitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(formatLongitudeAndLatitude2.x)));
        jSONObject3.put((String) "latitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(formatLongitudeAndLatitude2.y)));
        JSONObject jSONObject4 = new JSONObject();
        jSONObject4.put((String) "southwest", (Object) jSONObject2);
        jSONObject4.put((String) "northeast", (Object) jSONObject3);
        h5BridgeContext.sendBridgeResult(jSONObject4);
        String simpleName = GetRegionActionProcessor.class.getSimpleName();
        StringBuilder sb = new StringBuilder("getRegion");
        sb.append(jSONObject4.toJSONString());
        AMapLog.debug("infoservice.miniapp", simpleName, sb.toString());
    }

    private DPoint formatLongitudeAndLatitude(DPoint dPoint) {
        if (dPoint == null) {
            return dPoint;
        }
        dPoint.x = BigDecimal.valueOf(dPoint.x).setScale(7, 1).doubleValue();
        dPoint.y = BigDecimal.valueOf(dPoint.y).setScale(7, 1).doubleValue();
        return dPoint;
    }
}
