package com.autonavi.minimap.archive.param;

import com.amap.bundle.aosservice.request.AosMultipartRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class TrafficeventUpdateRequest extends AosMultipartRequest {
    public static final String f;
    public String A = null;
    public String B = null;
    public String C = null;
    public String D = null;
    public int E = 0;
    public String F = null;
    public String G = null;
    public int H = 0;
    public String I = null;
    public String J = null;
    public String K = null;
    public int L = 0;
    public String M = null;
    public int N = 0;
    public String O = null;
    public int P = 0;
    public int Q = 0;
    public String R = null;
    public int g = 0;
    public int h = 0;
    public String i = null;
    public String j = null;
    public String k = null;
    public String l = null;
    public String m = null;
    public String n = null;
    public int o = 0;
    public int p = 0;
    public int q = 0;
    public int r = 0;
    public String s = null;
    public String t = null;
    public String u = null;
    public String v = null;
    public String w = null;
    public String x = null;
    public String y = null;
    public String z = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/archive/trafficevent_update/");
        f = sb.toString();
    }
}
