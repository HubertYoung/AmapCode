package com.autonavi.minimap.route.bus.realtimebus.data;

import com.amap.bundle.aosservice.request.AosPostRequest;

public class HeartBeatRequest extends AosPostRequest {
    int a;
    public boolean b;

    public HeartBeatRequest() {
        this(0);
    }

    private HeartBeatRequest(byte b2) {
        this.a = 30;
    }
}
