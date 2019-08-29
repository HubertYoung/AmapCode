package com.alipay.mobile.antui.basic;

import com.alipay.mobile.antui.utils.AuiLogger;
import org.json.JSONObject;

/* compiled from: AUNetErrorView */
final class aa implements Runnable {
    final /* synthetic */ JSONObject a;
    final /* synthetic */ AUNetErrorView b;

    aa(AUNetErrorView this$0, JSONObject jSONObject) {
        this.b = this$0;
        this.a = jSONObject;
    }

    public final void run() {
        AuiLogger.debug("AUNetErrorView", "load lottie");
        this.b.loadLottie(this.a);
    }
}
