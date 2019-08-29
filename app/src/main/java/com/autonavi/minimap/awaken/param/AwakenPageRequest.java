package com.autonavi.minimap.awaken.param;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class AwakenPageRequest extends AosGetRequest {
    public static final String a;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.WAKE_UP_URL));
        sb.append("ws/awaken/page/");
        a = sb.toString();
    }
}
