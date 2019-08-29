package com.autonavi.minimap.route.bus.busline.newmodel;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusline;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public final class RouteRealTimeRequestModel {
    public final String a;
    public RealTimeBusCallback b = new RealTimeBusCallback();
    public com.autonavi.common.Callback.a c;
    public HashMap<String, RealTimeBusline> d = new HashMap<>();
    public a e;

    public class RealTimeBusCallback implements AosResponseCallback<AosByteResponse> {
        /* access modifiers changed from: private */
        public int b;
        /* access modifiers changed from: private */
        public boolean c;
        /* access modifiers changed from: private */
        public boolean d;

        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            JSONObject jSONObject;
            try {
                jSONObject = new JSONObject(new String((byte[]) ((AosByteResponse) aosResponse).getResult(), "UTF-8").trim());
            } catch (UnsupportedEncodingException | JSONException e) {
                e.printStackTrace();
                jSONObject = null;
            }
            new dyl();
            final dym a2 = dyl.a(jSONObject);
            aho.a(new Runnable() {
                public final void run() {
                    HashMap<String, RealTimeBusline> hashMap = a2.a;
                    if (hashMap != null) {
                        RouteRealTimeRequestModel.this.d.clear();
                        RouteRealTimeRequestModel.this.d.putAll(hashMap);
                        RealTimeBusCallback.this.c = true;
                        RealTimeBusCallback.this.b = 0;
                    } else {
                        RealTimeBusCallback.this.b = RealTimeBusCallback.this.b + 1;
                    }
                    if (RealTimeBusCallback.this.d) {
                        ebj.b(1);
                        RealTimeBusCallback.this.d = false;
                    }
                    if (RouteRealTimeRequestModel.this.e != null) {
                        RouteRealTimeRequestModel.this.e.b();
                    }
                }
            });
        }

        public RealTimeBusCallback() {
        }

        public void onFailure(AosRequest aosRequest, final AosResponseException aosResponseException) {
            aho.a(new Runnable() {
                public final void run() {
                    if (RealTimeBusCallback.this.c) {
                        RealTimeBusCallback.this.b = RealTimeBusCallback.this.b + 1;
                    }
                    if (RealTimeBusCallback.this.d) {
                        if ("请检查网络后重试".equals(aosResponseException.getMessage())) {
                            ebj.b(3);
                        } else {
                            ebj.b(2);
                        }
                        RealTimeBusCallback.this.d = false;
                    }
                    if (RouteRealTimeRequestModel.this.e != null) {
                        RouteRealTimeRequestModel.this.e.b();
                    }
                }
            });
        }
    }

    public interface a {
        void b();
    }

    public RouteRealTimeRequestModel(String str) {
        this.a = str;
    }

    public final RealTimeBusline a(String str) {
        return this.d.get(str);
    }

    public final boolean a() {
        return ebj.a(this.b.b);
    }
}
