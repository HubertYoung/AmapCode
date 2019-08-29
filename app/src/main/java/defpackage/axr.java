package defpackage;

import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: axr reason: default package */
/* compiled from: Parser */
public final class axr {
    public static JSONArray a(JSONObject jSONObject, String str) {
        try {
            JSONArray optJSONArray = jSONObject.optJSONArray(str);
            if (optJSONArray == null) {
                JSONObject optJSONObject = jSONObject.optJSONObject(str);
                if (optJSONObject != null) {
                    optJSONArray = new JSONArray().put(optJSONObject);
                }
            }
            return optJSONArray;
        } catch (Exception unused) {
            return null;
        }
    }

    public static int b(JSONObject jSONObject, String str) {
        try {
            String optString = jSONObject.optString(str);
            if (optString.equals("")) {
                return 0;
            }
            return Integer.parseInt(optString);
        } catch (Exception unused) {
            return 0;
        }
    }

    public static double c(JSONObject jSONObject, String str) {
        try {
            String optString = jSONObject.optString(str);
            if (TextUtils.isEmpty(optString)) {
                return 0.0d;
            }
            return Double.parseDouble(optString);
        } catch (Exception unused) {
            return 0.0d;
        }
    }

    public static int d(JSONObject jSONObject, String str) {
        try {
            String optString = jSONObject.optString(str);
            if (optString.equals("")) {
                return -1;
            }
            return Integer.parseInt(optString);
        } catch (Exception unused) {
            return -1;
        }
    }

    public static String e(JSONObject jSONObject, String str) {
        String str2;
        if (jSONObject == null || str.length() == 0) {
            return null;
        }
        try {
            str2 = jSONObject.getString(str);
            try {
                return str2.equals("null") ? "" : str2;
            } catch (JSONException unused) {
                return str2;
            }
        } catch (JSONException unused2) {
            str2 = "";
            return str2;
        }
    }

    public static byte f(JSONObject jSONObject, String str) {
        try {
            String optString = jSONObject.optString(str);
            if (optString.equals("")) {
                return 0;
            }
            if (optString.contains("0x")) {
                optString = optString.substring(2, optString.length());
            }
            return (byte) Integer.parseInt(optString, 16);
        } catch (Exception unused) {
            return 0;
        }
    }
}
