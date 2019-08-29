package com.autonavi.minimap.app.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class EntranceListRequest extends AosPostRequest {
    public static final String a;
    public String b = null;
    public String c = null;
    public String d = null;
    public String e = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_URL_KEY));
        sb.append("ws/mapapi/entrance_list");
        a = sb.toString();
    }
}
