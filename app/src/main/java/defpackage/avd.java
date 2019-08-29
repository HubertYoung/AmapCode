package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.alipay.mobile.tinyappcustom.h5plugin.H5ContactPlugin;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.huawei.android.pushagent.PushReceiver.KEY_TYPE;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: avd reason: default package */
/* compiled from: FeedbackAjxDataUtils */
public final class avd {
    public static PageBundle a(@NonNull POI poi, @Nullable String str, int i) {
        String str2;
        PageBundle pageBundle = new PageBundle();
        if (i == 1) {
            pageBundle.putString("url", "path://amap_bundle_feedback/src/poi/add/FeedbackLocationAdd.page.js");
        } else {
            pageBundle.putString("url", "path://amap_bundle_feedback/src/poi/entry/MainPage.page.js");
        }
        JSONObject b = bnx.b(poi);
        if (b == null) {
            b = new JSONObject();
        }
        try {
            b.put("mapScreenShot", str);
            b.put(KEY_TYPE.PLUGINREPORTTYPE, i);
            b.put(H5ContactPlugin.CONTACT, a());
            str2 = b.toString();
        } catch (JSONException unused) {
            str2 = null;
        }
        if (str2 == null) {
            str2 = "";
        }
        pageBundle.putObject("jsData", str2);
        return pageBundle;
    }

    public static PageBundle a(int i, String str, String str2, String str3, String str4, String str5, String str6, int i2, int i3, boolean z, Object obj) {
        String str7;
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_basemap_feedback/src/newpoi_feedback/NewPoiFeedback.page.js");
        JSONObject b = bnx.b(null);
        if (b == null) {
            b = new JSONObject();
        }
        try {
            b.put("sourcePage", i);
            b.put("mapScreenShot", str);
            b.put(KEY_TYPE.PLUGINREPORTTYPE, -1);
            b.put(H5ContactPlugin.CONTACT, a());
            b.put(AutoJsonUtils.JSON_ADCODE, str2);
            b.put("lineid", str3);
            b.put("linename", str4);
            b.put("stationid", str5);
            b.put("stationname", str6);
            b.put("stationx", i2);
            b.put("stationy", i3);
            b.put("isRealTime", z);
            b.put("allStations", obj);
        } catch (JSONException unused) {
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", 1);
            jSONObject.put("subType", -1);
            jSONObject.put("data", b);
            str7 = jSONObject.toString();
        } catch (JSONException unused2) {
            str7 = null;
        }
        if (str7 == null) {
            str7 = "";
        }
        pageBundle.putObject("jsData", str7);
        return pageBundle;
    }

    public static JSONObject a(String str, String str2, String str3, String str4, int i, int i2, boolean z, Object obj) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("lineid", str);
            jSONObject.put("linename", str2);
            jSONObject.put("stationid", str3);
            jSONObject.put("stationname", str4);
            jSONObject.put("stationx", i);
            jSONObject.put("stationy", i2);
            jSONObject.put("isRealTime", z);
            jSONObject.put("allStations", obj);
        } catch (JSONException unused) {
        }
        return jSONObject;
    }

    public static String a() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        String str = e.h;
        return str != null ? str : "";
    }
}
