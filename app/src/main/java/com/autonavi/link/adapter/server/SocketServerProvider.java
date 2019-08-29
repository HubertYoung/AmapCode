package com.autonavi.link.adapter.server;

import android.content.Context;
import com.autonavi.link.adapter.server.d.a;
import com.autonavi.link.adapter.server.listener.OnWifiConnectedListener;
import com.autonavi.link.adapter.server.listener.OnWifiDataReceivedListener;

public class SocketServerProvider {
    private static volatile SocketServerProvider a;
    private a b;
    private boolean c = false;

    private SocketServerProvider() {
    }

    public static SocketServerProvider getInstance() {
        if (a == null) {
            synchronized (SocketServerProvider.class) {
                a = new SocketServerProvider();
            }
        }
        return a;
    }

    public void init(Context context, int i, int i2) {
        if (!this.c) {
            this.c = true;
            this.b = new a(context);
        }
    }

    public void unInit() {
        if (this.c) {
            this.b.a();
            this.c = false;
        }
    }

    public boolean isInit() {
        return this.c;
    }

    public void setOnWifiListerner(OnWifiConnectedListener onWifiConnectedListener) {
        if (this.b != null) {
            this.b.a(onWifiConnectedListener);
        }
    }

    public void setOnWifiDataReceivedListener(OnWifiDataReceivedListener onWifiDataReceivedListener) {
        if (this.b != null) {
            this.b.a(onWifiDataReceivedListener);
        }
    }

    public void sendDataRequest(byte[] bArr) {
        if (this.b != null) {
            this.b.a(bArr);
        }
    }
}
