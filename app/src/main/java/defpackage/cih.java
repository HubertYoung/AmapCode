package defpackage;

import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cih reason: default package */
/* compiled from: AgroupConfig */
public class cih implements cug {
    private static volatile cih o;
    int a = 0;
    int b = 10;
    int c = MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_;
    int d = 86400;
    int e = 21600;
    int f = 0;
    int g = 0;
    int h = 1;
    a i;
    private final int j = 10;
    private final int k = MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_;
    private final int l = 86400;
    private final int m = 21600;
    private final int n = 1;

    /* renamed from: cih$a */
    /* compiled from: AgroupConfig */
    interface a {
        void a();
    }

    private cih() {
    }

    public static cih a() {
        if (o == null) {
            synchronized (cih.class) {
                try {
                    if (o == null) {
                        o = new cih();
                    }
                }
            }
        }
        return o;
    }

    public final void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int optInt = jSONObject.optInt("KeepAliveMode");
                if (!(optInt == 1 || optInt == 0)) {
                    optInt = 0;
                }
                int optInt2 = jSONObject.optInt("AGroupHttps", -1);
                if (optInt2 < 0) {
                    optInt2 = 1;
                }
                int optInt3 = jSONObject.optInt("PollingTimerPeriod");
                if (optInt3 <= 0) {
                    optInt3 = 10;
                }
                int optInt4 = jSONObject.optInt("BgPollingTimerPeriod");
                if (optInt4 <= 0) {
                    optInt4 = MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_;
                }
                int optInt5 = jSONObject.optInt("AndroidBgContinuousTime");
                if (optInt5 <= 0) {
                    optInt5 = 86400;
                }
                int optInt6 = jSONObject.optInt("BgMaxUploadTime");
                if (optInt6 <= 0) {
                    optInt6 = 21600;
                }
                int optInt7 = jSONObject.optInt("AGroupOpen");
                if (optInt7 < 0 || optInt7 > 1) {
                    optInt7 = 0;
                }
                int optInt8 = jSONObject.optInt("AndroidBgUploadOpen");
                if (optInt8 < 0 || optInt8 > 1) {
                    optInt8 = 0;
                }
                MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
                mapSharePreference.putIntValue("keep_alive_mode", optInt);
                mapSharePreference.putIntValue("polling_timer_period", optInt3);
                mapSharePreference.putIntValue("bg_polling_timer_period", optInt4);
                mapSharePreference.putIntValue("agroup_open", optInt7);
                mapSharePreference.putIntValue("agroup_bg_upload_open", optInt8);
                mapSharePreference.putIntValue("agroup_bg_continuous_time", optInt5);
                mapSharePreference.putIntValue("agroup_bg_max_upload_time", optInt6);
                mapSharePreference.putIntValue("agroup_https", optInt2);
                if (optInt7 == 1 && this.f != optInt7) {
                    this.f = optInt7;
                    if (this.i != null) {
                        this.i.a();
                    }
                }
                if (optInt8 == 1 && this.g != optInt8) {
                    this.g = optInt8;
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public final boolean b() {
        return this.h == 1;
    }

    public final boolean c() {
        return this.f == 1;
    }
}
