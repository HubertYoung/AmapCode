package defpackage;

import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;

/* renamed from: emv reason: default package */
/* compiled from: AppInitALCLogger */
public final class emv {
    private static boolean a = true;
    private static boolean b = true;
    private static boolean c = true;
    private static boolean d = true;
    private static boolean e = true;
    private static boolean f = true;
    private static boolean g = true;
    private static boolean h = true;
    private static boolean i = true;
    private static boolean j = true;
    private static boolean k = true;
    private static boolean l = true;
    private static boolean m = true;

    public static void a() {
        if (c) {
            c = false;
            AMapAppGlobal.getApplication().getString(R.string.init_log_first_map_create);
        }
    }

    public static void b() {
        if (d) {
            d = false;
            AMapAppGlobal.getApplication().getString(R.string.init_log_first_map_created);
        }
    }

    public static void c() {
        if (e) {
            e = false;
            AMapAppGlobal.getApplication().getString(R.string.init_log_first_map_frame_create);
        }
    }

    public static void d() {
        if (a) {
            a = false;
            AMapAppGlobal.getApplication().getString(R.string.init_log_first_map_surface_create);
        }
    }

    public static void e() {
        if (b) {
            b = false;
            AMapAppGlobal.getApplication().getString(R.string.init_log_first_map_surface_change);
        }
    }

    public static void a(String str) {
        if (h) {
            h = false;
            StringBuilder sb = new StringBuilder();
            sb.append(AMapAppGlobal.getApplication().getString(R.string.init_log_app_start));
            sb.append(":");
            sb.append(str);
        }
    }

    public static void f() {
        if (i) {
            i = false;
            AMapAppGlobal.getApplication().getString(R.string.init_log_enter_main_map);
        }
    }

    public static void g() {
        if (j) {
            j = false;
            AMapAppGlobal.getApplication().getString(R.string.init_log_layout_complete);
        }
    }

    public static void h() {
        if (k) {
            k = false;
            AMapAppGlobal.getApplication().getString(R.string.init_log_draw_first_frame);
        }
    }

    public static void i() {
        if (l) {
            l = false;
            AMapAppGlobal.getApplication().getString(R.string.init_log_draw_first_frame_ui);
        }
    }

    public static void j() {
        if (m) {
            m = false;
            AMapAppGlobal.getApplication().getString(R.string.init_log_map_complete);
        }
    }
}
