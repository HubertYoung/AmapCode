package defpackage;

import com.alibaba.fastjson.JSONObject;

/* renamed from: kg reason: default package */
/* compiled from: FastJsonUtils */
public final class kg {
    public static long a(JSONObject jSONObject, String str) {
        try {
            return jSONObject.getLongValue(str);
        } catch (Exception unused) {
            return 0;
        }
    }

    public static int b(JSONObject jSONObject, String str) {
        try {
            return jSONObject.getIntValue(str);
        } catch (Exception unused) {
            return 0;
        }
    }

    public static double c(JSONObject jSONObject, String str) {
        try {
            return jSONObject.getDoubleValue(str);
        } catch (Exception unused) {
            return 0.0d;
        }
    }
}
