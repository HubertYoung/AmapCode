package com.xiaomi.metoknlp.devicediscover;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

class o extends Handler {
    final /* synthetic */ g a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public o(g gVar, Looper looper) {
        // this.a = gVar;
        super(looper);
    }

    public void handleMessage(Message message) {
        boolean z = false;
        switch (message.what) {
            case 1:
                this.a.a(false);
                return;
            case 2:
                if (message.obj != null) {
                    z = ((Boolean) message.obj).booleanValue();
                }
                this.a.b(z);
                return;
            default:
                return;
        }
    }
}
