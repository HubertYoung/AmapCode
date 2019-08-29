package com.autonavi.minimap.alimama.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class SplashScreenRequest extends AosPostRequest {
    public static final String a;
    public String b = null;
    public double c = 0.0d;
    public String d = null;
    public String e = null;
    public String f = null;
    public String g = null;
    public int h = 0;
    public String i = null;
    public String j = null;
    public String k = null;
    public String l = null;
    public String m = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_URL_KEY));
        sb.append("ws/valueadded/alimama/splash_screen");
        a = sb.toString();
    }
}
