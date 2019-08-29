package com.alipay.mobile.tinyappcommon.ws;

import com.alipay.android.phone.mobilesdk.socketcraft.api.DefaultWebSocketClient;

/* compiled from: WebSocketSession */
public final class e {
    public DefaultWebSocketClient a;

    public final void a() {
        if (this.a != null) {
            this.a.close();
        }
    }

    public final boolean b() {
        if (this.a != null) {
            return this.a.isOpen();
        }
        return false;
    }
}
