package com.amap.bundle.schoolbus.request;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.common.Callback;

public class SchoolbusCheckRoleRequest$1 implements AosResponseCallbackOnUi<AosByteResponse> {
    final /* synthetic */ Callback a;
    final /* synthetic */ adn b;

    public SchoolbusCheckRoleRequest$1(adn adn, Callback callback) {
        this.b = adn;
        this.a = callback;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        adp adp = new adp();
        try {
            adp.parser(aosByteResponse.getResponseBodyData());
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
        if (!this.b.a) {
            adj adj = null;
            if (adp.a != null) {
                adj = adp.a;
                int i = adj.c;
                adl a2 = adl.a();
                a2.b = i;
                a2.a = "initByRole";
            }
            if (adj != null) {
                this.a.callback(adj);
            }
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (!this.b.a) {
        }
    }
}
