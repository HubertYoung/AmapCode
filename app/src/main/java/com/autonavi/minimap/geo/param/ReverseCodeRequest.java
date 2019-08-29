package com.autonavi.minimap.geo.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class ReverseCodeRequest extends AosPostRequest {
    public static final String a;
    public String b = null;
    public String c = null;
    public boolean d = false;
    public boolean e = false;
    public int f = 0;
    public int g = 0;
    public int h = 0;
    public int i = 0;
    public boolean j = false;
    public String k = null;
    public boolean l = false;
    public boolean m = false;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.SEARCH_AOS_URL_KEY));
        sb.append("ws/mapapi/geo/reversecode/");
        a = sb.toString();
    }
}
