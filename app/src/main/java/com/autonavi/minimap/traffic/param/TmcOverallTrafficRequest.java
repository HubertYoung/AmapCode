package com.autonavi.minimap.traffic.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class TmcOverallTrafficRequest extends AosPostRequest {
    public static final String a;
    public String b = null;
    public String c = null;
    public String d = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_URL_KEY));
        sb.append("ws/mapapi/traffic/tmc/overalltraffic");
        a = sb.toString();
    }
}
