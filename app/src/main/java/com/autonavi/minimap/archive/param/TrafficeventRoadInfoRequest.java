package com.autonavi.minimap.archive.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class TrafficeventRoadInfoRequest extends AosPostRequest {
    public static final String a;
    public String b = null;
    public String c = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/archive/trafficevent_roadinfo/");
        a = sb.toString();
    }
}