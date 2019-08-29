package com.autonavi.minimap.shortaddress;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.shortaddress.param.TransformRequest;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class ShortAddressRequestHolder {
    private static volatile ShortAddressRequestHolder instance;

    private ShortAddressRequestHolder() {
    }

    public static ShortAddressRequestHolder getInstance() {
        if (instance == null) {
            synchronized (ShortAddressRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new ShortAddressRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendTransform(TransformRequest transformRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendTransform(transformRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendTransform(TransformRequest transformRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            transformRequest.addHeaders(dkn.d);
            transformRequest.setTimeout(dkn.b);
            transformRequest.setRetryTimes(dkn.c);
        }
        transformRequest.setUrl(TransformRequest.a);
        transformRequest.addSignParam("channel");
        transformRequest.addSignParam("flag");
        transformRequest.addSignParam("address");
        transformRequest.addReqParam("flag", transformRequest.b);
        transformRequest.addReqParam("address", transformRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) transformRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) transformRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
