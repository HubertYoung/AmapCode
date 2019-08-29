package com.autonavi.minimap.poi.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class InfoRequest extends AosPostRequest {
    public static final String a;
    public String b = "IDQ";
    public String c = null;
    public String d = "POI";
    public String e = null;
    public String f = null;
    public String g = "1";
    public String h = "0";
    public String i = "2.19";

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.SEARCH_AOS_URL_KEY));
        sb.append("ws/mapapi/poi/info");
        a = sb.toString();
    }
}
