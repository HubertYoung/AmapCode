package com.alipay.mobile.android.verify.sdk.b;

import com.alipay.mobile.android.verify.bridge.BusProvider;
import com.alipay.mobile.security.zim.api.ZIMResponse;

/* compiled from: ZolozPlugin */
class f implements Runnable {
    final /* synthetic */ ZIMResponse a;
    final /* synthetic */ e b;

    f(e eVar, ZIMResponse zIMResponse) {
        this.b = eVar;
        this.a = zIMResponse;
    }

    public void run() {
        if (this.a != null) {
            this.b.a.data.put((String) "code", (Object) Integer.valueOf(this.a.code));
            this.b.a.data.put((String) "reason", (Object) this.a.reason);
            this.b.a.data.put((String) "bizData", (Object) this.a.bizData);
            this.b.a.data.put((String) "extInfo", (Object) this.a.extInfo);
        } else {
            this.b.a.data.put((String) "success", (Object) Boolean.FALSE);
            this.b.a.data.put((String) "errorMessage", (Object) "核身失败");
        }
        BusProvider.getInstance().post(this.b.a);
    }
}
