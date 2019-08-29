package com.autonavi.minimap.recommend.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class RouteRrunRequest extends AosPostRequest {
    public static final String a;
    public String b = null;
    public String c = null;
    public String d = "2.9";
    public String e = null;
    public String f = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_URL_KEY));
        sb.append("ws/shield/recommend/route/run");
        a = sb.toString();
    }
}
