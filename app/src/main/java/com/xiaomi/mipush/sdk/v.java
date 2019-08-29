package com.xiaomi.mipush.sdk;

import com.xiaomi.push.mpcd.d;

final class v implements Runnable {
    v() {
    }

    public final void run() {
        d.a(MiPushClient.sContext);
    }
}
