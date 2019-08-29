package defpackage;

import android.text.TextUtils;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import org.json.JSONObject;

/* renamed from: ack reason: default package */
/* compiled from: PlanHomeConfigUtil */
public class ack {
    private static final String a = "ack";

    public static boolean a() {
        String a2 = lo.a().a((String) DIYMainMapPresenter.DIY_MAIN_MAP_CONFIG_MODULE_NAME);
        return !TextUtils.isEmpty(a2) && a2.contains("\"free_ride\"");
    }

    public static boolean b() {
        String a2 = lo.a().a((String) "etrip");
        if (TextUtils.isEmpty(a2)) {
            return false;
        }
        try {
            if (new JSONObject(a2).optInt("etrip") == 1) {
                return true;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean c() {
        String a2 = lo.a().a((String) "aeroplane_tab");
        if (TextUtils.isEmpty(a2)) {
            return false;
        }
        try {
            if (new JSONObject(a2).optInt("aeroplane") == 1) {
                return true;
            }
            return false;
        } catch (Exception e) {
            eao.a(a, "fetch grey-scale switcher for etrip fail!", e);
            return false;
        }
    }

    public static boolean d() {
        String a2 = lo.a().a((String) "motorcycle");
        if (TextUtils.isEmpty(a2)) {
            return false;
        }
        try {
            if (new JSONObject(a2).optInt("motor_tab") == 1) {
                return true;
            }
            return false;
        } catch (Exception e) {
            eao.a(a, "fetch grey-scale switcher for motor fail!", e);
            return false;
        }
    }

    public static boolean e() {
        String a2 = lo.a().a((String) DIYMainMapPresenter.DIY_MAIN_MAP_CONFIG_MODULE_NAME);
        return !TextUtils.isEmpty(a2) && a2.contains("\"cab\"");
    }
}
