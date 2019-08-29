package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.request.param.NetworkParam;

/* renamed from: eoi reason: default package */
/* compiled from: QATestSwitch */
public final class eoi {
    public static boolean a() {
        boolean z = false;
        try {
            MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
            AMapLog.d("QAInfo", "getScreenShotEnable默认false");
            if (!mapSharePreference.contains("ScreenShotEnable")) {
                return false;
            }
            boolean booleanValue = mapSharePreference.getBooleanValue("ScreenShotEnable", false);
            try {
                AMapLog.d("QAInfo", "getScreenShotEnable开关".concat(String.valueOf(booleanValue)));
                return booleanValue;
            } catch (Exception e) {
                boolean z2 = booleanValue;
                e = e;
                z = z2;
                e.printStackTrace();
                return z;
            }
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            return z;
        }
    }

    public static boolean b() {
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        AMapLog.d("QAInfo", "getUTChannel默认true");
        if (mapSharePreference.contains("UTChannel")) {
            boolean booleanValue = mapSharePreference.getBooleanValue("UTChannel", true);
            AMapLog.d("QAInfo", "getUTChannel开关".concat(String.valueOf(booleanValue)));
            return booleanValue;
        }
        boolean a = enz.a().a("UTChannel", NetworkParam.getAdiu());
        AMapLog.d("QAInfo", "getUTChannel云控".concat(String.valueOf(a)));
        return a;
    }
}
