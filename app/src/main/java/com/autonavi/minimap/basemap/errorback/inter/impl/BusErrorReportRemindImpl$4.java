package com.autonavi.minimap.basemap.errorback.inter.impl;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;

public class BusErrorReportRemindImpl$4 implements AosResponseCallback<AosByteResponse> {
    final /* synthetic */ coj a;

    public BusErrorReportRemindImpl$4(coj coj) {
        this.a = coj;
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        this.a.f = null;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        this.a.f = null;
    }
}
