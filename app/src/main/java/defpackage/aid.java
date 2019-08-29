package defpackage;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: aid reason: default package */
/* compiled from: JsonParseUtil */
public final class aid {
    public static String a(int i, @Nullable JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("code", i);
            if (jSONObject == null) {
                jSONObject2.put("data", new JSONObject());
            } else {
                jSONObject2.put("data", jSONObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject2.toString();
    }

    public static JSONObject a(Pair<String, Object> pair) {
        JSONObject jSONObject = new JSONObject();
        if (b(pair)) {
            try {
                jSONObject.put((String) pair.first, pair.second);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject;
    }

    public static JSONObject a(List<Pair<String, Object>> list) {
        JSONObject jSONObject = new JSONObject();
        if (list != null && list.size() > 0) {
            for (Pair next : list) {
                if (b(next)) {
                    try {
                        jSONObject.put((String) next.first, next.second);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return jSONObject;
    }

    private static boolean b(Pair<String, Object> pair) {
        return pair != null && !TextUtils.isEmpty((CharSequence) pair.first);
    }
}
