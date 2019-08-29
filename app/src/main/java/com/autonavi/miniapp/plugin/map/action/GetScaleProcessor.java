package com.autonavi.miniapp.plugin.map.action;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils;
import java.lang.ref.WeakReference;

public class GetScaleProcessor extends BaseActionProcessor {
    public boolean shouldForceRefresh() {
        return false;
    }

    public GetScaleProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView) {
        super("getScale", weakReference, weakReference2, adapterTextureMapView);
    }

    /* access modifiers changed from: protected */
    public void doProcess(JSONObject jSONObject, H5BridgeContext h5BridgeContext) {
        if (h5BridgeContext == null) {
            AMapLog.error("infoservice.miniapp", GetScaleProcessor.class.getSimpleName(), "doProcess bridgeContext is null!");
            return;
        }
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put((String) WidgetType.SCALE, (Object) Double.valueOf(H5MapUtils.convertScale((double) this.mRealView.getMap().v())));
        h5BridgeContext.sendBridgeResult(jSONObject2);
        String simpleName = GetScaleProcessor.class.getSimpleName();
        StringBuilder sb = new StringBuilder("getScale");
        sb.append(jSONObject2.toJSONString());
        AMapLog.debug("infoservice.miniapp", simpleName, sb.toString());
    }

    /* access modifiers changed from: protected */
    public void doDestroy() {
        super.doDestroy();
    }
}
