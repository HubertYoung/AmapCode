package com.autonavi.minimap.route.bus.realtimebus.data;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.route.bus.realtimebus.data.HeartBeatManager.HearBeatResponse;
import com.autonavi.minimap.route.bus.realtimebus.data.HeartBeatManager.b;

class HeartBeatManager$HeartBeatTask$1 implements AosResponseCallback<HearBeatResponse> {
    final /* synthetic */ b a;

    HeartBeatManager$HeartBeatTask$1(b bVar) {
        this.a = bVar;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        final HearBeatResponse hearBeatResponse = (HearBeatResponse) aosResponse;
        this.a.d = false;
        aho.a(new Runnable() {
            public final void run() {
                if (!(HeartBeatManager$HeartBeatTask$1.this.a.a == null || HeartBeatManager$HeartBeatTask$1.this.a.b == null)) {
                    try {
                        String responseBodyString = hearBeatResponse == null ? null : hearBeatResponse.getResponseBodyString();
                        if (!TextUtils.isEmpty(responseBodyString)) {
                            HeartBeatManager$HeartBeatTask$1.this.a.a.a(JSONObject.parseObject(responseBodyString, HeartBeatManager$HeartBeatTask$1.this.a.b));
                            return;
                        }
                        dyc<T> dyc = HeartBeatManager$HeartBeatTask$1.this.a.a;
                        if (hearBeatResponse != null) {
                            hearBeatResponse.getAosRequest();
                        }
                        new AosResponseException((String) "body is empty");
                        dyc.a();
                    } catch (Exception unused) {
                        if (hearBeatResponse != null) {
                            dyc<T> dyc2 = HeartBeatManager$HeartBeatTask$1.this.a.a;
                            hearBeatResponse.getAosRequest();
                            new AosResponseException((String) "parse data  fail");
                            dyc2.a();
                        }
                    }
                }
            }
        });
    }

    public void onFailure(final AosRequest aosRequest, final AosResponseException aosResponseException) {
        this.a.d = false;
        aho.a(new Runnable() {
            public final void run() {
                if (HeartBeatManager$HeartBeatTask$1.this.a.a != null) {
                    HeartBeatManager$HeartBeatTask$1.this.a.a.a();
                }
            }
        });
    }
}
