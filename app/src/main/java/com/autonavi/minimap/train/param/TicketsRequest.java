package com.autonavi.minimap.train.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class TicketsRequest extends AosPostRequest {
    public static final String a;
    public String b = "116.321337";
    public String c = "39.894966";
    public String d = "113.257633";
    public String e = "23.148876";
    public String f = "B000A83M61";
    public String g = "B00140WEW0";
    public String h = "北京西站";
    public String i = "广州站";
    public String j = "50";
    public String k = "2016-9-6";
    public String l = "19-51";
    public String m = "0";
    public String n = "0";
    public String o = "3";
    public String p = "0";

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_URL_KEY));
        sb.append("ws/valueadded/train/tickets/");
        a = sb.toString();
    }
}
