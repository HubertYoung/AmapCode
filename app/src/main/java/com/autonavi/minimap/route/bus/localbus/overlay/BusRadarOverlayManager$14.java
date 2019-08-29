package com.autonavi.minimap.route.bus.localbus.overlay;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.route.bus.realtimebus.RecommendResponse;
import com.autonavi.minimap.route.bus.realtimebus.model.RecommendStation;

public class BusRadarOverlayManager$14 implements AosResponseCallback<RecommendResponse> {
    final /* synthetic */ boolean a;
    final /* synthetic */ dwa b;

    public BusRadarOverlayManager$14(dwa dwa, boolean z) {
        this.b = dwa;
        this.a = z;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        this.b.a((RecommendStation) ((RecommendResponse) aosResponse).getResult(), this.a);
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        this.b.a((RecommendStation) null, false);
    }
}
