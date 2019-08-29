package com.autonavi.minimap.route.train.net;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.common.Callback;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public final class TrainTicketPurchaseCallback implements AosResponseCallback<AosByteResponse> {
    /* access modifiers changed from: private */
    public eji a;
    /* access modifiers changed from: private */
    public Callback<eji> b;

    public final /* synthetic */ void onSuccess(AosResponse aosResponse) {
        try {
            this.a.parser((byte[]) ((AosByteResponse) aosResponse).getResult());
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
        aho.a(new Runnable() {
            public final void run() {
                if (TrainTicketPurchaseCallback.this.b != null) {
                    TrainTicketPurchaseCallback.this.b.callback(TrainTicketPurchaseCallback.this.a);
                }
            }
        });
    }

    public TrainTicketPurchaseCallback(eji eji, Callback<eji> callback) {
        this.a = eji;
        this.b = callback;
    }

    public final void onFailure(AosRequest aosRequest, final AosResponseException aosResponseException) {
        aho.a(new Runnable() {
            public final void run() {
                if (TrainTicketPurchaseCallback.this.b != null) {
                    TrainTicketPurchaseCallback.this.b.error(aosResponseException, aosResponseException != null && aosResponseException.isCallbackError);
                }
            }
        });
    }
}
