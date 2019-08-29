package com.amap.location.e;

import org.json.JSONObject;

/* compiled from: CloudConfig */
public class b {
    public static int a = 10;
    public static int b = 20;
    public static long c = 20;
    public static int d = 5;
    public static long e = 21600;
    public static int f = 20;
    public static int g = 4;
    public static long h;

    public static void a(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                a = Math.min(Math.max(jSONObject.optInt("clql", a), 1), 50);
                b = Math.min(Math.max(jSONObject.optInt("clmgd", b), 1), 100);
                c = Math.min(Math.max(jSONObject.optLong("clmgt", c), 1), 100);
                d = Math.min(Math.max(jSONObject.optInt("clmg", d), 1), 50);
                e = Math.min(Math.max(jSONObject.optLong("clmt", e), 3600), 259200);
                f = Math.min(Math.max(jSONObject.optInt("clwmx", f), 1), 200);
                g = Math.min(Math.max(jSONObject.optInt("clcmx", g), 1), 50);
                h = Math.min(Math.max(jSONObject.optLong("olinter", h), 1), 60) * 1000;
            } catch (Throwable unused) {
            }
        }
    }
}
