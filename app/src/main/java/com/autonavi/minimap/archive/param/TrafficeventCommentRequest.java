package com.autonavi.minimap.archive.param;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class TrafficeventCommentRequest extends AosGetRequest {
    public static final String a;
    public String b = null;
    public String c = null;
    public String d = null;
    public String e = null;
    public int f = 0;
    public String g = null;
    public int h = 0;
    public int i = 0;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/archive/trafficevent_comment/");
        a = sb.toString();
    }
}
