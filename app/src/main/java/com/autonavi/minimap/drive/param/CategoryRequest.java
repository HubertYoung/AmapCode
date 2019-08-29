package com.autonavi.minimap.drive.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class CategoryRequest extends AosPostRequest {
    public static final String a;
    public String b = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.OPERATIONAL_URL_KEY));
        sb.append("ws/drive/category");
        a = sb.toString();
    }
}
