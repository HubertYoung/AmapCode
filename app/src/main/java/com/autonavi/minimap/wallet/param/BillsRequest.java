package com.autonavi.minimap.wallet.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class BillsRequest extends AosPostRequest {
    public static final String a;
    public int b = 0;
    public int c = 0;
    public int d = 0;
    public String e = null;
    public String f = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/wallet/bills");
        a = sb.toString();
    }
}
