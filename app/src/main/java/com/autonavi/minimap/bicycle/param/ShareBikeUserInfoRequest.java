package com.autonavi.minimap.bicycle.param;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class ShareBikeUserInfoRequest extends AosGetRequest {
    public static final String a;
    public String b = String.valueOf(System.currentTimeMillis());

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/valueadded/sharebike/userinfo");
        a = sb.toString();
    }
}
