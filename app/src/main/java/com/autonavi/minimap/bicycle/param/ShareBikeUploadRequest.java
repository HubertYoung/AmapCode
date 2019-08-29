package com.autonavi.minimap.bicycle.param;

import com.amap.bundle.aosservice.request.AosMultipartRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;
import java.io.File;

public class ShareBikeUploadRequest extends AosMultipartRequest {
    public static final String f;
    public String g = null;
    public String h = null;
    public String i = null;
    public File j = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/valueadded/sharebike/upload");
        f = sb.toString();
    }
}
