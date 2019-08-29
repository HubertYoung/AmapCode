package com.autonavi.minimap.provider.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class InsuranceTokenRequest extends AosPostRequest {
    public static final String a;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("ws/pp/provider/insurance-token/");
        a = sb.toString();
    }
}
