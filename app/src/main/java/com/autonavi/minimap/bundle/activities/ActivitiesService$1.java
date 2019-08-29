package com.autonavi.minimap.bundle.activities;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.common.Callback;
import org.json.JSONException;

public class ActivitiesService$1 implements AosResponseCallbackOnUi<AosByteResponse> {
    final /* synthetic */ Callback a;
    final /* synthetic */ String b;
    final /* synthetic */ ctj c;

    public ActivitiesService$1(ctj ctj, Callback callback, String str) {
        this.c = ctj;
        this.a = callback;
        this.b = str;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        try {
            this.a.callback(this.c.a(AbstractAOSParser.aosByteResponseToJSONObject((AosByteResponse) aosResponse), this.b));
        } catch (JSONException unused) {
            ctj.a(this.a, 0);
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        ctj.a(this.a, 0);
    }
}
