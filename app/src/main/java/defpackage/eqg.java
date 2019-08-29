package defpackage;

import android.text.TextUtils;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import org.json.JSONObject;

/* renamed from: eqg reason: default package */
/* compiled from: VuiTravelUtils */
public final class eqg {
    public static boolean a(int i) {
        RouteType type = RouteType.getType(i);
        if (type == RouteType.CAR || type == RouteType.TRUCK || type == RouteType.BUS || type == RouteType.ONFOOT || type == RouteType.TRAIN) {
            return true;
        }
        if (type == RouteType.RIDE) {
            return lt.a().g();
        }
        if (type == RouteType.COACH) {
            return lt.a().f();
        }
        if (type == RouteType.TAXI) {
            return e();
        }
        if (type == RouteType.ETRIP) {
            return b();
        }
        if (type == RouteType.FREERIDE) {
            return a();
        }
        if (type == RouteType.AIRTICKET) {
            return c();
        }
        if (type == RouteType.MOTOR) {
            return d();
        }
        bfh.a("VuiTravelUtils", "isSupportRouteType  no find tab ".concat(String.valueOf(type)));
        return false;
    }

    private static boolean a() {
        String a = lo.a().a((String) DIYMainMapPresenter.DIY_MAIN_MAP_CONFIG_MODULE_NAME);
        return !TextUtils.isEmpty(a) && a.contains("\"free_ride\"");
    }

    private static boolean b() {
        String a = lo.a().a((String) "etrip");
        if (TextUtils.isEmpty(a)) {
            return false;
        }
        try {
            if (new JSONObject(a).optInt("etrip") == 1) {
                return true;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    private static boolean c() {
        String a = lo.a().a((String) "aeroplane_tab");
        if (TextUtils.isEmpty(a)) {
            return false;
        }
        try {
            if (new JSONObject(a).optInt("aeroplane") == 1) {
                return true;
            }
            return false;
        } catch (Exception e) {
            bfh.a("VuiTravelUtils", "fetch grey-scale switcher for etrip fail! ".concat(String.valueOf(e)));
            return false;
        }
    }

    private static boolean d() {
        String a = lo.a().a((String) "motorcycle");
        if (TextUtils.isEmpty(a)) {
            return false;
        }
        try {
            if (new JSONObject(a).optInt("motor_tab") == 1) {
                return true;
            }
            return false;
        } catch (Exception e) {
            bfh.a("VuiTravelUtils", "fetch grey-scale switcher for motor fail! ".concat(String.valueOf(e)));
            return false;
        }
    }

    private static boolean e() {
        String a = lo.a().a((String) DIYMainMapPresenter.DIY_MAIN_MAP_CONFIG_MODULE_NAME);
        return !TextUtils.isEmpty(a) && a.contains("\"cab\"");
    }

    public static int a(String str) {
        int i;
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        try {
            i = new JSONObject(str).optInt("token_id");
        } catch (Exception e) {
            e.printStackTrace();
            i = -1;
        }
        return i;
    }
}
