package com.autonavi.minimap.realtimebus;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.realtimebus.param.LineStationRequest;
import com.autonavi.minimap.realtimebus.param.LinesExRequest;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class RealtimeBusRequestHolder {
    private static volatile RealtimeBusRequestHolder instance;

    private RealtimeBusRequestHolder() {
    }

    public static RealtimeBusRequestHolder getInstance() {
        if (instance == null) {
            synchronized (RealtimeBusRequestHolder.class) {
                if (instance == null) {
                    instance = new RealtimeBusRequestHolder();
                }
            }
        }
        return instance;
    }

    public void sendLinesEx(LinesExRequest linesExRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendLinesEx(linesExRequest, new dkn(), aosResponseCallback);
    }

    public void sendLineStation(LineStationRequest lineStationRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendLineStation(lineStationRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendLinesEx(LinesExRequest linesExRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            linesExRequest.addHeaders(dkn.d);
            linesExRequest.setTimeout(dkn.b);
            linesExRequest.setRetryTimes(dkn.c);
        }
        linesExRequest.setUrl(LinesExRequest.a);
        linesExRequest.addSignParam("channel");
        linesExRequest.addSignParam(AutoJsonUtils.JSON_ADCODE);
        linesExRequest.addSignParam("xy");
        linesExRequest.addSignParam("lines");
        linesExRequest.addReqParam("lines", linesExRequest.b);
        linesExRequest.addReqParam("xy", linesExRequest.c);
        linesExRequest.addReqParam(AutoJsonUtils.JSON_ADCODE, linesExRequest.d);
        linesExRequest.addReqParam("from_page", linesExRequest.e);
        linesExRequest.addReqParam("is_refresh", linesExRequest.f);
        if (dkn != null) {
            in.a().a((AosRequest) linesExRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) linesExRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendLineStation(LineStationRequest lineStationRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            lineStationRequest.addHeaders(dkn.d);
            lineStationRequest.setTimeout(dkn.b);
            lineStationRequest.setRetryTimes(dkn.c);
        }
        lineStationRequest.setUrl(LineStationRequest.a);
        lineStationRequest.addSignParam("channel");
        lineStationRequest.addSignParam(AutoJsonUtils.JSON_ADCODE);
        lineStationRequest.addSignParam("lines");
        lineStationRequest.addSignParam("stations");
        lineStationRequest.addReqParam(AutoJsonUtils.JSON_ADCODE, lineStationRequest.b);
        lineStationRequest.addReqParam("lines", lineStationRequest.c);
        lineStationRequest.addReqParam("stations", lineStationRequest.d);
        lineStationRequest.addReqParam("source_type", lineStationRequest.e);
        lineStationRequest.addReqParam("from_page", lineStationRequest.f);
        lineStationRequest.addReqParam("is_refresh", lineStationRequest.g);
        lineStationRequest.addReqParam(NewHtcHomeBadger.COUNT, lineStationRequest.h);
        lineStationRequest.addReqParam("need_not_depart", lineStationRequest.i ? "true" : "false");
        lineStationRequest.addReqParam("need_over_station", lineStationRequest.j);
        lineStationRequest.addReqParam("need_bus_track", lineStationRequest.k);
        lineStationRequest.addReqParam("need_bus_status", lineStationRequest.l);
        if (dkn != null) {
            in.a().a((AosRequest) lineStationRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) lineStationRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
