package com.autonavi.minimap.feedback.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class UserpayListRequest extends AosPostRequest {
    public static final String a;
    public String b;
    public String c;
    public String d;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/feedback/userpay/list");
        a = sb.toString();
    }

    public UserpayListRequest() {
        this.b = "4";
        this.c = null;
        this.d = null;
        this.b = null;
    }
}
