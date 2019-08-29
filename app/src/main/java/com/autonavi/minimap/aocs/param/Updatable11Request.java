package com.autonavi.minimap.aocs.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class Updatable11Request extends AosPostRequest {
    public static final String a;
    public String b = null;
    public String c = null;
    public String d = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_URL_KEY));
        sb.append("ws/shield/frogdistribution/aocs/updatable/11");
        a = sb.toString();
    }
}
