package com.autonavi.minimap.traffic;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.traffic.param.TmcOverallTrafficRequest;
import com.autonavi.minimap.traffic.param.VectorBoardInfoRequest;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class TrafficRequestHolder {
    private static volatile TrafficRequestHolder instance;

    private TrafficRequestHolder() {
    }

    public static TrafficRequestHolder getInstance() {
        if (instance == null) {
            synchronized (TrafficRequestHolder.class) {
                if (instance == null) {
                    instance = new TrafficRequestHolder();
                }
            }
        }
        return instance;
    }

    public void sendTmcOverallTraffic(TmcOverallTrafficRequest tmcOverallTrafficRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendTmcOverallTraffic(tmcOverallTrafficRequest, new dkn(), aosResponseCallback);
    }

    public void sendVectorBoardInfo(VectorBoardInfoRequest vectorBoardInfoRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendVectorBoardInfo(vectorBoardInfoRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendTmcOverallTraffic(TmcOverallTrafficRequest tmcOverallTrafficRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            tmcOverallTrafficRequest.addHeaders(dkn.d);
            tmcOverallTrafficRequest.setTimeout(dkn.b);
            tmcOverallTrafficRequest.setRetryTimes(dkn.c);
        }
        tmcOverallTrafficRequest.setUrl(TmcOverallTrafficRequest.a);
        tmcOverallTrafficRequest.addSignParam("channel");
        tmcOverallTrafficRequest.addSignParam("longitude");
        tmcOverallTrafficRequest.addSignParam("latitude");
        tmcOverallTrafficRequest.addSignParam("city");
        tmcOverallTrafficRequest.addReqParam("longitude", tmcOverallTrafficRequest.b);
        tmcOverallTrafficRequest.addReqParam("latitude", tmcOverallTrafficRequest.c);
        tmcOverallTrafficRequest.addReqParam("city", tmcOverallTrafficRequest.d);
        if (dkn != null) {
            in.a().a((AosRequest) tmcOverallTrafficRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) tmcOverallTrafficRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendVectorBoardInfo(VectorBoardInfoRequest vectorBoardInfoRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            vectorBoardInfoRequest.addHeaders(dkn.d);
            vectorBoardInfoRequest.setTimeout(dkn.b);
            vectorBoardInfoRequest.setRetryTimes(dkn.c);
        }
        vectorBoardInfoRequest.setUrl(VectorBoardInfoRequest.a);
        vectorBoardInfoRequest.addSignParam("channel");
        vectorBoardInfoRequest.addSignParam("panelid");
        vectorBoardInfoRequest.addSignParam(AutoJsonUtils.JSON_ADCODE);
        vectorBoardInfoRequest.addReqParam("panelid", vectorBoardInfoRequest.b);
        vectorBoardInfoRequest.addReqParam(AutoJsonUtils.JSON_ADCODE, vectorBoardInfoRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) vectorBoardInfoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) vectorBoardInfoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
