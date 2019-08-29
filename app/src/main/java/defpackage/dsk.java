package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;

/* renamed from: dsk reason: default package */
/* compiled from: OfflineDataDiffController */
public class dsk {
    private static volatile dsk a;

    private dsk() {
    }

    public static dsk a() {
        if (a == null) {
            synchronized (dsk.class) {
                if (a == null) {
                    a = new dsk();
                }
            }
        }
        return a;
    }

    public static void b() {
        boolean booleanValue = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("lab_offlinedata_diff", false);
        bty mapView = DoNotUseTool.getMapView();
        if (mapView != null) {
            bty e = mapView.e();
            if (e != null) {
                e.u(booleanValue);
            }
        }
    }

    public static void a(boolean z) {
        bty e = DoNotUseTool.getMapView().e();
        if (e != null) {
            e.u(z);
        }
    }
}
