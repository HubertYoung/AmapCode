package com.autonavi.minimap.transfer;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.transfer.param.AosStateRequest;
import com.autonavi.minimap.transfer.param.AuthTrafficCongestionRequest;
import com.autonavi.minimap.transfer.param.NavigationBusExtRequest;
import com.autonavi.minimap.transfer.param.SearchWordinBoxRequest;
import com.autonavi.minimap.transfer.param.ViolationConditionsRequest;
import com.autonavi.minimap.transfer.param.ViolationSupportcityRequest;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class TransferRequestHolder {
    private static volatile TransferRequestHolder instance;

    private TransferRequestHolder() {
    }

    public static TransferRequestHolder getInstance() {
        if (instance == null) {
            synchronized (TransferRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new TransferRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendAosState(AosStateRequest aosStateRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendAosState(aosStateRequest, new dkn(), aosResponseCallback);
    }

    public void sendNavigationBusExt(NavigationBusExtRequest navigationBusExtRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendNavigationBusExt(navigationBusExtRequest, new dkn(), aosResponseCallback);
    }

    public void sendSearchWordinBox(SearchWordinBoxRequest searchWordinBoxRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendSearchWordinBox(searchWordinBoxRequest, new dkn(), aosResponseCallback);
    }

    public void sendAuthTrafficCongestion(AuthTrafficCongestionRequest authTrafficCongestionRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendAuthTrafficCongestion(authTrafficCongestionRequest, new dkn(), aosResponseCallback);
    }

    public void sendViolationConditions(ViolationConditionsRequest violationConditionsRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendViolationConditions(violationConditionsRequest, new dkn(), aosResponseCallback);
    }

    public void sendViolationSupportcity(ViolationSupportcityRequest violationSupportcityRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendViolationSupportcity(violationSupportcityRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendAosState(AosStateRequest aosStateRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            aosStateRequest.addHeaders(dkn.d);
            aosStateRequest.setTimeout(dkn.b);
            aosStateRequest.setRetryTimes(dkn.c);
        }
        aosStateRequest.setUrl(AosStateRequest.a);
        aosStateRequest.addSignParam("channel");
        aosStateRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        aosStateRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        aosStateRequest.addReqParam(LocationParams.PARA_COMMON_DIU, aosStateRequest.b);
        aosStateRequest.addReqParam(LocationParams.PARA_COMMON_DIV, aosStateRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) aosStateRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) aosStateRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendNavigationBusExt(NavigationBusExtRequest navigationBusExtRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            navigationBusExtRequest.addHeaders(dkn.d);
            navigationBusExtRequest.setTimeout(dkn.b);
            navigationBusExtRequest.setRetryTimes(dkn.c);
        }
        navigationBusExtRequest.setUrl(NavigationBusExtRequest.a);
        navigationBusExtRequest.addSignParam("x1");
        navigationBusExtRequest.addSignParam("y1");
        navigationBusExtRequest.addSignParam("x2");
        navigationBusExtRequest.addSignParam("y2");
        navigationBusExtRequest.addSignParam("poiid1");
        navigationBusExtRequest.addSignParam("poiid2");
        navigationBusExtRequest.addReqParam("type", navigationBusExtRequest.b);
        navigationBusExtRequest.addReqParam("x1", Double.toString(navigationBusExtRequest.c));
        navigationBusExtRequest.addReqParam("y1", Double.toString(navigationBusExtRequest.d));
        navigationBusExtRequest.addReqParam("x2", Double.toString(navigationBusExtRequest.e));
        navigationBusExtRequest.addReqParam("y2", Double.toString(navigationBusExtRequest.f));
        navigationBusExtRequest.addReqParam("areacode", navigationBusExtRequest.g);
        navigationBusExtRequest.addReqParam("group", Integer.toString(navigationBusExtRequest.h));
        navigationBusExtRequest.addReqParam("night", Integer.toString(navigationBusExtRequest.i));
        navigationBusExtRequest.addReqParam("poiid1", navigationBusExtRequest.j);
        navigationBusExtRequest.addReqParam("poiid2", navigationBusExtRequest.k);
        navigationBusExtRequest.addReqParam("precision1", Integer.toString(navigationBusExtRequest.l));
        navigationBusExtRequest.addReqParam("precision2", Integer.toString(navigationBusExtRequest.m));
        navigationBusExtRequest.addReqParam("date", navigationBusExtRequest.n);
        navigationBusExtRequest.addReqParam("time", navigationBusExtRequest.o);
        navigationBusExtRequest.addReqParam("arrive", Integer.toString(navigationBusExtRequest.p));
        navigationBusExtRequest.addReqParam("altercoord", Integer.toString(navigationBusExtRequest.q));
        navigationBusExtRequest.addReqParam(FunctionSupportConfiger.TAXI_TAG, Integer.toString(navigationBusExtRequest.r));
        navigationBusExtRequest.addReqParam("isindoor", Integer.toString(navigationBusExtRequest.s));
        navigationBusExtRequest.addReqParam("pure_walk", Integer.toString(navigationBusExtRequest.t));
        navigationBusExtRequest.addReqParam("max_trans", Integer.toString(navigationBusExtRequest.u));
        navigationBusExtRequest.addReqParam("req_num", Integer.toString(navigationBusExtRequest.v));
        navigationBusExtRequest.addReqParam("humanize", Integer.toString(navigationBusExtRequest.w));
        navigationBusExtRequest.addReqParam("eta", Integer.toString(navigationBusExtRequest.x));
        navigationBusExtRequest.addReqParam("ad1", navigationBusExtRequest.y);
        navigationBusExtRequest.addReqParam("ad2", navigationBusExtRequest.z);
        navigationBusExtRequest.addReqParam("ver", navigationBusExtRequest.A);
        if (dkn != null) {
            in.a().a((AosRequest) navigationBusExtRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) navigationBusExtRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendSearchWordinBox(SearchWordinBoxRequest searchWordinBoxRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            searchWordinBoxRequest.addHeaders(dkn.d);
            searchWordinBoxRequest.setTimeout(dkn.b);
            searchWordinBoxRequest.setRetryTimes(dkn.c);
        }
        searchWordinBoxRequest.setUrl(SearchWordinBoxRequest.a);
        searchWordinBoxRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        searchWordinBoxRequest.addReqParam("user_loc", searchWordinBoxRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) searchWordinBoxRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) searchWordinBoxRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendAuthTrafficCongestion(AuthTrafficCongestionRequest authTrafficCongestionRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            authTrafficCongestionRequest.addHeaders(dkn.d);
            authTrafficCongestionRequest.setTimeout(dkn.b);
            authTrafficCongestionRequest.setRetryTimes(dkn.c);
        }
        authTrafficCongestionRequest.setUrl(AuthTrafficCongestionRequest.a);
        authTrafficCongestionRequest.addSignParam("");
        authTrafficCongestionRequest.addReqParam("md5", authTrafficCongestionRequest.b);
        authTrafficCongestionRequest.addReqParam(DictionaryKeys.CTRLXY_X, authTrafficCongestionRequest.c);
        authTrafficCongestionRequest.addReqParam(DictionaryKeys.CTRLXY_Y, authTrafficCongestionRequest.d);
        authTrafficCongestionRequest.addReqParam("citycode", authTrafficCongestionRequest.e);
        authTrafficCongestionRequest.addReqParam("listindex", authTrafficCongestionRequest.f);
        if (dkn != null) {
            in.a().a((AosRequest) authTrafficCongestionRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) authTrafficCongestionRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendViolationConditions(ViolationConditionsRequest violationConditionsRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            violationConditionsRequest.addHeaders(dkn.d);
            violationConditionsRequest.setTimeout(dkn.b);
            violationConditionsRequest.setRetryTimes(dkn.c);
        }
        violationConditionsRequest.setUrl(ViolationConditionsRequest.a);
        violationConditionsRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        violationConditionsRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        violationConditionsRequest.addReqParam("tvmd5", violationConditionsRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) violationConditionsRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) violationConditionsRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendViolationSupportcity(ViolationSupportcityRequest violationSupportcityRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            violationSupportcityRequest.addHeaders(dkn.d);
            violationSupportcityRequest.setTimeout(dkn.b);
            violationSupportcityRequest.setRetryTimes(dkn.c);
        }
        violationSupportcityRequest.setUrl(ViolationSupportcityRequest.a);
        violationSupportcityRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        violationSupportcityRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        violationSupportcityRequest.addSignParam("tid");
        violationSupportcityRequest.addSignParam("tvuid");
        violationSupportcityRequest.addReqParam("tvuid", violationSupportcityRequest.b);
        violationSupportcityRequest.addReqParam("carNumber", violationSupportcityRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) violationSupportcityRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) violationSupportcityRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
