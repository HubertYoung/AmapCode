package com.autonavi.minimap.photograph.jsaction;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.common.Callback;
import org.json.JSONObject;

public class AddPhotoAction$1 implements Callback<JSONObject> {
    final /* synthetic */ JsAdapter a;
    final /* synthetic */ wa b;
    final /* synthetic */ dtl c;

    public AddPhotoAction$1(dtl dtl, JsAdapter jsAdapter, wa waVar) {
        this.c = dtl;
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
