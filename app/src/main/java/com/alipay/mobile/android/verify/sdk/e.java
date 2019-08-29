package com.alipay.mobile.android.verify.sdk;

import android.app.Activity;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.android.verify.logger.Logger;
import com.alipay.mobile.android.verify.sdk.a.a;
import com.alipay.mobile.android.verify.sdk.interfaces.ICallback;
import java.util.HashMap;

/* compiled from: ServiceImpl */
class e implements Runnable {
    final /* synthetic */ Activity a;
    final /* synthetic */ JSONObject b;
    final /* synthetic */ ICallback c;
    final /* synthetic */ c d;

    e(c cVar, Activity activity, JSONObject jSONObject, ICallback iCallback) {
        this.d = cVar;
        this.a = activity;
        this.b = jSONObject;
        this.c = iCallback;
    }

    public void run() {
        try {
            this.d.b = new b(this.a, this.b.getString("url"), this.c);
            this.d.b.a(this.b);
            this.d.b.show();
        } catch (Exception e) {
            Logger.t("ServiceImpl").e(e, "start container got error", new Object[0]);
            a.b("zmCallback");
            a.a((String) null);
            this.c.onResponse(new HashMap());
        }
    }
}
