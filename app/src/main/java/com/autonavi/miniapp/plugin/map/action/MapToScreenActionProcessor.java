package com.autonavi.miniapp.plugin.map.action;

import android.content.Context;
import android.graphics.PointF;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.point.GeoPointHD;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils;
import java.lang.ref.WeakReference;

public class MapToScreenActionProcessor extends BaseActionProcessor {
    private static final String INVALID_POSITION = "坐标转换超出范围";
    private static final String TAG = "MapToScreenActionProcessor";

    public MapToScreenActionProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView) {
        super("mapToScreen", weakReference, weakReference2, adapterTextureMapView);
    }

    /* access modifiers changed from: protected */
    public void doProcess(JSONObject jSONObject, H5BridgeContext h5BridgeContext) {
        Context context = (Context) this.mContext.get();
        if (context != null) {
            try {
                double doubleValue = jSONObject.getDouble("latitude").doubleValue();
                double doubleValue2 = jSONObject.getDouble("longitude").doubleValue();
                StringBuilder sb = new StringBuilder("params, lat:");
                sb.append(doubleValue);
                sb.append(", lon: ");
                sb.append(doubleValue2);
                AMapLog.debug("infoservice.miniapp", TAG, sb.toString());
                if (!H5MapUtils.isLatLonValid(doubleValue, doubleValue2)) {
                    h5BridgeContext.sendError(11, (String) INVALID_POSITION);
                    return;
                }
                GeoPointHD geoPointHD = new GeoPointHD(doubleValue2, doubleValue);
                PointF pointF = new PointF();
                akq.b().d.p20ToScreenPoint(this.mRealView.getMap().j(), geoPointHD.x, geoPointHD.y, pointF);
                AMapLog.debug("infoservice.miniapp", TAG, "out: ".concat(String.valueOf(pointF)));
                if (!H5MapUtils.isPointValid((double) pointF.x, (double) pointF.y, this.mRealView.getMap())) {
                    h5BridgeContext.sendError(11, (String) INVALID_POSITION);
                    return;
                }
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put((String) DictionaryKeys.CTRLXY_X, (Object) Double.valueOf(H5MapUtils.convertScreenDP((double) H5MapUtils.px2dp(context, pointF.x))));
                jSONObject2.put((String) DictionaryKeys.CTRLXY_Y, (Object) Double.valueOf(H5MapUtils.convertScreenDP((double) H5MapUtils.px2dp(context, pointF.y))));
                h5BridgeContext.sendBridgeResult(jSONObject2);
            } catch (Exception unused) {
                h5BridgeContext.sendError(2, (String) "invalid parameter!");
            }
        }
    }
}
