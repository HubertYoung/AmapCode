package defpackage;

import android.text.TextUtils;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.routecommute.drive.page.AjxDriveCommutePage;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.Ajx3Page;
import org.json.JSONException;
import org.json.JSONObject;

@BundleInterface(axw.class)
/* renamed from: bap reason: default package */
/* compiled from: StartPageCommuteImpl */
public class bap implements axw {
    public final void a(bid bid, String str) {
        if (!TextUtils.isEmpty(str)) {
            Ajx.sStartTime = System.currentTimeMillis();
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", AjxDriveCommutePage.AJX_PAGE_URL);
            Boolean bool = Boolean.TRUE;
            StringBuilder sb = new StringBuilder();
            sb.append("\"");
            sb.append("is_enter_by_user_click");
            sb.append("\":");
            sb.append(bool);
            sb.append(",");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("{");
            sb2.append(sb);
            sb2.append(str, str.indexOf("{") + 1, str.length());
            pageBundle.putObject("jsData", sb2.toString());
            pageBundle.putLong("startTime", System.currentTimeMillis());
            pageBundle.putString("env", "path://amap_bundle_routecommute/src/drive_commute/DriveCommutePreload.js");
            bid.startPageForResult(AjxDriveCommutePage.class, pageBundle, 99);
        }
    }

    public final void b(bid bid, String str) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_routecommute/src/bus_commute/pages/CommuteSetting.page.js");
        JSONObject jSONObject = new JSONObject();
        try {
            if (TextUtils.isEmpty(str)) {
                str = "others";
            }
            jSONObject.put("pageflag", str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pageBundle.putString("jsData", jSONObject.toString());
        bid.startPage(Ajx3Page.class, pageBundle);
    }

    public final boolean a() {
        Object a = bfi.a();
        if (a == null) {
            return false;
        }
        long j = 0;
        if (a instanceof bgm) {
            j = ((bgm) a).getScenesID();
        } else if (a instanceof bfk) {
            j = ((bfk) a).h();
        }
        if (j == 9007199254740992L) {
            return true;
        }
        return false;
    }
}
