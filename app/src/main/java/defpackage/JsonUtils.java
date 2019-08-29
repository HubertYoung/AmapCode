package defpackage;

import android.graphics.PointF;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: if reason: invalid class name and default package and case insensitive filesystem */
public final class JsonUtils {
    public static PointF a(JSONObject jSONObject, float f) {
        return new PointF(a(jSONObject.opt(DictionaryKeys.CTRLXY_X)) * f, a(jSONObject.opt(DictionaryKeys.CTRLXY_Y)) * f);
    }

    public static PointF a(JSONArray jSONArray, float f) {
        if (jSONArray.length() >= 2) {
            return new PointF(((float) jSONArray.optDouble(0, 1.0d)) * f, ((float) jSONArray.optDouble(1, 1.0d)) * f);
        }
        throw new IllegalArgumentException("Unable to parse point for ".concat(String.valueOf(jSONArray)));
    }

    public static float a(Object obj) {
        if (obj instanceof Float) {
            return ((Float) obj).floatValue();
        }
        if (obj instanceof Integer) {
            return (float) ((Integer) obj).intValue();
        }
        if (obj instanceof Double) {
            return (float) ((Double) obj).doubleValue();
        }
        if (obj instanceof JSONArray) {
            return (float) ((JSONArray) obj).optDouble(0);
        }
        return 0.0f;
    }
}
