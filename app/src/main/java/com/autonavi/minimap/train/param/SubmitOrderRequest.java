package com.autonavi.minimap.train.param;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class SubmitOrderRequest extends AosGetRequest {
    public static final String a;
    public String b = null;
    public String c = null;
    public String d = null;
    public String e = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/boss/order/train/submit_order/");
        a = sb.toString();
    }
}
