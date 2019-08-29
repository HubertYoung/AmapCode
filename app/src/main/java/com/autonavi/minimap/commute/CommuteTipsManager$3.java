package com.autonavi.minimap.commute;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;

public class CommuteTipsManager$3 implements AosResponseCallback<AosByteResponse> {
    final /* synthetic */ ddu a;

    public CommuteTipsManager$3(ddu ddu) {
        this.a = ddu;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        if (aosByteResponse != null && aosByteResponse.getResult() != null) {
            ddv ddv = new ddv();
            try {
                ddv.parser((byte[]) aosByteResponse.getResult());
                ddx ddx = ddv.a;
                ddu.b;
                new StringBuilder("callback: ").append(ddx.toString());
                ddu.b(this.a, ddx);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        ddu.b;
    }
}
