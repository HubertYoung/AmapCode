package com.autonavi.minimap.address;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.address.param.UploadRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.sdk.location.LocationInstrument;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class AddressRequestHolder {
    private static volatile AddressRequestHolder instance;

    private AddressRequestHolder() {
    }

    public static AddressRequestHolder getInstance() {
        if (instance == null) {
            synchronized (AddressRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new AddressRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendUpload(UploadRequest uploadRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendUpload(uploadRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendUpload(UploadRequest uploadRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            uploadRequest.addHeaders(dkn.d);
            uploadRequest.setTimeout(dkn.b);
            uploadRequest.setRetryTimes(dkn.c);
        }
        uploadRequest.setUrl(UploadRequest.f);
        uploadRequest.addSignParam("channel");
        uploadRequest.addSignParam(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
        uploadRequest.addReqParam(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, uploadRequest.g);
        uploadRequest.addReqParam("dataname", uploadRequest.h);
        uploadRequest.addReqParam(LocationParams.PARA_COMMON_DIV, uploadRequest.i);
        uploadRequest.addReqParam(DictionaryKeys.CTRLXY_X, uploadRequest.j);
        uploadRequest.addReqParam(DictionaryKeys.CTRLXY_Y, uploadRequest.k);
        uploadRequest.addReqParam("phototime", uploadRequest.l);
        uploadRequest.addReqParam("gpstime", uploadRequest.m);
        uploadRequest.addReqParam("accury", uploadRequest.n);
        uploadRequest.a((String) "image", uploadRequest.o);
        if (dkn != null) {
            in.a().a((AosRequest) uploadRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) uploadRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
