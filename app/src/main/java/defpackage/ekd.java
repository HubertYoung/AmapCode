package defpackage;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.alibaba.appmonitor.offline.TempEvent;
import com.amap.bundle.mapstorage.MapSharePreference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ekd reason: default package */
/* compiled from: UGCCache */
public final class ekd {
    private Context a;
    private JSONArray b;
    private JSONArray c;

    public ekd(Context context) {
        this.a = context;
        String a2 = a((String) "ugc_cache_bus");
        if (!a2.isEmpty()) {
            try {
                this.b = new JSONArray(a2);
            } catch (JSONException e) {
                this.b = new JSONArray();
                e.printStackTrace();
            }
        }
        String a3 = a((String) "ugc_cache_foot");
        if (!a3.isEmpty()) {
            try {
                this.c = new JSONArray(a3);
            } catch (JSONException e2) {
                this.c = new JSONArray();
                e2.printStackTrace();
            }
        }
    }

    public static String a(String str) {
        return new MapSharePreference((String) "SharedPreferences").sharedPrefs().getString(str, "");
    }

    public static void a(String str, String str2) {
        Editor edit = new MapSharePreference((String) "SharedPreferences").sharedPrefs().edit();
        edit.putString(str, str2);
        edit.apply();
    }

    private static JSONArray a(JSONArray jSONArray) {
        JSONArray jSONArray2 = new JSONArray();
        if (jSONArray == null) {
            return null;
        }
        for (int i = 1; i < jSONArray.length(); i++) {
            try {
                jSONArray2.put(jSONArray.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONArray2;
    }

    public final String a(String str, JSONObject jSONObject) {
        JSONArray jSONArray;
        int length = this.b != null ? this.b.length() + 0 : 0;
        if (this.c != null) {
            length += this.c.length();
        }
        if (length >= 10) {
            if (this.b.length() == 0) {
                this.c = a(this.c);
            }
            if (this.c.length() == 0) {
                this.b = a(this.b);
            }
            try {
                if (Integer.parseInt(this.b.getJSONObject(0).optString(TempEvent.TAG_COMMITTIME, "0")) > Integer.parseInt(this.c.getJSONObject(0).optString(TempEvent.TAG_COMMITTIME, "0"))) {
                    a(this.c);
                } else {
                    a(this.b);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (str != null) {
            try {
                if (!str.isEmpty()) {
                    jSONArray = new JSONArray(str);
                    jSONArray.put(jSONObject);
                    return jSONArray.toString();
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
                return str;
            }
        }
        jSONArray = new JSONArray();
        jSONArray.put(jSONObject);
        return jSONArray.toString();
    }
}
