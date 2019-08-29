package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.tinyappcommon.h5plugin.H5SensorPlugin;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import org.json.JSONObject;

/* renamed from: cyf reason: default package */
/* compiled from: FrequentLocationConfigParser */
public final class cyf {
    private MapSharePreference a = new MapSharePreference(SharePreferenceName.SharedPreferences);

    public final void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                this.a.putIntValue("sp_key_open_frequent_location_aocs", jSONObject.optInt("EnableFrequentLocation", 0));
                JSONObject optJSONObject = jSONObject.optJSONObject("FrequentLocation_strategy");
                if (optJSONObject != null) {
                    int optInt = optJSONObject.optInt("number", -1);
                    if (optInt >= 0) {
                        this.a.putIntValue("sp_key_open_frequent_location_aocs_number", optInt);
                    }
                    int optInt2 = optJSONObject.optInt(H5SensorPlugin.PARAM_INTERVAL, -1);
                    if (optInt2 >= 0) {
                        this.a.putIntValue("sp_key_open_frequent_location_aocs_interval", optInt2);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
