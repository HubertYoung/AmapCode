package com.amap.bundle.schoolbus.response;

import org.json.JSONObject;

public class SchoolbusStatusResponse extends bpk<adk> {
    /* access modifiers changed from: private */
    /* renamed from: a */
    public adk parseResult() {
        adk adk = new adk();
        byte[] responseBodyData = getResponseBodyData();
        if (responseBodyData == null) {
            return adk;
        }
        try {
            JSONObject optJSONObject = new JSONObject(new String(responseBodyData, "UTF-8")).optJSONObject("data");
            adk.c = optJSONObject.optString("routeId");
            adk.d = optJSONObject.optInt("routeStatus");
            adk.e = optJSONObject.optInt("lostControl");
        } catch (Exception unused) {
        }
        return adk;
    }
}
