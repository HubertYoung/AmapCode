package com.alipay.mobile.android.verify.sdk;

import android.app.Activity;
import com.alipay.mobile.android.verify.logger.Logger;
import com.alipay.mobile.android.verify.sdk.a.a;
import com.alipay.mobile.android.verify.sdk.interfaces.ICallback;
import java.util.HashMap;

/* compiled from: ServiceImpl */
class d implements Runnable {
    final /* synthetic */ Activity a;
    final /* synthetic */ String b;
    final /* synthetic */ ICallback c;
    final /* synthetic */ c d;

    d(c cVar, Activity activity, String str, ICallback iCallback) {
        this.d = cVar;
        this.a = activity;
        this.b = str;
        this.c = iCallback;
    }

    public void run() {
        try {
            this.d.b = new b(this.a, this.b, this.c);
            this.d.b.show();
        } catch (Exception e) {
            Logger.t("ServiceImpl").e(e, "start container got error", new Object[0]);
            a.b("zmCallback");
            a.a((String) null);
            this.c.onResponse(new HashMap());
        }
    }
}
