package com.xiaomi.metoknlp.devicediscover;

import java.net.InetAddress;

class e implements Runnable {
    final /* synthetic */ m a;
    private String b;

    public e(m mVar, String str) {
        this.a = mVar;
        this.b = str;
    }

    public void run() {
        try {
            InetAddress.getByName(this.b).isReachable(500);
        } catch (Exception unused) {
        }
    }
}
