package defpackage;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.minimap.offline.model.data.NaviTtsConstant;
import com.autonavi.minimap.offline.preference.OfflinePreference;
import java.util.LinkedHashSet;
import java.util.Set;

/* renamed from: dfo reason: default package */
/* compiled from: NaviTtsSpUtil */
public final class dfo {
    private static String a = "";

    public static String a() {
        return dhh.a().getStringValue("bannerList", "");
    }

    public static int b() {
        return dhh.a().getIntValue("bannerStamp", 0);
    }

    public static void a(String str, int i) {
        if (str != null) {
            dhh.a().putStringValue("bannerList", str);
        } else {
            dhh.a().remove("bannerList");
        }
        if (i != 0) {
            dhh.a().putIntValue("bannerStamp", i);
        } else {
            dhh.a().remove("bannerStamp");
        }
    }

    public static String c() {
        String stringValue = sy.a().getStringValue(OfflinePreference.KEY_NAVITTS_USING_VOICE, null);
        return NaviTtsConstant.DEFAULT_VOICE_SUBNAME.equals(stringValue) ? "nvzhongyin" : stringValue;
    }

    public static void a(String str) {
        sy.a().putStringValue(OfflinePreference.KEY_NAVITTS_USING_VOICE, str);
    }

    public static String d() {
        return sy.a().getStringValue(OfflinePreference.KEY_NAVITTS_USING_VOICE_JSON_DATA, null);
    }

    public static void b(String str) {
        sy.a().putStringValue(OfflinePreference.KEY_NAVITTS_USING_VOICE_JSON_DATA, str);
    }

    public static void a(int i) {
        sy.a().putIntValue(OfflinePreference.KEY_NAVITTS_VERSION_TIME, i);
    }

    public static String e() {
        return sy.a().getStringValue(OfflinePreference.KEY_NAVITTS_VERSION, "1.0");
    }

    public static void c(String str) {
        sy.a().putStringValue(OfflinePreference.KEY_NAVITTS_VERSION, str);
    }

    public static boolean f() {
        return dhi.a().getBooleanValue("ACTIVE_CURRENT_CUSTOM_VOICE_TAG", false);
    }

    public static void a(boolean z) {
        dhi.a().putBooleanValue("ACTIVE_CURRENT_CUSTOM_VOICE_TAG", z);
    }

    public static String g() {
        return dhi.a().getStringValue("CURRENT_CUSTOM_VOICE_TAG", null);
    }

    public static void d(String str) {
        if (TextUtils.isEmpty(str)) {
            dhi.a().remove("CURRENT_CUSTOM_VOICE_TAG");
        } else {
            dhi.a().putStringValue("CURRENT_CUSTOM_VOICE_TAG", str);
        }
    }

    public static void a(String str, Set<String> set) {
        if (set == null || set.isEmpty()) {
            dhi.a().remove(str);
        } else {
            dhi.a().sharedPrefs().edit().putStringSet(str, set).apply();
        }
    }

    public static Set<String> e(String str) {
        Set<String> stringSet = dhi.a().sharedPrefs().getStringSet(str, null);
        return stringSet == null ? new LinkedHashSet() : stringSet;
    }

    public static void h() {
        sy.a().putBooleanValue(OfflinePreference.KEY_NAVITTS_NEW_FEATURE, false);
    }

    public static boolean i() {
        return sy.a().getBooleanValue(OfflinePreference.KEY_NAVITTS_NEW_FEATURE, true);
    }

    public static void k() {
        sy.a().putBooleanValue(OfflinePreference.KEY_NAVITTS_RED_HINT_ADD_737, false);
    }

    public static void m() {
        sy.a().putBooleanValue("lzl_dlg_show", false);
    }

    public static void n() {
        sy.a().putBooleanValue("lzl_voice_first_set", false);
    }

    public static boolean o() {
        return sy.a().getBooleanValue("lzl_voice_first_set", true);
    }

    public static void p() {
        sy.a().putBooleanValue("lzl_voice_first_tts_init", false);
    }

    public static boolean q() {
        return sy.a().getBooleanValue("lzl_voice_first_tts_init", true);
    }

    public static void j() {
        String stringValue = sy.a().getStringValue(OfflinePreference.KEY_NAVITTS_UPDATE_VERSION, "");
        if (!TextUtils.isEmpty(stringValue) && !TextUtils.isEmpty(stringValue)) {
            sy.a().putBooleanValue(OfflinePreference.KEY_NAVITTS_UPDATE_VERSION.concat(String.valueOf(stringValue)), false);
        }
    }

    public static void l() {
        try {
            if (TextUtils.isEmpty(a)) {
                dhf dhf = (dhf) ank.a(dhf.class);
                PackageManager packageManager = dhf.c().getPackageManager();
                if (packageManager != null) {
                    try {
                        PackageInfo packageInfo = packageManager.getPackageInfo(dhf.c().getPackageName(), 0);
                        if (packageInfo != null) {
                            String str = packageInfo.versionName;
                            int i = packageInfo.versionCode;
                            StringBuilder sb = new StringBuilder();
                            sb.append(str);
                            sb.append("/");
                            sb.append(i);
                            a = sb.toString();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            long e2 = dhd.e();
            MapSharePreference a2 = sy.a();
            StringBuilder sb2 = new StringBuilder(OfflinePreference.KEY_PREFIX_NAVITTS_UPDATE_CHECK_TAG);
            sb2.append(a);
            a2.putLongValue(sb2.toString(), e2);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }
}
