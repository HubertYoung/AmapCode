package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;

/* renamed from: cey reason: default package */
/* compiled from: TripGuideSpHelper */
public final class cey {
    MapSharePreference a = new MapSharePreference(SharePreferenceName.SharedPreferences);
    MapSharePreference b;
    private final String c = "trip_guide_dialog_show_flag";

    public final boolean a() {
        return this.a.getBooleanValue("trip_guide_dialog_show_flag", false);
    }
}
