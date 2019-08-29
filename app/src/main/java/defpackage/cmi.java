package defpackage;

import com.amap.bundle.statistics.LogManager;
import org.json.JSONObject;

/* renamed from: cmi reason: default package */
/* compiled from: StatisticDelegate */
class cmi implements k {
    cmi() {
    }

    public final String a(String str) {
        return new afo(5, str).a();
    }

    public final void a(String str, String str2, JSONObject jSONObject) {
        LogManager.actionLogV2(str, str2, jSONObject);
    }

    public final void a(String str, long j, JSONObject jSONObject) {
        LogManager.actionLog(2000, str, j, jSONObject);
    }
}
