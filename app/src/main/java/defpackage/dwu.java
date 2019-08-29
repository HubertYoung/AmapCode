package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;

/* renamed from: dwu reason: default package */
/* compiled from: BusNaviDetailPreferences */
public final class dwu {
    private static final SharePreferenceName a = SharePreferenceName.user_route_method_info;

    public static boolean a() {
        return new MapSharePreference((String) "SharedPreferences").getBooleanValue("agree_busnavi_declare", false);
    }

    public static void b() {
        new MapSharePreference((String) "SharedPreferences").putBooleanValue("agree_busnavi_declare", true);
    }

    public static boolean c() {
        return new MapSharePreference((String) "SharedPreferences").getBooleanValue("agree_onfoot_declare", false);
    }

    public static boolean d() {
        return new MapSharePreference((String) "SharedPreferences").getBooleanValue("agree_ondest_declare", false);
    }

    public static void e() {
        new MapSharePreference((String) "SharedPreferences").putBooleanValue("agree_onfoot_declare", true);
    }

    public static void f() {
        new MapSharePreference((String) "SharedPreferences").putBooleanValue("agree_ondest_declare", true);
    }

    public static int g() {
        return new MapSharePreference(a).getIntValue("route_bus_detail_ugc_close_count", 0);
    }

    public static void h() {
        MapSharePreference mapSharePreference = new MapSharePreference(a);
        mapSharePreference.putIntValue("route_bus_detail_ugc_close_count", mapSharePreference.getIntValue("route_bus_detail_ugc_close_count", 0) + 1);
    }

    public static void i() {
        new MapSharePreference(a).putIntValue("route_bus_detail_ugc_close_count", 0);
    }

    public static void j() {
        new MapSharePreference(a).putIntValue("route_bus_detail_ugc_close_version", a.a().b);
    }

    public static int k() {
        return new MapSharePreference(a).getIntValue("route_bus_detail_ugc_close_version", 0);
    }
}
