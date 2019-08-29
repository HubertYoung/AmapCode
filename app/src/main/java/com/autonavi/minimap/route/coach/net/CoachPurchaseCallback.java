package com.autonavi.minimap.route.coach.net;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.common.Callback;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public final class CoachPurchaseCallback implements AosResponseCallback<AosByteResponse> {
    /* access modifiers changed from: private */
    public dzp a;
    /* access modifiers changed from: private */
    public Callback<dzp> b;

    public final /* synthetic */ void onSuccess(AosResponse aosResponse) {
        try {
            this.a.parser((byte[]) ((AosByteResponse) aosResponse).getResult());
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
        aho.a(new Runnable() {
            public final void run() {
                if (CoachPurchaseCallback.this.b != null) {
                    CoachPurchaseCallback.this.b.callback(CoachPurchaseCallback.this.a);
                }
            }
        });
    }

    public CoachPurchaseCallback(dzp dzp, Callback<dzp> callback) {
        this.a = dzp;
        this.b = callback;
    }

    public final void onFailure(AosRequest aosRequest, final AosResponseException aosResponseException) {
        aho.a(new Runnable() {
            public final void run() {
                if (CoachPurchaseCallback.this.b != null) {
                    CoachPurchaseCallback.this.b.error(aosResponseException, aosResponseException != null && aosResponseException.isCallbackError);
                }
            }
        });
    }
}
