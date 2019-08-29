package com.autonavi.minimap.basemap.intent.inner;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;

public class OperationIntentDispatcherImpl$6$1 implements AosResponseCallbackOnUi<AosByteResponse> {
    final /* synthetic */ AnonymousClass6 a;

    public OperationIntentDispatcherImpl$6$1(AnonymousClass6 r1) {
        this.a = r1;
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        cpo.d(r11);
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        cpo.d(r11);
    }
}
