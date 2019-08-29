package com.autonavi.minimap.route.train.net;

import android.content.Context;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.common.Callback;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.common.RouteRequestCallBack;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public class RouteTrainResultCallback extends RouteRequestCallBack<ejh> {
    private Context g;

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        final ejh ejh = new ejh();
        try {
            ejh.parser((byte[]) aosByteResponse.getResult());
        } catch (UnsupportedEncodingException | JSONException e) {
            kf.a(e);
        }
        aho.a(new Runnable() {
            public final void run() {
                if (RouteTrainResultCallback.this.a != null) {
                    RouteTrainResultCallback.this.a.callback(ejh);
                }
            }
        });
    }

    public RouteTrainResultCallback(Context context, Callback<ejh> callback, POI poi, POI poi2) {
        super(callback, poi, poi2, null, 0);
        this.a = callback;
        this.g = context;
    }

    public void onFailure(AosRequest aosRequest, final AosResponseException aosResponseException) {
        aho.a(new Runnable() {
            public final void run() {
                if (RouteTrainResultCallback.this.a != null) {
                    RouteTrainResultCallback.this.a.error(aosResponseException, aosResponseException != null && aosResponseException.isCallbackError);
                }
            }
        });
    }
}
