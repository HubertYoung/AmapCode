package defpackage;

import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bob reason: default package */
/* compiled from: RedesignABLogManager */
public final class bob {
    private final String a;
    private MapSharePreference b;

    /* renamed from: bob$a */
    /* compiled from: RedesignABLogManager */
    public interface a {
        public static final bob a = new bob(0);
    }

    /* synthetic */ bob(byte b2) {
        this();
    }

    private bob() {
        this.a = "redesign_ab_page_switch_log";
        this.b = new MapSharePreference(SharePreferenceName.SharedPreferences);
    }

    public final void a(String str, String str2, JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("pageId", str);
            jSONObject2.put("buttonId", str2);
            jSONObject2.put("key_data", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.b.edit().putString("redesign_ab_page_switch_log", jSONObject2.toString());
    }

    public final void a() {
        String stringValue = this.b.getStringValue("redesign_ab_page_switch_log", "");
        if (!TextUtils.isEmpty(stringValue)) {
            try {
                JSONObject jSONObject = new JSONObject(stringValue);
                String string = jSONObject.getString("pageId");
                String string2 = jSONObject.getString("buttonId");
                JSONObject jSONObject2 = jSONObject.getJSONObject("key_data");
                if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                    if (jSONObject2 != null) {
                        LogManager.actionLogV2(string, string2, jSONObject2);
                    } else {
                        LogManager.actionLogV2(string, string2);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                b();
            }
        }
    }

    private void b() {
        this.b.edit().putString("redesign_ab_page_switch_log", "");
    }
}
