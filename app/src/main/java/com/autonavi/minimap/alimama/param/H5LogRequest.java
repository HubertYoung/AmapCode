package com.autonavi.minimap.alimama.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class H5LogRequest extends AosPostRequest {
    public static final String a;
    public String b = null;
    public String c = null;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.H5_LOG_URL));
        sb.append("ws/h5_log?");
        a = sb.toString();
    }
}
