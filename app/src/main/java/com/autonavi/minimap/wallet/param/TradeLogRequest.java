package com.autonavi.minimap.wallet.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class TradeLogRequest extends AosPostRequest {
    public static final String a;
    public int b = 0;
    public int c = 0;
    public String d = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/wallet/tradelog/list");
        a = sb.toString();
    }
}
