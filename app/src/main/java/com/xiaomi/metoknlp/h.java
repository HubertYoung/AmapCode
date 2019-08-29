package com.xiaomi.metoknlp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

class h extends Handler {
    final /* synthetic */ a a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public h(a aVar, Looper looper) {
        // this.a = aVar;
        super(looper);
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            case 101:
                this.a.g();
                return;
            case 102:
                b.a().d();
                return;
            default:
                return;
        }
    }
}
