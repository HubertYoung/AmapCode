package com.autonavi.minimap.config.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class FileUpdateRequest extends AosPostRequest {
    public static final String a;
    public String b = null;
    public String c = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_URL_KEY));
        sb.append("ws/mapapi/conf/tbt/file_update");
        a = sb.toString();
    }
}
