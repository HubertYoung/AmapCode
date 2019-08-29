package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ajl reason: default package */
/* compiled from: UCCloudConfigManager */
public class ajl {
    private static volatile ajl b;
    public lp a;

    public static ajl a() {
        if (b == null) {
            synchronized (ajl.class) {
                try {
                    if (b == null) {
                        b = new ajl();
                    }
                }
            }
        }
        return b;
    }

    public static boolean b() {
        return new MapSharePreference((String) "uc_options").getBooleanValue("OPTION_WEB_CONTENTS_DEBUGGING_ENABLE", false);
    }

    public static void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                boolean z = true;
                if (jSONObject.has("OPTION_USE_SYSTEM_WEBVIEW")) {
                    boolean z2 = jSONObject.optInt("OPTION_USE_SYSTEM_WEBVIEW", 0) == 1;
                    new MapSharePreference((String) "uc_options").putBooleanValue("OPTION_USE_SYSTEM_WEBVIEW", z2);
                    AMapLog.d("UCCloudConfig", "update OPTION_USE_SYSTEM_WEBVIEW:".concat(String.valueOf(z2)));
                }
                if (jSONObject.has("OPTION_WEB_CONTENTS_DEBUGGING_ENABLE")) {
                    if (jSONObject.optInt("OPTION_WEB_CONTENTS_DEBUGGING_ENABLE", 0) != 1) {
                        z = false;
                    }
                    new MapSharePreference((String) "uc_options").putBooleanValue("OPTION_WEB_CONTENTS_DEBUGGING_ENABLE", z);
                    AMapLog.d("UCCloudConfig", "update OPTION_WEB_CONTENTS_DEBUGGING_ENABLE:".concat(String.valueOf(z)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    static /* synthetic */ void c() {
        new MapSharePreference((String) "uc_options").edit().clear().apply();
        AMapLog.d("UCCloudConfig", "delete all config!");
    }
}
