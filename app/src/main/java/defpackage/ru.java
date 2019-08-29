package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;

/* renamed from: ru reason: default package */
/* compiled from: RoutePathManagerImpl */
public final class ru {
    public static String a(String str) {
        String stringValue = new MapSharePreference(SharePreferenceName.user_route_method_info).getStringValue("car_method", str);
        if (stringValue == null || stringValue.length() <= 0) {
            return str;
        }
        return (!stringValue.equals("1") || !"0".equals(str)) ? stringValue : str;
    }
}
