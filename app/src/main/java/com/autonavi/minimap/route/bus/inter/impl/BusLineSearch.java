package com.autonavi.minimap.route.bus.inter.impl;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.common.SuperId;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.poi.PoiRequestHolder;
import com.autonavi.minimap.poi.param.BusBaseRequest;
import com.autonavi.minimap.poi.param.BusLiteRequest;
import com.autonavi.minimap.poi.param.NewBusRequest;
import com.autonavi.minimap.route.bus.busline.model.BusLineSearchException;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public final class BusLineSearch {

    static class BusLineSearchCallback implements AosResponseCallback<AosByteResponse> {
        /* access modifiers changed from: private */
        public BusLineSearchResultCallback a;
        /* access modifiers changed from: private */
        public BusBaseRequest b;

        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            JSONObject jSONObject;
            AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
            try {
                jSONObject = new JSONObject(aosByteResponse.getResponseBodyString());
            } catch (JSONException e) {
                e.printStackTrace();
                jSONObject = null;
            }
            if (jSONObject != null) {
                final duk duk = new duk();
                try {
                    duk.parser((byte[]) aosByteResponse.getResult());
                } catch (UnsupportedEncodingException | JSONException e2) {
                    kf.a(e2);
                } catch (BusLineSearchException e3) {
                    aho.a(new Runnable() {
                        public final void run() {
                            if (BusLineSearchCallback.this.a != null) {
                                BusLineSearchCallback.this.a.error(e3, false);
                            }
                        }
                    });
                    return;
                }
                aho.a(new Runnable() {
                    public final void run() {
                        if (duk.a != null) {
                            duk.a.setBusRequest(BusLineSearchCallback.this.b);
                        }
                        BusLineSearchCallback.this.a.callback(duk.a);
                    }
                });
            }
        }

        public BusLineSearchCallback(BusLineSearchResultCallback busLineSearchResultCallback, BusBaseRequest busBaseRequest) {
            this.a = busLineSearchResultCallback;
            this.b = busBaseRequest;
        }

        public void onFailure(AosRequest aosRequest, final AosResponseException aosResponseException) {
            aho.a(new Runnable() {
                public final void run() {
                    if (BusLineSearchCallback.this.a != null) {
                        BusLineSearchCallback.this.a.error(aosResponseException, aosResponseException != null && aosResponseException.isCallbackError);
                    }
                }
            });
        }
    }

    static class BusLineSearchResultCallback implements Callback<IBusLineSearchResult> {
        private Callback<IBusLineSearchResult> mCallbackLinstener = null;

        public BusLineSearchResultCallback(Callback<IBusLineSearchResult> callback) {
            this.mCallbackLinstener = callback;
        }

        public void callback(IBusLineSearchResult iBusLineSearchResult) {
            if (this.mCallbackLinstener != null) {
                this.mCallbackLinstener.callback(iBusLineSearchResult);
            }
        }

        public void error(Throwable th, boolean z) {
            if (this.mCallbackLinstener != null) {
                this.mCallbackLinstener.error(th, z);
            }
        }
    }

    public static AosRequest a(String str, int i, String str2, Callback<IBusLineSearchResult> callback) {
        String scenceId = SuperId.getInstance().getScenceId();
        BusLiteRequest busLiteRequest = new BusLiteRequest();
        busLiteRequest.setPriority(400);
        busLiteRequest.c = str;
        busLiteRequest.e = str2;
        busLiteRequest.d = "";
        busLiteRequest.f = scenceId;
        busLiteRequest.a = i;
        busLiteRequest.l = 10;
        PoiRequestHolder.getInstance().sendBusLite(busLiteRequest, new BusLineSearchCallback(new BusLineSearchResultCallback(callback), busLiteRequest));
        return busLiteRequest;
    }

    public static AosRequest a(String str, String str2, Callback<IBusLineSearchResult> callback, boolean z) {
        if (!z || NetworkReachability.b()) {
            String scenceId = SuperId.getInstance().getScenceId();
            NewBusRequest newBusRequest = new NewBusRequest();
            newBusRequest.setPriority(400);
            if (str2 == null) {
                newBusRequest.g = "";
            } else {
                newBusRequest.g = str2;
            }
            newBusRequest.d = str;
            newBusRequest.h = scenceId;
            newBusRequest.f = 10;
            PoiRequestHolder.getInstance().sendNewBus(newBusRequest, new BusLineSearchCallback(new BusLineSearchResultCallback(callback), newBusRequest));
            return newBusRequest;
        }
        ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.ic_net_error_tipinfo));
        return null;
    }
}
