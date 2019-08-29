package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cmb reason: default package */
/* compiled from: FreeCdnConfigProvider */
class cmb implements e {
    private Map<String, String> a = new ConcurrentHashMap();
    private boolean b;
    private int c;

    public final boolean a() {
        return this.b;
    }

    public final Map<String, String> b() {
        return this.a;
    }

    static /* synthetic */ void a(cmb cmb) {
        cmb.a.clear();
        cmb.b = false;
        cmb.c = 0;
    }

    static /* synthetic */ void a(cmb cmb, String str) {
        boolean z;
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject optJSONObject = new JSONObject(str).optJSONObject("cdn2free");
                if (optJSONObject != null && "1".equals(optJSONObject.optString("cdn2freeEnabled"))) {
                    int parseInt = Integer.parseInt(optJSONObject.optString("mapVersion"));
                    if (parseInt > cmb.c) {
                        cmb.c = parseInt;
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        JSONArray optJSONArray = optJSONObject.optJSONArray("cdn2freeMap");
                        if (optJSONArray != null) {
                            if (optJSONArray.length() != 0) {
                                for (int i = 0; i < optJSONArray.length(); i++) {
                                    JSONObject jSONObject = optJSONArray.getJSONObject(i);
                                    if (jSONObject != null) {
                                        String optString = jSONObject.optString("cdn");
                                        String optString2 = jSONObject.optString("free");
                                        if (!TextUtils.isEmpty(optString) && !TextUtils.isEmpty(optString2)) {
                                            cmb.a.put(optString, optString2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                AMapLog.e("network.FreeCdnConfigProvider", e.getLocalizedMessage());
            }
        }
    }
}
