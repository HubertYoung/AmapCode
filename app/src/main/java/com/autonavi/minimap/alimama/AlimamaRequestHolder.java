package com.autonavi.minimap.alimama;

import com.alipay.sdk.app.statistic.c;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.alimama.param.H5LogRequest;
import com.autonavi.minimap.alimama.param.SplashScreenRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.ta.audid.store.UtdidContentBuilder;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class AlimamaRequestHolder {
    private static volatile AlimamaRequestHolder instance;

    private AlimamaRequestHolder() {
    }

    public static AlimamaRequestHolder getInstance() {
        if (instance == null) {
            synchronized (AlimamaRequestHolder.class) {
                if (instance == null) {
                    instance = new AlimamaRequestHolder();
                }
            }
        }
        return instance;
    }

    public void sendH5Log(H5LogRequest h5LogRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendH5Log(h5LogRequest, new dkn(), aosResponseCallback);
    }

    public void sendSplashScreen(SplashScreenRequest splashScreenRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendSplashScreen(splashScreenRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendH5Log(H5LogRequest h5LogRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            h5LogRequest.addHeaders(dkn.d);
            h5LogRequest.setTimeout(dkn.b);
            h5LogRequest.setRetryTimes(dkn.c);
        }
        h5LogRequest.setUrl(H5LogRequest.a);
        h5LogRequest.addSignParam("channel");
        h5LogRequest.addSignParam("id");
        h5LogRequest.addSignParam("timestamp");
        h5LogRequest.addReqParam("id", h5LogRequest.b);
        h5LogRequest.addReqParam("timestamp", h5LogRequest.c);
        h5LogRequest.addReqParam("page", h5LogRequest.e);
        h5LogRequest.addReqParam("session_id", h5LogRequest.d);
        h5LogRequest.addReqParam("click", h5LogRequest.f);
        h5LogRequest.addReqParam("displayfail", h5LogRequest.g);
        h5LogRequest.addReqParam("click_third", h5LogRequest.h);
        h5LogRequest.addReqParam("thirdurl", h5LogRequest.i);
        if (dkn != null) {
            in.a().a((AosRequest) h5LogRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) h5LogRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendSplashScreen(SplashScreenRequest splashScreenRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            splashScreenRequest.addHeaders(dkn.d);
            splashScreenRequest.setTimeout(dkn.b);
            splashScreenRequest.setRetryTimes(dkn.c);
        }
        splashScreenRequest.setUrl(SplashScreenRequest.a);
        splashScreenRequest.addSignParam("channel");
        splashScreenRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        splashScreenRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        splashScreenRequest.addReqParam(UtdidContentBuilder.TYPE_RS, splashScreenRequest.b);
        splashScreenRequest.addReqParam("dpr", Double.toString(splashScreenRequest.c));
        splashScreenRequest.addReqParam(c.a, splashScreenRequest.d);
        splashScreenRequest.addReqParam("netp", splashScreenRequest.e);
        splashScreenRequest.addReqParam("mnc", splashScreenRequest.f);
        splashScreenRequest.addReqParam("ict", splashScreenRequest.g);
        splashScreenRequest.addReqParam("it", Integer.toString(splashScreenRequest.h));
        splashScreenRequest.addReqParam("bn", splashScreenRequest.i);
        splashScreenRequest.addReqParam("mn", splashScreenRequest.j);
        splashScreenRequest.addReqParam("osv", splashScreenRequest.k);
        splashScreenRequest.addReqParam("mcc", splashScreenRequest.l);
        splashScreenRequest.addReqParam("mac", splashScreenRequest.m);
        if (dkn != null) {
            in.a().a((AosRequest) splashScreenRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) splashScreenRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
