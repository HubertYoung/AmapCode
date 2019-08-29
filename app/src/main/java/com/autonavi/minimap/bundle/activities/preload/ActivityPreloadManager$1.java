package com.autonavi.minimap.bundle.activities.preload;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.bundle.activities.network.ActivityPreloadResponse;

public class ActivityPreloadManager$1 implements AosResponseCallbackOnUi<ActivityPreloadResponse> {
    final /* synthetic */ cua a;

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        ActivityPreloadResponse activityPreloadResponse = (ActivityPreloadResponse) aosResponse;
        if (activityPreloadResponse == null || !activityPreloadResponse.j || activityPreloadResponse.f != 1) {
            cua.a(this.a);
            return;
        }
        cua cua = this.a;
        if (!cua.b) {
            cua.a(activityPreloadResponse.m);
            if (!TextUtils.isEmpty(activityPreloadResponse.l) && cua.a != null) {
                cua.a.setTitleText(activityPreloadResponse.l);
            }
            cua.a(activityPreloadResponse.k);
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        AMapLog.i("ActivityPreload", "fetch data failure!");
        cua.a(this.a);
    }
}
