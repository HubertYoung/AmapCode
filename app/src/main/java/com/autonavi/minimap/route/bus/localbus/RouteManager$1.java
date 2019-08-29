package com.autonavi.minimap.route.bus.localbus;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.common.Callback;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public class RouteManager$1 implements AosResponseCallback<AosByteResponse> {
    final /* synthetic */ Callback a;

    public RouteManager$1(Callback callback) {
        this.a = callback;
    }

    public final /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        final dvy dvy = new dvy();
        try {
            dvy.parser(aosByteResponse.getResponseBodyData());
        } catch (UnsupportedEncodingException | JSONException e) {
            kf.a(e);
        }
        aho.a(new Runnable() {
            public final void run() {
                if (RouteManager$1.this.a != null) {
                    RouteManager$1.this.a.callback(dvy);
                }
            }
        });
    }

    public final void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.a != null) {
            this.a.error(aosResponseException, aosResponseException != null && aosResponseException.isCallbackError);
        }
    }
}
