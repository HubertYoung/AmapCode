package com.autonavi.minimap.scenic;

import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.scenic.param.ScenicMainPageRecRequest;
import com.autonavi.sdk.location.LocationInstrument;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class ScenicMainPageRecRequestHolder {
    private static volatile ScenicMainPageRecRequestHolder instance;

    private ScenicMainPageRecRequestHolder() {
    }

    public static ScenicMainPageRecRequestHolder getInstance() {
        if (instance == null) {
            synchronized (ScenicMainPageRecRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new ScenicMainPageRecRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendScenicMainPageRec(ScenicMainPageRecRequest scenicMainPageRecRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendScenicMainPageRec(scenicMainPageRecRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendScenicMainPageRec(ScenicMainPageRecRequest scenicMainPageRecRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            scenicMainPageRecRequest.addHeaders(dkn.d);
            scenicMainPageRecRequest.setTimeout(dkn.b);
            scenicMainPageRecRequest.setRetryTimes(dkn.c);
        }
        scenicMainPageRecRequest.setUrl(ScenicMainPageRecRequest.a);
        scenicMainPageRecRequest.addSignParam("channel");
        scenicMainPageRecRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        scenicMainPageRecRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        scenicMainPageRecRequest.addReqParam(H5PermissionManager.level, scenicMainPageRecRequest.b);
        scenicMainPageRecRequest.addReqParam(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, scenicMainPageRecRequest.c);
        scenicMainPageRecRequest.addReqParam("scene_type", scenicMainPageRecRequest.d);
        scenicMainPageRecRequest.addReqParam("types", scenicMainPageRecRequest.e);
        scenicMainPageRecRequest.addReqParam("geoobj", scenicMainPageRecRequest.f);
        scenicMainPageRecRequest.addReqParam("user_loc", scenicMainPageRecRequest.g);
        if (dkn != null) {
            in.a().a((AosRequest) scenicMainPageRecRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) scenicMainPageRecRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
