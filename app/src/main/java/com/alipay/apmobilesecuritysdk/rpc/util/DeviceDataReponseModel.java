package com.alipay.apmobilesecuritysdk.rpc.util;

import org.json.JSONObject;

public class DeviceDataReponseModel extends BaseResponseModel {
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    public String m;

    public final boolean a() {
        return "1".equals(this.f);
    }

    public final String b() {
        if (this.h == null) {
            return "0";
        }
        return this.h;
    }

    public final JSONObject c() {
        try {
            return new JSONObject(this.l);
        } catch (Throwable unused) {
            return new JSONObject();
        }
    }
}
