package com.autonavi.minimap.order.param;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class HotelListRequest extends AosGetRequest {
    public static final String a;
    public int b = 1;
    public int c = 20;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/boss/order/hotel/list");
        a = sb.toString();
    }
}
