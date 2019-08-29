package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;

/* renamed from: bmn reason: default package */
/* compiled from: RealTimeUtil */
public final class bmn {
    private static MapSharePreference a = new MapSharePreference(SharePreferenceName.SharedPreferences);
    private static volatile bmm b = null;
    private static volatile boolean c = false;
    private static volatile boolean d = false;
    private static boolean e = false;

    public static boolean b() {
        return false;
    }

    public static boolean a() {
        return e;
    }

    public static void a(boolean z) {
        e = z;
    }

    public static void b(boolean z) {
        a.putBooleanValue("realTimeBus", z);
    }

    public static boolean c() {
        return a.getBooleanValue("realtime_bus_cloud_config_has_init_key", false);
    }

    public static void d() {
        a.putBooleanValue("realtime_bus_cloud_config_has_init_key", true);
    }

    public static void a(bty bty) {
        if (bty != null) {
            int l = bty.l(false);
            int p = bty.p(false);
            int k = bty.k(false);
            bty.a(true);
            if (!d && k != 2) {
                bmm bmm = new bmm();
                b = bmm;
                bmm.a = l;
                b.b = p;
                b.c = k;
                c = bty.j(bty.j());
                d = true;
                bty.a(bty.j(), bty.p(false), 0, 2, 0);
            }
        }
    }

    public static void b(bty bty) {
        if (bty != null && b != null && d) {
            bty.a(false);
            if (bty.p(false) != b.b) {
                d = false;
            } else if (bty.k(false) != 2) {
                d = false;
            } else {
                bmm bmm = b;
                if ((bmm.a == -1 || bmm.b == -1 || bmm.c == -1) ? false : true) {
                    d = false;
                    bty.a(bty.j(), b.b, b.a, b.c, c ? 1 : 0);
                }
            }
        }
    }
}
