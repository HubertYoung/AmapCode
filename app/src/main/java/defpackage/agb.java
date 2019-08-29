package defpackage;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;

/* renamed from: agb reason: default package */
/* compiled from: CarRemoteControlUtils */
public final class agb {
    public static void a(boolean z) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("ali_auto_car_connection_user_enable", z);
    }

    public static boolean a() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("ali_auto_car_connection_user_enable", false);
    }

    public static boolean b() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("ali_auto_car_readme", true);
    }

    public static void a(Boolean bool) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("ali_auto_car_readme", bool.booleanValue());
    }

    public static void b(boolean z) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("amap_auto_car_connected", z);
    }

    public static void c(boolean z) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("ali_auto_car_connected", z);
    }

    public static Boolean c() {
        return Boolean.valueOf(new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("amap_auto_car_connected", false));
    }

    public static void a(String str) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putStringValue("auto_connection_type", str);
    }

    public static String d() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getStringValue("auto_connection_type", Token.SEPARATOR);
    }
}
