package com.autonavi.minimap.route.coach.net;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.common.Callback;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.common.RouteRequestCallBack;

public class RouteCoachRequestCallback extends RouteRequestCallBack<dzo> {
    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        final dzo dzo = new dzo();
        dzo.parser(((AosByteResponse) aosResponse).getResponseBodyData());
        aho.a(new Runnable() {
            public final void run() {
                if (RouteCoachRequestCallback.this.a != null) {
                    RouteCoachRequestCallback.this.a.callback(dzo);
                }
            }
        });
    }

    public RouteCoachRequestCallback(Callback<dzo> callback, POI poi, POI poi2) {
        super(callback, poi, poi2, null, 0);
        this.a = callback;
    }

    public void onFailure(AosRequest aosRequest, final AosResponseException aosResponseException) {
        aho.a(new Runnable() {
            public final void run() {
                if (RouteCoachRequestCallback.this.a != null) {
                    RouteCoachRequestCallback.this.a.error(aosResponseException, aosResponseException != null && aosResponseException.isCallbackError);
                }
            }
        });
    }
}
