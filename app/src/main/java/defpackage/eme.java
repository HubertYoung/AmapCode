package defpackage;

import android.content.Context;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.map.core.MapManager;
import com.autonavi.minimap.R;

/* renamed from: eme reason: default package */
/* compiled from: TrafficConditionHelper */
public class eme implements bqx {
    private static boolean a = false;

    public final void a(boolean z, boolean z2, MapManager mapManager, Context context) {
        a(true, z, z2, mapManager, context);
    }

    public final void a(boolean z, bty bty, Context context) {
        a(true, z, false, bty, context);
    }

    public final void a(boolean z, boolean z2, boolean z3, MapManager mapManager, Context context) {
        if (mapManager != null) {
            a(z, z2, z3, mapManager.getMapView(), context);
        }
    }

    public final void a(boolean z, boolean z2, boolean z3, bty bty, Context context) {
        if (bty != null && context != null) {
            boolean s = bty.s();
            bty.b(z2);
            if (z) {
                new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("traffic", z2);
            }
            if (z3 && (!s || !z2)) {
                ToastHelper.showToast(context.getResources().getString(z2 ? R.string.map_traffics_on : R.string.map_traffics_off));
            }
            cdd.n().c(z2);
        }
    }

    public final boolean a() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("traffic", false);
    }

    public static boolean b() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).contains("traffic");
    }
}
