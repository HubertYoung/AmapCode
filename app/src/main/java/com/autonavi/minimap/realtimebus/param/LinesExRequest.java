package com.autonavi.minimap.realtimebus.param;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class LinesExRequest extends AosGetRequest {
    public static final String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_URL_KEY));
        sb.append("ws/mapapi/realtimebus/lines/ex/");
        a = sb.toString();
    }

    public LinesExRequest() {
        this.e = "";
        this.f = "";
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
    }
}
