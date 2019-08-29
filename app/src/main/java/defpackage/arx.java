package defpackage;

import android.support.annotation.NonNull;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;

/* renamed from: arx reason: default package */
/* compiled from: HomePageSpHelper */
public final class arx {
    private final String a = "trip_guide_dialog_show_flag";
    private MapSharePreference b = new MapSharePreference(SharePreferenceName.SharedPreferences);
    private MapSharePreference c;

    @NonNull
    public final MapSharePreference a() {
        if (this.c == null) {
            this.c = new MapSharePreference((String) "basemap");
        }
        return this.c;
    }
}
