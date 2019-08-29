package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;

/* renamed from: edr reason: default package */
/* compiled from: RouteSpUtil */
public final class edr {
    private static MapSharePreference a = new MapSharePreference(SharePreferenceName.SharedPreferences);

    public static boolean a(String str, boolean z) {
        return a.getBooleanValue(str, z);
    }

    public static void b(String str, boolean z) {
        a.putBooleanValue(str, z);
    }

    public static String a(String str, String str2) {
        return a.getStringValue(str, str2);
    }

    public static void b(String str, String str2) {
        a.putStringValue(str, str2);
    }

    public static void a(int i) {
        a.putIntValue("current_ride_type", i);
    }

    public static int a() {
        return a.getIntValue("current_ride_type", 0);
    }

    public static void b() {
        a.putBooleanValue("show_ele_remind_anim", true);
    }

    public static boolean c() {
        return a.getBooleanValue("show_ele_remind_anim", false);
    }
}
