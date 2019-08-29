package com.autonavi.minimap.feedback.param;

import com.amap.bundle.aosservice.request.AosMultipartRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TwiceReportRequest extends AosMultipartRequest {
    public static final String f;
    public String g = null;
    public String h = null;
    public String i = null;
    public String j = null;
    public Map<String, File> k = new HashMap();

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/feedback/twice_report");
        f = sb.toString();
    }
}
