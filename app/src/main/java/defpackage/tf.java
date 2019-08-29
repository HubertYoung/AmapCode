package defpackage;

import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.mapstorage.MapSharePreference;

/* renamed from: tf reason: default package */
/* compiled from: NavigationSPUtil */
public final class tf {
    public static boolean a() {
        return a("agree_navi_declare_info", "agree_navi_declare", false);
    }

    public static boolean a(String str, String str2, boolean z) {
        return new MapSharePreference(str).getBooleanValue(str2, z);
    }

    public static boolean b(String str, String str2, boolean z) {
        new MapSharePreference(str).edit().putBoolean(str2, z).apply();
        return z;
    }

    public static void a(boolean z) {
        b("SharedPreferences", DriveSpUtil.NAVIMODE, z);
        b("SharedPreferences", "NaviMapMode", z);
    }
}
