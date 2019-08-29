package defpackage;

import com.amap.bundle.statistics.LogManager;
import com.autonavi.core.network.inter.response.ResponseException;
import com.autonavi.core.network.inter.response.StringResponse;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: vs reason: default package */
/* compiled from: ImpressionReporterCallback */
final class vs implements bpl<StringResponse> {
    private int a;

    public final /* synthetic */ void onSuccess(bpk bpk) {
        a(1, ((StringResponse) bpk).getRequest().getUrl());
    }

    vs(int i) {
        this.a = i;
    }

    public final void onFailure(bph bph, ResponseException responseException) {
        a(2, bph.getUrl());
    }

    private void a(int i, String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", this.a);
            jSONObject.put("status", i);
            jSONObject.put("url", str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00001", "B278", jSONObject);
    }
}
