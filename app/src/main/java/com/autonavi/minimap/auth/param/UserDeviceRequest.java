package com.autonavi.minimap.auth.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class UserDeviceRequest extends AosPostRequest {
    public static final String a;
    public String A = null;
    public String B = null;
    public String C = null;
    public String D = null;
    public String E = null;
    public String F = null;
    public String b = "ANDROID";
    public String c = null;
    public String d = null;
    public String e = null;
    public String f = null;
    public String g = null;
    public String h = "0";
    public String i = "0";
    public String j = null;
    public String k = "0";
    public String l = null;
    public String m = null;
    public String n = null;
    public String o = null;
    public String p = "0";
    public String q = null;
    public String r = null;
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
        sb.append("ws/auth/user-device");
        a = sb.toString();
    }
}
