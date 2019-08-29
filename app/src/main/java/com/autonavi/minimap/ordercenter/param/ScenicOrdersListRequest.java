package com.autonavi.minimap.ordercenter.param;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class ScenicOrdersListRequest extends AosGetRequest {
    public static final String a;
    public int b = 0;
    public int c = 0;
    public String d = null;
    public String e = null;
    public String f = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/valueadded/ordercenter/scenic/list_orders/");
        a = sb.toString();
    }
}
