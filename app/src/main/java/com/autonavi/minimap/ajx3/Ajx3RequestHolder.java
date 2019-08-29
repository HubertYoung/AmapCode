package com.autonavi.minimap.ajx3;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.ajx3.param.UpdatableInitRequest;
import com.autonavi.minimap.ajx3.param.UpdatableSchemeRequest;
import com.autonavi.minimap.ajx3.param.UpdatableWebRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class Ajx3RequestHolder {
    private static volatile Ajx3RequestHolder instance;

    private Ajx3RequestHolder() {
    }

    public static Ajx3RequestHolder getInstance() {
        if (instance == null) {
            synchronized (Ajx3RequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new Ajx3RequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendUpdatableInit(UpdatableInitRequest updatableInitRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendUpdatableInit(updatableInitRequest, new dkn(), aosResponseCallback);
    }

    public void sendUpdatableScheme(UpdatableSchemeRequest updatableSchemeRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendUpdatableScheme(updatableSchemeRequest, new dkn(), aosResponseCallback);
    }

    public void sendUpdatableWeb(UpdatableWebRequest updatableWebRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendUpdatableWeb(updatableWebRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendUpdatableInit(UpdatableInitRequest updatableInitRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            updatableInitRequest.addHeaders(dkn.d);
            updatableInitRequest.setTimeout(dkn.b);
            updatableInitRequest.setRetryTimes(dkn.c);
        }
        updatableInitRequest.build();
        if (dkn != null) {
            in.a().a((AosRequest) updatableInitRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) updatableInitRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendUpdatableScheme(UpdatableSchemeRequest updatableSchemeRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            updatableSchemeRequest.addHeaders(dkn.d);
            updatableSchemeRequest.setTimeout(dkn.b);
            updatableSchemeRequest.setRetryTimes(dkn.c);
        }
        updatableSchemeRequest.build();
        if (dkn != null) {
            in.a().a((AosRequest) updatableSchemeRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) updatableSchemeRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendUpdatableWeb(UpdatableWebRequest updatableWebRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            updatableWebRequest.addHeaders(dkn.d);
            updatableWebRequest.setTimeout(dkn.b);
            updatableWebRequest.setRetryTimes(dkn.c);
        }
        updatableWebRequest.build();
        if (dkn != null) {
            in.a().a((AosRequest) updatableWebRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) updatableWebRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
