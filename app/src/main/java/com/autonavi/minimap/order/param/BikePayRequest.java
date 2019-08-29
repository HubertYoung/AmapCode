package com.autonavi.minimap.order.param;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class BikePayRequest extends AosGetRequest {
    public static final String a;
    public String b = null;
    public String c = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/boss/order/bike/pay");
        a = sb.toString();
    }
}
