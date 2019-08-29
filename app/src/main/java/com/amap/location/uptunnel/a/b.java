package com.amap.location.uptunnel.a;

import org.json.JSONObject;

/* compiled from: CommandUtil */
public class b {
    public static a a(JSONObject jSONObject) {
        a cVar = jSONObject.optInt("commandType", -1) == 1 ? new c(jSONObject) : null;
        if (cVar != null) {
            try {
                cVar.a(jSONObject);
            } catch (Exception unused) {
            }
            if (cVar.b()) {
                return cVar;
            }
        }
        return null;
    }
}
