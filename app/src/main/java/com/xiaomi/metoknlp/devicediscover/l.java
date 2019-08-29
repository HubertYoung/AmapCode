package com.xiaomi.metoknlp.devicediscover;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class l extends BroadcastReceiver {
    final /* synthetic */ g a;

    l(g gVar) {
        this.a = gVar;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            if (this.a.h.hasMessages(1)) {
                this.a.h.removeMessages(1);
            }
            this.a.h.sendMessageDelayed(this.a.h.obtainMessage(1), 10000);
        }
    }
}
