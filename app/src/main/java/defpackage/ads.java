package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.ae.AEUtil;

/* renamed from: ads reason: default package */
/* compiled from: DriveTaxiUtil */
public final class ads {
    private static MapSharePreference a = new MapSharePreference(SharePreferenceName.SharedPreferences);

    public static void a(String str, String str2) {
        if (AEUtil.IS_DEBUG || a.getBooleanValue("TAXI_LOG_SWITCH", false)) {
            ku.a().b(str, str2);
        }
        AMapLog.d(str, str2);
    }
}
