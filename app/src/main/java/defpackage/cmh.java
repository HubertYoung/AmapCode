package defpackage;

import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cmh reason: default package */
/* compiled from: SecurityGuardSignConfigProvider */
final class cmh implements j, lp {
    MapSharePreference a;
    private boolean b = true;

    public final void onConfigCallBack(int i) {
    }

    public final boolean a() {
        if (this.a == null) {
            this.a = new MapSharePreference((String) "network_config");
            this.b = this.a.getBooleanValue("security_guard_sign_switch", true);
        }
        return this.b;
    }

    public final void onConfigResultCallBack(int i, String str) {
        switch (i) {
            case 0:
            case 1:
            case 4:
                if (!TextUtils.isEmpty(str)) {
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        if (jSONObject.has("is_open")) {
                            this.b = jSONObject.getInt("is_open") == 1;
                            if (this.a == null) {
                                this.a = new MapSharePreference((String) "network_config");
                            }
                            this.a.putBooleanValue("security_guard_sign_switch", this.b);
                        }
                        return;
                    } catch (JSONException unused) {
                        this.b = true;
                        return;
                    }
                } else {
                    return;
                }
            case 3:
                if (this.a == null) {
                    this.a = new MapSharePreference((String) "network_config");
                }
                this.a.sharedPrefs().edit().clear().apply();
                break;
        }
    }
}
