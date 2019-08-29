package com.autonavi.minimap.agroup.network;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;

public class GroupNetworkUtil$5 implements AosResponseCallback<AosByteResponse> {
    final /* synthetic */ dku a;
    final /* synthetic */ String b;

    public GroupNetworkUtil$5(dku dku, String str) {
        this.a = dku;
        this.b = str;
    }

    public final /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        String str = null;
        if (aosByteResponse != null) {
            try {
                str = new String(aosByteResponse.getResponseBodyData(), "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cin.a;
        cjo.d();
        ye.b().a("HTTPPolling onSuccess data: ".concat(String.valueOf(str)));
        if (this.a != null) {
            this.a.a(this.b, str);
        }
    }

    public final void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        cin.a;
        cjo.a();
        ye b2 = ye.b();
        StringBuilder sb = new StringBuilder("HTTPPolling onFailure errorCode: ");
        sb.append(aosResponseException.errorCode);
        b2.a(sb.toString());
        if (this.a != null) {
            dku dku = this.a;
            StringBuilder sb2 = new StringBuilder("HTTPPolling");
            sb2.append(aosResponseException.errorCode);
            dku.b(sb2.toString());
        }
    }
}
