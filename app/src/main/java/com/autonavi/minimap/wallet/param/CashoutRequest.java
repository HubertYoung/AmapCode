package com.autonavi.minimap.wallet.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class CashoutRequest extends AosPostRequest {
    public static final String a;
    public String b = "1";
    public String c = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/wallet/cashout/request");
        a = sb.toString();
    }
}
