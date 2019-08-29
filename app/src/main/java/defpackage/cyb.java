package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.annotation.MainMapFeature;

@MainMapFeature
/* renamed from: cyb reason: default package */
/* compiled from: FrequentLocationRedesignController */
public class cyb implements czu, czy, lp {
    private cyf a;
    private MapSharePreference b = new MapSharePreference(SharePreferenceName.SharedPreferences);

    public void onConfigCallBack(int i) {
    }

    public void onPause() {
    }

    public void onCreate() {
        this.a = new cyf();
        lo.a().a((String) "main_map_frequent_location", (lp) this);
        try {
            this.a.a(lo.a().a((String) "main_map_frequent_location"));
        } catch (Exception unused) {
        }
    }

    public void onDestroy() {
        lo.a().c("main_map_frequent_location");
    }

    public void onResume() {
        a();
    }

    public void onConfigResultCallBack(int i, String str) {
        this.a.a(str);
        a();
    }

    private void a() {
        boolean z = true;
        if (this.b.getIntValue("sp_key_open_frequent_location_aocs", 0) != 1) {
            z = false;
        }
        cye.a = z;
    }
}
