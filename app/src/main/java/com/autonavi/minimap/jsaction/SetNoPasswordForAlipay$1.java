package com.autonavi.minimap.jsaction;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.common.Callback;
import org.json.JSONObject;

public class SetNoPasswordForAlipay$1 implements Callback<JSONObject> {
    final /* synthetic */ JsAdapter a;
    final /* synthetic */ wa b;
    final /* synthetic */ dnf c;

    public SetNoPasswordForAlipay$1(dnf dnf, JsAdapter jsAdapter, wa waVar) {
        this.c = dnf;
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
