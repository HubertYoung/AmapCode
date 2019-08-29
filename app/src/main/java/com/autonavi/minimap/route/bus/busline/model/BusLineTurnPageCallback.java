package com.autonavi.minimap.route.bus.busline.model;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.common.Callback;
import com.autonavi.minimap.poi.param.BusBaseRequest;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public class BusLineTurnPageCallback implements AosResponseCallback<AosByteResponse> {
    private BusBaseRequest a;
    /* access modifiers changed from: private */
    public Callback<IBusLineSearchResult> b;
    /* access modifiers changed from: private */
    public boolean c;

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        final duk duk = new duk();
        try {
            duk.parser((byte[]) aosByteResponse.getResult());
        } catch (UnsupportedEncodingException | JSONException e) {
            kf.a(e);
        }
        if (duk.a != null) {
            duk.a.setBusRequest(this.a);
            duk.a.setSearchKeyword(this.a.c);
        }
        aho.a(new Runnable() {
            public final void run() {
                if (!BusLineTurnPageCallback.this.c && BusLineTurnPageCallback.this.b != null) {
                    BusLineTurnPageCallback.this.b.callback(duk.a);
                }
            }
        });
    }

    public void onFailure(AosRequest aosRequest, final AosResponseException aosResponseException) {
        aho.a(new Runnable() {
            public final void run() {
                if (BusLineTurnPageCallback.this.b != null) {
                    BusLineTurnPageCallback.this.b.error(aosResponseException, false);
                }
            }
        });
    }
}
