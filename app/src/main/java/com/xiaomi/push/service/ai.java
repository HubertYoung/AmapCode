package com.xiaomi.push.service;

import android.app.NotificationManager;
import com.xiaomi.channel.commonutils.misc.h.a;

final class ai extends a {
    final /* synthetic */ int a;
    final /* synthetic */ NotificationManager b;

    ai(int i, NotificationManager notificationManager) {
        this.a = i;
        this.b = notificationManager;
    }

    public final int a() {
        return this.a;
    }

    public final void run() {
        this.b.cancel(this.a);
    }
}
