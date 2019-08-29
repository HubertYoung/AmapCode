package com.autonavi.minimap.realtimebus.param;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class LineStationRequest extends AosGetRequest {
    public static final String a;
    public String b = null;
    public String c = null;
    public String d = null;
    public String e = null;
    public String f = null;
    public String g = null;
    public String h = null;
    public boolean i = false;
    public String j = null;
    public String k = null;
    public String l = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_URL_KEY));
        sb.append("ws/mapapi/realtimebus/linestation/");
        a = sb.toString();
    }
}
