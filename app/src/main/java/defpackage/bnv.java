package defpackage;

import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.bundle.uitemplate.mapwidget.inter.LogVersionType;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bnv reason: default package */
/* compiled from: LayerSwitchSPHelper */
public final class bnv {
    private static String e;
    public MapSharePreference a = new MapSharePreference(SharePreferenceName.SharedPreferences);
    private final String b = "key_ab_page_switch";
    private final String c = "key_redesign_ab_cloud_state";
    private final String d = "redesign_ab";

    public final boolean a() {
        return this.a.getBooleanValue("key_ab_page_switch", e());
    }

    private boolean e() {
        return this.a.getBooleanValue("key_redesign_ab_cloud_state", false);
    }

    public static String b() {
        String str = new bnv().a() ? "1" : "0";
        StringBuilder sb = new StringBuilder();
        sb.append("{\"data\":{\"isRedesign\":\"");
        sb.append(str);
        sb.append("\"}}");
        return sb.toString();
    }

    public static int c() {
        String a2 = lo.a().a((String) "redesign_ab");
        if (!TextUtils.isEmpty(a2)) {
            try {
                int i = new JSONObject(a2).getInt("redesign_ab");
                if (i == 1 || i == 0) {
                    return i;
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return -1;
    }

    public static String d() {
        if (e == null) {
            e = new bnv().a() ? LogVersionType.REDESIGN : LogVersionType.UN_REDESIGN;
        }
        return e;
    }
}
