package com.autonavi.minimap.feedback.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class ReportSatisfactionRequest extends AosPostRequest {
    public static final String a;
    public String b = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/feedback/report_satisfaction");
        a = sb.toString();
    }
}
