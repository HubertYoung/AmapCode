package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;

/* renamed from: brv reason: default package */
/* compiled from: MapStateSpHelper */
public final class brv {
    public static boolean a() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("indoor_map_switch", true);
    }
}
