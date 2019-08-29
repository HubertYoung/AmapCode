package com.autonavi.common.js.action;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.common.Callback;
import org.json.JSONObject;

public class GetInfoForShareBike$1 implements Callback<JSONObject> {
    final /* synthetic */ JsAdapter a;
    final /* synthetic */ wa b;
    final /* synthetic */ bky c;

    public GetInfoForShareBike$1(bky bky, JsAdapter jsAdapter, wa waVar) {
        this.c = bky;
        this.a = jsAdapter;
        this.b = waVar;
    }

    public void callback(JSONObject jSONObject) {
        this.a.callJs(this.b.a, jSONObject.toString());
    }

    public void error(Throwable th, boolean z) {
        th.printStackTrace();
    }
}
