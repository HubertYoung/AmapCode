package com.autonavi.minimap.transfer.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class NavigationBusExtRequest extends AosPostRequest {
    public static final String a;
    public String A = "3";
    public String b = null;
    public double c = 0.0d;
    public double d = 0.0d;
    public double e = 0.0d;
    public double f = 0.0d;
    public String g = null;
    public int h = 0;
    public int i = 0;
    public String j = null;
    public String k = null;
    public int l = 0;
    public int m = 0;
    public String n = null;
    public String o = null;
    public int p = 0;
    public int q = 0;
    public int r = 0;
    public int s = 0;
    public int t = 0;
    public int u = 0;
    public int v = 0;
    public int w = 0;
    public int x = 0;
    public String y = null;
    public String z = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_URL_KEY));
        sb.append("ws/transfer/auth/navigation/bus-ext/");
        a = sb.toString();
    }
}
