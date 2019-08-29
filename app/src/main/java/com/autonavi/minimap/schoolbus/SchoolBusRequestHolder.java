package com.autonavi.minimap.schoolbus;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.schoolbus.param.CheckRoleRequest;
import com.autonavi.minimap.schoolbus.param.RouteStatusRequest;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class SchoolBusRequestHolder {
    private static volatile SchoolBusRequestHolder instance;

    private SchoolBusRequestHolder() {
    }

    public static SchoolBusRequestHolder getInstance() {
        if (instance == null) {
            synchronized (SchoolBusRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new SchoolBusRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendCheckRole(CheckRoleRequest checkRoleRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendCheckRole(checkRoleRequest, new dkn(), aosResponseCallback);
    }

    public void sendRouteStatus(RouteStatusRequest routeStatusRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendRouteStatus(routeStatusRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendCheckRole(CheckRoleRequest checkRoleRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            checkRoleRequest.addHeaders(dkn.d);
            checkRoleRequest.setTimeout(dkn.b);
            checkRoleRequest.setRetryTimes(dkn.c);
        }
        checkRoleRequest.setUrl(CheckRoleRequest.a);
        checkRoleRequest.addSignParam("channel");
        checkRoleRequest.addSignParam(LocationParams.PARA_COMMON_ADIU);
        checkRoleRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        checkRoleRequest.addReqParam(LocationParams.PARA_COMMON_ADIU, checkRoleRequest.b);
        checkRoleRequest.addReqParam(LocationParams.PARA_COMMON_DIU, checkRoleRequest.c);
        checkRoleRequest.addReqParam("tid", checkRoleRequest.d);
        checkRoleRequest.addReqParam("push_token", checkRoleRequest.e);
        if (dkn != null) {
            in.a().a((AosRequest) checkRoleRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) checkRoleRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendRouteStatus(RouteStatusRequest routeStatusRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            routeStatusRequest.addHeaders(dkn.d);
            routeStatusRequest.setTimeout(dkn.b);
            routeStatusRequest.setRetryTimes(dkn.c);
        }
        routeStatusRequest.setUrl(RouteStatusRequest.a);
        routeStatusRequest.addSignParam("channel");
        routeStatusRequest.addSignParam(LocationParams.PARA_COMMON_ADIU);
        routeStatusRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        routeStatusRequest.addReqParam(LocationParams.PARA_COMMON_ADIU, routeStatusRequest.b);
        routeStatusRequest.addReqParam(LocationParams.PARA_COMMON_DIU, routeStatusRequest.c);
        routeStatusRequest.addReqParam("role", routeStatusRequest.d);
        routeStatusRequest.addReqParam("routeId", routeStatusRequest.e);
        routeStatusRequest.addReqParam("latitude", routeStatusRequest.f);
        routeStatusRequest.addReqParam("longitude", routeStatusRequest.g);
        if (dkn != null) {
            in.a().a((AosRequest) routeStatusRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) routeStatusRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
