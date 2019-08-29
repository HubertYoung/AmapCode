package com.xiaomi.mipush.sdk;

import android.content.Context;

final class u implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;

    u(Context context, String str, String str2) {
        this.a = context;
        this.b = str;
        this.c = str2;
    }

    public final void run() {
        MiPushClient.initialize(this.a, this.b, this.c, null);
    }
}
