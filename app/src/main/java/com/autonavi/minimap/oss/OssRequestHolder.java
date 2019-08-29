package com.autonavi.minimap.oss;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.oss.param.MonopolyRescuePlayRequest;
import com.autonavi.minimap.oss.param.OperationInfoRequest;
import com.autonavi.minimap.oss.param.TripConfigListRequest;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class OssRequestHolder {
    private static volatile OssRequestHolder instance;

    private OssRequestHolder() {
    }

    public static OssRequestHolder getInstance() {
        if (instance == null) {
            synchronized (OssRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new OssRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendMonopolyRescuePlay(MonopolyRescuePlayRequest monopolyRescuePlayRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendMonopolyRescuePlay(monopolyRescuePlayRequest, new dkn(), aosResponseCallback);
    }

    public void sendOperationInfo(OperationInfoRequest operationInfoRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendOperationInfo(operationInfoRequest, new dkn(), aosResponseCallback);
    }

    public void sendTripConfigList(TripConfigListRequest tripConfigListRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendTripConfigList(tripConfigListRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendMonopolyRescuePlay(MonopolyRescuePlayRequest monopolyRescuePlayRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            monopolyRescuePlayRequest.addHeaders(dkn.d);
            monopolyRescuePlayRequest.setTimeout(dkn.b);
            monopolyRescuePlayRequest.setRetryTimes(dkn.c);
        }
        monopolyRescuePlayRequest.setUrl(MonopolyRescuePlayRequest.a);
        monopolyRescuePlayRequest.addSignParam("channel");
        monopolyRescuePlayRequest.addSignParam("tid");
        monopolyRescuePlayRequest.addReqParam("tid", monopolyRescuePlayRequest.b);
        monopolyRescuePlayRequest.addReqParam("token", monopolyRescuePlayRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) monopolyRescuePlayRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) monopolyRescuePlayRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendOperationInfo(OperationInfoRequest operationInfoRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            operationInfoRequest.addHeaders(dkn.d);
            operationInfoRequest.setTimeout(dkn.b);
            operationInfoRequest.setRetryTimes(dkn.c);
        }
        operationInfoRequest.setUrl(OperationInfoRequest.a);
        operationInfoRequest.addSignParam("channel");
        operationInfoRequest.addSignParam("tid");
        operationInfoRequest.addSignParam("type");
        operationInfoRequest.addReqParam("tid", operationInfoRequest.b);
        operationInfoRequest.addReqParam(LocationParams.PARA_COMMON_DIV, operationInfoRequest.c);
        operationInfoRequest.addReqParam("type", operationInfoRequest.d);
        operationInfoRequest.addReqParam(DictionaryKeys.CTRLXY_X, operationInfoRequest.e);
        operationInfoRequest.addReqParam(DictionaryKeys.CTRLXY_Y, operationInfoRequest.f);
        if (dkn != null) {
            in.a().a((AosRequest) operationInfoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) operationInfoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendTripConfigList(TripConfigListRequest tripConfigListRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            tripConfigListRequest.addHeaders(dkn.d);
            tripConfigListRequest.setTimeout(dkn.b);
            tripConfigListRequest.setRetryTimes(dkn.c);
        }
        tripConfigListRequest.setUrl(TripConfigListRequest.a);
        tripConfigListRequest.addSignParam("channel");
        tripConfigListRequest.addSignParam(DictionaryKeys.CTRLXY_X);
        tripConfigListRequest.addSignParam(DictionaryKeys.CTRLXY_Y);
        tripConfigListRequest.addReqParam(DictionaryKeys.CTRLXY_X, tripConfigListRequest.b);
        tripConfigListRequest.addReqParam(DictionaryKeys.CTRLXY_Y, tripConfigListRequest.c);
        tripConfigListRequest.addReqParam("md5", tripConfigListRequest.d);
        tripConfigListRequest.addReqParam("trip_version", tripConfigListRequest.e);
        tripConfigListRequest.addReqParam("traffic_restrict", tripConfigListRequest.f);
        tripConfigListRequest.addReqParam("date", tripConfigListRequest.g);
        if (dkn != null) {
            in.a().a((AosRequest) tripConfigListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) tripConfigListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
