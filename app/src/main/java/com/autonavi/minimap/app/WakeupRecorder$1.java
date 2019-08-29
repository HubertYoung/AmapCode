package com.autonavi.minimap.app;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.logs.AMapLog;

public class WakeupRecorder$1 implements AosResponseCallback<AosByteResponse> {
    final /* synthetic */ ckd a;

    public WakeupRecorder$1(ckd ckd) {
        this.a = ckd;
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        AMapLog.i("Wakeup", "wakeup failure!");
        this.a.a = null;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AMapLog.i("Wakeup", "wakeup success!");
        this.a.a = null;
    }
}
