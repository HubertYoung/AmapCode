package com.autonavi.minimap.awaken;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.awaken.param.AwakenPageRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class AwakenRequestHolder {
    private static volatile AwakenRequestHolder instance;

    private AwakenRequestHolder() {
    }

    public static AwakenRequestHolder getInstance() {
        if (instance == null) {
            synchronized (AwakenRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new AwakenRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendAwakenPage(AwakenPageRequest awakenPageRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendAwakenPage(awakenPageRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendAwakenPage(AwakenPageRequest awakenPageRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            awakenPageRequest.addHeaders(dkn.d);
            awakenPageRequest.setTimeout(dkn.b);
            awakenPageRequest.setRetryTimes(dkn.c);
        }
        awakenPageRequest.setUrl(AwakenPageRequest.a);
        awakenPageRequest.addSignParam("");
        if (dkn != null) {
            in.a().a((AosRequest) awakenPageRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) awakenPageRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
