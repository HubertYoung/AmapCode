package com.xiaomi.push.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.xiaomi.channel.commonutils.logger.b;

class bm implements ServiceConnection {
    final /* synthetic */ XMPushService a;

    bm(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        b.b("onServiceConnected ".concat(String.valueOf(iBinder)));
        Service a2 = XMJobService.a();
        if (a2 != null) {
            this.a.startForeground(XMPushService.i, XMPushService.a((Context) this.a));
            a2.startForeground(XMPushService.i, XMPushService.a((Context) this.a));
            a2.stopForeground(true);
            this.a.unbindService(this);
            return;
        }
        b.a("XMService connected but innerService is null ".concat(String.valueOf(iBinder)));
    }

    public void onServiceDisconnected(ComponentName componentName) {
    }
}
