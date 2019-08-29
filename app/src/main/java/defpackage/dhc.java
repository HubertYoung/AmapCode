package defpackage;

import com.autonavi.minimap.offline.preference.OfflinePreference;

/* renamed from: dhc reason: default package */
/* compiled from: OfflineSpUtil */
public final class dhc {
    public static boolean a() {
        boolean z = false;
        try {
            if (dhd.e() == sy.a().getLongValue(OfflinePreference.KEY_UPDATE_DATA_DATE, -1)) {
                z = true;
            }
            return z;
        } catch (Exception unused) {
            return false;
        }
    }

    public static void a(String str) {
        sy.a().putStringValue(OfflinePreference.KEY_NAVITTS_PP_SWITCH, str);
    }

    public static void b(String str) {
        sy.a().putStringValue(OfflinePreference.KEY_NAVITTS_PP_APK_DOWNLOAD_PATH, str);
    }
}
