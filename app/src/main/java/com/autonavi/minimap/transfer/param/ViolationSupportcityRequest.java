package com.autonavi.minimap.transfer.param;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class ViolationSupportcityRequest extends AosGetRequest {
    public static final String a;
    public String b = null;
    public String c = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/transfer/auth/traffic-violation-web/violation/supportcity/");
        a = sb.toString();
    }
}
