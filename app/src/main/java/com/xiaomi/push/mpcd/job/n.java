package com.xiaomi.push.mpcd.job;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.metoknlp.devicediscover.a;

class n implements a {
    final /* synthetic */ m a;

    n(m mVar) {
        this.a = mVar;
    }

    public void a(String str) {
        this.a.b = m.b(str);
        synchronized (this.a.a) {
            try {
                this.a.a.notify();
            } catch (Exception e) {
                b.a((Throwable) e);
            }
        }
    }
}
