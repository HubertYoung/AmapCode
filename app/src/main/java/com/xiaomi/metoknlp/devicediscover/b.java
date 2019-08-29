package com.xiaomi.metoknlp.devicediscover;

import android.os.Handler;
import android.os.Message;
import java.util.HashMap;

class b extends Handler {
    final /* synthetic */ n a;

    b(n nVar) {
        this.a = nVar;
    }

    public void handleMessage(Message message) {
        n nVar;
        synchronized (this.a.b) {
            int i = message.what;
            if (i != 3) {
                switch (i) {
                    case 0:
                        this.a.a((HashMap) message.obj);
                        break;
                    case 1:
                        this.a.d = 0;
                        if (this.a.g != null) {
                            this.a.g.cancel(true);
                        }
                        nVar = this.a;
                        break;
                }
            } else {
                if (message.obj != null) {
                    String str = (String) message.obj;
                    if (this.a.e != null) {
                        this.a.e.d(str);
                    }
                }
                nVar = this.a;
            }
            nVar.c();
        }
    }
}
