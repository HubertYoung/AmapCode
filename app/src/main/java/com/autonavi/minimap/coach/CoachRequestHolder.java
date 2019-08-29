package com.autonavi.minimap.coach;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.coach.param.ArrivalStationRequest;
import com.autonavi.minimap.coach.param.TicketsRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class CoachRequestHolder {
    private static volatile CoachRequestHolder instance;

    private CoachRequestHolder() {
    }

    public static CoachRequestHolder getInstance() {
        if (instance == null) {
            synchronized (CoachRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new CoachRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendTickets(TicketsRequest ticketsRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendTickets(ticketsRequest, new dkn(), aosResponseCallback);
    }

    public void sendArrivalStation(ArrivalStationRequest arrivalStationRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendArrivalStation(arrivalStationRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendTickets(TicketsRequest ticketsRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            ticketsRequest.addHeaders(dkn.d);
            ticketsRequest.setTimeout(dkn.b);
            ticketsRequest.setRetryTimes(dkn.c);
        }
        ticketsRequest.setUrl(TicketsRequest.a);
        ticketsRequest.addSignParam("channel");
        ticketsRequest.addSignParam("x1");
        ticketsRequest.addSignParam("x2");
        ticketsRequest.addSignParam("y1");
        ticketsRequest.addSignParam("y2");
        ticketsRequest.addSignParam("date");
        ticketsRequest.addSignParam("time");
        ticketsRequest.addSignParam("coachAgentID");
        ticketsRequest.addReqParam("x1", ticketsRequest.b);
        ticketsRequest.addReqParam("y1", ticketsRequest.c);
        ticketsRequest.addReqParam("x2", ticketsRequest.d);
        ticketsRequest.addReqParam("y2", ticketsRequest.e);
        ticketsRequest.addReqParam("poiid1", ticketsRequest.f);
        ticketsRequest.addReqParam("poiid2", ticketsRequest.g);
        ticketsRequest.addReqParam("pn1", ticketsRequest.h);
        ticketsRequest.addReqParam("pn2", ticketsRequest.i);
        ticketsRequest.addReqParam("req_num", ticketsRequest.j);
        ticketsRequest.addReqParam("date", ticketsRequest.k);
        ticketsRequest.addReqParam("time", ticketsRequest.l);
        ticketsRequest.addReqParam("coachAgentID", ticketsRequest.m);
        ticketsRequest.addReqParam("ver", ticketsRequest.n);
        if (dkn != null) {
            in.a().a((AosRequest) ticketsRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) ticketsRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendArrivalStation(ArrivalStationRequest arrivalStationRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            arrivalStationRequest.addHeaders(dkn.d);
            arrivalStationRequest.setTimeout(dkn.b);
            arrivalStationRequest.setRetryTimes(dkn.c);
        }
        arrivalStationRequest.setUrl(ArrivalStationRequest.a);
        arrivalStationRequest.addSignParam("channel");
        arrivalStationRequest.addSignParam(AutoJsonUtils.JSON_ADCODE);
        arrivalStationRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        arrivalStationRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        arrivalStationRequest.addReqParam(AutoJsonUtils.JSON_ADCODE, arrivalStationRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) arrivalStationRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) arrivalStationRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
