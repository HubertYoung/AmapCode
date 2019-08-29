package com.xiaomi.mipush.sdk;

import com.xiaomi.mipush.sdk.MiTinyDataClient.a.C0079a;

class z implements Runnable {
    final /* synthetic */ C0079a a;

    z(C0079a aVar) {
        this.a = aVar;
    }

    public void run() {
        if (this.a.a.size() != 0) {
            this.a.b();
            return;
        }
        if (this.a.d != null) {
            this.a.d.cancel(false);
            this.a.d = null;
        }
    }
}
