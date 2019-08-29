package com.amap.location.sdk.b.a;

import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: DataConfig */
public class b implements com.amap.location.uptunnel.b.b {
    private volatile JSONObject a;
    private volatile JSONObject b;
    private Object c = new Object();

    public b(JSONObject jSONObject) {
        this.a = jSONObject == null ? new JSONObject() : jSONObject;
    }

    public void a(JSONObject jSONObject) {
        if (jSONObject != null && jSONObject.length() != 0) {
            synchronized (this.c) {
                this.b = jSONObject;
            }
        }
    }

    public void b() {
        if (this.b != null) {
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
    }

    public int a() {
        return this.a.optInt("blockCount", 50);
    }

    public long c() {
        return this.a.optLong("bufferSize", StatisticConfig.MIN_UPLOAD_INTERVAL);
    }

    public long d() {
        return this.a.optLong("timeGapOfAutoUpload", 900000);
    }

    public long e() {
        return this.a.optLong("storePeriod", 120000);
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
        return this.a.optLong("maxSizeInDB", 1000000);
    }

    public long h() {
        return this.a.optLong("validityTimeInDB", 864000000);
    }

    public long b(int i) {
        if (i == 1) {
            return this.a.optLong("maxUploadSizePerDayWifi", 30000000);
        }
        return this.a.optLong("maxUploadSizePerDayMobile", 400000);
    }

    public boolean c(int i) {
        if (i == 0) {
            return this.a.optBoolean("enableUploadMobile", false);
        }
        return this.a.optBoolean("enableUploadWifi", false);
    }
}
