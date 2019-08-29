package defpackage;

import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.bundle.searchhome.ajx.ModuleSearchHome;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cbh reason: default package */
/* compiled from: SearchHomePageData */
public final class cbh {
    private JSONObject a;
    private String b;
    private String c;
    private String d;

    public cbh(String str) {
        this.d = str;
        try {
            this.a = new JSONObject(lo.a().a((String) ModuleSearchHome.MODULE_NAME));
            this.a.remove("type");
            this.a.put(BehavorReporter.PROVIDE_BY_LOCAL, 0);
        } catch (Exception unused) {
            this.a = new JSONObject();
            try {
                this.a.put(BehavorReporter.PROVIDE_BY_LOCAL, 0);
            } catch (JSONException unused2) {
            }
        }
        MapSharePreference mapSharePreference = new MapSharePreference((String) "lu_ban_hot_word");
        this.b = mapSharePreference.getStringValue("Lu_ban_hot_word_hint_cache", "");
        this.c = mapSharePreference.getStringValue("Lu_ban_hot_word_hint_color_cache", "");
    }

    public final String a() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (this.a != null) {
                jSONObject.put("aocs", this.a);
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("hint", this.b);
            jSONObject2.put("color", this.c);
            jSONObject.put("luban", jSONObject2);
            jSONObject.put(TrafficUtil.KEYWORD, this.d);
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }
}
