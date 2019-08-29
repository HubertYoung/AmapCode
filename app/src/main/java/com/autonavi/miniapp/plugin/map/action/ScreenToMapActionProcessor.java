package com.autonavi.miniapp.plugin.map.action;

import android.content.Context;
import android.graphics.Point;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.point.GeoPointHD;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils;
import java.lang.ref.WeakReference;

public class ScreenToMapActionProcessor extends BaseActionProcessor {
    private static final String INVALID_POSITION = "坐标转换超出范围";
    private static final String TAG = "ScreenToMapActionProcessor";

    public ScreenToMapActionProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView) {
        super("screenToMap", weakReference, weakReference2, adapterTextureMapView);
    }

    /* access modifiers changed from: protected */
    public void doProcess(JSONObject jSONObject, H5BridgeContext h5BridgeContext) {
        Context context = (Context) this.mContext.get();
        if (context != null) {
            try {
                float dp2px = H5MapUtils.dp2px(context, jSONObject.getFloat(DictionaryKeys.CTRLXY_X).floatValue());
                float dp2px2 = H5MapUtils.dp2px(context, jSONObject.getFloat(DictionaryKeys.CTRLXY_Y).floatValue());
                if (!H5MapUtils.isPointValid((double) dp2px, (double) dp2px2, this.mRealView.getMap())) {
                    h5BridgeContext.sendError(11, (String) INVALID_POSITION);
                    return;
                }
                StringBuilder sb = new StringBuilder("params, x:");
                sb.append(dp2px);
                sb.append(", y: ");
                sb.append(dp2px2);
                AMapLog.debug("infoservice.miniapp", TAG, sb.toString());
                Point point = new Point();
                akq.b().d.screenToP20Point(this.mRealView.getMap().j(), dp2px, dp2px2, point);
                GeoPointHD geoPointHD = new GeoPointHD(point.x, point.y);
                if (!H5MapUtils.isLatLonValid(geoPointHD.getLatitude(), geoPointHD.getLongitude())) {
                    h5BridgeContext.sendError(11, (String) INVALID_POSITION);
                    return;
                }
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put((String) "latitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(geoPointHD.getLatitude())));
                jSONObject2.put((String) "longitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(geoPointHD.getLongitude())));
                AMapLog.debug("infoservice.miniapp", TAG, "result: ".concat(String.valueOf(jSONObject2)));
                h5BridgeContext.sendBridgeResult(jSONObject2);
            } catch (Exception unused) {
                h5BridgeContext.sendError(2, (String) "invalid parameter!");
            }
        }
    }
}
