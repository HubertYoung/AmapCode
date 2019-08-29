package com.autonavi.minimap.navigation.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class RestrictedAreaRequest extends AosPostRequest {
    public static final String a;
    public int b = 0;
    public String c = null;
    public int d = 0;
    public int e = 0;
    public String f = null;
    public String g = null;
    public String h = null;
    public double i = 0.0d;
    public String j = null;
    public double k = 0.0d;
    public String l = null;
    public String m = null;
    public String n = null;
    public String o = null;
    public String p = null;
    public String q = null;
    public long r = 0;
    public long s = 0;
    public String t = null;
    public String u = null;
    public String v = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_URL_KEY));
        sb.append("ws/mapapi/navigation/auto/restrictedarea/");
        a = sb.toString();
    }
}
