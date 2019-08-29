package com.autonavi.minimap.recommend;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.recommend.param.RouteRrunRequest;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class RecommendRequestHolder {
    private static volatile RecommendRequestHolder instance;

    private RecommendRequestHolder() {
    }

    public static RecommendRequestHolder getInstance() {
        if (instance == null) {
            synchronized (RecommendRequestHolder.class) {
                if (instance == null) {
                    instance = new RecommendRequestHolder();
                }
            }
        }
        return instance;
    }

    public void sendRouteRrun(RouteRrunRequest routeRrunRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendRouteRrun(routeRrunRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendRouteRrun(RouteRrunRequest routeRrunRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            routeRrunRequest.addHeaders(dkn.d);
            routeRrunRequest.setTimeout(dkn.b);
            routeRrunRequest.setRetryTimes(dkn.c);
        }
        routeRrunRequest.setUrl(RouteRrunRequest.a);
        routeRrunRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        routeRrunRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        routeRrunRequest.addReqParam(DictionaryKeys.CTRLXY_X, routeRrunRequest.b);
        routeRrunRequest.addReqParam(DictionaryKeys.CTRLXY_Y, routeRrunRequest.c);
        routeRrunRequest.addReqParam("lv", routeRrunRequest.d);
        routeRrunRequest.addReqParam("length", routeRrunRequest.e);
        if (dkn != null) {
            in.a().a((AosRequest) routeRrunRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) routeRrunRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
