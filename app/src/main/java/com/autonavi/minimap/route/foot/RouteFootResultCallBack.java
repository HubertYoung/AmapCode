package com.autonavi.minimap.route.foot;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.common.Callback;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.common.RouteRequestCallBack;

public class RouteFootResultCallBack extends RouteRequestCallBack {
    public RouteFootResultCallBack(Callback<byte[]> callback, POI poi, POI poi2) {
        super(callback, poi, poi2, null, 0);
    }

    public void onSuccess(final AosResponse aosResponse) {
        aho.a(new Runnable() {
            public final void run() {
                if (RouteFootResultCallBack.this.a != null) {
                    RouteFootResultCallBack.this.a.callback(aosResponse.getResponseBodyData());
                }
            }
        });
    }

    public void onFailure(AosRequest aosRequest, final AosResponseException aosResponseException) {
        aho.a(new Runnable() {
            public final void run() {
                if (RouteFootResultCallBack.this.a != null) {
                    RouteFootResultCallBack.this.a.error(aosResponseException, aosResponseException != null && aosResponseException.isCallbackError);
                }
            }
        });
    }
}
