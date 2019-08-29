package com.autonavi.minimap.tservice.param;

import com.amap.bundle.aosservice.request.AosPostRequest;

public class SendPoi2CarRequest extends AosPostRequest {
    public static final String a;
    public String b = null;
    public String c = null;
    public String d = null;
    public String e = null;
    public String f = null;
    public String g = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a("ts_polling_https_url"));
        sb.append("ws/tservice/send2car/poi/");
        a = sb.toString();
    }
}
