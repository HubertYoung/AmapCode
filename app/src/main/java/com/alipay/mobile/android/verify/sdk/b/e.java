package com.alipay.mobile.android.verify.sdk.b;

import com.alipay.mobile.android.verify.bridge.protocol.BridgeEvent;
import com.alipay.mobile.android.verify.sdk.a.a;
import com.alipay.mobile.android.verify.sdk.f;
import com.alipay.mobile.security.zim.api.ZIMCallback;
import com.alipay.mobile.security.zim.api.ZIMResponse;

/* compiled from: ZolozPlugin */
class e implements ZIMCallback {
    final /* synthetic */ BridgeEvent a;
    final /* synthetic */ d b;

    e(d dVar, BridgeEvent bridgeEvent) {
        this.b = dVar;
        this.a = bridgeEvent;
    }

    public boolean response(ZIMResponse zIMResponse) {
        f.a(new f(this, zIMResponse));
        a.b("zolozCallback");
        return true;
    }
}
