package com.autonavi.minimap.tservice;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.tservice.param.SendPoi2CarRequest;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class TserviceRequestHolder {
    private static volatile TserviceRequestHolder instance;

    private TserviceRequestHolder() {
    }

    public static TserviceRequestHolder getInstance() {
        if (instance == null) {
            synchronized (TserviceRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new TserviceRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendSendPoi2Car(SendPoi2CarRequest sendPoi2CarRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendSendPoi2Car(sendPoi2CarRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendSendPoi2Car(SendPoi2CarRequest sendPoi2CarRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            sendPoi2CarRequest.addHeaders(dkn.d);
            sendPoi2CarRequest.setTimeout(dkn.b);
            sendPoi2CarRequest.setRetryTimes(dkn.c);
        }
        sendPoi2CarRequest.setUrl(SendPoi2CarRequest.a);
        sendPoi2CarRequest.addSignParam("channel");
        sendPoi2CarRequest.addSignParam("message");
        sendPoi2CarRequest.addSignParam("isReliable");
        sendPoi2CarRequest.addSignParam("expiration");
        sendPoi2CarRequest.addReqParam("message", sendPoi2CarRequest.b);
        sendPoi2CarRequest.addReqParam("isReliable", sendPoi2CarRequest.c);
        sendPoi2CarRequest.addReqParam("expiration", sendPoi2CarRequest.d);
        sendPoi2CarRequest.addReqParam("aimChannel", sendPoi2CarRequest.e);
        sendPoi2CarRequest.addReqParam("sourceid", sendPoi2CarRequest.f);
        sendPoi2CarRequest.addReqParam("bizType", sendPoi2CarRequest.g);
        if (dkn != null) {
            in.a().a((AosRequest) sendPoi2CarRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) sendPoi2CarRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
