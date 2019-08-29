package com.autonavi.minimap.operation.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class InfoRequest extends AosPostRequest {
    public static final String a;
    public String b = null;
    public String c = null;
    public int d = 0;
    public String e = null;
    public String f = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/oss/operation/info");
        a = sb.toString();
    }
}
