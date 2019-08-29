package com.autonavi.minimap.bicycle.param;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class ShareBikeIConconfRequest extends AosGetRequest {
    public static final String a;
    public String b = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/valueadded/sharebike/iconconf");
        a = sb.toString();
    }
}
