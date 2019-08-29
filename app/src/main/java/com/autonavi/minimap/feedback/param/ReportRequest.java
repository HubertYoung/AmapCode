package com.autonavi.minimap.feedback.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class ReportRequest extends AosPostRequest {
    public static final String a;
    public String A = null;
    public String B = null;
    public String C = null;
    public String D = null;
    public String E = null;
    public String F = null;
    public String G = null;
    public String H = null;
    public String I = null;
    public String J = null;
    public String K = null;
    public int L = 0;
    public String M = null;
    public String N = null;
    public String O = null;
    public String P = null;
    public String Q = null;
    public String R = null;
    public String S = null;
    public String T = null;
    public String U = null;
    public String b = null;
    public String c = null;
    public String d = null;
    public String e = null;
    public String f = null;
    public String g = null;
    public String h = null;
    public String i = null;
    public String j = null;
    public String k = null;
    public String l = null;
    public String m = null;
    public String n = null;
    public String o = null;
    public String p = null;
    public String q = null;
    public String r = null;
    public String s = null;
    public String t = null;
    public String u = null;
    public String v = null;
    public int w = 0;
    public String x = null;
    public String y = null;
    public int z = 0;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/feedback/report/");
        a = sb.toString();
    }
}
