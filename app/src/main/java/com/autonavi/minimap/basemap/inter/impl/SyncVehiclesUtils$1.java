package com.autonavi.minimap.basemap.inter.impl;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.logs.AMapLog;

public class SyncVehiclesUtils$1 implements AosResponseCallback<AosByteResponse> {
    public final /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        if (aosByteResponse != null && aosByteResponse.getResult() != null) {
            aua aua = new aua();
            aua.parser((byte[]) aosByteResponse.getResult());
            AMapLog.i("zyl", "msg--->".concat(String.valueOf(aua.a)));
        }
    }

    public final void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        StringBuilder sb = new StringBuilder("error--->");
        sb.append(aosResponseException.getMessage());
        AMapLog.i("zyl", sb.toString());
    }
}
