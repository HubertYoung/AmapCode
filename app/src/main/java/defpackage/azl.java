package defpackage;

import android.os.Environment;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.util.LocalStorageHelper;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: azl reason: default package */
/* compiled from: RouteCommuteUtil */
public final class azl {
    public static POI a() {
        com com2 = (com) ank.a(com.class);
        if (com2 != null) {
            cop b = com2.b(com2.a());
            if (b != null) {
                return b.c();
            }
        }
        return null;
    }

    public static POI b() {
        com com2 = (com) ank.a(com.class);
        if (com2 != null) {
            cop b = com2.b(com2.a());
            if (b != null) {
                return b.d();
            }
        }
        return null;
    }

    public static String c() {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        StringBuilder sb = new StringBuilder("autonavi");
        sb.append(File.separator);
        sb.append("routecommute");
        sb.append(File.separator);
        sb.append("userguide");
        sb.append(File.separator);
        return new File(externalStorageDirectory, sb.toString()).getAbsolutePath();
    }

    public static boolean d() {
        String c = c();
        return new File(c, "bus_normal_bg.png").exists() && new File(c, "bus_normal_fg.png").exists() && new File(c, "bus_realtime.png").exists() && new File(c, "car_first.png").exists() && new File(c, "car_second.png").exists();
    }

    public static boolean e() {
        return Boolean.parseBoolean(new LocalStorageHelper(AMapAppGlobal.getApplication(), "route_commute").getItem("enter_user_guide", "false"));
    }

    public static void a(String str, int i) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("from", str);
            jSONObject.put("addrType", i == 11 ? "company" : "home");
            PageBundle pageBundle = new PageBundle();
            if (e()) {
                pageBundle.putString("url", "path://amap_bundle_routecommute/src/guide/pages/CommuteAddrSettingPage.page.js");
            } else {
                JSONObject jSONObject2 = new JSONObject();
                String c = c();
                StringBuilder sb = new StringBuilder();
                sb.append(c);
                sb.append(File.separator);
                sb.append("bus_normal_bg.png");
                jSONObject2.put("bus_normal_bg", sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(c);
                sb2.append(File.separator);
                sb2.append("bus_normal_fg.png");
                jSONObject2.put("bus_normal_fg", sb2.toString());
                StringBuilder sb3 = new StringBuilder();
                sb3.append(c);
                sb3.append(File.separator);
                sb3.append("bus_realtime.png");
                jSONObject2.put("bus_realtime", sb3.toString());
                StringBuilder sb4 = new StringBuilder();
                sb4.append(c);
                sb4.append(File.separator);
                sb4.append("car_first.png");
                jSONObject2.put("car_first", sb4.toString());
                StringBuilder sb5 = new StringBuilder();
                sb5.append(c);
                sb5.append(File.separator);
                sb5.append("car_second.png");
                jSONObject2.put("car_second", sb5.toString());
                jSONObject.put("pictures", jSONObject2);
                pageBundle.putString("url", "path://amap_bundle_routecommute/src/guide/pages/CommuteGuidePage.page.js");
            }
            pageBundle.putString("jsData", jSONObject.toString());
            bui mVPActivityContext = AMapPageUtil.getMVPActivityContext();
            if (mVPActivityContext != null) {
                mVPActivityContext.a(Ajx3Page.class, pageBundle);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void a(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject();
            String str3 = (TextUtils.isEmpty(str) || Integer.parseInt(str) != 0) ? ShowRouteActionProcessor.SEARCH_TYPE_BUS : "drive";
            String str4 = (TextUtils.isEmpty(str2) || Integer.parseInt(str2) != 0) ? "company" : "home";
            jSONObject.put("from", str3);
            jSONObject.put("addrType", str4);
            PageBundle pageBundle = new PageBundle();
            if (e()) {
                pageBundle.putString("url", "path://amap_bundle_routecommute/src/guide/pages/CommuteAddrSettingPage.page.js");
            } else {
                pageBundle.putString("url", "path://amap_bundle_routecommute/src/guide/pages/CommuteGuideDownloadPage.page.js");
            }
            pageBundle.putString("jsData", jSONObject.toString());
            bui mVPActivityContext = AMapPageUtil.getMVPActivityContext();
            if (mVPActivityContext != null) {
                mVPActivityContext.a(Ajx3Page.class, pageBundle);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
        }
    }
}
