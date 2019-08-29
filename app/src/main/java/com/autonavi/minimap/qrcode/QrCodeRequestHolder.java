package com.autonavi.minimap.qrcode;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.qrcode.param.ConfirmRequest;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class QrCodeRequestHolder {
    private static volatile QrCodeRequestHolder instance;

    private QrCodeRequestHolder() {
    }

    public static QrCodeRequestHolder getInstance() {
        if (instance == null) {
            synchronized (QrCodeRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new QrCodeRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendConfirm(ConfirmRequest confirmRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendConfirm(confirmRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendConfirm(ConfirmRequest confirmRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            confirmRequest.addHeaders(dkn.d);
            confirmRequest.setTimeout(dkn.b);
            confirmRequest.setRetryTimes(dkn.c);
        }
        confirmRequest.setUrl(ConfirmRequest.a);
        confirmRequest.addSignParam("channel");
        confirmRequest.addSignParam("qrcode_id");
        confirmRequest.addReqParam("qrcode_id", confirmRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) confirmRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) confirmRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
