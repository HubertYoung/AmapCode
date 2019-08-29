package com.autonavi.minimap.aliyun;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.aliyun.param.OcrVehicleRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class AliyunRequestHolder {
    private static volatile AliyunRequestHolder instance;

    private AliyunRequestHolder() {
    }

    public static AliyunRequestHolder getInstance() {
        if (instance == null) {
            synchronized (AliyunRequestHolder.class) {
                if (instance == null) {
                    instance = new AliyunRequestHolder();
                }
            }
        }
        return instance;
    }

    public void sendOcrVehicle(OcrVehicleRequest ocrVehicleRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendOcrVehicle(ocrVehicleRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendOcrVehicle(OcrVehicleRequest ocrVehicleRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            ocrVehicleRequest.addHeaders(dkn.d);
            ocrVehicleRequest.setTimeout(dkn.b);
            ocrVehicleRequest.setRetryTimes(dkn.c);
        }
        ocrVehicleRequest.setUrl(OcrVehicleRequest.a);
        ocrVehicleRequest.addSignParam("channel");
        ocrVehicleRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        ocrVehicleRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        ocrVehicleRequest.addReqParam("data_type", ocrVehicleRequest.b);
        ocrVehicleRequest.addReqParam("data_value", ocrVehicleRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) ocrVehicleRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) ocrVehicleRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
