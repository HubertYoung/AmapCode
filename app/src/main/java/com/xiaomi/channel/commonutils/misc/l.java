package com.xiaomi.channel.commonutils.misc;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.xiaomi.channel.commonutils.misc.k.b;

class l extends Handler {
    final /* synthetic */ k a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    l(k kVar, Looper looper) {
        // this.a = kVar;
        super(looper);
    }

    public void handleMessage(Message message) {
        b bVar = (b) message.obj;
        if (message.what == 0) {
            bVar.a();
        } else if (message.what == 1) {
            bVar.c();
        }
        super.handleMessage(message);
    }
}
