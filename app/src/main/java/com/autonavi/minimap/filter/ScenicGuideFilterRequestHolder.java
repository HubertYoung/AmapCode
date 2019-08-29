package com.autonavi.minimap.filter;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.filter.param.ScenicGuideFilterRequest;
import com.autonavi.sdk.location.LocationInstrument;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class ScenicGuideFilterRequestHolder {
    private static ScenicGuideFilterRequestHolder instance;

    private ScenicGuideFilterRequestHolder() {
    }

    public static ScenicGuideFilterRequestHolder getInstance() {
        if (instance == null) {
            synchronized (ScenicGuideFilterRequestHolder.class) {
                if (instance == null) {
                    instance = new ScenicGuideFilterRequestHolder();
                }
            }
        }
        return instance;
    }

    public void sendScenicGuideFilter(ScenicGuideFilterRequest scenicGuideFilterRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendScenicGuideFilter(scenicGuideFilterRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendScenicGuideFilter(ScenicGuideFilterRequest scenicGuideFilterRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            scenicGuideFilterRequest.addHeaders(dkn.d);
            scenicGuideFilterRequest.setTimeout(dkn.b);
            scenicGuideFilterRequest.setRetryTimes(dkn.c);
        }
        scenicGuideFilterRequest.setUrl(ScenicGuideFilterRequest.a);
        scenicGuideFilterRequest.addSignParam("channel");
        scenicGuideFilterRequest.addSignParam(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
        scenicGuideFilterRequest.addReqParam("super_id", scenicGuideFilterRequest.b);
        scenicGuideFilterRequest.addReqParam("geoobj", scenicGuideFilterRequest.c);
        scenicGuideFilterRequest.addReqParam(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, scenicGuideFilterRequest.d);
        scenicGuideFilterRequest.addReqParam("back_args", scenicGuideFilterRequest.e);
        scenicGuideFilterRequest.addReqParam("user_loc", scenicGuideFilterRequest.f);
        if (dkn != null) {
            in.a().a((AosRequest) scenicGuideFilterRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) scenicGuideFilterRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
