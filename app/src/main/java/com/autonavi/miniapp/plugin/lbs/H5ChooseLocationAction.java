package com.autonavi.miniapp.plugin.lbs;

import android.content.Intent;
import android.location.Location;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.nebula.util.H5Log;
import com.autonavi.common.model.POI;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.sdk.location.LocationInstrument;

public class H5ChooseLocationAction extends H5BaseLocationAction {
    private static final String TAG = "com.autonavi.miniapp.plugin.lbs.H5ChooseLocationAction";
    /* access modifiers changed from: private */
    public final H5BridgeContext mBridgeContext;
    private SimpleLocationCache mSimpleLocationCache;

    public H5ChooseLocationAction(H5Event h5Event, H5BridgeContext h5BridgeContext, H5Location h5Location, long j, SimpleLocationCache simpleLocationCache) {
        super(h5Event, h5BridgeContext, h5Location, j);
        this.mTag = "H5ChooseLocationAction";
        this.mSimpleLocationCache = simpleLocationCache;
        this.mBridgeContext = h5BridgeContext;
    }

    /* access modifiers changed from: protected */
    public void handleValidDomainEvent() {
        super.handleValidDomainEvent();
        H5Log.d(TAG, "H5ChooseLocationAction");
        if (this.mH5Event == null) {
            H5Log.d(TAG, "H5ChooseLocationAction event == null");
            return;
        }
        try {
            if (this.mH5Event.getParam() == null) {
                H5Log.d(TAG, "H5ChooseLocationAction param == null");
                return;
            }
            AnonymousClass1 r0 = new OnMapPoiSelectedListener() {
                public void onPoiSelectCancel() {
                    H5ChooseLocationAction.this.mBridgeContext.sendError(11, (String) "用户取消操作");
                }

                public void onPoiSelected(POI poi) {
                    if (poi != null) {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put((String) "adCode", (Object) poi.getAdCode());
                        jSONObject.put((String) "cityCode", (Object) poi.getCityCode());
                        jSONObject.put((String) "cityName", (Object) poi.getCityName());
                        jSONObject.put((String) "distance", (Object) Integer.valueOf(poi.getDistance()));
                        jSONObject.put((String) "latLonPoint", (Object) new LatLonPoint(H5MapUtils.convertLatLon(poi.getPoint().getLatitude()), H5MapUtils.convertLatLon(poi.getPoint().getLongitude())));
                        jSONObject.put((String) TrafficUtil.POIID, (Object) poi.getId());
                        jSONObject.put((String) "longitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(poi.getPoint().getLongitude())));
                        jSONObject.put((String) "latitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(poi.getPoint().getLatitude())));
                        jSONObject.put((String) "name", (Object) poi.getName());
                        jSONObject.put((String) "address", (Object) poi.getAddr());
                        H5ChooseLocationAction.this.mBridgeContext.sendBridgeResult(jSONObject);
                    }
                }

                public void onHideLocationSelected() {
                    H5ChooseLocationAction.this.mBridgeContext.sendBridgeResult(new JSONObject());
                }
            };
            Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
            Intent intent = new Intent();
            intent.putExtra("latitude", latestLocation.getLatitude());
            intent.putExtra("longitude", latestLocation.getLongitude());
            intent.setClass(LauncherApplicationAgent.getInstance().getApplicationContext(), H5MapChooseLocationActivity.class);
            ((awi) a.a.a(awi.class)).a(intent, r0);
        } catch (Exception e) {
            H5Log.e(TAG, "H5ChooseLocationAction exception.", e);
        }
    }

    /* access modifiers changed from: protected */
    public void doPositiveEvent() {
        super.doPositiveEvent();
    }
}
