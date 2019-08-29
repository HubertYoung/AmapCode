package defpackage;

import android.graphics.Color;
import com.autonavi.minimap.R;

/* renamed from: eaz reason: default package */
/* compiled from: RideRouteLineConfig */
final class eaz extends eap {
    private static eaz a;

    public final int f() {
        return 5;
    }

    private eaz() {
    }

    public static synchronized eaz g() {
        eaz eaz;
        synchronized (eaz.class) {
            try {
                if (a == null) {
                    a = new eaz();
                }
                eaz = a;
            }
        }
        return eaz;
    }

    public final int a() {
        return Color.parseColor("#3a95ff");
    }

    public final int b() {
        return Color.parseColor("#1e6cc7");
    }

    public final int e() {
        return Color.parseColor("#a5b2bd");
    }

    public final int d() {
        return Color.parseColor("#6c8192");
    }

    public final int c() {
        return R.drawable.route_map_aolr_ride;
    }
}
