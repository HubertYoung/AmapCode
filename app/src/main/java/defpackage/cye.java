package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;

/* renamed from: cye reason: default package */
/* compiled from: FrequentLocationConfig */
public final class cye {
    public static boolean a;

    public static boolean a() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("sp_key_open_frequent_location_local", true);
    }

    public static int b() {
        int intValue = new MapSharePreference(SharePreferenceName.SharedPreferences).getIntValue("sp_key_open_frequent_location_aocs_number", -1);
        if (intValue >= 0) {
            return intValue;
        }
        return 5;
    }

    public static boolean c() {
        return a && a();
    }
}
