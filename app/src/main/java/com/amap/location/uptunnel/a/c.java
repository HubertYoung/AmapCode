package com.amap.location.uptunnel.a;

import org.json.JSONObject;

/* compiled from: UploadLogCmd */
public class c extends a {
    private int c;

    public c(JSONObject jSONObject) {
        super(jSONObject);
        this.a = 1;
    }

    public void a(JSONObject jSONObject) {
        JSONObject optJSONObject = jSONObject.optJSONObject("cmdBody");
        if (optJSONObject != null) {
            this.c = optJSONObject.optInt("tunnelType", -1);
        }
    }

    public boolean b() {
        return this.c == 3 || this.c == 4;
    }

    public int c() {
        return this.c;
    }

    public static final JSONObject a(int i) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("commandType", 1);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("tunnelType", i);
            jSONObject.put("cmdBody", jSONObject2);
            return jSONObject;
        } catch (Throwable unused) {
            return null;
        }
    }
}
