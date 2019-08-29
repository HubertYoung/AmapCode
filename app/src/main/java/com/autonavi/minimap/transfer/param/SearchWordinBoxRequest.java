package com.autonavi.minimap.transfer.param;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class SearchWordinBoxRequest extends AosGetRequest {
    public static final String a;
    public String b = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.SEARCH_AOS_URL_KEY));
        sb.append("ws/transfer/auth/poi/searchwordinbox");
        a = sb.toString();
    }
}
