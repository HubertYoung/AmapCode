package defpackage;

import android.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: enw reason: default package */
/* compiled from: ProfileInfoParser */
public final class enw {
    public static String a(String str) {
        enx.a("start encode: ".concat(String.valueOf(str)));
        String str2 = new String(Base64.encode(str.getBytes(), 0));
        enx.a("encode result: ".concat(String.valueOf(str2)));
        return str2;
    }

    public static JSONObject b(String str) {
        JSONObject jSONObject;
        enx.a("start decode: ".concat(String.valueOf(str)));
        try {
            jSONObject = new JSONObject(new String(Base64.decode(str, 0)));
        } catch (JSONException e) {
            e.printStackTrace();
            jSONObject = null;
        }
        enx.a("decode result: ".concat(String.valueOf(jSONObject)));
        return jSONObject;
    }
}
