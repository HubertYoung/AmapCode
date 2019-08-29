package com.autonavi.minimap.operation;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.operation.param.InfoRequest;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class OperationRequestHolder {
    private static volatile OperationRequestHolder instance;

    private OperationRequestHolder() {
    }

    public static OperationRequestHolder getInstance() {
        if (instance == null) {
            synchronized (OperationRequestHolder.class) {
                if (instance == null) {
                    instance = new OperationRequestHolder();
                }
            }
        }
        return instance;
    }

    public void sendInfo(InfoRequest infoRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendInfo(infoRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendInfo(InfoRequest infoRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            infoRequest.addHeaders(dkn.d);
            infoRequest.setTimeout(dkn.b);
            infoRequest.setRetryTimes(dkn.c);
        }
        infoRequest.setUrl(InfoRequest.a);
        infoRequest.addSignParam("channel");
        infoRequest.addSignParam("tid");
        infoRequest.addSignParam("type");
        infoRequest.addReqParam("tid", infoRequest.b);
        infoRequest.addReqParam(LocationParams.PARA_COMMON_DIV, infoRequest.c);
        infoRequest.addReqParam("type", Integer.toString(infoRequest.d));
        infoRequest.addReqParam(DictionaryKeys.CTRLXY_X, infoRequest.e);
        infoRequest.addReqParam(DictionaryKeys.CTRLXY_Y, infoRequest.f);
        if (dkn != null) {
            in.a().a((AosRequest) infoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) infoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
