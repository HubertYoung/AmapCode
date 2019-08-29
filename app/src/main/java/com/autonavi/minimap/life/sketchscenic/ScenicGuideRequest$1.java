package com.autonavi.minimap.life.sketchscenic;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;

public class ScenicGuideRequest$1 implements AosResponseCallback {
    final /* synthetic */ a a;
    final /* synthetic */ dqt b;

    ScenicGuideRequest$1(dqt dqt, a aVar) {
        this.b = dqt;
        this.a = aVar;
    }

    public void onSuccess(AosResponse aosResponse) {
        String responseBodyString = aosResponse.getResponseBodyString();
        if (this.a != null) {
            this.a.a(responseBodyString);
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.a != null) {
            this.a.a("requestGuideFailure");
        }
    }
}
