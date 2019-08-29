package com.autonavi.minimap.businfo;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.businfo.param.CleanRequest;
import com.autonavi.minimap.businfo.param.SubscribeRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class BusInfoRequestHolder {
    private static volatile BusInfoRequestHolder instance;

    private BusInfoRequestHolder() {
    }

    public static BusInfoRequestHolder getInstance() {
        if (instance == null) {
            synchronized (BusInfoRequestHolder.class) {
                if (instance == null) {
                    instance = new BusInfoRequestHolder();
                }
            }
        }
        return instance;
    }

    public void sendClean(CleanRequest cleanRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendClean(cleanRequest, new dkn(), aosResponseCallback);
    }

    public void sendSubscribe(SubscribeRequest subscribeRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendSubscribe(subscribeRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendClean(CleanRequest cleanRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            cleanRequest.addHeaders(dkn.d);
            cleanRequest.setTimeout(dkn.b);
            cleanRequest.setRetryTimes(dkn.c);
        }
        cleanRequest.setUrl(CleanRequest.a);
        cleanRequest.addSignParam("channel");
        cleanRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        cleanRequest.addReqParam(LocationParams.PARA_COMMON_DIU, cleanRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) cleanRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) cleanRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendSubscribe(SubscribeRequest subscribeRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            subscribeRequest.addHeaders(dkn.d);
            subscribeRequest.setTimeout(dkn.b);
            subscribeRequest.setRetryTimes(dkn.c);
        }
        subscribeRequest.setUrl(SubscribeRequest.a);
        subscribeRequest.addSignParam("channel");
        subscribeRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        subscribeRequest.addSignParam("station_id");
        subscribeRequest.addSignParam("line_id");
        subscribeRequest.addReqParam(LocationParams.PARA_COMMON_DIU, subscribeRequest.b);
        subscribeRequest.addReqParam("tid", subscribeRequest.c);
        subscribeRequest.addReqParam(LocationParams.PARA_COMMON_DIV, subscribeRequest.d);
        subscribeRequest.addReqParam("token", subscribeRequest.e);
        subscribeRequest.addReqParam("station_id", subscribeRequest.f);
        subscribeRequest.addReqParam("line_id", subscribeRequest.g);
        subscribeRequest.addReqParam("is_push", subscribeRequest.h);
        subscribeRequest.addReqParam("push_time", subscribeRequest.i);
        subscribeRequest.addReqParam("push_weekday", subscribeRequest.j);
        if (dkn != null) {
            in.a().a((AosRequest) subscribeRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) subscribeRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
