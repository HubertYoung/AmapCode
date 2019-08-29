package com.amap.location.sdk.b.a;

import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CountConfig */
public class a implements com.amap.location.uptunnel.b.a {
    private volatile JSONObject a;
    private volatile JSONObject b;
    private Object c = new Object();

    public a(JSONObject jSONObject) {
        this.a = jSONObject == null ? new JSONObject() : jSONObject;
    }

    public void b() {
        synchronized (this.c) {
            if (this.b != null) {
                Iterator<String> keys = this.b.keys();
                while (keys != null && keys.hasNext()) {
                    String next = keys.next();
                    try {
                        this.a.put(next, this.b.get(next));
                    } catch (JSONException unused) {
                    }
                }
                this.b = null;
            }
        }
    }

    public long a() {
        return this.a.optLong("timeGapOfAutoMerge", 600000);
    }

    public long c() {
        return this.a.optLong("bufferSize", 100);
    }

    public long d() {
        return this.a.optLong("timeGapOfAutoUpload", 900000);
    }

    public long e() {
        return this.a.optLong("storePeriod", 60000);
    }

    public long a(int i) {
        JSONObject jSONObject = this.a;
        StringBuilder sb = new StringBuilder("sizeOfPerRequest");
        sb.append(i == 1 ? "Wifi" : "Mobile");
        return jSONObject.optLong(sb.toString(), 400000);
    }

    public int f() {
        return this.a.optInt("uploadTimeout", 120000);
    }

    public long g() {
        return this.a.optLong("maxSizeInDB", 500000);
    }

    public long h() {
        return this.a.optLong("validityTimeInDB", 864000000);
    }

    public long b(int i) {
        if (i == 1) {
            return this.a.optLong("maxUploadSizePerDayWifi", 2000000);
        }
        return this.a.optLong("maxUploadSizePerDayMobile", 2000000);
    }

    public boolean c(int i) {
        JSONObject jSONObject = this.a;
        StringBuilder sb = new StringBuilder("enableUpload");
        sb.append(i == 1 ? "Wifi" : "Mobile");
        return jSONObject.optBoolean(sb.toString(), true);
    }
}
