package defpackage;

import android.content.Context;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

/* renamed from: nk reason: default package */
/* compiled from: EdogUtils */
public final class nk {
    public static String a(AbstractBasePage abstractBasePage, int i) {
        return abstractBasePage.getResources().getString(i);
    }

    public static void a(Context context, int i) {
        context.getSharedPreferences("Traffic_Config", 0).edit().putInt("traffic_edog_config", i).apply();
    }

    public static int a(Context context) {
        return context.getSharedPreferences("Traffic_Config", 0).getInt("traffic_edog_config", 7);
    }

    public static int b(Context context) {
        return context.getSharedPreferences("Traffic_Config", 0).getInt("key_edog_show_camera_layer", 1);
    }
}
