package com.huawei.android.pushselfshow.richpush.html.api;

import com.huawei.android.pushagent.a.a.c;

class a implements Runnable {
    final /* synthetic */ OnlineEventsBridgeMode a;

    a(OnlineEventsBridgeMode onlineEventsBridgeMode) {
        this.a = onlineEventsBridgeMode;
    }

    public void run() {
        boolean a2 = NativeToJsMessageQueue.this.d();
        c.a("PushSelfShowLog", "bEmptyMsg is ".concat(String.valueOf(a2)));
        if (!a2) {
            this.a.a = !this.a.a;
            NativeToJsMessageQueue.this.a.setNetworkAvailable(this.a.a);
            StringBuilder sb = new StringBuilder("setNetworkAvailable ： ");
            sb.append(this.a.a);
            c.a("PushSelfShowLog", sb.toString());
        }
    }
}
