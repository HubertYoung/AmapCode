package defpackage;

import android.text.TextUtils;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;

/* renamed from: awz reason: default package */
/* compiled from: RouteServiceCloudUtil */
public final class awz {
    private static awz f;
    private final String a = "plan_home_car";
    private final String b = "plan_home_bus";
    private final String c = "plan_home_walking";
    private final String d = "plan_home_riding";
    private final String e = "plan_home_truck";

    private awz() {
    }

    public static awz a() {
        if (f == null) {
            f = new awz();
        }
        return f;
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (TextUtils.equals(str, "car")) {
            return "plan_home_car";
        }
        if (TextUtils.equals(str, ShowRouteActionProcessor.SEARCH_TYPE_BUS)) {
            return "plan_home_bus";
        }
        if (TextUtils.equals(str, "foot")) {
            return "plan_home_walking";
        }
        if (TextUtils.equals(str, ShowRouteActionProcessor.SEARCH_TYPE_RIDE)) {
            return "plan_home_riding";
        }
        return TextUtils.equals(str, DriveUtil.NAVI_TYPE_TRUCK) ? "plan_home_truck" : "";
    }
}
