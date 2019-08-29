package com.autonavi.minimap.config;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.config.param.FileUpdateRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class ConfigRequestHolder {
    private static volatile ConfigRequestHolder instance;

    private ConfigRequestHolder() {
    }

    public static ConfigRequestHolder getInstance() {
        if (instance == null) {
            synchronized (ConfigRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new ConfigRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendFileUpdate(FileUpdateRequest fileUpdateRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendFileUpdate(fileUpdateRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendFileUpdate(FileUpdateRequest fileUpdateRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            fileUpdateRequest.addHeaders(dkn.d);
            fileUpdateRequest.setTimeout(dkn.b);
            fileUpdateRequest.setRetryTimes(dkn.c);
        }
        fileUpdateRequest.setUrl(FileUpdateRequest.a);
        fileUpdateRequest.addSignParam("channel");
        fileUpdateRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        fileUpdateRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        fileUpdateRequest.addReqParam("tbt_version", fileUpdateRequest.b);
        fileUpdateRequest.addReqParam("version", fileUpdateRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) fileUpdateRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) fileUpdateRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
