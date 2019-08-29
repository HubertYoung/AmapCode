package com.autonavi.minimap.weather;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.weather.param.InfoRequest;
import com.autonavi.minimap.weather.param.MojiRequest;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class WeatherRequestHolder {
    private static volatile WeatherRequestHolder instance;

    private WeatherRequestHolder() {
    }

    public static WeatherRequestHolder getInstance() {
        if (instance == null) {
            synchronized (WeatherRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new WeatherRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendInfo(InfoRequest infoRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendInfo(infoRequest, new dkn(), aosResponseCallback);
    }

    public void sendMoji(MojiRequest mojiRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendMoji(mojiRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendInfo(InfoRequest infoRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            infoRequest.addHeaders(dkn.d);
            infoRequest.setTimeout(dkn.b);
            infoRequest.setRetryTimes(dkn.c);
        }
        infoRequest.setUrl(InfoRequest.a);
        infoRequest.addSignParam("channel");
        infoRequest.addSignParam(AutoJsonUtils.JSON_ADCODE);
        infoRequest.addSignParam("longitude");
        infoRequest.addSignParam("latitude");
        infoRequest.addReqParam(AutoJsonUtils.JSON_ADCODE, infoRequest.b);
        infoRequest.addReqParam("today", infoRequest.c);
        infoRequest.addReqParam("index", infoRequest.d);
        infoRequest.addReqParam("pic", infoRequest.e);
        infoRequest.addReqParam("bg", infoRequest.f);
        infoRequest.addReqParam("pm25", infoRequest.g);
        infoRequest.addReqParam("traffic_restrict", infoRequest.h);
        infoRequest.addReqParam("longitude", infoRequest.i);
        infoRequest.addReqParam("latitude", infoRequest.j);
        if (dkn != null) {
            in.a().a((AosRequest) infoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) infoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendMoji(MojiRequest mojiRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            mojiRequest.addHeaders(dkn.d);
            mojiRequest.setTimeout(dkn.b);
            mojiRequest.setRetryTimes(dkn.c);
        }
        mojiRequest.setUrl(MojiRequest.a);
        mojiRequest.addSignParam("channel");
        mojiRequest.addSignParam(LocationParams.PARA_FLP_AUTONAVI_LON);
        mojiRequest.addSignParam("lat");
        mojiRequest.addSignParam("image_standard");
        mojiRequest.addReqParam(LocationParams.PARA_FLP_AUTONAVI_LON, mojiRequest.b);
        mojiRequest.addReqParam("lat", mojiRequest.c);
        mojiRequest.addReqParam("image_standard", mojiRequest.d);
        mojiRequest.addReqParam("traffic_restrict", mojiRequest.e);
        mojiRequest.addReqParam("car_washing", mojiRequest.f);
        mojiRequest.addReqParam("theme", mojiRequest.g);
        mojiRequest.addReqParam("aqi", mojiRequest.h);
        mojiRequest.addReqParam("forecast", mojiRequest.i);
        mojiRequest.addReqParam(AutoJsonUtils.JSON_ADCODE, mojiRequest.j);
        mojiRequest.addReqParam("local_desc", mojiRequest.k);
        if (dkn != null) {
            in.a().a((AosRequest) mojiRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) mojiRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
