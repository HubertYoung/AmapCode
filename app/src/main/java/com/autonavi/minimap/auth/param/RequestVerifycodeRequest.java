package com.autonavi.minimap.auth.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class RequestVerifycodeRequest extends AosPostRequest {
    public static final String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("ws/auth/request-verifycode");
        a = sb.toString();
    }

    public RequestVerifycodeRequest() {
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
    }

    public RequestVerifycodeRequest(String str, String str2, String str3, String str4) {
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = str4;
        this.f = null;
    }
}
