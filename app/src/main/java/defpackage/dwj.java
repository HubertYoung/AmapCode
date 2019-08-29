package defpackage;

import com.amap.bundle.statistics.LogManager;
import org.json.JSONObject;

/* renamed from: dwj reason: default package */
/* compiled from: RouteBusDetailLogUtil */
public final class dwj {
    public static void a(String str, JSONObject jSONObject) {
        eao.d("Bus-Remind", "pageid=P00019,nodeid=".concat(String.valueOf(str)));
        if (jSONObject == null) {
            LogManager.actionLogV2("P00019", str);
        } else {
            LogManager.actionLogV2("P00019", str, jSONObject);
        }
    }

    public static void a(String str, String str2, long j, long j2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("startJumpPage", j);
            jSONObject.put("endPageLoad", j2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2(str, str2, jSONObject);
    }
}
