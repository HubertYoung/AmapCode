package com.autonavi.minimap.geo;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.geo.param.GeoCodeRequest;
import com.autonavi.minimap.geo.param.ReverseCodeRequest;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class GeoRequestHolder {
    private static volatile GeoRequestHolder instance;

    private GeoRequestHolder() {
    }

    public static GeoRequestHolder getInstance() {
        if (instance == null) {
            synchronized (GeoRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new GeoRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendReverseCode(ReverseCodeRequest reverseCodeRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendReverseCode(reverseCodeRequest, new dkn(), aosResponseCallback);
    }

    public void sendGeoCode(GeoCodeRequest geoCodeRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendGeoCode(geoCodeRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendReverseCode(ReverseCodeRequest reverseCodeRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            reverseCodeRequest.addHeaders(dkn.d);
            reverseCodeRequest.setTimeout(dkn.b);
            reverseCodeRequest.setRetryTimes(dkn.c);
        }
        reverseCodeRequest.setUrl(ReverseCodeRequest.a);
        reverseCodeRequest.addSignParam("channel");
        reverseCodeRequest.addSignParam("longitude");
        reverseCodeRequest.addSignParam("latitude");
        reverseCodeRequest.addReqParam("longitude", reverseCodeRequest.b);
        reverseCodeRequest.addReqParam("latitude", reverseCodeRequest.c);
        reverseCodeRequest.addReqParam("near", reverseCodeRequest.d ? "true" : "false");
        reverseCodeRequest.addReqParam("isoffset", reverseCodeRequest.e ? "true" : "false");
        reverseCodeRequest.addReqParam("desctype", Integer.toString(reverseCodeRequest.f));
        reverseCodeRequest.addReqParam("poinum", Integer.toString(reverseCodeRequest.g));
        reverseCodeRequest.addReqParam("crossnum", Integer.toString(reverseCodeRequest.h));
        reverseCodeRequest.addReqParam("roadnum", Integer.toString(reverseCodeRequest.i));
        reverseCodeRequest.addReqParam("outdoor_scene", reverseCodeRequest.j ? "true" : "false");
        reverseCodeRequest.addReqParam("category", reverseCodeRequest.k);
        reverseCodeRequest.addReqParam("show_sea_area", reverseCodeRequest.l ? "true" : "false");
        reverseCodeRequest.addReqParam("show_storecircle", reverseCodeRequest.m ? "true" : "false");
        if (dkn != null) {
            in.a().a((AosRequest) reverseCodeRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) reverseCodeRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendGeoCode(GeoCodeRequest geoCodeRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            geoCodeRequest.addHeaders(dkn.d);
            geoCodeRequest.setTimeout(dkn.b);
            geoCodeRequest.setRetryTimes(dkn.c);
        }
        geoCodeRequest.setUrl(GeoCodeRequest.a);
        geoCodeRequest.addSignParam("channel");
        geoCodeRequest.addSignParam("address");
        geoCodeRequest.addReqParam("address", geoCodeRequest.b);
        geoCodeRequest.addReqParam(AutoJsonUtils.JSON_ADCODE, geoCodeRequest.c);
        geoCodeRequest.addReqParam("onerow", geoCodeRequest.d);
        if (dkn != null) {
            in.a().a((AosRequest) geoCodeRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) geoCodeRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
