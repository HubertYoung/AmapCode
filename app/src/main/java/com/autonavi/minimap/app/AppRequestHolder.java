package com.autonavi.minimap.app;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.app.param.EntranceListRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class AppRequestHolder {
    private static volatile AppRequestHolder instance;

    private AppRequestHolder() {
    }

    public static AppRequestHolder getInstance() {
        if (instance == null) {
            synchronized (AppRequestHolder.class) {
                if (instance == null) {
                    instance = new AppRequestHolder();
                }
            }
        }
        return instance;
    }

    public void sendEntranceList(EntranceListRequest entranceListRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendEntranceList(entranceListRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendEntranceList(EntranceListRequest entranceListRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            entranceListRequest.addHeaders(dkn.d);
            entranceListRequest.setTimeout(dkn.b);
            entranceListRequest.setRetryTimes(dkn.c);
        }
        entranceListRequest.setUrl(EntranceListRequest.a);
        entranceListRequest.addSignParam("channel");
        entranceListRequest.addSignParam(LocationParams.PARA_FLP_AUTONAVI_LON);
        entranceListRequest.addSignParam("lat");
        entranceListRequest.addReqParam(LocationParams.PARA_FLP_AUTONAVI_LON, entranceListRequest.b);
        entranceListRequest.addReqParam("lat", entranceListRequest.c);
        entranceListRequest.addReqParam(AutoJsonUtils.JSON_ADCODE, entranceListRequest.d);
        entranceListRequest.addReqParam("xy_backup", entranceListRequest.e);
        if (dkn != null) {
            in.a().a((AosRequest) entranceListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) entranceListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
