package com.autonavi.minimap.poi.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class VoiceSearchRequest extends AosPostRequest {
    public static final String a;
    public boolean A = true;
    public boolean B = true;
    public String C = "2.5.2";
    public String D = null;
    public String E = null;
    public String F = null;
    public String G = null;
    public String H = "2";
    public String I = null;
    public String J = null;
    public String K = null;
    public String L = null;
    public String M = null;
    public String N = null;
    public String O = "voice";
    public String b = null;
    public String c = null;
    public String d = "TQUERY";
    public int e = 10;
    public int f = 1;
    public String g = null;
    public String h = null;
    public String i = "101000";
    public int j = 5;
    public String k = "true";
    public String l = null;
    public String m = null;
    public String n = "voice";
    public boolean o = false;
    public boolean p = true;
    public String q = "2.19";
    public boolean r = true;
    public int s = 1;
    public boolean t = true;
    public boolean u = true;
    public boolean v = true;
    public boolean w = true;
    public String x = null;
    public String y = null;
    public boolean z = true;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.SEARCH_AOS_URL_KEY));
        sb.append("ws/mapapi/poi/voicesearch/");
        a = sb.toString();
    }
}
