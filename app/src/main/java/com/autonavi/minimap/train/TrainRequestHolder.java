package com.autonavi.minimap.train;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.train.param.IndexRequest;
import com.autonavi.minimap.train.param.StaticStationsRequest;
import com.autonavi.minimap.train.param.StationRequest;
import com.autonavi.minimap.train.param.SubmitOrderRequest;
import com.autonavi.minimap.train.param.TicketsRequest;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class TrainRequestHolder {
    private static volatile TrainRequestHolder instance;

    private TrainRequestHolder() {
    }

    public static TrainRequestHolder getInstance() {
        if (instance == null) {
            synchronized (TrainRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new TrainRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendIndex(IndexRequest indexRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendIndex(indexRequest, new dkn(), aosResponseCallback);
    }

    public void sendStation(StationRequest stationRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendStation(stationRequest, new dkn(), aosResponseCallback);
    }

    public void sendSubmitOrder(SubmitOrderRequest submitOrderRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendSubmitOrder(submitOrderRequest, new dkn(), aosResponseCallback);
    }

    public void sendTickets(TicketsRequest ticketsRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendTickets(ticketsRequest, new dkn(), aosResponseCallback);
    }

    public void sendStaticStations(StaticStationsRequest staticStationsRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendStaticStations(staticStationsRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendIndex(IndexRequest indexRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            indexRequest.addHeaders(dkn.d);
            indexRequest.setTimeout(dkn.b);
            indexRequest.setRetryTimes(dkn.c);
        }
        indexRequest.setUrl(IndexRequest.a);
        indexRequest.addSignParam("channel");
        indexRequest.addSignParam("train");
        indexRequest.addReqParam("train", indexRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) indexRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) indexRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendStation(StationRequest stationRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            stationRequest.addHeaders(dkn.d);
            stationRequest.setTimeout(dkn.b);
            stationRequest.setRetryTimes(dkn.c);
        }
        stationRequest.setUrl(StationRequest.a);
        stationRequest.addSignParam("channel");
        stationRequest.addSignParam("from_station");
        stationRequest.addSignParam("to_station");
        stationRequest.addReqParam("from_station", stationRequest.b);
        stationRequest.addReqParam("to_station", stationRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) stationRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) stationRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendSubmitOrder(SubmitOrderRequest submitOrderRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            submitOrderRequest.addHeaders(dkn.d);
            submitOrderRequest.setTimeout(dkn.b);
            submitOrderRequest.setRetryTimes(dkn.c);
        }
        submitOrderRequest.setUrl(SubmitOrderRequest.a);
        submitOrderRequest.addSignParam("channel");
        submitOrderRequest.addSignParam("train_num");
        submitOrderRequest.addReqParam("start_station", submitOrderRequest.b);
        submitOrderRequest.addReqParam("end_station", submitOrderRequest.c);
        submitOrderRequest.addReqParam(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME, submitOrderRequest.d);
        submitOrderRequest.addReqParam("train_num", submitOrderRequest.e);
        if (dkn != null) {
            in.a().a((AosRequest) submitOrderRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) submitOrderRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
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
        ticketsRequest.addReqParam("traintype", ticketsRequest.m);
        ticketsRequest.addReqParam("tickettype", ticketsRequest.n);
        ticketsRequest.addReqParam("ver", ticketsRequest.o);
        ticketsRequest.addReqParam("client_src", ticketsRequest.p);
        if (dkn != null) {
            in.a().a((AosRequest) ticketsRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) ticketsRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendStaticStations(StaticStationsRequest staticStationsRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            staticStationsRequest.addHeaders(dkn.d);
            staticStationsRequest.setTimeout(dkn.b);
            staticStationsRequest.setRetryTimes(dkn.c);
        }
        staticStationsRequest.setUrl(StaticStationsRequest.a);
        staticStationsRequest.addSignParam("channel");
        staticStationsRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        staticStationsRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        staticStationsRequest.addReqParam("station_type", staticStationsRequest.b);
        staticStationsRequest.addReqParam("md5", staticStationsRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) staticStationsRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) staticStationsRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
