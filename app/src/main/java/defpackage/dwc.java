package defpackage;

import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import org.json.JSONObject;

/* renamed from: dwc reason: default package */
/* compiled from: RealTimeBusLogManager */
public final class dwc {
    public static void a(int i) {
        JSONObject jSONObject = new JSONObject();
        boolean a = bmn.a();
        try {
            jSONObject.put("type", i);
            jSONObject.put("status", a ? ModulePoi.TIPS : "main");
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00367", "B001", jSONObject);
    }

    public static void b(int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00367", "B007", jSONObject);
    }

    public static void a(boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", z ? "focus" : "cancel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00367", "B011", jSONObject);
    }
}
