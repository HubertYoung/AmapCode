package com.autonavi.minimap.aocs;

import com.alibaba.appmonitor.offline.TempEvent;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.aocs.param.Updatable11Request;
import com.autonavi.minimap.aocs.param.UpdatableRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class AocsRequestHolder {
    private static volatile AocsRequestHolder instance;

    private AocsRequestHolder() {
    }

    public static AocsRequestHolder getInstance() {
        if (instance == null) {
            synchronized (AocsRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new AocsRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendUpdatable(UpdatableRequest updatableRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendUpdatable(updatableRequest, new dkn(), aosResponseCallback);
    }

    public void sendUpdatable11(Updatable11Request updatable11Request, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendUpdatable11(updatable11Request, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendUpdatable(UpdatableRequest updatableRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            updatableRequest.addHeaders(dkn.d);
            updatableRequest.setTimeout(dkn.b);
            updatableRequest.setRetryTimes(dkn.c);
        }
        updatableRequest.setUrl(UpdatableRequest.a);
        updatableRequest.addSignParam("channel");
        updatableRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        updatableRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        updatableRequest.addReqParam("update_mode", updatableRequest.b);
        updatableRequest.addReqParam(TempEvent.TAG_MODULE, updatableRequest.c);
        updatableRequest.addReqParam(LocationParams.PARA_FLP_AUTONAVI_LON, updatableRequest.d);
        updatableRequest.addReqParam("lat", updatableRequest.e);
        if (dkn != null) {
            in.a().a((AosRequest) updatableRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) updatableRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendUpdatable11(Updatable11Request updatable11Request, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            updatable11Request.addHeaders(dkn.d);
            updatable11Request.setTimeout(dkn.b);
            updatable11Request.setRetryTimes(dkn.c);
        }
        updatable11Request.setUrl(Updatable11Request.a);
        updatable11Request.addSignParam("channel");
        updatable11Request.addSignParam(LocationParams.PARA_COMMON_DIU);
        updatable11Request.addSignParam(LocationParams.PARA_COMMON_DIV);
        updatable11Request.addReqParam("update_mode", updatable11Request.b);
        updatable11Request.addReqParam(TempEvent.TAG_MODULE, updatable11Request.c);
        updatable11Request.addReqParam("dversion", updatable11Request.d);
        if (dkn != null) {
            in.a().a((AosRequest) updatable11Request, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) updatable11Request, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
