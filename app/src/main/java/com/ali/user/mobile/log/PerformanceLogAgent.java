package com.ali.user.mobile.log;

import android.os.SystemClock;
import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class PerformanceLogAgent {
    private long a = 0;
    private boolean b = false;

    public final void a() {
        this.a = SystemClock.elapsedRealtime();
        this.b = true;
    }

    public final void a(boolean z) {
        this.b = z;
    }

    public final void b() {
        if (this.b) {
            this.b = false;
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.a;
            if (elapsedRealtime > 0) {
                LoggerFactory.c().a("MainLinkRecord", "LINK_LOGIN", Long.valueOf(-1), "PHASE_LINK_LOGIN_RPC=".concat(String.valueOf(elapsedRealtime)));
            }
        }
    }
}
