package com.amap.bundle.schoolbus.response;

import org.json.JSONObject;

public class SchoolbusCheckRoleResponse extends bpk<adj> {
    /* access modifiers changed from: private */
    /* renamed from: a */
    public adj parseResult() {
        adj adj = new adj();
        byte[] responseBodyData = getResponseBodyData();
        if (responseBodyData == null) {
            adj.a = -1;
            return adj;
        }
        try {
            JSONObject jSONObject = new JSONObject(new String(responseBodyData, "UTF-8"));
            adj.a = jSONObject.optInt("code", 0);
            adj.b = jSONObject.optString("result");
            adj.c = jSONObject.getJSONObject("data").optInt("role");
        } catch (Exception unused) {
            adj.a = -2;
        }
        return adj;
    }
}
