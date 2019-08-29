package com.autonavi.miniapp.plugin.map.action;

import android.content.Context;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView.ComponentJsonObj;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor;
import com.autonavi.miniapp.plugin.map.property.MiscPropertyProcssor;
import com.autonavi.miniapp.plugin.map.property.PolylinePropertyProcessor;
import java.lang.ref.WeakReference;

public class UpdateComponentsActionProcessor extends BaseActionProcessor {
    private MarkerPropertyProcessor mMarkerProcessor;
    private MiscPropertyProcssor mMiscProcessor;
    private PolylinePropertyProcessor mPolylineProcessor;

    public UpdateComponentsActionProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView, MiscPropertyProcssor miscPropertyProcssor, PolylinePropertyProcessor polylinePropertyProcessor, MarkerPropertyProcessor markerPropertyProcessor) {
        super("updateComponents", weakReference, weakReference2, adapterTextureMapView);
        this.mMiscProcessor = miscPropertyProcssor;
        this.mPolylineProcessor = polylinePropertyProcessor;
        this.mMarkerProcessor = markerPropertyProcessor;
    }

    /* access modifiers changed from: protected */
    public void doProcess(JSONObject jSONObject, H5BridgeContext h5BridgeContext) {
        if (jSONObject == null) {
            AMapLog.warning("infoservice.miniapp", AMapH5EmbedMapView.TAG, "update components with empty json data");
            return;
        }
        try {
            ComponentJsonObj componentJsonObj = (ComponentJsonObj) jSONObject.toJavaObject(ComponentJsonObj.class);
            if (componentJsonObj != null) {
                this.mMiscProcessor.process(componentJsonObj);
                if (componentJsonObj.polyline != null) {
                    this.mPolylineProcessor.process(componentJsonObj);
                }
                if (!(componentJsonObj.markers == null && componentJsonObj.command == null)) {
                    this.mMarkerProcessor.process(componentJsonObj);
                }
            }
        } catch (Throwable th) {
            AMapLog.warning("infoservice.miniapp", AMapH5EmbedMapView.TAG, Log.getStackTraceString(th));
        }
    }
}
