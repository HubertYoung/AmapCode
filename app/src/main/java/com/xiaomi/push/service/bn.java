package com.xiaomi.push.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

class bn extends Handler {
    final /* synthetic */ XMPushService a;

    bn(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (message != null) {
            try {
                switch (message.what) {
                    case 17:
                        if (message.obj != null) {
                            this.a.onStart((Intent) message.obj, XMPushService.c);
                            break;
                        }
                        break;
                    case 18:
                        Message obtain = Message.obtain(null, 0);
                        obtain.what = 18;
                        Bundle bundle = new Bundle();
                        bundle.putString("xmsf_region", this.a.f);
                        obtain.setData(bundle);
                        message.replyTo.send(obtain);
                        return;
                    default:
                        return;
                }
            } catch (Throwable unused) {
            }
        }
    }
}
