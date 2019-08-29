package com.autonavi.minimap.shoping.param;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class PoiListRequest extends AosGetRequest {
    public static final String a;
    public int b;
    public int c;
    public String d;
    public String e;
    public String f;
    public String g;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_URL_KEY));
        sb.append("ws/valueadded/shopping/poi_list");
        a = sb.toString();
    }

    public PoiListRequest() {
        this.b = 1;
        this.c = 10;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
    }

    public PoiListRequest(String str, String str2, String str3, String str4, int i) {
        this.d = str;
        this.e = str2;
        this.f = str3;
        this.g = str4;
        this.b = i;
        this.c = 10;
    }
}
