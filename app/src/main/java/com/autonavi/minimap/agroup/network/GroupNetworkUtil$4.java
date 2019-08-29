package com.autonavi.minimap.agroup.network;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;

public class GroupNetworkUtil$4 implements AosResponseCallback<AosByteResponse> {
    final /* synthetic */ a a;
    final /* synthetic */ JsFunctionCallback b;

    public GroupNetworkUtil$4(a aVar, JsFunctionCallback jsFunctionCallback) {
        this.a = aVar;
        this.b = jsFunctionCallback;
    }

    public final /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        String str = "";
        if (aosByteResponse != null) {
            try {
                str = new String(aosByteResponse.getResponseBodyData(), "UTF-8");
            } catch (Exception unused) {
            }
        }
        cin.a;
        cjo.a();
        if (this.a != null) {
            this.a.a(str);
        }
        if (this.b != null) {
            this.b.callback(str);
        }
    }

    public final void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        cin.a;
        cjo.a();
        if (this.a != null) {
            this.a.a("");
        }
        if (this.b != null) {
            this.b.callback("");
        }
    }
}
