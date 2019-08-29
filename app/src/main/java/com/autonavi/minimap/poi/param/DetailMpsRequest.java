package com.autonavi.minimap.poi.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class DetailMpsRequest extends AosPostRequest {
    public static final String a;
    public String b = null;
    public String c = null;
    public String d = null;
    public String e = null;
    public String f = null;
    public String g = null;
    public String h = null;
    public String i = null;
    public String j = null;
    public String k = null;
    public String l = null;
    public String m = null;
    public String n = "2.16";
    public String o = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.SEARCH_AOS_URL_KEY));
        sb.append("ws/mapapi/poidetail/mps/");
        a = sb.toString();
    }
}
