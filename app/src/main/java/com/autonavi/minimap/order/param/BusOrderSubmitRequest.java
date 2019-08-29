package com.autonavi.minimap.order.param;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class BusOrderSubmitRequest extends AosGetRequest {
    public static final String a;
    public String b = null;
    public String c = null;
    public String d = null;
    public String e = null;
    public String f = null;
    public int g = 0;
    public String h = null;
    public String i = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/boss/order/bus/submit_order");
        a = sb.toString();
    }
}
