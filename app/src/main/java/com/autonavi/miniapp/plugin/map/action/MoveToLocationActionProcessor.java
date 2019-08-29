package com.autonavi.miniapp.plugin.map.action;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.datamodel.point.GeoPointHD;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils;
import com.autonavi.sdk.location.LocationInstrument;
import java.lang.ref.WeakReference;

public class MoveToLocationActionProcessor extends BaseActionProcessor {
    public MoveToLocationActionProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView) {
        super("moveToLocation", weakReference, weakReference2, adapterTextureMapView);
    }

    /* access modifiers changed from: protected */
    public void doProcess(JSONObject jSONObject, H5BridgeContext h5BridgeContext) {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        GeoPointHD geoPointHD = new GeoPointHD(latestPosition);
        H5MapUtils.setMapCenter(geoPointHD.getLatitude(), geoPointHD.getLongitude(), this.mRealView.getMap());
        StringBuilder sb = new StringBuilder("moveToLocation ");
        sb.append(latestPosition.getLatitude());
        sb.append(Token.SEPARATOR);
        sb.append(latestPosition.getLongitude());
        AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, sb.toString());
    }
}
