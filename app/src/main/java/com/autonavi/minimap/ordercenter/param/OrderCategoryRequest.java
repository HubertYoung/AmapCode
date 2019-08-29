package com.autonavi.minimap.ordercenter.param;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class OrderCategoryRequest extends AosGetRequest {
    public static final String a;
    public int b = 0;
    public int c = 0;
    public int d = 1;
    public int e = 20;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/valueadded/ordercenter/order/category/list/");
        a = sb.toString();
    }
}
