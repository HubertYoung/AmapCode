package com.autonavi.minimap.bundle.activities.jsaction;

import com.autonavi.common.Callback;
import org.json.JSONException;
import org.json.JSONObject;

public class FetchActivityAction$1 implements Callback<ctm> {
    final /* synthetic */ String a;
    final /* synthetic */ wa b;
    final /* synthetic */ cto c;

    public void error(Throwable th, boolean z) {
    }

    public FetchActivityAction$1(cto cto, String str, wa waVar) {
        this.c = cto;
        this.a = str;
        this.b = waVar;
    }

    public void callback(ctm ctm) {
        int i = ctm.a;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("result", i);
            jSONObject.put("_action", this.a);
            if (i == 1) {
                jSONObject.put("scheme", ctm.c);
                this.c.a().callJs(this.b.a, jSONObject.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
